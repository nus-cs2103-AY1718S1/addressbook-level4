# sunarjo-denny-reused
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
        if (!argMultimap.getPreamble().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (argMultimap.size() == 1) {
            attribute = PREFIX_NAME_FOR_SORTING.getPrefix();
        }

        argMultimap.getValue(PREFIX_NAME_FOR_SORTING).ifPresent(setOrder(PREFIX_NAME_FOR_SORTING));
        argMultimap.getValue(PREFIX_PHONE_FOR_SORTING).ifPresent(setOrder(PREFIX_PHONE_FOR_SORTING));

        return new SortCommand(attribute, isReversed);
    }
    private Consumer<String> setOrder(Prefix prefix) {
        return s-> {
            attribute = prefix.toString();

            if (s.equals(SortCommand.REVERSE_SEQUENCE)) {
                isReversed = new Boolean(true);
                return;
            } else {
                isReversed = new Boolean(false);
                return;
            }
        };
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
