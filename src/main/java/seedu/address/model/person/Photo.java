package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's photo in the address book.
 */
public class Photo {

    public final String pathName;

    /**
     *  Constructs a default photo.
     */
    public Photo() {
        pathName = "..\\addressbook4\\docs\\images\\default_photo.png";
    }

    /**
     * Constructs with a given pathName.
     */
    public Photo(String pathName) throws IllegalValueException {
        requireNonNull(pathName);

        this.pathName = pathName;
    }

    /**
     * Returns true if a given string is empty, which means an unknown path
     */
    private static boolean isUnknownPath(String test) {
        return test.equals("");
    }

    /**
     *
     * @return true if a given pathName has unknown value
     */
    public boolean isUnknownPathName() {
        return pathName.equals("");
    }

    public String getPathName() {
        return pathName;
    }

    /**
     *  Displace the photo
     */
    public void showPhoto() {
        JFrame frame = new JFrame("Icon Photo");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon imgIcon = new ImageIcon(img);
        JLabel lbl = new JLabel();
        lbl.setIcon(imgIcon);
        frame.getContentPane().add(lbl, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public String toString() {
        return pathName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.pathName.equals(((Photo) other).pathName)); // state check
    }

    @Override
    public int hashCode() {
        return pathName.hashCode();
    }

}
