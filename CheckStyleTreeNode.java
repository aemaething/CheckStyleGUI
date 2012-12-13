import java.util.ArrayList;
import java.util.List;

/**
 * Implements a single node within the tree<br />
 * and the tree itself.
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 */
class CheckStyleTreeNode {

    /**
     * Name of the node.
     * Used for filename.
     */
    private String name;

    /**
     * Type of message.
     */
    private String type;

    /**
     * Column of the message.
     */
    private String column;

    /**
     * Line of the message.
     */
    private String line;

    /**
     * Message from CheckStyle.
     */
    private String message;

    /**
     * List of child nodes.
     */
    private List<CheckStyleTreeNode> children;




    /**
     * Creates a new node.
     * Initializes name only.
     * @param n     the new name of the node
     * @see #CheckStyleTreeNode(String, String, String, String, String)
     */
    public CheckStyleTreeNode(String n) {
        this(n, "", "", "", "");
    }

    /**
     * Create a new node.
     * @param n     name of the checkstyle node
     * @param t     type of the node
     * @param l     line, where the message appears from
     * @param c     column, where the message appears from
     * @param m     the message itself
     */
    public CheckStyleTreeNode(String n, String t, String l, String c, String m) {
        name = n;
        type = t;
        line = l;
        column = c;
        message = m;
        children = new ArrayList<CheckStyleTreeNode>();
    }


    /**
     * Getter of name.
     * @return  the name of the node
     */
    public String getName() {
        return name;
    }


    /**
     * Getter of type.
     * @return  the type of the node.
     */
    public String getType() {
        return type;
    }


    /**
     * Getter of column.
     * @return  column of the node
     */
    public String getColumn() {
        return column;
    }


    /**
     * Getter of line.
     * @return  line of the node
     */
    public String getLine() {
        return line;
    }


    /**
     * Getter of message.
     * @return  message of the node
     */
    public String getMessage() {
        return message;
    }


    /**
     * Getter of the list of children of the node.
     * @return  list of children
     */
    public List<CheckStyleTreeNode> getChildren() {
        return children;
    }


    /**
     * Adds a new child to the list of children.
     * @param newChild      new child to add
     */
    public void addChild(CheckStyleTreeNode newChild) {
        children.add(newChild);
    }


    /**
     * Recursive helper to build a string representation.
     * @param sb        string builder to append strings to
     * @param node      the node to build a string of
     * @param depth     current recursion depth
     */
    private void toStringHelper(StringBuilder sb, CheckStyleTreeNode node, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append("\t");
        }
        sb.append(node.getName()).append("\n");

        for (int i = 0; i < node.getChildren().size(); i++) {
            toStringHelper(sb, node.getChildren().get(i), depth + 1);
        }
    }


    /**
     * Creates a string representation of the current instance.
     * @return      string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(sb, this, 0);

        return sb.toString();
    }
}
