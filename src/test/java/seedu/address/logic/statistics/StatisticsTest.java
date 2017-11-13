package seedu.address.logic.statistics;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.statistics.Statistics.INSUFFICIENT_DATA_MESSAGE;
import static seedu.address.logic.statistics.Statistics.NO_PERSONS_MESSAGE;

import org.junit.Test;

//@@author lincredibleJC
public class StatisticsTest {

    private Statistics statistics1 = new Statistics(new double[]{64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120,
        51135, 67060}); // Small data set
    private Statistics statistics2 = new Statistics(new double[]{6392, 51608, 71247, 14271, 48327, 50618, 67435, 47029,
        61857, 22987, 64858, 99745, 75504, 85464, 60482, 30320, 11342, 48808, 66882, 40522}); // Medium data set
    private Statistics statistics3 = new Statistics(new double[]{33664, 35702, 7049, 74334, 5725, 12090, 62774, 1149,
        81848, 84123, 17698, 42269, 42457, 80934, 83409, 19075, 87353, 63407, 20669, 36812, 44473, 46723, 41091, 54903,
        1749, 88514, 65760, 47322, 17365, 24779, 20301, 97839, 69612, 13975, 89694, 14585, 37259, 38361, 13720, 18729,
        8283, 3886, 26681, 8005, 48460, 13101, 52287, 44583, 878, 16133, 6334, 21592, 87253, 34537, 10974, 87484, 36937,
        79336, 78434, 76977, 75040, 77796, 62173, 63217, 68712, 60934, 88017, 3811, 41145, 57471, 6887, 74612, 78798,
        7308, 88094, 43245, 57157, 86406, 2922, 42919, 74375, 40076, 26030, 65306, 94610, 11923, 90367, 5603, 52189,
        45765, 44982, 27234, 77150, 75570, 40800, 22550, 64134, 4029, 13841, 91097}); // Large data set
    private Statistics statistics4 = new Statistics((new double[]{3, 7, 8, 5, 12, 14, 21, 13, 18})); //odd number
    private Statistics statistics5 = new Statistics((new double[]{3, 7, 8, 5, 12, 14, 21, 13, 18, 14})); // even number
    private Statistics statistics6 = new Statistics((new double[]{})); // null set
    private Statistics statistics7 = new Statistics((new double[]{1})); // single item set

    @Test
    public void getMeanString() throws Exception {
        assertEquals(statistics1.getMeanString(), "43900.6");
        assertEquals(statistics2.getMeanString(), "51284.9");
        assertEquals(statistics3.getMeanString(), "45357.45");
        assertEquals(statistics6.getMeanString(), NO_PERSONS_MESSAGE);
    }

    @Test
    public void getMedianString() throws Exception {
        assertEquals(statistics1.getMedianString(), "44627.5");
        assertEquals(statistics2.getMedianString(), "51113.0");
        assertEquals(statistics3.getMedianString(), "43082.0");
        assertEquals(statistics6.getMedianString(), NO_PERSONS_MESSAGE);

    }

    @Test
    public void getModeString() throws Exception {
        assertEquals(statistics1.getModeString(), "4978.0");
        assertEquals(statistics2.getModeString(), "6392.0");
        assertEquals(statistics3.getModeString(), "878.0");
        assertEquals(statistics6.getModeString(), NO_PERSONS_MESSAGE);

    }

    @Test
    public void getQuartile1String() throws Exception {
        assertEquals(statistics4.getQuartile1String(), "6.0");
        assertEquals(statistics5.getQuartile1String(), "7.0");
        assertEquals(statistics6.getQuartile1String(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getQuartile1String(), INSUFFICIENT_DATA_MESSAGE);

    }

    @Test
    public void getQuartile3String() throws Exception {
        assertEquals(statistics4.getQuartile3String(), "16.0");
        assertEquals(statistics5.getQuartile3String(), "14.0");
        assertEquals(statistics6.getQuartile3String(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getQuartile3String(), INSUFFICIENT_DATA_MESSAGE);
    }

    @Test
    public void getInterquartileRangeString() throws Exception {
        assertEquals(statistics4.getInterquartileRangeString(), "10.0");
        assertEquals(statistics5.getInterquartileRangeString(), "7.0");
        assertEquals(statistics6.getInterquartileRangeString(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getInterquartileRangeString(), INSUFFICIENT_DATA_MESSAGE);
    }

    //TODO:Fix value problems
    @Test
    public void getVarianceString() throws Exception {
        assertEquals(statistics1.getVarianceString(), "1.03137210182E9");
        assertEquals(statistics2.getVarianceString(), "6.1878123241E8");
        assertEquals(statistics3.getVarianceString(), "8.6507074558E8");
        assertEquals(statistics6.getVarianceString(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getVarianceString(), INSUFFICIENT_DATA_MESSAGE);
    }

    @Test
    public void getStdDevString() throws Exception {
        assertEquals(statistics1.getStdDevString(), "32114.98");
        assertEquals(statistics2.getStdDevString(), "24875.31");
        assertEquals(statistics3.getStdDevString(), "29412.09");
        assertEquals(statistics6.getStdDevString(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getStdDevString(), INSUFFICIENT_DATA_MESSAGE);
    }

}
