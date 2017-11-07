package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

//@@author keithsoc-reused
/**
 * Manages resizing of UI Window by adding mouse event listeners to each UI component.
 * To be used when JavaFX StageStyle is set to TRANSPARENT or UNDECORATED.
 *
 * By: Evgenii Kanivets and Alexander.Berg
 * Adapted From: https://stackoverflow.com/questions/19455059/allow-user-to-resize-an-undecorated-stage
 */
public class UiResize {

    /**
     * Registers an event handler for the Scene in the specified Stage (top-level UI container)
     * @param stage
     */
    public static void enableResizableWindow(Stage stage,
                                             double minWidth,
                                             double minHeight,
                                             double maxWidth,
                                             double maxHeight) {
        ResizeHandler resizeHandler = new ResizeHandler(stage);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeHandler);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeHandler);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeHandler);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeHandler);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeHandler);

        resizeHandler.setMinWidth(minWidth);
        resizeHandler.setMinHeight(minHeight);
        resizeHandler.setMaxWidth(maxWidth);
        resizeHandler.setMaxHeight(maxHeight);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addHandlerToNodes(child, resizeHandler);
        }
    }

    /**
     * Registers an event handler for each UI node in the hierarchy
     * @param node
     * @param handler
     */
    private static void addHandlerToNodes(Node node, EventHandler<MouseEvent> handler) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, handler);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, handler);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, handler);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addHandlerToNodes(child, handler);
            }
        }
    }

    /**
     * Configures mouse event handler
     */
    static class ResizeHandler implements EventHandler<MouseEvent> {
        private Stage stage;
        private Cursor cursorEvent = Cursor.DEFAULT;
        private int border = 4;
        private double startX = 0;
        private double startY = 0;

        // Max and min sizes for controlled stage
        private double minWidth;
        private double maxWidth;
        private double minHeight;
        private double maxHeight;

        public ResizeHandler(Stage stage) {
            this.stage = stage;
        }

        public void setMinWidth(double minWidth) {
            this.minWidth = minWidth;
        }

        public void setMinHeight(double minHeight) {
            this.minHeight = minHeight;
        }

        public void setMaxWidth(double maxWidth) {
            this.maxWidth = maxWidth;
        }

        public void setMaxHeight(double maxHeight) {
            this.maxHeight = maxHeight;
        }

        private void setStageWidth(double width) {
            width = Math.min(width, maxWidth);
            width = Math.max(width, minWidth);
            stage.setWidth(width);
        }

        private void setStageHeight(double height) {
            height = Math.min(height, maxHeight);
            height = Math.max(height, minHeight);
            stage.setHeight(height);
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            Scene scene = stage.getScene();

            double mouseEventX = mouseEvent.getSceneX();
            double mouseEventY = mouseEvent.getSceneY();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                if (mouseEventX < border && mouseEventY < border) {
                    cursorEvent = Cursor.NW_RESIZE;
                } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                    cursorEvent = Cursor.NE_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE;
                } else if (mouseEventX < border) {
                    cursorEvent = Cursor.W_RESIZE;
                } else if (mouseEventX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE;
                } else if (mouseEventY < border) {
                    cursorEvent = Cursor.N_RESIZE;
                } else if (mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE;
                } else {
                    cursorEvent = Cursor.DEFAULT;
                }
                scene.setCursor(cursorEvent);
            } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType)
                    || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
                scene.setCursor(Cursor.DEFAULT);
            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                startX = stage.getWidth() - mouseEventX;
                startY = stage.getHeight() - mouseEventY;
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {

                if (!Cursor.DEFAULT.equals(cursorEvent)) {
                    if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                        double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
                        if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent)
                                || Cursor.NE_RESIZE.equals(cursorEvent)) {
                            if (stage.getHeight() > minHeight || mouseEventY < 0) {
                                setStageHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
                                stage.setY(mouseEvent.getScreenY());
                            }
                        } else {
                            if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
                                setStageHeight(mouseEventY + startY);
                            }
                        }
                    }

                    if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                        double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
                        if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent)
                                || Cursor.SW_RESIZE.equals(cursorEvent)) {
                            if (stage.getWidth() > minWidth || mouseEventX < 0) {
                                setStageWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
                                stage.setX(mouseEvent.getScreenX());
                            }
                        } else {
                            if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
                                setStageWidth(mouseEventX + startX);
                            }
                        }
                    }
                }
            }
        }
    }
}
//@@author
