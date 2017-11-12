# arturs68
###### \AddressBookStorage.java
``` java
    /**
     * Saves the given {@link ReadOnlyAddressBook} to the fixed temporary location (standard location + "-backup.xml")
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
}
```
