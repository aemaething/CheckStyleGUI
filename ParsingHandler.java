import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Used to parse the xml data.
 */
class ParsingHandler extends DefaultHandler {

    /**
     * Used to detect XML node file.
     */
    private static final String TOKEN_FILE = "file";

    /**
     * Used to detect XML node error.
     */
    private static final String TOKEN_ERROR = "error";

    /**
     * Used to fetch attribute <i>name</i>.
     */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * Used to fetch attribute <i>message</i>.
     */
    private static final String ATTRIBUTE_COLUMN = "column";

    /**
     * Used to fetch attribute <i>severity</i>.
     */
    private static final String ATTRIBUTE_SEVERITY = "severity";

    /**
     * Used to fetch attribute <i>line</i>.
     */
    private static final String ATTRIBUTE_LINE = "line";

    /**
     * Used to fetch attribute <i>message</i>.
     */
    private static final String ATTRIBUTE_MESSAGE = "message";

    /**
     * Used to build a new tree.
     */
    private CheckStyleTreeNode rootNode;

    /**
     * Current node to append leafs to.
     */
    private CheckStyleTreeNode currentNode;




    /**
     * Creates a new parser.
     */
    public ParsingHandler() {
        rootNode = new CheckStyleTreeNode("root");
    }


    /**
     * Getter of the new tree.
     * @return  the new tree, build by parsing run
     */
    public CheckStyleTreeNode getTree() {
        return rootNode;
    }


    /**
     * Called if a start element was found.
     * Used to build a new model tree
     * @param namespaceUri  namespace of the uri - not used
     * @param localName     local name of the doc-element
     * @param qualifiedName qualified name of the doc-element
     * @param attributes    attributes of the doc-element
     */
    @Override
    public void startElement(String namespaceUri, String localName,
                             String qualifiedName, Attributes attributes) {

        String elementName = "".equals(localName) ? qualifiedName : localName;

        if (elementName.equalsIgnoreCase(TOKEN_FILE)) {
            CheckStyleTreeNode newNode = new CheckStyleTreeNode(
                    attributes.getValue(ATTRIBUTE_NAME));
            rootNode.addChild(newNode);
        } else if (elementName.equalsIgnoreCase(TOKEN_ERROR)) {
            currentNode = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
            CheckStyleTreeNode newSubChild = new CheckStyleTreeNode(currentNode.getName(),
                    attributes.getValue(ATTRIBUTE_SEVERITY), attributes.getValue(ATTRIBUTE_LINE),
                    attributes.getValue(ATTRIBUTE_COLUMN), attributes.getValue(ATTRIBUTE_MESSAGE));
            currentNode.addChild(newSubChild);
        }
    }
}
