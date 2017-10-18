package seedu.address.model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents a Photo in the address book.
 */

public class Photo {

    private Image photo;

    public Photo(String filepath) throws IOException {
        try {
            File sourceimage = new File(filepath);
            this.photo = ImageIO.read(sourceimage);
        } catch (IOException e) {
            System.out.println("Not a valid filepath");
        }
    }
    public Image getPhoto() {
        return photo;
    }
}
