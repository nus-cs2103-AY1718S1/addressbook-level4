# darrinloh-reused
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Optional<Phone> checkPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (!checkPhone.isPresent()) {
                phone = new Phone(null);
            } else {
                phone = checkPhone.get();
            }
            Optional<Birthday> checkBirthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY));
            if (!checkBirthday.isPresent()) {
                birthday = new Birthday(null);
            } else {
                birthday = checkBirthday.get();
            }
            Optional<Email> checkEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (!checkEmail.isPresent()) {
                email = new Email(null);
            } else {
                email = checkEmail.get();
            }
            Optional<Address> checkAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (!checkAddress.isPresent()) {
                address = new Address(null);
            } else {
                address = checkAddress.get();
            }
            Optional<Organisation> checkOrganisation = ParserUtil
                    .parseOrganisation(argMultimap.getValue(PREFIX_ORGANISATION));
            if (!checkOrganisation.isPresent()) {
                organisation = new Organisation(null);
            } else {
                organisation = checkOrganisation.get();
            }
            Optional<Remark> checkRemark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK));
            if (!checkRemark.isPresent()) {
                remark = new Remark(null);
            } else {
                remark = checkRemark.get();
            }

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, birthday, email, address, organisation, remark, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    }



    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    public void loadDefaultPage() {
        URL defaultPage;
        if (prefs.getTheme().contains(LIGHT_MODE)) {
            defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_LIGHT_PAGE);
        } else {
            defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        }

        loadPage(defaultPage.toExternalForm());
    }

```
