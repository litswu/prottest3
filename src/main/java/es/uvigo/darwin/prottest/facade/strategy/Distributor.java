/*
Copyright (C) 2009  Diego Darriba

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package es.uvigo.darwin.prottest.facade.strategy;

import java.util.Collections;

import mpi.MPI;
import mpi.Request;
import mpi.Status;
import es.uvigo.darwin.prottest.model.Model;
import es.uvigo.darwin.prottest.util.collection.ModelCollection;
import es.uvigo.darwin.prottest.util.comparator.ModelWeightComparator;
import es.uvigo.darwin.prottest.util.exception.ProtTestInternalException;

/**
 * This class distributes wrok load in an MPJ-Express environment.
 * Its behavior is inherently coupled to 
 * ImprovedDynamicDistributionStrategy's, providing the root process with
 * the possibility of distribute the models and also computing likelihood
 * values asynchronously.
 * 
 * @see ImprovedDynamicDistributionStrategy
 */
public class Distributor implements Runnable {

	/** MPJ Tag for requesting a new model. */
	private static final int TAG_SEND_REQUEST = 1;
	
	/** MPJ Tag for sending a new model. */
	private static final int TAG_SEND_MODEL = 2;

	/** MPJ Rank of the processor. */
	private int mpjMe;
	
	/** MPJ Size of the communicator. */
	private int mpjSize;

	/** The improved dynamic distribution strategy who calls this instance. */
	private ImprovedDynamicDistributionStrategy caller;
	
	/** The collection of all models to distribute. */
	private ModelCollection modelCollection;
	
	/** The heuristic algorithm to compare models. */
	private ModelWeightComparator comparator;
	
	/** The number of models per processor. It will be necessary
	 * by the root process to do the non-uniform gathering */
	private int[] itemsPerProc;
	
	/** The array of displacements after the distribution.
	 * It will be necessary by the root process to do the non-uniform gathering */
	private int[] displs;

/**
 * Gets the array of items per processor.
 * 
 * @return the array of items per processor
 */
public int[] getItemsPerProc() { return itemsPerProc; }
	
	/**
	 * Gets the array of displacements. The root process needs this attribute
	 * in order to do the non-uniform gathering.
	 * 
	 * @return the array of displacements
	 */
	public int[] getDispls() { return displs; }
	
	/**
	 * Instantiates a new distributor. The root process needs this attribute
	 * in order to do the non-uniform gathering.
	 * 
	 * @param caller the ImprovedDynamicDistributionStrategy instance which calls this constructor
	 * @param modelCollection the models to distribute amongst processors
	 * @param mpjMe the rank of the current process in MPJ
	 * @param mpjSize the size of the MPJ communicator
	 */
	public Distributor(ImprovedDynamicDistributionStrategy caller,
			ModelCollection modelCollection, ModelWeightComparator comparator,
			int mpjMe, int mpjSize) {
		this.comparator = comparator;
		this.modelCollection = modelCollection;
		this.mpjMe = mpjMe;
		this.mpjSize = mpjSize;
		this.caller = caller;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		distribute(modelCollection, comparator);
			
	}
	
	/**
	 * Distributes the whole model collection amongst the processors in
	 * the communicator. This method should be synchronized with the
	 * request method in the Improved Dynamic Distribution Strategy.
	 * 
	 * @see es.uvigo.darwin.prottest.facade.strategy.ImprovedDynamicDistributionStrategy#request()
	 * 
	 * @param modelSet the model collection to distribute amongst processors
	 * @param comparator implementation of the heuristic algorithm for sort and distribute models
	 */
	private void distribute(ModelCollection modelSet, ModelWeightComparator comparator) {

		itemsPerProc = new int[mpjSize];
		displs = new int[mpjSize];
		
		Collections.sort(modelSet, comparator);
		
		for (Model model : modelSet) {
			// check root processor
			//
			// This strategy is an easy way to avoid the problem of thread-safety
			// in MPJ-Express. It works correctly, but it also causes to introduce
			// coupling between this class and ImprovedDynamicDistributionStrategy,
			// having to define two public attributes: rootModelRequest and rootModel.
			//
			if (caller.rootModelRequest) {
				if (caller.rootModel != null) {
					caller.setCheckpoint(modelSet);
				}
				caller.rootModel = model;
				caller.rootModelRequest = false;
				itemsPerProc[mpjMe]++;
			} else {
				Model[] computedModel = new Model[1];
				// getModel request
				Request modelRequest = MPI.COMM_WORLD.Irecv(computedModel, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, TAG_SEND_REQUEST);
				// prepare model
				Model[] modelToSend = new Model[1];
				modelToSend[0] = model;
				// wait for request
				Status requestStatus = modelRequest.Wait();
				if (computedModel[0] != null) {
					// set checkpoint
					int index = modelSet.indexOf(computedModel[0]);
					modelSet.set(index, computedModel[0]);
					caller.setCheckpoint(modelSet);
				}
				// send model
				Request modelSend = MPI.COMM_WORLD.Isend(modelToSend, 0, 1, MPI.OBJECT, requestStatus.source, TAG_SEND_MODEL);
				// update structures
				itemsPerProc[requestStatus.source]++;
				// wait for send
				modelSend.Wait();
			}
		}
		displs[0] = 0;
		for (int i = 1; i < mpjSize; i++)
			displs[i] = displs[i-1] + itemsPerProc[i-1];

		// finalize
		for (int i = 1; i < mpjSize; i++) {
			Model[] computedModel = new Model[1];
			// getModel request
			Request modelRequest = MPI.COMM_WORLD.Irecv(computedModel, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, TAG_SEND_REQUEST);
			Model[] modelToSend = { null };
			// wait for request
			Status requestStatus = modelRequest.Wait();
			if (computedModel[0] != null) {
				// set checkpoint
				int index = modelSet.indexOf(computedModel[0]);
				modelSet.set(index, computedModel[0]);
				caller.setCheckpoint(modelSet);
			}
			// send null model
			Request modelSend = MPI.COMM_WORLD.Isend(modelToSend, 0, 1, MPI.OBJECT, requestStatus.source, TAG_SEND_MODEL);

			modelSend.Wait();
		}
		// check root
		while (!caller.rootModelRequest) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				throw new ProtTestInternalException("Thread interrupted");
			}
		}
		caller.rootModel = null;
		caller.rootModelRequest = false;
	}

}
