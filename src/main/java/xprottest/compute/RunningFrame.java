/*
 * RunningFrame.java
 *
 * Created on 1 de octubre de 2009, 19:43
 */

package xprottest.compute;

import es.uvigo.darwin.prottest.exe.ExternalExecutionManager;
import es.uvigo.darwin.prottest.global.options.ApplicationOptions;
import xprottest.*;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import xprottest.util.TextAreaAppender;
import es.uvigo.darwin.prottest.model.Model;
import es.uvigo.darwin.prottest.observer.ModelUpdaterObserver;
import es.uvigo.darwin.prottest.observer.ObservableModelUpdater;
import java.io.PrintWriter;

/**
 *
 * @author  diego
 */
public class RunningFrame extends javax.swing.JFrame 
    implements ModelUpdaterObserver {
    
    private XProtTestView mainFrame;
    private Task task;
    private int computedModels;
    private int numModels;
    private PrintWriter displayWriter;
    
    // var used to discard concurrent messages during
    // proccess cancel
    private boolean running;
    
    /** Creates new form RunningFrame */
    public RunningFrame(XProtTestView mainFrame, int numModels) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        
        this.mainFrame = mainFrame;
        this.numModels = numModels;
        this.displayWriter = new PrintWriter(new TextAreaAppender(computedTextArea));
        this.running = true;
        
        lblNumModels.setText(String.valueOf(numModels));
        lblExecutedModels.setText(String.valueOf(computedModels));
        runningProgress.setMaximum(numModels);
        runningProgress.setMinimum(0);
        runningProgress.setValue(computedModels);
    }
    
    public void setTask(Task task) {
        this.task = task;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRunning = new javax.swing.JLabel();
        runningProgress = new javax.swing.JProgressBar();
        cancelButton = new javax.swing.JButton();
        lblNumModels = new javax.swing.JLabel();
        lblExecutedModels = new javax.swing.JLabel();
        lblSeparator = new javax.swing.JLabel();
        computedScrollArea = new javax.swing.JScrollPane();
        computedTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(xprottest.XProtTestApp.class).getContext().getResourceMap(RunningFrame.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        lblRunning.setText(resourceMap.getString("lblRunning.text")); // NOI18N
        lblRunning.setName("lblRunning"); // NOI18N

        runningProgress.setName("runningProgress"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(xprottest.XProtTestApp.class).getContext().getActionMap(RunningFrame.class, this);
        cancelButton.setAction(actionMap.get("cancelExecution")); // NOI18N
        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        lblNumModels.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNumModels.setText(resourceMap.getString("lblNumModels.text")); // NOI18N
        lblNumModels.setName("lblNumModels"); // NOI18N

        lblExecutedModels.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblExecutedModels.setText(resourceMap.getString("lblExecutedModels.text")); // NOI18N
        lblExecutedModels.setName("lblExecutedModels"); // NOI18N

        lblSeparator.setText(resourceMap.getString("lblSeparator.text")); // NOI18N
        lblSeparator.setName("lblSeparator"); // NOI18N

        computedScrollArea.setName("computedScrollArea"); // NOI18N

        computedTextArea.setBackground(resourceMap.getColor("computedTextArea.background")); // NOI18N
        computedTextArea.setColumns(20);
        computedTextArea.setEditable(false);
        computedTextArea.setRows(15);
        computedTextArea.setName("computedTextArea"); // NOI18N
        computedScrollArea.setViewportView(computedTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(computedScrollArea, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(runningProgress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(lblRunning, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                        .addComponent(lblExecutedModels)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSeparator)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumModels, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRunning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runningProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(lblNumModels)
                    .addComponent(lblSeparator)
                    .addComponent(lblExecutedModels))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(computedScrollArea, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    @Action
    public void cancelExecution() {
        ExternalExecutionManager.getInstance().killProcesses();
        task.cancel(true);
        unload();
    }
    
    public void finishedExecution() {
        unload();
    }
    
    private void unload() {
        mainFrame.unloadRunningView(this);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane computedScrollArea;
    private javax.swing.JTextArea computedTextArea;
    private javax.swing.JLabel lblExecutedModels;
    private javax.swing.JLabel lblNumModels;
    private javax.swing.JLabel lblRunning;
    private javax.swing.JLabel lblSeparator;
    private javax.swing.JProgressBar runningProgress;
    // End of variables declaration//GEN-END:variables

    public void update(ObservableModelUpdater o, Model model, ApplicationOptions options) {
        if (running) {
            computedModels++;
            runningProgress.setValue(computedModels);
            lblExecutedModels.setText(String.valueOf(computedModels));
            if (model.isComputed())
                displayWriter.println("Computed " + model.getModelName() 
                        + "(" + model.getLk() + ")");
            else {
                // Follow error behavior
                if (mainFrame.getErrorBehavior() == XProtTestApp.ERROR_BEHAVIOR_CONTINUE)
                    displayWriter.println("There were errors computing " + model.getModelName() 
                            + " !!! ");
                else if (mainFrame.getErrorBehavior() == XProtTestApp.ERROR_BEHAVIOR_STOP) {
                    running = false;
                    mainFrame.computationInterrupted();
                    cancelExecution();
                }
                else {
                    running = false;
                    cancelExecution();
                    throw new RuntimeException("Unsupported error behavior : " + mainFrame.getErrorBehavior());
                }
            }
        }
    }
    
}
