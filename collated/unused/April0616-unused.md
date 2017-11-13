# April0616-unused
###### \PersonInfoOverview.java
``` java
    /**
     * Set the default contact photo to the default person.
     */
    public void setDefaultContactPhoto() {
        String defaultPhotoPath = "src/main/resources/images/help_icon.png";
        File defaultPhoto = new File(defaultPhotoPath);
        URI defaultPhotoUri = defaultPhoto.toURI();
        Image defaultImage = new Image(defaultPhotoUri.toString());
        centerImage(defaultImage);
        contactPhoto.setImage(defaultImage);
    }

    /**
     * Load the photo of the specified person.
     * @param person
     */
    public void loadPhoto(ReadOnlyPerson person) {
        String photoPath = person.getPhotoPath().value;
        File photo = new File(photoPath);
        URI photoUri = photo.toURI();
        Image image = new Image(photoUri.toString());

        contactPhoto.setPreserveRatio(true);
        centerImage(image);
        contactPhoto.setImage(image);
    }

```
###### \PhotoCommandTest.java
``` java

public class PhotoCommandTest {
    @Test
    public void equals() throws FileNotFoundException, IllegalValueException {
        File amyFile = new File(VALID_PHOTONAME_AMY);
        File bobFile = new File(VALID_PHOTONAME_BOB);

        try {
            amyFile.createNewFile();
            bobFile.createNewFile();
        } catch (IOException ioe) {
            System.err.println("Cannot create temporary files!");
        }

        String amyPath = amyFile.getAbsolutePath();
        String bobPath = bobFile.getAbsolutePath();
        //TestUtil.createTempFile(VALID_PHOTOPATH_AMY);
        //TestUtil.createTempFile(VALID_PHOTOPATH_BOB);

        PhotoCommand addFirstPersonPhotoPath = new PhotoCommand(INDEX_FIRST_PERSON, amyPath);
        PhotoCommand addSecondPersonPhotoPath = new PhotoCommand(INDEX_SECOND_PERSON, bobPath);

        // same object -> returns true
        assertTrue(addFirstPersonPhotoPath.equals(addFirstPersonPhotoPath));

        // same value -> returns true
        PhotoCommand copy = new PhotoCommand(addFirstPersonPhotoPath.getIndex(),
                addFirstPersonPhotoPath.getLocalPhotoPath());
        assertTrue(addFirstPersonPhotoPath.equals(copy));

        // different types -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(1));

        // null -> returns false
        assertFalse(addFirstPersonPhotoPath == null);

        // different objects -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(addSecondPersonPhotoPath));

        //TestUtil.removeFileAndItsParentsTillRoot(VALID_PHOTOPATH_BOB);
        //TestUtil.removeFileAndItsParentsTillRoot(VALID_PHOTOPATH_AMY);
        amyFile.delete();
        bobFile.delete();
    }
}
```
###### \TestUtil.java
``` java
    /**
     * Removes the temporary test files and parents if they exist till the root of the path.
     * @param pathString of the file
     * @throws IOException
     */
    public static void removeFileAndItsParentsTillRoot(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        if (path.toString().matches("[a-zA-Z]+[:]+[\\\\]") || path == null) {
            return;
        } else if (Files.isRegularFile(path)) {
            Files.deleteIfExists(path);

        }

        if (Files.isDirectory(path)) {
            try {
                Files.delete(path);
            } catch (DirectoryNotEmptyException e) {
                return;
            }
        }
        String parentPathString = path.getParent().toString();
        //use recursion here
        removeFileAndItsParentsTillRoot(parentPathString);
    }

    /**
     * If the file in the specific path in the app directory exists, delete the file in the path
     * @param path
     */
    public static void removeAppFile(String path) {
        File fileToDelete = new File(path);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    /**
     * Creates temporary folders and a photo file for parseCommand_photo() test, i.e.,
     * "C:\\family\\photo.jpg"
     */
    public static void createTempFile(String path) {
        if (!PhotoCommand.isValidLocalPhotoPath(path)) {
            assert false : "Invalid photo path!";
            return;
        }
        final String photoPath = path;
        File testFile = new File(photoPath);

        //create new test file
        testFile.getParentFile().mkdirs();
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            assert false : "Cannot open or write file!";
        }

    }
}
```
