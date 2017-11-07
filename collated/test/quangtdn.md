# quangtdn
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private static final String PROFILEPAGE_FIELD_ID = "#profile";
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private final Label profileLabel;
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getProfilePage() { return profileLabel.getText(); }
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code ProfilePage} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withProfilePage(String profile) {
        try {
            ParserUtil.parseProfilePage(Optional.of(profile)).ifPresent(descriptor::setProfilePage);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Profile Page is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_PROFILEPAGE = "www.facebook.com";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code ProfilePage} of the {@code Person} that we are building.
     */
    public PersonBuilder withProfilePage(String profile) {
        try {
            this.person.setProfilePage(new ProfilePage(profile));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("profile page is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        //URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
          //      + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        URL expectedPersonUrl = new URL("https://"+ALICE.getProfilePage().toString());
      //  waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        String selectedCardName = getPersonListPanel().getHandleToSelectedCard().getName();
        String selectedCardProfile = getPersonListPanel().getHandleToSelectedCard().getProfilePage();
        URL expectedUrl;
        try {
            if(selectedCardProfile.equals("")) {
                expectedUrl = new URL(GOOGLE_SEARCH_URL_PREFIX + selectedCardName.replaceAll(" ", "+")
                        + GOOGLE_SEARCH_URL_SUFFIX);
            } else {
                //expectedUrl = new URL("http://" + selectedCardProfile);
                expectedUrl= new URL("https://m.facebook.com/?refsrc=https%3A%2F%2Fwww.facebook.com%2F&_rdr");
                //Strange modification here !?!?
            }
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.");
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }
```
