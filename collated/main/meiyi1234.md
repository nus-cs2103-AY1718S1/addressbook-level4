# meiyi1234
###### /java/seedu/address/logic/parser/RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");
        return new RemarkCommand(index, new Remark(remark));
    }
}
```
###### /java/seedu/address/model/person/Remark.java
``` java
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "The remark of a person can take any value.";

    public final String value;

    public Remark(String remark) {
        //requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof Remark)
                && this.value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
