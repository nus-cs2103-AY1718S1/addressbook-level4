# wenzongteo
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Check if the input is empty. If the input is not empty, return the input. Else, return UNFILLED_PARAMETER instead
     *
     * @param userInput Input entered by the user that is parsed by argMultimap.
     * @return the value of the input entered by the user or '-' if no input was entered.
     */
    private static Optional<String> checkInput(Optional<String> userInput) {
        return Optional.of(userInput.orElse(UNFILLED_PARAMETER));
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * Check if data/images and data/edited folders exist. If they do not exist, create them.
     */
    private void checkIfFilesExist() {
        File imagesFolder = new File(PHOTO_FOLDER);
        File editedFolder = new File(EDITED_FOLDER);

        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
            logger.info("Image storage location does not exist. Will be creating 'data/images' folder");
        } else {

        }

        if (!editedFolder.exists()) {
            editedFolder.mkdirs();
            logger.info("Temporary image storage does not exist. Will be creating 'data/edited' folder");
        } else {

        }
    }

    /**
     * Deletes all existing images in data/edited folder first before deleting the folder itself.
     */
    private void removeFiles() {
        File toBeDeletedFolder = new File(EDITED_FOLDER);
        File[] toBeDeletedImages = toBeDeletedFolder.listFiles();
        if (toBeDeletedImages != null) {
            for (File f : toBeDeletedImages) {
                f.delete();
            }
        }
        toBeDeletedFolder.delete();
    }

    /**
     * Copy default photo used in Augustine from the build resource.
     * @param config Configurations Augustine is using.
     */
    private void checkDefaultImage(Config config) {
        try {
            InputStream is = this.getClass().getResourceAsStream(config.getDefaultPhoto());
            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            File targetFile = new File(DEFAULT_PHOTO);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            logger.info("Copying default photo over to " + DEFAULT_PHOTO);

        } catch (IOException ioe) {
            throw new AssertionError("Impossible");
        } catch (Exception e) {
            throw new AssertionError("No other exceptions possible");
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Deletes all existing images in data/edited folder first before deleting the folder itself.
     */
    private void moveFilesToEditedFolder() {
        String photoFolder = "data/images/";
        File toBeDeletedFolder = new File(photoFolder);
        File[] toBeDeletedImages = toBeDeletedFolder.listFiles();

        if (toBeDeletedImages != null) {
            for (File f : toBeDeletedImages) {
                removePhoto(f);
            }
        }
    }

    /**
     * Check if photo in image is the default photo. If it is, ignore. Else, move to edited folder.
     * Add into the photoStack in UniquePersonList to record the latest image file.
     * @param f current photo to check.
     */
    private void removePhoto(File f) {
        String defaultPhoto = "data/images/default.jpeg";
        try {
            if (!f.equals(new File(defaultPhoto))) {
                String destPath = f.toString().substring(12);
                destPath = getEditedPhotoPath(destPath);

                Files.copy(f.toPath(), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
                UniquePersonList.addToPhotoStack(destPath);
                f.delete();
            }
        } catch (IOException ioe) {
            throw new AssertionError("Clearing of files have issue");
        }
    }

    /**
     * Check if there are existing photos from the same contact in the data/edited/ folder. if there are, add a counter
     * to the file name so that the photos will not be overwritten.
     * @param originalPath photo path in data/images
     * @return the updated photo path to data/edited/ folder.
     */
    private String getEditedPhotoPath(String originalPath) {
        String editedFolder = "data/edited/";
        String destPath = editedFolder + originalPath;
        String emailAddr = originalPath.substring(0, originalPath.length() - 4);
        String fileExt = ".jpg";
        int counter = 0;

        while (FileUtil.isFileExists(new File(destPath))) {
            counter++;
            destPath = editedFolder + emailAddr + counter + fileExt;
        }

        return destPath;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized String addImage(EmailAddress email, Photo photo) throws IOException {
        String folder = "data/images/";
        String fileExt = ".jpg";

        File imageFolder = new File(folder);

        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        } else {

        }

        String destination = folder + email.toString() + fileExt;
        Path sourcePath = Paths.get(photo.toString());
        Path destPath = Paths.get(destination);

        Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);

        return folder + email.toString() + fileExt;
    }
```
###### \java\seedu\address\model\person\Photo.java
``` java
/**
 * Represents a Person's display picture in the address book
 * Guarantees: immutable; is valid as declared in (@Link #isValidImage(String))
 */
public class Photo {
    public static final String MESSAGE_PHOTO_CONSTRAINTS =
            "Person's photo should be in .jpg or .jpeg and preferred to be of 340px x 453px dimension. If the "
                    + "photo is on the local system, please provide the absolute file path. If the photo is from the "
                    + "internet, ensure that the link starts with http or https and ends with .jpg or .jpeg";
    public static final String MESSAGE_PHOTO_NOT_FOUND = "Error! Photo does not exist!";
    public static final String MESSAGE_LINK_ERROR = "Error! URL given is invalid!";

    /**
     * Can contain multiple words but must end with .jpg or .jpeg
     */
    public static final String PHOTO_VALIDATION_REGEX = "([^\\s]+[\\s\\w]*(\\.(?i)(jpg|jpeg|))$)";
    /**
     * Can contain multiple words or symbols but must start with either http or https and end with either .jpg or .jpeg
     */
    public static final String URL_REGEX =
            "((^(https?)(://))[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]*(\\.(?i)(jpg|jpeg))$)";
    public static final String DEFAULT_PHOTO = "data/images/default.jpeg";
    public static final String DOWNLOAD_LOCATION = "data/download.jpg";
    public static final String UNFILLED = "-";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public final String value;
    private final String hash;

    /**
     * Validates given photo.
     * @throws IllegalValueException if given photo string is invalid or file is not found.
     */
    public Photo(String photo) throws IllegalValueException {
        photo = photo.trim();

        if (photo.equals(UNFILLED)) {
            logger.info("photo fill is left unfilled, using default photo as contact's photo.");
            photo = DEFAULT_PHOTO;
        }

        if (!isValidPhoto(photo)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        } else {
            this.value = getPhoto(photo);
            File image = new File(this.value);
            this.hash = generateHash(image);
        }
    }

    /**
     * Photo entered by the app that does not require validation as Image should already exist.
     * If Photo entered by the app does not exist, default image will used instead.
     * @param photo path to the image stored.
     * @param num used for overloading constructor.
     */
    public Photo(String photo, int num) {
        photo = photo.trim();

        File image = new File(photo);
        this.value = photo;

        if (!FileUtil.isFileExists(image)) {
            logger.info("contact's photo cannot be found. Using default photo instead.");
            copyDefaultPhoto(photo);
        }
        this.hash = generateHash(image);
    }

    /**
     * Check if photo source given is from the internet or local system and call the relevant methods.
     * Return the path for the photo that is obtained from the relevant methods.
     * @param photo photo path specified by the user.
     * @return photo path of the photo that is obtained from the relevant methods.
     * @throws IllegalValueException if the photo is not found.
     */
    private String getPhoto(String photo) throws IllegalValueException {
        if (photo.matches(URL_REGEX)) {
            logger.info("Attempting to download photo from the internet");
            return getPhotoFromInternet(photo);
        } else {
            return getPhotoFromLocal(photo);
        }
    }

    /**
     * Check if the file exists in the local system. Return IllegalValueException if the image is not found.
     * @param photo photo path specified by the user.
     * @return photo path specified by the user if the photo exists.
     * @throws IllegalValueException if the photo is not found on the local system
     */
    private String getPhotoFromLocal(String photo) throws IllegalValueException {
        File image = new File(photo);
        if (!FileUtil.isFileExists(image)) {
            throw new IllegalValueException(MESSAGE_PHOTO_NOT_FOUND);
        } else {
            return photo;
        }
    }

    /**
     * Copy the default photo for contact to serve as the contact's photo.
     * @param destPath File path of the contact's photo.
     */
    public void copyDefaultPhoto(String destPath) {
        try {
            Files.copy(Paths.get(DEFAULT_PHOTO), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new AssertionError("Photo should already exist!");
        }
    }

    /**
     *  @return the generated hash of the image.
     *  @throws IOException if the file does not exist.
     *  @throws NoSuchAlgorithmException if the algorithm does not exist.
     */
    public String generateHash(File photo) {
        try {
            MessageDigest hashing = MessageDigest.getInstance("MD5");
            return new String(hashing.digest(Files.readAllBytes(photo.toPath())));
        } catch (Exception e) {
            throw new AssertionError("Impossible");
        }
    }

    /**
     * @return true if a given string is a valid person photo.
     */
    public static boolean isValidPhoto(String test) {
        if (test.matches(URL_REGEX)) {
            return true;
        } else if (test.matches(PHOTO_VALIDATION_REGEX)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the hash of the current image.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Downloads photo from the path given by the user and store it locally. Returns the local file path of the image.
     * @param photo URL link given by the user
     * @return the file path of the downloaded image in the local system.
     * @throws IllegalValueException if errors are faced when visiting the link or downloading the file.
     */
    private String getPhotoFromInternet(String photo) throws IllegalValueException {
        try {
            URL url = new URL(photo);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(DOWNLOAD_LOCATION);
            byte[] buffer = new byte[4096];

            int length = 0;

            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }

            is.close();
            os.close();

            logger.info("Photo downloaded to " + DOWNLOAD_LOCATION);
            return DOWNLOAD_LOCATION;
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_LINK_ERROR);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.value.equals(((Photo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Accessor to allow other classes to push into the edited photo stack when they delete photos from Augustine.
     * @param photoPath photoPath in data/edited/ folder.
     */
    public static void addToPhotoStack(String photoPath) {
        photoStack.push(photoPath);
    }

    /**
     * Calculates the MD5 hash value of the person's display picture in {@code srcPath}.
     * returns the calculated hash in {@code hashValue}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     * @throws NoSuchAlgorithmException if the algorithm used for hashing is invalid.
     */
    public String calculateHash(String srcPath) throws IOException, NoSuchAlgorithmException {
        MessageDigest hashing;
        hashing = MessageDigest.getInstance("MD5");

        File existingImage = new File(srcPath);
        String hashValue = new String(hashing.digest(Files.readAllBytes(existingImage.toPath())));

        return hashValue;
    }

    /**
     * Creates a backup copy of the person's display picture in {@code srcPath} to {@code destPath}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     */
    public void createBackUpPhoto(String srcPath, String emailAddr) throws IOException {
        String destPath = editedFolder + emailAddr + photoFileType;
        int counter = 0;

        while (FileUtil.isFileExists(new File(destPath))) {
            counter++;
            destPath = editedFolder + emailAddr + counter + photoFileType;
        }

        Files.copy(Paths.get(srcPath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        photoStack.push(destPath);
        logger.info("Image for " + emailAddr + photoFileType + " copied to " + editedFolder);
    }

    /**
     * Creates a local copy of the person's display picture in {@code srcPath} to {@code destPath}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     */
    public void createCurrentPhoto(String srcPath, String emailAddr) throws IOException {
        String destPath = photoFolder + emailAddr + photoFileType;
        if (!srcPath.equals(destPath)) {
            Files.copy(Paths.get(srcPath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        }
        logger.info("Image for " + emailAddr + photoFileType + " copied to " + photoFolder);
    }

    /**
     * Deletes the existing copy of the person's display picture in {@code srcPath}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     */
    public void deleteExistingPhoto(String srcPath) throws IOException {
        Files.delete(Paths.get(srcPath));
        logger.info("Image " + srcPath + " deleted");
    }

    /**
     * Update the photo of the person in (@code person) to reflect the new address of local file.
     *
     * @return the updated person
     */
    public Person updatePhoto(Person person, String srcPath) {
        person.setPhoto(new Photo(srcPath, 0));
        return person;
    }

    /**
     * Check if the photo or email of the person is changed during the edit command.
     *
     * @param target Original Person in Augustine
     * @param editedPerson Edited Person by the user
     * @return which case of change occurred.
     */
    public int updateCasesForPhoto(ReadOnlyPerson target, ReadOnlyPerson editedPerson) {
        if (target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && !target.getPhoto().equals(editedPerson.getPhoto())) { //Only Photo changed.
            return ONLY_PHOTO_CHANGED;
        } else if (!target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && target.getPhoto().equals(editedPerson.getPhoto())) { //only email changed.
            return ONLY_EMAIL_CHANGED;
        } else if (!target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && !target.getPhoto().equals(editedPerson.getPhoto())) { //Both changed.
            return BOTH_PHOTO_AND_EMAIL_CHANGED;
        } else if (target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && target.getPhoto().equals(editedPerson.getPhoto())) { //No special update
            return NEITHER_PHOTO_OR_EMAIL_CHANGED;
        } else {
            throw new AssertionError("Shouldn't be here");
        }
    }

    /**
     * Copy the contact's photo from data/edited folder to data/images folder when an undo command is executed.
     *
     * @param person the edited person.
     * @param toBeCopied Previous photo of the person.
     */
    private void copyBackupPhoto (ReadOnlyPerson person, File toBeCopied) {
        try {
            if (FileUtil.isFileExists(toBeCopied)) {
                createCurrentPhoto(toBeCopied.toString(), person.getEmailAddress().toString());
            }
        } catch (IOException ioe) {
            throw new AssertionError("Photo does not exist");
        }
    }

    /**
     * Compare the hash of the contact's current photo and the stored photo in the Person's Photo Object to ensure that
     * the photo is correct.
     *
     * @param person person who the photos belong to.
     * @param toBeCopied previous photo of the person.
     */
    private void comparePhotoHash (ReadOnlyPerson person, File toBeCopied) {
        try {
            String hashValue = calculateHash(person.getPhoto().toString());
            if (!hashValue.equals(person.getPhoto().getHash())) { //Not equal, go take the old image
                createCurrentPhoto(photoStack.pop(), person.getEmailAddress().toString());
                //createCurrentPhoto(toBeCopied.toString(), person.getEmailAddress().toString());
            }
        } catch (IOException ioe) {
            throw new AssertionError("Photo does not exist!");
        } catch (NoSuchAlgorithmException nsa) {
            throw new AssertionError("Impossible");
        }
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Bind the path of a contact's display picture into an Image and set the image into the ImageView photo.
     */
    public void getPhoto() {
        try {
            StringExpression filePath = Bindings.convert(person.photoProperty());
            FileInputStream imageInputStream = new FileInputStream(filePath.getValue());
            Image image = new Image(imageInputStream, 100, 200, true, true);
            photo.setImage(image);
            imageInputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
```
###### \java\seedu\address\ui\PersonCardBirthday.java
``` java
    /**
     * Bind the path of a contact's display picture into an Image and set the image into the ImageView photo.
     */
    public void getPhoto() {
        try {
            StringExpression filePath = Bindings.convert(person.photoProperty());
            FileInputStream imageInputStream = new FileInputStream(filePath.getValue());
            Image image = new Image(imageInputStream, 100, 200, true, true);
            photo.setImage(image);
            imageInputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
```
