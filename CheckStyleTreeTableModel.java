import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import javax.swing.tree.TreePath;


/**
 * Implements a tree table model.
 * Heavily inspired by an example I found on the web,<br />
 * unfortunately I lost the URL.
 *
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 * @version 1.00
 */
class CheckStyleTreeTableModel extends AbstractTreeTableModel {

    /**
     * Contains the column names and also the number of columns.
     */
    private static final String[] COLUMNS = {"Name", "Type", "Line", "Col", "Message"};


    /**
     * Root node of the model.
     */
    private CheckStyleTreeNode rootNode;




    /**
     * Creates an new model with a new rootNode.
     */
    public CheckStyleTreeTableModel() {
        rootNode = new CheckStyleTreeNode("root");
    }


    /**
     * Returns the number of columns.
     * @return  number of columns
     */
    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }


    /**
     * Returns a specific column name by a given index.
     * @param column    the index of the column
     * @return          the name of the column
     */
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }


    /**
     * Returns a specific value of a node by a column index.
     * @param o     the node
     * @param i     the index
     * @return      the value
     */
    @Override
    public Object getValueAt(Object o, int i) {
        CheckStyleTreeNode treeNode = (CheckStyleTreeNode) o;

        switch (i) {
        case 0:
            return treeNode.getName();
        case 1:
            return treeNode.getType();
        case 2:
            return treeNode.getLine();
        case 3:
            return treeNode.getColumn();
        case 4:
            return treeNode.getMessage();
        default:
            return "unknown";
        }
    }


    /**
     * Returns a specific child of a parent node, determined by an index.
     * @param parent        the parent node
     * @param index         the child's index
     * @return              the child
     */
    @Override
    public Object getChild(Object parent, int index) {
        CheckStyleTreeNode treeNode = (CheckStyleTreeNode) parent;
        return treeNode.getChildren().get(index);
    }


    /**
     * Returns the number of children of a given node.
     * @param parent        the parent to count the children of
     * @return              the number of children
     */
    @Override
    public int getChildCount(Object parent) {
        CheckStyleTreeNode treeNode = (CheckStyleTreeNode) parent;
        return treeNode.getChildren().size();
    }


    /**
     * Returns the index of a child of a specific parent node.
     * @param parent        the parent node
     * @param child         the child node
     * @return              the index of the child node
     */
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        CheckStyleTreeNode treeNode = (CheckStyleTreeNode) parent;

        for (int i = 0; i < treeNode.getChildren().size(); i++) {
            if (treeNode.getChildren().get(i) == child) {
                return i;
            }
        }

        return 0;
    }


    /**
     * Checks if a given node is a leaf.
     * @param o     the node to check
     * @return      <i>true</i> if node is a leaf<br />
     *              <i>false</i> otherwise
     */
    @Override
    public boolean isLeaf(Object o) {
        CheckStyleTreeNode treeNode = (CheckStyleTreeNode) o;

        return treeNode.getChildren().size() == 0;
    }


    /**
     * Getter for the root node.
     * @return  the root node of the current model
     */
    @Override
    public Object getRoot() {
        return rootNode;
    }


    /**
     * Sets a new root of the current model.
     * @param o     new root object
     */
    public void setRoot(Object o) {
        rootNode = (CheckStyleTreeNode) o;

        TreePath nodePath = new TreePath(rootNode);
        modelSupport.fireTreeStructureChanged(nodePath);
    }


    /**
     * Sets the edit mode for a given node in a specific column.
     * Here, every node is read only
     * @param node      the node to check
     * @param col       the col to check
     * @return          always false
     */
    @Override
    public boolean isCellEditable(Object node, int col) {
        return false;
    }


    /**
     * Generates a string representation of current model data.
     * @return  the string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rootNode.getChildren().size(); i++) {
            CheckStyleTreeNode currentNode = rootNode.getChildren().get(i);
            sb.append(currentNode.getName()).append("\n");
            for (int j = 0; j < currentNode.getChildren().size(); j++) {
                CheckStyleTreeNode subNode = currentNode.getChildren().get(j);
                sb.append("\t").append(subNode.getName()).append("\n");
            }
        }

        return sb.toString();
    }
}
