# basyiro
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Remove the tag of person by the given {@code tag}.
     * @throws PersonNotFoundException or DuplicatePersonException if {@code tag} is null or duplicated.
     */
    void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);

            Person newPerson;
            if (oldPerson.isHomepageManuallySet()) {
                newPerson = new Person(oldPerson, oldPerson.getHomepage());
            } else {
                newPerson = new Person(oldPerson);
            }
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);
            addressBook.updatePerson(oldPerson, newPerson);
            indicateAddressBookChanged();
        }
    }

```
