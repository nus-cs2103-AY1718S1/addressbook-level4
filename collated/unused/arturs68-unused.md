# arturs68-unused
###### \StorageManager.java
``` java
	// not used due to the lack of time to implement
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        String path = addressBookStorage.getAddressBookFilePath() + "-backup.xml";
        logger.fine("Attempting to write to data file: " + path);
        saveAddressBook(addressBook, path);
    }
```
###### \XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }
```
