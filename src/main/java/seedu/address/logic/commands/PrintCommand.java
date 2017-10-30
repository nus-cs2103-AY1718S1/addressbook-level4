package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        int personIndex = 1;
        for (ReadOnlyPerson person: lastShownList) {
            String entry = personIndex + ". " + person.getAsText();
            lines.add(entry);
            lines.add("\n");
            personIndex++;
        }

        Path file = Paths.get("doc/books/"+ fileName +".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult("lol");
    }

}
