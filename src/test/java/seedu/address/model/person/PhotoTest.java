package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhotoTest {

    @Test
    public void isValidPhoto() {
        // blank photo
        assertFalse(Photo.isValidPhoto("")); // empty string
        assertFalse(Photo.isValidPhoto(" ")); // spaces only

        // invalid parts
        assertFalse(Photo.isValidPhoto(" .jpg")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto(" .png")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto(" .gif")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto(" .bmp")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto("ashleysimpsons.txt")); // invalid file extension
        assertFalse(Photo.isValidPhoto("ashleysimpsons.exe")); // invalid file extension
        assertFalse(Photo.isValidPhoto("ashleysimpsons.mp3")); // invalid file extension


        // missing parts
        assertFalse(Photo.isValidPhoto(".jpg")); // missing file name
        assertFalse(Photo.isValidPhoto(".png")); // missing file name
        assertFalse(Photo.isValidPhoto(".gif")); // missing file name
        assertFalse(Photo.isValidPhoto(".bmp")); // missing file name
        assertFalse(Photo.isValidPhoto("AshleySimpsons")); // missing file extension
        assertFalse(Photo.isValidPhoto("AshleySimpsonsjpg")); // missing "." for file extension

        // valid photo
        assertTrue(Photo.isValidPhoto("AshleySimpsons.jpg"));
        assertTrue(Photo.isValidPhoto("a.jpg")); // minimal
        assertTrue(Photo.isValidPhoto("..png")); // special character as file name
        assertTrue(Photo.isValidPhoto("ASHLEYSIMPSONS.png")); // upper case letters for file name
        assertTrue(Photo.isValidPhoto("AshleySimpsons.GIF")); // upper case letters for file extension
        assertTrue(Photo.isValidPhoto("AshleySimpsons.BmP")); // camel case for file extension
        assertTrue(Photo.isValidPhoto("Ash_leySimp_sons.jpg")); // underscores
        assertTrue(Photo.isValidPhoto("123.jpg")); // numeric file name
        assertTrue(Photo.isValidPhoto("jpg.jpg")); // file name is file extension name
        assertTrue(Photo.isValidPhoto("Ashley_Simpsons_Is_My_Best_Friend.png")); // long file name
    }
}
