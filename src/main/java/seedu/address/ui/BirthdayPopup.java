package seedu.address.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

//@@author liliwei25
/**
 * Creates a birthday notification
 */
public class BirthdayPopup {
    private static final int WIDTH = 300;
    private static final int MIN_HEIGHT = 125;
    private static final int INCREMENT = 50;
    private static final String BIRTHDAY_ALERT = "Birthday Alert!";
    private static final String ICON_LOCATION = "/images/birthday_cake.png";
    private static final String ICON_DESCRIPTION = "birthday icon";
    private static final String CLOSE_BUTTON = "x";
    private static final String BIRTHDAY_MESSAGE = "There are birthdays today: \n";
    private static final int DELAY = 5000;

    private JDialog frame = new JDialog();
    private GridBagConstraints constraints = new GridBagConstraints();

    public BirthdayPopup(String[] person) {
        createFrame(person.length);
        createIcon();
        createCloseButton();
        createMessage(person);
        createPopup();
    }

    /**
     * Creates the dialog for the popup
     *
     * @param size of popup
     */
    private void createFrame(int size) {
        frame.setSize(WIDTH, MIN_HEIGHT + size * INCREMENT);
        frame.setUndecorated(true);
        frame.setLayout(new GridBagLayout());
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height - toolHeight.bottom - frame.getHeight());
        frame.setAlwaysOnTop(true);
    }

    /**
     * Creates the icon for the popup
     */
    private void createIcon() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;

        Icon headingIcon = new ImageIcon(getClass().getResource(ICON_LOCATION), ICON_DESCRIPTION);
        JLabel headingLabel = new JLabel(BIRTHDAY_ALERT);
        headingLabel.setIcon(headingIcon);
        headingLabel.setOpaque(false);
        frame.add(headingLabel, constraints);
    }

    /**
     * Creates the close button for the popup
     */
    private void createCloseButton() {
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        JButton closeButton = new JButton(new AbstractAction(CLOSE_BUTTON) {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
            }
        });
        closeButton.setMargin(new Insets(1, 4, 1, 4));
        closeButton.setFocusable(false);
        frame.add(closeButton, constraints);
    }

    /**
     * create the message for all the person with birthday
     *
     * @param person names that have birthdays
     */
    private void createMessage(String[] person) {
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel messageLabel = new JLabel(BIRTHDAY_MESSAGE);
        frame.add(messageLabel, constraints);
        for (String e: person) {
            constraints.gridy++;
            messageLabel = new JLabel(e);
            frame.add(messageLabel, constraints);
        }
        frame.setVisible(true);
    }

    /**
     * create a timer for the popup
     */
    private void createPopup() {
        new Thread(() -> {
            try {
                Thread.sleep(DELAY);
                frame.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
