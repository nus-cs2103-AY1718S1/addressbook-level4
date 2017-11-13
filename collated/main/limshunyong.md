# limshunyong
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
/**
 * Exports contact to external source (in .vcf format)
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export contact details to external source (in .vcf format).\n"
            + "Parameters: FILENAME \n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Contacts successfully exported as output.vcf !!";
    public static final String MESSAGE_FAIL = "Contacts not exported!!";
    public static final String MESSAGE_EMPTY_AB = "AddressBook is empty. Nothing to export !!";



    @Override
    public CommandResult execute() {
        if (model.getAddressBook().getPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_AB);
        } else {
            try {
                writeToFile();
            } catch (IOException e) {
                return new CommandResult(MESSAGE_FAIL);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    /**
     * This method handles the writing of contacts to a file
     */
    private void writeToFile() throws IOException {

        final String filename = "output.vcf";

        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);

        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {

            String header = "BEGIN:VCARD\n";
            String version = "VERSION:3.0\n";
            String fullName = "FN:" + p.getName().toString() + "\n";
            String name = "N:;" + p.getName().toString() + ";;;\n";
            String email = "EMAIL;TYPE=INTERNET;TYPE=HOME:" + p.getEmail().toString() + "\n";
            String tel = "TEL;TYPE=CELL:" + p.getPhone().toString() + "\n";
            String address = "ADR:;;" + p.getAddress().toString() + ";;;;\n";
            String footer = "END:VCARD\n";

            bw.write(header);
            bw.write(version);
            bw.write(fullName);
            bw.write(name);
            bw.write(email);
            bw.write(tel);
            bw.write(address);
            bw.write(footer);
        }

        if (bw != null) {
            bw.close();
        }

        if (fw != null) {
            fw.close();
        }

    }
}
```
###### /java/seedu/address/logic/commands/ImportCommand.java
``` java
/**
 * Imports contact from external source (in .vcf format)
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "im";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Import contact details from external source (must be in .vcf format).\n"
            + "Parameters: FILENAME \n"
            + "Example: " + COMMAND_WORD + " contacts.vcf";

    public static final String MESSAGE_SUCCESS = "Contacts successfully imported";
    public static final String MESSAGE_FAILURE = "Error importing contacts. File not found or Filename incorrect.";

    private ArrayList<ReadOnlyPerson> p;

    public ImportCommand(ArrayList<ReadOnlyPerson> list) {
        this.p = list;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (p.isEmpty()) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            try {
                for (ReadOnlyPerson pp : p) {
                    model.addPerson(pp);
                }
            } catch (DuplicatePersonException de) {
                throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
            }
            LoggingCommand loggingCommand = new LoggingCommand();
            loggingCommand.keepLog("", "Import Action");
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts the contacts in the address book based on name.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "ss";
    public static final String MESSAGE_SUCCESS = "Address book successfully sorted!";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortContact();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/parser/ImportCommandParser.java
``` java
/**
 * Parses input arguments as file and adds the contact into the address book
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String DEFAULT_NAME = "Example name";
    public static final String DEFAULT_ADD = "13 Computing Drive";
    public static final String DEFAULT_PHONE = "11111111";
    public static final String DEFAULT_EMAIL = "@example.com";
    public static final String DEFAULT_TAG = "containsDummy";

    /**
     * Parses the given {@code String} of arguments in the context of the Import command
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) {
        String filename = args.trim();
        ArrayList<ReadOnlyPerson> p = new ArrayList<ReadOnlyPerson>();
        BufferedReader br = null;
        FileReader fr = null;


        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            // Create Default values
            String currLine;
            String name = null;
            String email = null;
            String address = null;
            String phone = null;
            Set<Tag> tagList = new HashSet<Tag>();

            while ((currLine = br.readLine()) != null) {

                if (currLine.contains("FN")) {
                    name = currLine.split(":")[1];
                }

                if (currLine.contains("TEL")) {
                    phone = currLine.split(":")[1];
                }

                if (currLine.contains("ADR")) {
                    address = currLine.split(";")[2];
                }

                if (currLine.contains("EMAIL")) {
                    email = currLine.split(":")[1];
                }

                if (currLine.contains("END")) {
                    try {
                        if (email == null) {
                            email = name.replaceAll("\\s+", "") + "@example.com";
                            tagList.add(new Tag(DEFAULT_TAG + "email"));
                        }
                        if (name == null) {
                            name = DEFAULT_NAME;
                            tagList.add(new Tag(DEFAULT_TAG + "name"));
                        }
                        if (phone == null) {
                            phone = DEFAULT_PHONE;
                            tagList.add(new Tag(DEFAULT_TAG + "phone"));
                        }
                        if (address == null) {
                            address = DEFAULT_ADD;
                            tagList.add(new Tag(DEFAULT_TAG + "address"));
                        }

                        Name n = new Name(name);
                        Phone pe = new Phone(phone);
                        Email e = new Email(email);
                        Address a = new Address(address);
                        ReadOnlyPerson person = new Person(n, pe, e, a, tagList);
                        p.add(person);

                    } catch (IllegalValueException ie) {
                        ie.getMessage();
                    }
                }
            }
        } catch (IOException e) {
            e.getMessage();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return new ImportCommand(p);
    }


}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     *  Sorts the address book
     */
    public void sort() {
        persons.sort();
    }

```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the list
     */
    public void sort() {
        internalList.sort((person1, person2) -> (
                person1.getName().fullName
                        .compareToIgnoreCase(person2.getName().fullName)));
    }
```
###### /java/seedu/address/storage/AddressBookStorage.java
``` java
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
}
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-copy.xml");
    }

```
