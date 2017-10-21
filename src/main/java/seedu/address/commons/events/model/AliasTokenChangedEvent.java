package seedu.address.commons.events.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.alias.ReadOnlyAliasToken;


/**
 * Represents an event where the user has changed an AliasToken
 */
public class AliasTokenChangedEvent extends BaseEvent {

    /**
     * Action markers for each AliasToken added and removed
     */
    public enum Action {
        Added,
        Removed
    }

    private final ReadOnlyAliasToken token;
    private final Action action;

    public AliasTokenChangedEvent(ReadOnlyAliasToken token, Action action) {
        requireAllNonNull(token, action);

        this.token = token;
        this.action = action;
    }

    @Override
    public String toString() {
        return "Alias token " + action.toString() + ": " + token.toString();
    }

    public ReadOnlyAliasToken getToken() {
        return this.token;
    }

    public Action getAction() {
        return this.action;
    }

}
