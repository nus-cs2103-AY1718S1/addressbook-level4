package seedu.address.model.asana;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.asana.Client;
import com.asana.models.Project;
import com.asana.models.User;
import com.asana.models.Workspace;

import jdk.nashorn.internal.ir.annotations.Ignore;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.AsanaAuthenticationException;
import seedu.address.storage.asana.storage.AsanaCredentials;

//@@author Sri-vatsa
/***
 * Posts a task onto users meeting project on Asana
 */
@Ignore
public class PostTask extends Command {

    private String notes;
    private String date;

    public PostTask(String notes, String localDate) throws ParseException {
        this.notes = notes;
        this.date = dateFormatter(localDate);
    }
    @Override
    public CommandResult execute() throws CommandException {

        Client client;
        try {
            new CheckAuthenticateAsanaUser();
            client = Client.accessToken((new AsanaCredentials()).getAccessToken());
            //get user data
            User user = client.users.me().execute();

            // find user's "Personal Projects" project //default asana personal workspace
            Workspace meetingsWorkspace = null;
            for (Workspace workspace : client.workspaces.findAll()) {
                if (workspace.name.equals("Personal Projects")) {
                    meetingsWorkspace = workspace;
                    break;
                }
            }

            if (meetingsWorkspace == null) {
                throw new CommandException("Please create a workspace called "
                        + "\"Personal Projects\" in your Asana account");
            }

            // create a "Meetings" if it doesn't exist
            List<Project> projects = client.projects.findByWorkspace(meetingsWorkspace.id).execute();
            Project myMeetings = null;
            for (Project project : projects) {
                if (project.name.equals("Meetings")) {
                    myMeetings = project;
                    break;
                }
            }
            if (myMeetings == null) {
                myMeetings = client.projects.createInWorkspace(meetingsWorkspace.id)
                        .data("name", "Meetings")
                        .execute();
            }

            // create a task in the project
            client.tasks.createInWorkspace(meetingsWorkspace.id)
                    .data("name", notes)
                    .data("projects", Arrays.asList(myMeetings.id))
                    .data("due_on", date)
                    .data("assignee", user)
                    .execute();

        } catch (IOException io) {
            throw new CommandException("Please setup Asana again!");
        } catch (AsanaAuthenticationException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult("");
    }

    /**
     * Formats date to suit input needs of Asana API
     */
    private String dateFormatter (String date) throws ParseException {

        DateFormat dateParse = new SimpleDateFormat("yyyy/mm/dd");

        Date formattedDate = dateParse.parse(date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        return dateFormat.format(formattedDate);

    }

}
