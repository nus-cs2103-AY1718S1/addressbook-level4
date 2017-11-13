package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author lincredibleJC
/**
 * Provides a handle to the Statistics panel.
 */
public class StatisticsPanelHandle extends NodeHandle<Node> {

    public static final String STATISTICS_PANEL_ID = "#statisticsPanelPlaceholder";
    public static final String MEAN_FIELD_ID = "#mean";
    private static final String MEDIAN_FIELD_ID = "#median";
    private static final String MODE_FIELD_ID = "#mode";
    private static final String VARIANCE_FIELD_ID = "#variance";
    private static final String STANDARD_DEVIATION_FIELD_ID = "#standardDeviation";
    private static final String QUARTILE3_FIELD_ID = "#quartile3";
    private static final String QUARTILE1_FIELD_ID = "#quartile1";
    private static final String INTERQUARTILERANGE_FIELD_ID = "#interQuartileRange";


    private final Label meanLabel;
    private final Label medianLabel;
    private final Label modeLabel;
    private final Label varianceLabel;
    private final Label standardDeviationLabel;
    private final Label quartile3Label;
    private final Label quartile1Label;
    private final Label interquartileLabel;

    public StatisticsPanelHandle(Node node) {
        super(node);

        this.meanLabel = getChildNode(MEAN_FIELD_ID);
        this.medianLabel = getChildNode(MEDIAN_FIELD_ID);
        this.modeLabel = getChildNode(MODE_FIELD_ID);
        this.varianceLabel = getChildNode(VARIANCE_FIELD_ID);
        this.standardDeviationLabel = getChildNode(STANDARD_DEVIATION_FIELD_ID);
        this.quartile3Label = getChildNode(QUARTILE3_FIELD_ID);
        this.quartile1Label = getChildNode(QUARTILE1_FIELD_ID);
        this.interquartileLabel = getChildNode(INTERQUARTILERANGE_FIELD_ID);
    }

    public String getMeanLabel() {
        return meanLabel.getText();
    }

    public String getMedianLabel() {
        return medianLabel.getText();
    }

    public String getModeLabel() {
        return modeLabel.getText();
    }

    public String getVarianceLabel() {
        return varianceLabel.getText();
    }

    public String getStandardDeviationLabel() {
        return standardDeviationLabel.getText();
    }

    public String getQuartile3Label() {
        return quartile3Label.getText();
    }

    public String getQuartile1Label() {
        return quartile1Label.getText();
    }

    public String getInterquartileLabel() {
        return interquartileLabel.getText();
    }
}
