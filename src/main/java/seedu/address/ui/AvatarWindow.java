package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author teclu
/**
 * Controller for avatar page
 */
public class AvatarWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(AvatarWindow.class);
    private static final String ICON = "/images/AvatarWindowIcon.png";
    private static final String FXML = "AvatarWindow.fxml";
    private static final String TITLE = "Avatar Options";

    @FXML
    private ImageView avatarW;
    private PersonCard selectedPersonCard;
    private String url;

    private final Logic logic;
    private final Stage dialogStage;

    public AvatarWindow(PersonCard selectedPersonCard, Logic logic) {
        super(FXML);

        this.logic = logic;
        this.selectedPersonCard = selectedPersonCard;

        Scene scene = new Scene(getRoot());

        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(false);
        dialogStage.setAlwaysOnTop(true);
        dialogStage.setHeight(325);
        dialogStage.setWidth(250);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        avatarW.setImage(SwingFXUtils.toFXImage(selectedPersonCard.person.getAvatar().getImage(), null));
        show();
    }

    /**
     * Shows the avatar window.
     */
    public void show() {
        logger.fine("Showing avatar window.");
        dialogStage.showAndWait();
    }

    /**
     * Opens the file dialog window upon clicking the load button.
     */
    @FXML
    private void loadPrompt() throws IOException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJpg = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJpg, extFilterPng);

        File file = fileChooser.showOpenDialog(null);

        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        avatarW.setImage(image);
        url = file.getPath();
    }

    /**
     * Saves the avatar upon clicking the save button by executing an edit command.
     */
    @FXML
    private void savePrompt() {
        int displayedIndex = selectedPersonCard.getDisplayedIndex();

        try {
            CommandResult commandResult = logic.execute("edit " + displayedIndex + " v/" + url);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            dialogStage.close();
        } catch (CommandException | ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
}
