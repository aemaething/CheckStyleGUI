import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;


/**
 * Implements a simple GUI for easier checkstyle handling.
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 * @version 1.00
 */
public class CheckStyleGUI extends JFrame implements ActionListener, Observer {

    /**
     * Title of the app.
     */
    private static final String FRAME_TITLE = "CheckStyle GUI";

    /**
     * Panel identifier.
     */
    private static final String PANEL_INTRO = "intro";

    /**
     * Panel identifier.
     */
    private static final String PANEL_CHECK_STYLE = "checkStyle";

    /**
     * Preferences container/wrapper.
     */
    private final CheckStylePreferences preferences;

    /**
     * Layout manager.
     */
    private CardLayout      layout;

    /**
     * Intro panel.
     */
    private IntroPanel      introPanel;

    /**
     * CheckStyle panel.
     */
    private CheckStylePanel checkStylePanel;

    /**
     * Run item of menu bar.
     */
    private JMenuItem runMenuItem;





    /**
     *  Creates the GUI, based on a JFrame.
     */
    public CheckStyleGUI() {
        super(FRAME_TITLE);
        preferences = new CheckStylePreferences(getClass().getName());

        if (null != preferences.getWorkingDir()) {
            setTitle(String.format("%s - %s", FRAME_TITLE, preferences.getWorkingDir()));
        }

        layout = new CardLayout();

        introPanel = new IntroPanel();
        checkStylePanel = new CheckStylePanel();
        introPanel = new IntroPanel();

        getContentPane().setLayout(layout);
        getContentPane().add(introPanel, PANEL_INTRO);
        getContentPane().add(checkStylePanel, PANEL_CHECK_STYLE);

        setJMenuBar(createMenuBar());


        addWindowListener(new WindowAdapter() {
            /**
             * Called, if window is closing.
             * @param e     not used
             */
            @Override
            public void windowClosing(WindowEvent e) {
                preferences.updatePreferences();
                System.exit(0);
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        setMinimumSize(new Dimension(screenSize.width / 4, screenSize.height / 4));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(true);
        pack();
    }


    /**
     * Used to create the menu bar.
     * @return  the created menu bar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);


        JMenu checkStyle = new JMenu("CheckStyle");
        checkStyle.setMnemonic('C');

        JMenuItem openItem = new JMenuItem("Open Project");
        openItem.setMnemonic('O');
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int retValue = fileChooser.showOpenDialog(CheckStyleGUI.this);
                if (JFileChooser.APPROVE_OPTION == retValue) {
                    preferences.setWorkingDir(fileChooser.getSelectedFile().getAbsolutePath());
                    setTitle(String.format("%s - %s", FRAME_TITLE, preferences.getWorkingDir()));
                }
            }
        });
        checkStyle.add(openItem);

        JMenuItem checkStyleConfig = new JMenuItem("Set checkstyle config");
        checkStyleConfig.setMnemonic('S');
        checkStyleConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameFilter fileFilter = new FileNameFilter("checkstyle.xml", "checkstyle.xml");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(fileFilter);
                int retValue = fileChooser.showOpenDialog(CheckStyleGUI.this);
                if (JFileChooser.APPROVE_OPTION == retValue) {
                    preferences.setCheckStyleConfig(
                            fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        checkStyle.add(checkStyleConfig);


        runMenuItem = new JMenuItem("Run");
        runMenuItem.setMnemonic('R');
        runMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                checkStylePanel.performCheckStyleRun(preferences);
                layout.show(getContentPane(), PANEL_CHECK_STYLE);
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        checkStyle.add(runMenuItem);



        menuBar.add(fileMenu);
        menuBar.add(checkStyle);

        return menuBar;
    }


    /**
     * Called, if window gets closed.
     * @param e     the causing event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        processWindowEvent(
                new WindowEvent(
                        this, WindowEvent.WINDOW_CLOSING));
    }


    /**
     * Called by observable object.
     * @param o     not used
     * @param arg   not used
     */
    @Override
    public void update(Observable o, Object arg) {
        if ((preferences.getCheckStyleConfig().length() > 0)
                && (preferences.getWorkingDir().length() > 0)) {
            setTitle(String.format("%s - %s", FRAME_TITLE, preferences.getWorkingDir()));
            runMenuItem.setEnabled(true);
        } else {
            runMenuItem.setEnabled(false);
        }
    }
}
