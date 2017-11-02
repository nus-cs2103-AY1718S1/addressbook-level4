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

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label percentage;
    @FXML
    private Label daysLeft;

    public DebtRepaymentProgressBar(ReadOnlyPerson person) {
        super(FXML);
        Double totalDebt = person.getTotalDebt().toNumber();
        Double repaid = totalDebt - person.getDebt().toNumber();
        progressBar.setProgress(repaid / totalDebt);
        daysLeft.textProperty().bind(Bindings.convert(getDaysLeft(person)));
        percentage.textProperty().bind(Bindings.convert(getPercentage(repaid, totalDebt)));
        progressBar.getStyleClass().clear();
        progressBar.getStyleClass().add("progress-bar");
        registerAsAnEventHandler(this);
    }

    /**
     * Returns a {@code OcbservableValue<String>} to bind to {@code percentage} property
     */
    private ObservableValue<String> getPercentage(Double repaid, Double totalDebt) {
        String percentage = String.format("%.2f", repaid / totalDebt * 100);
        return new SimpleObjectProperty<>(percentage.concat("%"));
    }

    /**
     * Returns a {@code ObservableValue<String>} to bind to {@code daysLeft} property
     */
    private ObservableValue<String> getDaysLeft(ReadOnlyPerson person) {
        Deadline deadline = person.getDeadline();
        if (!deadline.toString().equals(NO_DEADLINE_SET)) {
            String day = deadline.getDay();
            String month = deadline.getMonth();
            String year = deadline.getYear();
            String deadlineFormatted = year + month + day;

            LocalDate deadlineDate = LocalDate.parse(deadlineFormatted, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate today = LocalDate.now();
            return new SimpleObjectProperty<>(Long.toString(
                    ChronoUnit.DAYS.between(today, deadlineDate)) + " days left to repay debt");
        }
        return new SimpleObjectProperty<>(deadline.toString());
    }
}
