import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Implements a FileFilter based on file extension.
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 * @version 1.00
 */
public class FileExtensionFilter extends FileFilter {

    /**
     * Description of current FileFilter.
     */
    private String description;

    /**
     * Array of valid file extensions.
     */
    private String[] extensions;




    /**
     * Creates a FileExtensionFileFilter.
     * @param d     description of the filter
     * @param e     array of valid file extensions
     */
    public FileExtensionFilter(String d, String[] e) {
        description = d;
        extensions = e;
    }


    /**
     * Implements the test.
     * @param f     file shown in the file open dialog
     * @return      <i>true</i> - if the file passes the filter<br />
     *              <i>false</i> - otherwise
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        for (String e : extensions) {
            if (f.getAbsolutePath().endsWith(e)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Getter of description.
     * @return  description
     */
    @Override
    public String getDescription() {
        return description;
    }
}
