package seedu.address.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Duration;

//@@author fongwz
/**
 * UI component to load splash screen and animate it
 */
public class SplashScreen extends UiPart<Region> {

    private static final String FXML = "SplashScreen.fxml";

    private Timeline timeline;

    @FXML
    private ImageView splashImage;

    @FXML
    private ImageView splashLoadingImage;


    public SplashScreen() {
        super(FXML);
        splashImage.setImage(new Image("/images/SplashScreen.png"));
        splashLoadingImage.setImage(new Image("/images/SplashScreenLoading.png"));
        setAnimation();
    }

    private void setAnimation() {
        KeyValue moveRight = new KeyValue(splashLoadingImage.translateXProperty(), 460);

        EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashLoadingImage.setTranslateX(-92);
            }
        };

        KeyFrame kf = new KeyFrame(Duration.millis(2000), onFinished, moveRight);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().addAll(kf);
        timeline.play();
    }
}
