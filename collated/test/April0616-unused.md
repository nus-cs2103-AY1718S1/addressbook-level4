# April0616-unused
###### \java\seedu\address\testutil\TestUtil.java
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
