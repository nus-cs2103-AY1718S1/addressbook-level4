# RSJunior37
###### \java\seedu\address\ui\InsuranceIdLabel.java
``` java
    public InsuranceIdLabel(ReadOnlyInsurance insurance) {
        super(FXML);
        insuranceId.textProperty().bind(insurance.signingDateStringProperty());
        insuranceId.setOnMouseClicked(e -> raise(new InsurancePanelSelectionChangedEvent(insurance)));


    }
}
```
###### \java\seedu\address\ui\InsuranceProfile.java
``` java

    public InsuranceProfile(ReadOnlyInsurance insurance, int displayIndex) {
        super(FXML);
        this.insurance = insurance;
        index.setText(displayIndex + ".");

        initializeContractFile(insurance);

        enableNameToProfileLink(insurance);

        bindListeners(insurance);
    }

    public ReadOnlyInsurance getInsurance() {
        return insurance;
    }


    /**
     * Listen for click event on person names to be displayed as profile
     * @param insurance
     */
    private void enableNameToProfileLink(ReadOnlyInsurance insurance) {
        owner.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getOwner())));
        insured.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getInsured())));
        beneficiary.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getBeneficiary())));
    }

    /**
     * Checks if pdf file exist in project, if not add click event on contract field to add file with filechooser
     * Then add click event on contract field to open up the file
     * @param insurance
     */
    private void initializeContractFile(ReadOnlyInsurance insurance) {
        insuranceFile =  new File(PDFFOLDERPATH + insurance.getContractPath());
        if (isFileExists(insuranceFile)) {
            activateLinkToInsuranceFile();
        } else {
            contractPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FileChooser.ExtensionFilter extFilter =
                            new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                    FileChooser chooser = new FileChooser();
                    chooser.getExtensionFilters().add(extFilter);
                    File openedFile = chooser.showOpenDialog(null);
                    activateLinkToInsuranceFile();

                    if (isFileExists(openedFile)) {
                        try {
                            Files.copy(openedFile.toPath(), insuranceFile.toPath());
                        } catch (IOException ex) {
                            logger.info("Unable to open at path: " + openedFile.getAbsolutePath());
                        }
                    }
                }
            });

        }
    }

    /**
     *  Enable the link to open contract pdf file and adjusting the text hover highlight
     */
    private void activateLinkToInsuranceFile() {
        contractPath.getStyleClass().add("particular-link");
        contractPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Desktop.getDesktop().open(insuranceFile);
                    // HostServices hostServices = getRoot().getHostservices();
                    // hostServices.showDocument(file.getAbsolutePath());
                } catch (IOException ee) {
                    logger.info("File do not exist: " + PDFFOLDERPATH + insurance.getContractPath());
                }
            }
        });
    }
```
###### \java\seedu\address\ui\ProfilePanel.java
``` java
    /**
     * Load default page with empty fields and default message
     */
    private void loadDefaultPage() {
        name.setText(DEFAULT_MESSAGE);
<<<<<<< HEAD
        phone.setText("");
        address.setText("");
        dob.setText("");
        gender.setText("");
        email.setText("");
    }
=======
        phone.setText(null);
        address.setText(null);
        dob.setText(null);
        email.setText(null);
        insuranceHeader.setText(null);
>>>>>>> Upstream/master

    }
    /**
     * To be called everytime a new person is selected and bind all information for real-time update
     * @param person
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        dob.textProperty().bind(Bindings.convert(person.dobProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InsuranceIdLabel}.
     */
    class InsuranceIdListViewCell extends ListCell<InsuranceIdLabel> {
        @Override
        protected void updateItem(InsuranceIdLabel insurance, boolean empty) {
            super.updateItem(insurance, empty);

            if (empty || insurance == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(insurance.getRoot());
            }
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        insurance.clear();
        loadPersonPage(event.getNewSelection().person);
        raise(new SwitchToProfilePanelRequestEvent());
    }

```
###### \resources\view\DarkTheme.css
``` css
.list-cell:filled #insuranceCardPane .particular-person:hover {
    -fx-font-size: 13pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #d06651;
    -fx-opacity: 1;
}
```
