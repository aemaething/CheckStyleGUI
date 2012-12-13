import org.jdesktop.swingx.JXEditorPane;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Implements an intro panel, used as splash screen.
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 * @see <a href="http://alvinalexander.com/blog/post/jfc-swing/how-create-simple-swing-html-viewer-browser-java">
 *     How create swimg html browser java</a>
 * @see <a href="http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-JEditorPane.html">
 *     Implementing a simple Web Browser</a>
 * @version 1.00
 */
public class IntroPanel extends JPanel implements HyperlinkListener {

    /**
     * Content displayed in the JEditorPane.
     */
    private static final String CONTENT = ""
            + "<html>\n"
            + "    <body>\n"
            + "        <h1>CheckStyleGUI</h1>\n"
            + "        <h3>v 1.00</h3>\n"
            + "        <p>Using:</p>\n"
            + "        <ul>\n"
            + "            <li><a href=\"http://checkstyle.sourceforge.net/\">checkstyle</a></li>\n"
            + "            <li><a href=\"http://swingx.java.net/\">SwingX</a></li>\n"
            + "        </ul>\n"
            + "    </body>\n"
            + "</html>";

    /**
     * CSS rules added to the document.
     */
    private static final String[] CSS_RULES = {
        "body {background-color: #393939; font-family: "
            + "Helvetica, Verdana, Arial, sans-serif; margin: 20px}",
        "h1, h2, h3, p, a {color: #d38d40}", };





    /**
     * Creates the intro panel.
     */
    public IntroPanel() {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JEditorPane htmlPane = new JXEditorPane();
        HTMLEditorKit editorKit = new HTMLEditorKit();

        htmlPane.setEditable(false);
        htmlPane.setEditorKit(editorKit);
        htmlPane.addHyperlinkListener(this);

        StyleSheet styleSheet = editorKit.getStyleSheet();
        for (String rule : CSS_RULES) {
            styleSheet.addRule(rule);
        }

        Document doc = editorKit.createDefaultDocument();
        htmlPane.setDocument(doc);
        htmlPane.setText(CONTENT);

        JScrollPane scrollPane = new JScrollPane(htmlPane);
        add(scrollPane);
    }


    /**
     * Catches hyperlink events.
     * Opens users default browser to open URL
     * @param e     event raised
     */
    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(e.getURL().toURI());
            } catch (IOException e1) {
                popupErrorDialog(e1.getLocalizedMessage(), "IOException");
            } catch (URISyntaxException e1) {
                popupErrorDialog(e1.getLocalizedMessage(), "URISyntaxException");
            }
        }
    }


    /**
     * Used to display exception messages.
     * @param message   message to display
     * @param title     title to display
     */
    private void popupErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
}
