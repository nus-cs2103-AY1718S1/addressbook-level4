package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class PhotoStorage {

private File fileReader = null;
private String filePath = "";
private BufferedImage imageReader = null;
private File fileWriter = null;
private int uniqueFileName;

public PhotoStorage(String filePath, int uniqueFileName) {
    this.uniqueFileName = uniqueFileName;
    this.filePath = filePath;
    imageReader = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
}

    public String setNewFilePath() throws IOException {
        String newFilePath= "src\\main\\resources\\displaypictures\\" + uniqueFileName + ".jpg"; 
        try {
            fileReader = new File(filePath);
            fileWriter = new File(newFilePath);
            imageReader = ImageIO.read(fileReader);
            ImageIO.write(imageReader, "jpg", fileWriter);
            return newFilePath;
            //return "/displaypictures/"+ uniqueFileName + ".jpg";
        } catch(IOException e) {
            throw new IOException("unable to write to local folder");            
        }
    }
    
}
