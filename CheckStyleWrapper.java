import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for easier checkstyle checks.
 * Heavily inspired by the original checkstyle code
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 * @version 1.00
 */
public class CheckStyleWrapper {

    /**
     * Users preferences.
     */
    private CheckStylePreferences currentPreferences;

    /**
     * List of files to check.
     */
    private List<File> filesToCheck;




    /**
     * Creates a new CheckStyleWrapper instance.
     * @param preferences   users preferences
     */
    public CheckStyleWrapper(CheckStylePreferences preferences) {
        currentPreferences = preferences;
        filesToCheck = new ArrayList<File>();
    }


    /**
     * Performs the checkstyle check process.
     * @return  a tree of CheckStyleTreeNode
     * @throws IOException                  if occurs
     * @throws SAXException                 if occurs
     * @throws ParserConfigurationException if occurs
     */
    public CheckStyleTreeNode runCheck() throws IOException, SAXException,
            ParserConfigurationException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        traverseFilesToCheck(new File(currentPreferences.getWorkingDir()));

        Checker checker = createChecker(bos);
        checker.process(filesToCheck);
        checker.destroy();

        return parseXml(new ByteArrayInputStream(bos.toByteArray()));
    }


    /**
     * Parses the XML generated by checkstyle.
     * @param byteInputStream       contains the xml data
     * @return                      a build CheckStyleTreeNode tree
     * @throws IOException                  if occurs
     * @throws SAXException                 if occurs
     * @throws ParserConfigurationException if occurs
     */
    private CheckStyleTreeNode parseXml(ByteArrayInputStream byteInputStream) throws
            IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        ParsingHandler handler = new ParsingHandler();

        SAXParser parser = factory.newSAXParser();
        parser.parse(byteInputStream, handler);

        return handler.getTree();
    }


    /**
     * Creates a list of files to check.
     * @param node      starting directory
     */
    private void traverseFilesToCheck(File node) {
        if (node.canRead()) {
            if (node.isDirectory()) {
                final File[] nodes = node.listFiles();
                assert nodes != null;
                for (File element : nodes) {
                    traverseFilesToCheck(element);
                }
            } else if (node.isFile()) {
                if (node.toString().endsWith(".java")) {
                    filesToCheck.add(node);
                }
            }
        }
    }


    /**
     * Creates a checkStyle checker instance.
     * @param os    the output stream to append xml data to
     * @return      the created checker
     */
    private Checker createChecker(OutputStream os) {
        Checker c = null;

        try {
            c = new Checker();

            final ClassLoader moduleClassLoader = Checker.class.getClassLoader();
            c.setModuleClassLoader(moduleClassLoader);
            c.configure(loadConfiguration());
            c.addListener(createListener(os));

        } catch (CheckstyleException e) {
            e.printStackTrace(System.err);
        }

        return c;
    }


    /**
     * Loads and creates a checkStyle configuration.
     * @return  configuration
     * @throws CheckstyleException  if occurs
     */
    private Configuration loadConfiguration() throws CheckstyleException {
        return ConfigurationLoader.loadConfiguration(
                currentPreferences.getCheckStyleConfig(),
                new PropertiesExpander(System.getProperties()));
    }


    /**
     * Creates a new AuditListener.
     * @param os    OutputStream listener is attached to
     * @return      the created listener
     */
    private AuditListener createListener(OutputStream os) {
        return new XMLLogger(os, true);
    }
}
