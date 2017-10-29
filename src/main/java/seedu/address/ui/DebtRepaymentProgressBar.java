package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
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

    public DebtRepaymentProgressBar(ReadOnlyPerson person) {
        super(FXML);
        Double totalDebt = person.getTotalDebt().toNumber();
        Double repaid = totalDebt - person.getDebt().toNumber();
        progressBar.setProgress(repaid / totalDebt);
        percentage.textProperty().bind(Bindings.convert(getPercentage(repaid, totalDebt)));
        progressBar.getStyleClass().clear();
        progressBar.getStyleClass().add("progress-bar");
        registerAsAnEventHandler(this);
    }

    /**
     * Returns a {@code OcbservableValue<String>} to bind to {@code percentage} property
     */
    private ObservableValue<String> getPercentage(Double repaid, Double totalDebt) {
        String percentage = Double.toString(repaid / totalDebt * 100).concat("%");
        return new SimpleObjectProperty<>(percentage);
    }
}
