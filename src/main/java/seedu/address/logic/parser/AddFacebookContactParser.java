package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.ResponseList;
import facebook4j.User;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddFacebookContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.social.SocialInfo;
import seedu.address.model.tag.Tag;

//@@author alexfoodw
/**
 * Parses the given {@code String} of arguments in the context of the AddFacebookContactCommand
 * and returns an AddFacebookContactCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddFacebookContactParser implements Parser<AddFacebookContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFacebookContactCommand
     * and returns an AddFacebookContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFacebookContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacebookContactCommand.MESSAGE_USAGE));
        }

        try {
            Facebook facebook = new FacebookFactory().getInstance();
            ResponseList<User> contactsList = facebook.searchUsers(trimmedArgs);
            User user = contactsList.get(0);

            Email email;
            // create new person object
            if (user.getEmail() != null) {
                email = new Email(user.getEmail());
            } else {
                // Placeholder for users without username
                email = new Email("placeholder@example.com");
            }

            Set<Tag> tags = new HashSet<>();
            tags.add(new Tag("facebookcontact"));

            Favorite favorite = new Favorite(false);

            Set<SocialInfo> socialInfos = new HashSet<>();
            // TODO(Marvin): Make social media identifiers public
            SocialInfo facebookInfo = SocialInfoMapping.parseSocialInfo("facebook " + user.getUsername());
            socialInfos.add(facebookInfo);


            Person newPerson = new Person(
                    new Name(user.getName()),
                    new Phone("000"), // Placeholder phone number
                    email,
                    new Address("Placeholder Address"), // Placeholder address
                    favorite,
                    tags,
                    socialInfos);

            return new AddFacebookContactCommand(newPerson);
        } catch (FacebookException e) {
            // TODO(Alex): Properly handle exceptions here
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacebookContactCommand.MESSAGE_USAGE));
    }

}
