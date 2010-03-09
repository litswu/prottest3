/*
 * TreeView.java
 *
 * Created on 6 de octubre de 2009, 18:04
 */

package xprottest.analysis;

import es.uvigo.darwin.prottest.facade.TreeFacade;
import es.uvigo.darwin.prottest.model.Model;
import java.awt.Font;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.application.Action;
import pal.tree.Tree;
import xprottest.util.TextAreaWriter;
/**
 *
 * @author  diego
 */
public class TreeView extends javax.swing.JFrame {
    
    private Tree tree;
    private List<Tree> trees;
    private TreeFacade facade;
    private PrintWriter displayWriter;
    
    /** Creates new form TreeView */
    public TreeView(TreeFacade facade, Tree tree, Model[] models) {
        this.tree = tree;
        this.facade = facade;
        this.trees = new ArrayList<Tree>(models.length);
        
        initComponents();
        displayWriter = new PrintWriter(new TextAreaWriter(displayArea));

        Font f = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        displayArea.setFont(f);
        
        if (tree != null)
            cmbTreeSelection.addItem(new TreeWrapper("Starting Topology", tree));
        if (models != null) {
            for (Model model : models) {
                if (model.getTree() != null) {
                    cmbTreeSelection.addItem(new TreeWrapper(model.getModelName(), model.getTree()));
                    trees.add(model.getTree());
                }
            }
        }
        if (cmbTreeSelection.getItemCount() > 0)
            cmbTreeSelection.setSelectedIndex(0);

        treeFormatSelection();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        treeDisplayTypeBtnGroup = new javax.swing.ButtonGroup();
        displayScroll = new javax.swing.JScrollPane();
        displayArea = new javax.swing.JTextArea();
        optionsPanel = new javax.swing.JPanel();
        cmbTreeSelection = new javax.swing.JComboBox();
        cmbTreeFormatSelection = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        windowMenu = new javax.swing.JMenu();
        closeMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        editCopyMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("xprottest/analysis/resources/TreeView"); // NOI18N
        setTitle(bundle.getString("title")); // NOI18N
        setName("Form"); // NOI18N

        displayScroll.setName("displayScroll"); // NOI18N

        displayArea.setColumns(20);
        displayArea.setEditable(false);
        displayArea.setRows(5);
        displayArea.setName("displayArea"); // NOI18N
        displayScroll.setViewportView(displayArea);

        optionsPanel.setName("optionsPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(xprottest.XProtTestApp.class).getContext().getResourceMap(TreeView.class);
        cmbTreeSelection.setToolTipText(resourceMap.getString("treeSelection.toolTipText")); // NOI18N
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(xprottest.XProtTestApp.class).getContext().getActionMap(TreeView.class, this);
        cmbTreeSelection.setAction(actionMap.get("treeFormatSelection")); // NOI18N
        cmbTreeSelection.setName("cmbTreeSelection"); // NOI18N

        cmbTreeFormatSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Newick Format", "ASCII Format" }));
        cmbTreeFormatSelection.setSelectedItem("ASCII Format");
        cmbTreeFormatSelection.setToolTipText(resourceMap.getString("treeFormatSelection.toolTipText")); // NOI18N
        cmbTreeFormatSelection.setAction(actionMap.get("treeFormatSelection")); // NOI18N
        cmbTreeFormatSelection.setName("cmbTreeFormatSelection"); // NOI18N

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbTreeSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192)
                .addComponent(cmbTreeFormatSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTreeSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTreeFormatSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        windowMenu.setText(resourceMap.getString("windowMenu.text")); // NOI18N
        windowMenu.setName("windowMenu"); // NOI18N

        closeMenuItem.setAction(actionMap.get("close")); // NOI18N
        closeMenuItem.setText(resourceMap.getString("menu-close")); // NOI18N
        closeMenuItem.setName("closeMenuItem"); // NOI18N
        windowMenu.add(closeMenuItem);

        jMenuBar1.add(windowMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        editCopyMenuItem.setAction(actionMap.get("editCopy")); // NOI18N
        editCopyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        editCopyMenuItem.setText(resourceMap.getString("menu-copy")); // NOI18N
        editCopyMenuItem.setName("editCopyMenuItem"); // NOI18N
        editMenu.add(editCopyMenuItem);

        jMenuItem1.setAction(actionMap.get("editSelectAll")); // NOI18N
        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText(resourceMap.getString("menu-selectAll")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        editMenu.add(jMenuItem1);

        jMenuBar1.add(editMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(displayScroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                    .addComponent(optionsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayAsciiTree(Tree tree) {
        displayArea.setText("");
        facade.displayASCIITree(tree, displayWriter);
    }

    private void displayNewickTree(Tree tree) {
        displayArea.setText("");
        facade.displayNewickTree(tree, displayWriter);
    }

    
    @Action
    public void treeFormatSelection() {
        Tree displayTree = ((TreeWrapper)cmbTreeSelection.getSelectedItem()).getTree();

        if (cmbTreeFormatSelection.getSelectedItem()
                .toString().toLowerCase().contains("newick"))
            displayNewickTree(displayTree);
        else if (cmbTreeFormatSelection.getSelectedItem()
                .toString().toLowerCase().contains("ascii"))
            displayAsciiTree(displayTree);
    }

    @Action
    public void editCopy() {
        displayArea.copy();
    }

    @Action
    public void editSelectAll() {
        displayArea.selectAll();
    }

    @Action
    public void close() {
        this.setVisible(false);
    }

//    @Action
//    public void buildConsensus() {
//        double threshold = Double.parseDouble(txtThreshold.getText());
//        
//        if (consensusTrees.containsKey(threshold)) {
//            cmbTreeSelection.setSelectedItem(consensusTrees.get(threshold));
//        } else {
//            Tree consensus = facade.createConsensusTree(trees, threshold);
//            TreeWrapper consensusWrapper = new TreeWrapper("Consensus " + threshold, consensus);
//            consensusTrees.put(threshold, consensusWrapper);
//            cmbTreeSelection.addItem(consensusWrapper);
//            cmbTreeSelection.setSelectedItem(consensusWrapper);
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JComboBox cmbTreeFormatSelection;
    private javax.swing.JComboBox cmbTreeSelection;
    private javax.swing.JTextArea displayArea;
    private javax.swing.JScrollPane displayScroll;
    private javax.swing.JMenuItem editCopyMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.ButtonGroup treeDisplayTypeBtnGroup;
    private javax.swing.JMenu windowMenu;
    // End of variables declaration//GEN-END:variables
    
}
