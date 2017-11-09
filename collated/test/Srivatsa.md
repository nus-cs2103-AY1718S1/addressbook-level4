# Srivatsa
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException, IllegalIdException {
            fail("This method should not be called.");
        }
        //@author
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList() {
            updateFilteredPersonList();
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public UserPrefs getUserPrefs() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void recordSearchHistory() throws CommandException {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sortPersonListBySearchCount() {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListLexicographically() {
            fail("This method should not be called.");
        }
```
