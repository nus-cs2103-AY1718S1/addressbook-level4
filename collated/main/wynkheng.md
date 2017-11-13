# wynkheng
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday, String command)
            throws IllegalValueException {
        requireNonNull(birthday);
        if (command.equals("add")) {
            return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.of(new Birthday(0));
        } else {
            return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
        }
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public boolean setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        return true;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
