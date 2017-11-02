//@@author aali195
package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Handles the compression of directories
 */
public class CompressUtil {
    private static List<String> fileList = new ArrayList<>();

    /**
     * Run the compression of the directory
     * @param source
     * @param path
     */
    public static void run(String source, String path) throws IOException {
        String dir = source;
        String zipFile = "Data.zip";

        compressDirectory(dir, zipFile);
    }

    /**
     * Compresses the directory
     * @param dir is the directory to compress
     * @param zipFile is the final zipped file
     */
    private static void compressDirectory(String dir, String zipFile) throws IOException {
        File directory = new File(dir);
        getFileList(directory);


        FileOutputStream fos  = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);

        for (String filePath : fileList) {
            String name = filePath.substring(directory.getAbsolutePath().length() + 1, filePath.length());
            ZipEntry zipEntry = new ZipEntry(name);
            zos.putNextEntry(zipEntry);

            FileInputStream fis = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }

        zos.close();
        fos.close();
    }

    /**
     * Get files list from the directory recursive to the sub directory.
     * @param directory to go through
     */
    private static void getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath());
                } else {
                    getFileList(file);
                }
            }
        }
    }
}
//@@author
