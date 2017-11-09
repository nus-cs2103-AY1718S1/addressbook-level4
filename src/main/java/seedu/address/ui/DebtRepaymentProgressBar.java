package seedu.address.ui;

import static seedu.address.model.person.Deadline.NO_DEADLINE_SET;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jelneo
/**
 * Displays debt repayment progress bar for each person
 */
public class DebtRepaymentProgressBar extends UiPart<Region> {
    private static final String FXML = "DebtRepaymentProgressBar.fxml";
    private static final String COMPLETED_REPAYMENT_MESSAGE = "Completed";
    private static final String NO_DEADLINE_REPAYMENT_MESSAGE = "No deadline set";
    public static final String DAYS_LEFT_TO_REPAY_DEBT_MESSAGE = " day(s) left to repay debt";
    private Double totalDebt;
    private Double repaid;
    private Double ratio;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label percentage;
    @FXML
    private Label repaymentInfo;

    public DebtRepaymentProgressBar(ReadOnlyPerson person) {
        super(FXML);

        totalDebt = person.getTotalDebt().toNumber();
        repaid = totalDebt - person.getDebt().toNumber();
        ratio = repaid / totalDebt;
        progressBar.setProgress(ratio);
        repaymentInfo.textProperty().bind(Bindings.convert(getRepaymentStatus(person)));
        repaymentInfo.textProperty().addListener((unused1, unused2, unused3) -> {
            // style repaymentInfo whenever its value changes
            styleRepaymentProgressInfo();
        });
        percentage.textProperty().bind(Bindings.convert(getPercentage(ratio)));
        percentage.textProperty().addListener((unused1, unused2, unused3) -> {
            getRepaymentStatus(person);
        });
        progressBar.getStyleClass().clear();
        progressBar.getStyleClass().add("progress-bar");
        styleRepaymentProgressInfo();
        registerAsAnEventHandler(this);
    }

    /**
     * Styles repayment information that is located next to the debt repayment progress bar
     */
    private void styleRepaymentProgressInfo() {
        String repaymentInformation = repaymentInfo.getText();
        if (repaymentInformation.equals(COMPLETED_REPAYMENT_MESSAGE)) {
            repaymentInfo.setId("completedText");
        } else if (repaymentInformation.equals(NO_DEADLINE_REPAYMENT_MESSAGE)) {
            repaymentInfo.setId("noDeadlineText");
        } else {
            repaymentInfo.setId("repaymentInfoText");
        }
    }

    /**
     * Returns a {@code ObservableValue<String>} to bind to {@code percentage} property
     */
    private ObservableValue<String> getPercentage(Double ratio) {
        String percentage = String.format("%.2f", ratio * 100);
        return new SimpleObjectProperty<>(percentage.concat("%"));
    }

    /**
     * Returns a {@code ObservableValue<String>} to bind to {@code repaymentInfo} property
     */
    private ObservableValue<String> getRepaymentStatus(ReadOnlyPerson person) {
        Deadline deadline = person.getDeadline();
        if (person.getDebt().toNumber() == 0) {
            return new SimpleObjectProperty<>(COMPLETED_REPAYMENT_MESSAGE);
        } else if (!deadline.toString().equals(NO_DEADLINE_SET)) {
            String day = deadline.getDay();
            String month = deadline.getMonth();
            String year = deadline.getYear();
            String deadlineFormatted = year + month + day;

            LocalDate deadlineDate = LocalDate.parse(deadlineFormatted, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate today = LocalDate.now();
            return new SimpleObjectProperty<>(Long.toString(
                    ChronoUnit.DAYS.between(today, deadlineDate)) + DAYS_LEFT_TO_REPAY_DEBT_MESSAGE);
        } else {
            return new SimpleObjectProperty<>(NO_DEADLINE_REPAYMENT_MESSAGE);
        }
    }
}
