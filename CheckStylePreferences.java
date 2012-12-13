import java.io.File;
import java.util.Observable;
import java.util.prefs.Preferences;

/**
 * Implements a helper class of user preferences.
 * @see java.util.prefs.Preferences
 */
class CheckStylePreferences extends Observable {

    /**
     * Used to identify checkStyleConfig preference.
     */
    private static final String CHECKSTYLE_CONFIG = "CHECKSTYLE_CONFIG";

    /**
     * Used to identify workingDir preference.
     */
    private static final String WORKING_DIR = "WORKING_DIR";

    /**
     * Path to CheckStyle config file.
     */
    private String checkStyleConfig;

    /**
     * Path to project directory to check.
     */
    private String workingDir;

    /**
     * Preferences of the user.
     */
    private Preferences prefs;




    /**
     * Creates a CheckStylePreferences instance.
     */
    public CheckStylePreferences(String preferencesName) {
        prefs = Preferences.userRoot().node(preferencesName);

        checkStyleConfig = prefs.get(CHECKSTYLE_CONFIG, null);
        workingDir = prefs.get(WORKING_DIR, null);
        checkPreferences();

        setChanged();
        notifyObservers();
    }


    /**
     * Resets preferences if files or directory have changed.
     */
    private void checkPreferences() {

        if (checkStyleConfig.length() != 0) {
            File csf = new File(checkStyleConfig);
            if (!(csf.isFile() && csf.canRead())) {
                checkStyleConfig = null;
            }
        }

        if (workingDir.length() != 0) {
            File wd = new File(workingDir);
            if (!(wd.isDirectory() && wd.canRead())) {
                workingDir = null;
            }
        }
    }



    /**
     * Updates and persists current preferences.
     */
    public void updatePreferences() {
        if (checkStyleConfig != null) {
            prefs.put(CHECKSTYLE_CONFIG, checkStyleConfig);
        }
        if (workingDir != null) {
            prefs.put(WORKING_DIR, workingDir);
        }

        setChanged();
        notifyObservers();
    }


    /**
     * Getter of checkStyleConfig.
     * @return      the checkStyleConfig
     */
    public String getCheckStyleConfig() {
        return checkStyleConfig;
    }


    /**
     * Setter of checkStyleConfig.
     * @param csc   the value to set
     */
    public void setCheckStyleConfig(String csc) {
        this.checkStyleConfig = csc;

        setChanged();
        notifyObservers();
    }


    /**
     * Getter of workingDir.
     * @return  workingDir
     */
    public String getWorkingDir() {
        return workingDir;
    }


    /**
     * Setter of workingDir.
     * @param wd    the value to set
     */
    public void setWorkingDir(String wd) {
        this.workingDir = wd;

        setChanged();
        notifyObservers();
    }


    /**
     * Creates a string representation of current config.
     * @return  the string representation
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("wd = ").append(workingDir)
                .append("\n")
                .append("xml = ").append(checkStyleConfig)
                .append("\n");
        return sb.toString();
    }
}