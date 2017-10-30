package seedu.address.logic.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Imports contact from a .vcf file.
 * Adds the contacts into the address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a .vcf file.";

    public static final String MESSAGE_SUCCESS = "Successfully imported contacts. %1$s duplicates were found";
    public static final String MESSAGE_FILEERROR = "Please ensure that VCard file contents are correct.";
    public static final String MESSAGE_NOFILECHOSEN = "No files were selected";

    private final BufferedInputStream bis;
    private final BufferedReader br;

    public ImportCommand (FileInputStream fis) {
        bis = new BufferedInputStream(fis);
        br = new BufferedReader(new InputStreamReader(bis));
    }

    public CommandResult executeUndoableCommand() throws CommandException {
        int duplicate = 0;

        try {
            String newLine = br.readLine();

            while (newLine != null) {
                if (newLine.equals("BEGIN:VCARD")) {
                    Name name = null;
                    Email email = null;
                    Phone phone = null;
                    Address address = null;
                    Remark remark = new Remark("");
                    Set<Tag> tagList = new HashSet<>();

                    newLine = br.readLine();
                    while (!newLine.equals("END:VCARD")) {
                        String type = newLine.split(":")[0];
                        String parameter = newLine.split(":")[1];

                        switch (type) {
                        case "FN":
                            name = new Name(parameter);
                            break;

                        case "EMAIL":
                            email = new Email(parameter);
                            break;

                        case "TEL":
                            phone = new Phone(parameter);
                            break;

                        case "ADR":
                            address = new Address(parameter);
                            break;
                        case "RM":
                            remark = new Remark(parameter);
                            break;
                        case "TAG":
                            Tag tag = new Tag(parameter);
                            tagList.add(tag);
                            break;
                        }
                        newLine = br.readLine();
                    }
                    newLine = br.readLine();
                    ReadOnlyPerson toAdd = new Person(name, phone, email, address, remark, tagList);
                    try {
                        model.addPerson(toAdd);
                    } catch (DuplicatePersonException dpe) {
                        duplicate++;
                    }
                } else {
                    newLine = br.readLine();
                }
            }
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILEERROR);
        } catch (IllegalValueException ive) {
            throw new CommandException("Data problem");
        } finally {
            try {
                bis.close();
                br.close();
            } catch (IOException ioe) {
                throw new CommandException("Problem Closing File");
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, duplicate));
    }
}
