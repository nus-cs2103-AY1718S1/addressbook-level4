package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class PhotoStorage {

private File fileReader = null;
private String filepath = "";
private BufferedImage imageReader = null;
private File fileWriter = null;
private int uniqueFileName;

public PhotoStorage(String filePath, int uniqueFileName) {
    this.uniqueFileName = uniqueFileName;
    imageReader = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
}

    public String setNewFilePath() throws IOException {
        String newFilePath= "/displaypictures/" + uniqueFileName + ".jpg";
        try {
            fileReader = new File(filepath);
            fileWriter = new File(newFilePath);
            imageReader = ImageIO.read(fileReader);
            ImageIO.write(imageReader, "jpg", fileWriter);
            return newFilePath;
        } catch(IOException e) {
            throw new IOException("unable to write to local folder");            
        }
    }
    
}
