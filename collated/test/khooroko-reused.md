# khooroko-reused
###### \java\seedu\address\ui\DetailsPanelTest.java
``` java
    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> detailsPanel = new DetailsPanel());
        uiPartRule.setUiPart(detailsPanel);

        detailsPanelHandle = new DetailsPanelHandle(detailsPanel.getRoot());
    }

```
###### \java\seedu\address\ui\DetailsPanelTest.java
``` java
    /**
     * Asserts that {@code infoPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertDetailsDisplay(DetailsPanel detailsPanel, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        DetailsPanelHandle personDetailsHandle = new DetailsPanelHandle(detailsPanel.getRoot());

        // verify person details are displayed correctly
        assertDetailsDisplaysPerson(expectedPerson, personDetailsHandle);
    }
}
```
