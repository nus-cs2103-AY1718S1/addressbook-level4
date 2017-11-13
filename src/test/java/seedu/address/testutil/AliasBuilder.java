package seedu.address.testutil;

import seedu.address.model.alias.Alias;

/**
 * A utility class to help with building Alias objects.
 */
public class AliasBuilder {

    public static final String DEFAULT_COMMAND = "testing";
    public static final String DEFAULT_ALIAS = "t";

    private Alias alias;

    public AliasBuilder() {
        String command = DEFAULT_COMMAND;
        String alias = DEFAULT_ALIAS;
        this.alias = new Alias(command, alias);
    }

    public AliasBuilder(String command, String alias) {
        this.alias = new Alias(command, alias);
    }

    /**
     * Initializes the AliasBuilder with the data of {@code aliasToCopy}.
     */
    public AliasBuilder(Alias aliasToCopy) {
        this.alias = aliasToCopy;
    }

    public Alias build() {
        return this.alias;
    }

}
