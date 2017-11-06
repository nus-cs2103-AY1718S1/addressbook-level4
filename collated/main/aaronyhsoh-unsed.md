# aaronyhsoh-unsed
###### \java\seedu\address\ui\PersonCard.java
``` java
        //highlightName(person);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /*private void highlightName(ReadOnlyPerson person) {
        if (person.getFavourite()) {
            name.setStyle("-fx-text-fill: red");
        }
    }*/
    //@@auther

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
