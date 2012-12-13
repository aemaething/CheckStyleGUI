import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Implements a FileFilter bases on file name.
 * @author Achim Gosse, achim.gosse+checkstyle-gui@gmail.com
 * @version 1.00
 */
public class FileNameFilter extends FileFilter {

    /**
     * Used to filter files.
     */
    private String fileName;

    /**
     * Description of current FileNameFilter.
     */
    private String description;




    /**
     * Creates a FileFilter instance.
     * @param desc  description of current filter
     * @param fn    fileName to filter for
     */
    public FileNameFilter(String desc, String fn) {
        description = desc;
        fileName = fn;
    }


    /**
     * Getter of description.
     * @return  description
     */
    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Implements the filter.
     * @param f     file shown in the open file dialog
     * @return      <i>true</i> - if the file passes the filter<br />
     *              <i>false</i> - otherwise
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        return f.getName().equals(fileName);
    }
}
