# philemontan-reused
###### \java\seedu\address\logic\commands\UnknownCommand.java
``` java
    /**
     * This switch is adapted from the original AddressBookParser.
     * If a match is found, but its parameters are invalid, we will not prompt the user for a response, but instead
     * with a formatting alert, through the exception thrown by the existings command parsers.
     * @param closestCommandWord
     * @throws ParseException
     */
    private void setSuggestedCommand(String closestCommandWord) throws ParseException {
        switch (closestCommandWord) {
        case AddCommand.COMMAND_WORD:
            suggestedCommand = new AddCommandParser().parse(arguments);
            break;

        case AddEventCommand.COMMAND_WORD:
            suggestedCommand = new AddEventCommandParser().parse(arguments);
            break;

        case EditCommand.COMMAND_WORD:
            suggestedCommand = new EditCommandParser().parse(arguments);
            break;

        case EditEventCommand.COMMAND_WORD:
            suggestedCommand = new EditEventCommandParser().parse(arguments);
            break;

        case UpdateProfilePicCommand.COMMAND_WORD:
            suggestedCommand = new UpdateProfilePicCommandParser().parse(arguments);
            break;

        case DeleteProfilePicCommand.COMMAND_WORD:
            suggestedCommand = new DeleteProfilePicCommandParser().parse(arguments);
            break;

        case SelectCommand.COMMAND_WORD:
            suggestedCommand = new SelectCommandParser().parse(arguments);
            break;

        case DeleteCommand.COMMAND_WORD:
            suggestedCommand = new DeleteCommandParser().parse(arguments);
            break;

        case DeleteEventCommand.COMMAND_WORD:
            suggestedCommand = new DeleteEventCommandParser().parse(arguments);
            break;

        case ClearCommand.COMMAND_WORD:
            suggestedCommand = new ClearCommand();
            break;

        case FindCommand.COMMAND_WORD:
            suggestedCommand = new FindCommandParser().parse(arguments);
            break;

        case LocationCommand.COMMAND_WORD:
            suggestedCommand = new LocationCommandParser().parse(arguments);
            break;

        case ListCommand.COMMAND_WORD:
            suggestedCommand = new ListCommand();
            break;

        case HistoryCommand.COMMAND_WORD:
            suggestedCommand = new HistoryCommand();
            break;

        case ExitCommand.COMMAND_WORD:
            suggestedCommand = new ExitCommand();
            break;

        case HelpCommand.COMMAND_WORD:
            suggestedCommand = new HelpCommand();
            break;

        case UndoCommand.COMMAND_WORD:
            suggestedCommand = new UndoCommand();
            break;

        case RedoCommand.COMMAND_WORD:
            suggestedCommand = new RedoCommand();
            break;

        case RemoveTagCommand.COMMAND_WORD:
            suggestedCommand = new RemoveTagCommandParser().parse(arguments);
            break;

        case ImportCommand.COMMAND_WORD:
            suggestedCommand = new ImportCommand();
            break;

        case ExportCommand.COMMAND_WORD:
            suggestedCommand = new ExportCommand();
            break;

        case EmailCommand.COMMAND_WORD:
            suggestedCommand = new EmailCommandParser().parse(arguments);
            break;

        case SortCommand.COMMAND_WORD:
            suggestedCommand = new SortCommand();
            break;
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

```
###### \java\seedu\address\logic\commands\UnknownCommand.java
``` java
    /**
     * Levenshtein distance algorithm implementation taken from:
     * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java,
     * under the Creative Commons Attribution-ShareAlike License.
     * No logical changes were made. Minor checkstyle formatting changes applied.
     * @param lhs
     * @param rhs
     * @return
     */
    public int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int costReplace = cost[i - 1] + match;
                int costInsert  = cost[i] + 1;
                int costDelete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }
}
```
