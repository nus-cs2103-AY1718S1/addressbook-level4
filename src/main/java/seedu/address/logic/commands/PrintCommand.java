package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

public class PrintCommand extends Command {

    private final String fileName;

    public PrintCommand(String filename) {
        requireNonNull(filename);

        this.fileName = filename;
    }

    @Override
    public CommandResult execute() {
        //EventsCenter.getInstance().post(new ShowHelpRequestEvent());

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        List<String> lines = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("dd/MM/YYYY" + " "+ "HH:mm:ss").format(new Date());
        lines.add("Addressbook was last updated on: " + timeStamp +"\n");


        /*
        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
        Name name = personToDelete.getName();
        Address address = personToDelete.getAddress();
        String reason = personToDelete.getReason();
        return new CommandResult(reason);
        */
        return new CommandResult("Lol");
    }

}
