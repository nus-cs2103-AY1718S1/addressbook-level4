# aali195-reused
###### \java\seedu\address\logic\commands\ImageCommand.java
``` java
    /**
     * Executes the command
     * @return a success message with the updated user's name
     * @throws CommandException if an error is encountered
     */
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        if (image.getPath().equalsIgnoreCase("")) {
            image.setPath("");

            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getExpiryDate(),
                    personToEdit.getRemark(), image);

            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredListToShowAll();

            return new CommandResult(generateSuccessMessage(editedPerson));
        }

        try {
            ImageUtil imageUtil = new ImageUtil();
            image.setPath(imageUtil.run(image.getPath(), personToEdit.getEmail().hashCode()));
        } catch (Exception e) {
            image.setPath("");
            return new CommandResult(generateFailureMessage());
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getExpiryDate(),
                personToEdit.getRemark(), image);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredListToShowAll();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     *  Assigns URL to the image depending on the path
     *  @param person image to be shown
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getImage().getPath().equals("")) {

            Image imageToSet = new Image("file:" + "data/" + person.getImage().getPath() + ".png",
                    100, 100, false, false);

            centerImage();
            pImage.setImage(imageToSet);
        }
    }

    /**
     * Centre the image in ImageView
     */
    public void centerImage() {
        Image img = pImage.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = pImage.getFitWidth() / img.getWidth();
            double ratioY = pImage.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            pImage.setX((pImage.getFitWidth() - w) / 2);
            pImage.setY((pImage.getFitHeight() - h) / 2);

        }
    }
```
###### \resources\view\PersonListCard.fxml
``` fxml
  <ImageView fx:id="pImage" fitHeight="100.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true">
    <HBox.margin>
      <Insets bottom="10.0" right="10.0" top="10.0" />
    </HBox.margin>
    <image>
      <Image url="@../defaults/personimage.png" />
    </image>
  </ImageView>
```
