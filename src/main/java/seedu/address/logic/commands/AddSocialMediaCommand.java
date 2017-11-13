package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOGLEPLUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TWITTER;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.socialmedia.UniqueSocialMediaList;
import seedu.address.model.tag.Tag;

/**
 * Adds a social media URL to a person.
 */
public class AddSocialMediaCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sadd";
    public static final String COMMAND_ALT = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Social Media URL to a person."
            + "Parameters: \n"
            + "p/PERSON INDEX "
            + "[" + PREFIX_FACEBOOK + "FACEBOOK_URL] "
            + "[" + PREFIX_TWITTER + "TWITTER_URL] "
            + "[" + PREFIX_INSTAGRAM + "INSTAGRAM_URL] "
            + "[" + PREFIX_GOOGLEPLUS + "GOOGLE PLUS_URL] \n"
            + "Example: " + COMMAND_WORD + " "
            + "p/2 " + PREFIX_FACEBOOK + "https://www.facebook.com/derek";

    public static final String MESSAGE_SOCIALMEDIA_ADD_SUCCESS = "Added a social media URL to %1$s.";

    private final Index personIndex;
    private final String socialMediaUrl;

    /**
     * Creates an CreateGroupCommand to add the specified {@code ReadOnlyGroup}
     */
    public AddSocialMediaCommand(Index personIndex, String socialMediaUrl) {
        this.personIndex = personIndex;
        this.socialMediaUrl = socialMediaUrl;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson toEdit = lastShownPersonList.get(personIndex.getZeroBased());
        String personName = toEdit.getName().toString();

        Person editedPerson;
        try {
            editedPerson = addSocialMediaToPerson(toEdit);
        } catch (ParseException e) {
            throw new AssertionError("Social media URL should be valid");
        }

        try {
            model.updatePerson(toEdit, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot exist in address book");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_SOCIALMEDIA_ADD_SUCCESS, personName));

    }

    /**
     * Creates and returns a {@code Person} with the the Favourite attribute set to true.
     */
    private Person addSocialMediaToPerson(ReadOnlyPerson personToEdit) throws ParseException {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Favourite updatedFavourite = personToEdit.getFavourite();
        Set<Tag> updatedTags = personToEdit.getTags();
        UniqueSocialMediaList socialMediaList = new UniqueSocialMediaList();
        try {
            socialMediaList.add(new SocialMedia(socialMediaUrl));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                socialMediaList.toSet(), updatedFavourite, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSocialMediaCommand // instanceof handles nulls
                && personIndex.equals(((AddSocialMediaCommand) other).personIndex));

    }
}
