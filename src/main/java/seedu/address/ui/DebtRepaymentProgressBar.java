package seedu.address.ui;

import static seedu.address.model.person.Deadline.NO_DEADLINE_SET;

import java.time.LocalDate;
import java.time.Period;

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
    private static final String OVERDUE_REPAYMENT_MESSAGE = "Overdue";
    private static final String NO_DEADLINE_REPAYMENT_MESSAGE = "No deadline set";
    private static final String YEARS_LEFT_TO_REPAY_DEBT_MESSAGE = "%1$s years(s)";
    private static final String MONTHS_LEFT_TO_REPAY_DEBT_MESSAGE = " %1$s month(s)";
    private static final String DAYS_LEFT_TO_REPAY_DEBT_MESSAGE = " %1$s day(s)";
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
        } else if (deadline.toString().equals(NO_DEADLINE_SET)) {
            return new SimpleObjectProperty<>(NO_DEADLINE_REPAYMENT_MESSAGE);
        } else {
            LocalDate deadlineDate = deadline.getDeadlineAsLocalDate();
            LocalDate today = LocalDate.now();

            if (Period.between(today, deadlineDate).isNegative()) {
                return new SimpleObjectProperty<>(OVERDUE_REPAYMENT_MESSAGE);
            } else {
                Period timeInterval = Period.between(today, deadlineDate);
                long years = timeInterval.getYears();
                long months = timeInterval.getMonths();
                long days = timeInterval.getDays();

                return new SimpleObjectProperty<>(formatTimeTillDeadline(years, months, days));
            }
        }
    }

    /**
     * Formats the difference in time from now till a deadline into a {@code String}
     * @param years years left till deadline
     * @param months months left till deadline
     * @param days days left till deadline
     * @return a formatted string that states the difference between now and the deadline
     */
    private String formatTimeTillDeadline(long years, long months, long days) {
        StringBuilder stringBuilder = new StringBuilder();
        if (years != 0) {
            stringBuilder.append(String.format(YEARS_LEFT_TO_REPAY_DEBT_MESSAGE, years));
        }
        if (months != 0) {
            stringBuilder.append(String.format(MONTHS_LEFT_TO_REPAY_DEBT_MESSAGE, months));
        }
        if (days != 0) {
            stringBuilder.append(String.format(DAYS_LEFT_TO_REPAY_DEBT_MESSAGE, days));
        }
        stringBuilder.append(" till deadline");
        return stringBuilder.toString();
    }
}
