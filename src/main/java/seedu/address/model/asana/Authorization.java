package seedu.address.model.asana;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.asana.Client;
import com.asana.OAuthApp;
import com.asana.models.Attachment;
import com.asana.models.Project;
import com.asana.models.Task;
import com.asana.models.User;
import com.asana.models.Workspace;
import com.google.common.io.LineReader;

import jdk.nashorn.internal.ir.annotations.Ignore;

/***
 * Performs OAuth for asana
 */
@Ignore
public class Authorization {
    /***
     * Performs OAuth for asana
     */
    public static void main(String[] args) throws Exception {

        // create an OAuth app with the OAuth credentials:
        OAuthApp app = new OAuthApp(
                "474342738710406",
                "a89bbb49213d6b58ebce25cfa0995290",
                OAuthApp.NATIVE_REDIRECT_URI
        );

        // create an OAuth client with the app
        Client client = Client.oauth(app);

        System.out.println("isAuthorized=" + app.isAuthorized());

        // get an authorization URL:
        String url = app.getAuthorizationUrl("FIXME: random state");
        System.out.println(url);

        // in a web app you'd redirect the user to this URL when they take action to
        // login with asana or connect their account to asana
        Desktop.getDesktop().browse(new URI(url));

        // prompt the user to copy and paste the code from the browser window
        System.out.println("Copy and paste the returned code from the browser and press enter:");
        String code = new LineReader(new InputStreamReader(System.in)).readLine();

        // exchange the code for a bearer token
        // normally you'd persist this token somewhere
        String accessToken = app.fetchToken(code);

        System.out.println("isAuthorized=" + app.isAuthorized());
        System.out.println("token=" + accessToken);

        // get some information about your own user
        User user = client.users.me().execute();
        System.out.println("me=" + user.name);
        System.out.println(user.id);

        // get your photo, if you have one
        if (user.photo != null) {
            System.out.println(user.photo.image_128x128);
        }

        System.out.println(user.workspaces.iterator().next().name);

        // demonstrate creating a client using a previously obtained bearer token
        System.out.println("== Example using OAuth Access Token:");
        app = new OAuthApp(
                "474342738710406",
                "a89bbb49213d6b58ebce25cfa0995290",
                "urn:ietf:wg:oauth:2.0:oob",
                "0/b62305d262c673af5c042bfad54ef834"
        );
        client = Client.oauth(app);

        System.out.println("isAuthorized=" + app.isAuthorized());
        System.out.println("me=" + client.users.me().execute().name);
        postTask();
    }

    /***
    * Posts a task onto users meeting project on Asana
    */
    public static void postTask() throws Exception {
        /***
        * Posts a task onto users meeting project on Asana
        */
        /*
        OAuthApp app = new OAuthApp(
                "474342738710406",
                "a89bbb49213d6b58ebce25cfa0995290",
                OAuthApp.NATIVE_REDIRECT_URI
        );*/

        // create an OAuth client with the app
        OAuthApp app = new OAuthApp(
                "474342738710406",
                "a89bbb49213d6b58ebce25cfa0995290",
                "urn:ietf:wg:oauth:2.0:oob",
                "0/b62305d262c673af5c042bfad54ef834"
        );
        //Client client = Client.oauth(app);*/

        Client client = Client.accessToken("0/b62305d262c673af5c042bfad54ef834");
        //System.out.println("isAuthorized=" + app.isAuthorized());

        //get an authorization URL:
        String url = app.getAuthorizationUrl("random state");
        System.out.println(url);

        // in a web app you'd redirect the user to this URL when they take action to
        // login with asana or connect their account to asana
        Desktop.getDesktop().browse(new URI(url));

        // prompt the user to copy and paste the code from the browser window
        System.out.println("Copy and paste the returned code from the browser and press enter:");
        String code = new LineReader(new InputStreamReader(System.in)).readLine();

        // exchange the code for a bearer token
        // normally you'd persist this token somewhere
        String accessToken = app.fetchToken(code);

        System.out.println("isAuthorized=" + app.isAuthorized());
        System.out.println("token=" + accessToken);

        // get some information about your own user
        User user = client.users.me().execute();
        System.out.println("me=" + user.name);
        System.out.println(user.id);

        // get your photo, if you have one
        if (user.photo != null) {
            System.out.println(user.photo.image_128x128);
        }

        System.out.println(user.workspaces.iterator().next().name);

        // demonstrate creating a client using a previously obtained bearer token
        System.out.println("== Example using OAuth Access Token:");

        //client = Client.oauth(app);

        //System.out.println("isAuthorized=" + app.isAuthorized());
        System.out.println("me=" + client.users.me().execute().name);

        // find your "Personal Projects" project
        Workspace personalProjects = null;
        for (Workspace workspace : client.workspaces.findAll()) {
            if (workspace.name.equals("Personal Projects")) {
                personalProjects = workspace;
                break;
            }
        }

        // create a "demo project" if it doesn't exist
        List<Project> projects = client.projects.findByWorkspace(personalProjects.id).execute();
        Project demoProject = null;
        for (Project project : projects) {
            if (project.name.equals("Meetings")) {
                demoProject = project;
                break;
            }
        }
        if (demoProject == null) {
            demoProject = client.projects.createInWorkspace(personalProjects.id)
                    .data("name", "Meetings")
                    .execute();
        }

        // create a task in the project
        Task demoTask = client.tasks.createInWorkspace(personalProjects.id)
                .data("name", "demo task created at " + new Date())
                .data("projects", Arrays.asList(demoProject.id))
                .data("due_on", "2017-11-09")
                .data("assignee", user)
                .execute();


        System.out.println("Task " + demoTask.id + " created.");

        // add an attachment to the task
        Attachment demoAttachment = client.attachments.createOnTask(
                demoTask.id,
                new ByteArrayInputStream("hello world".getBytes()),
                "upload.txt",
                "text/plain"
        ).execute();
        System.out.println("Attachment " + demoAttachment.id + " created.");
    }

}


