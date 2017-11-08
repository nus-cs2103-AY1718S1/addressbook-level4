
//@@author aali195
package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Handles the compression of directories
 */
public class CompressUtil {

    /**
     * Run the compression of the directory
     * @param source of directory to compress
     * @param destPath to save to
     * @throws Exception if problems with IO are encountered
     */
    public static void run(String source, String destPath) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        String destZipFile;

        if (destPath.equals("")) {
            destZipFile = "AddressbookData.zip";
        } else {
            destZipFile = destPath + ".zip";
        }

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", source, zip);
        zip.flush();
        zip.close();
    }

    /**
     * Add files to the zip file and write it out
     * @param path is the current directory path
     * @param source is the directory to zip
     * @param zip is the final zipped file
     * @throws Exception if problems with IO are encountered
     */
    private static void addFileToZip(String path, String source, ZipOutputStream zip) throws Exception {
        File folder = new File(source);
        if (folder.isDirectory()) {
            addFolderToZip(path, source, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(source);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    /**
     * Add directories to the zip file
     * @param path is the current directory path
     * @param source is the directory to zip
     * @param zip is the final zipped file
     * @throws Exception if problems with IO are encountered
     */
    private static void addFolderToZip(String path, String source, ZipOutputStream zip)
            throws Exception {
        File folder = new File(source);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), source + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), source + "/" + fileName, zip);
            }
        }
    }
}
//@@author
