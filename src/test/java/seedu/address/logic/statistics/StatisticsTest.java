package seedu.address.logic.statistics;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatisticsTest {

    private Statistics statistics1 = new Statistics(new double[]{64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120,
        51135, 67060});
    private Statistics statistics2 = new Statistics(new double[]{6392, 51608, 71247, 14271, 48327, 50618, 67435, 47029,
        61857, 22987, 64858, 99745, 75504, 85464, 60482, 30320, 11342, 48808, 66882, 40522});
    private Statistics statistics3 = new Statistics(new double[]{33664, 35702, 7049, 74334, 5725, 12090, 62774, 1149,
        81848, 84123, 17698, 42269, 42457, 80934, 83409, 19075, 87353, 63407, 20669, 36812, 44473, 46723, 41091, 54903,
        1749, 88514, 65760, 47322, 17365, 24779, 20301, 97839, 69612, 13975, 89694, 14585, 37259, 38361, 13720, 18729,
        8283, 3886, 26681, 8005, 48460, 13101, 52287, 44583, 878, 16133, 6334, 21592, 87253, 34537, 10974, 87484, 36937,
        79336, 78434, 76977, 75040, 77796, 62173, 63217, 68712, 60934, 88017, 3811, 41145, 57471, 6887, 74612, 78798,
        7308, 88094, 43245, 57157, 86406, 2922, 42919, 74375, 40076, 26030, 65306, 94610, 11923, 90367, 5603, 52189,
        45765, 44982, 27234, 77150, 75570, 40800, 22550, 64134, 4029, 13841, 91097});
    private Statistics statistics4 = new Statistics((new double[]{3, 7, 8, 5, 12, 14, 21, 13, 18})); //odd number
    private Statistics statistics5 = new Statistics((new double[]{3, 7, 8, 5, 12, 14, 21, 13, 18, 14})); // even number

    @Test
    public void getMean() throws Exception {
        assertTrue(statistics1.getMean() == 43900.6);
        assertTrue(statistics2.getMean() == 51284.9);
        assertTrue(statistics3.getMean() == 45357.45);
    }

    @Test
    public void getMedian() throws Exception {
        assertTrue(statistics1.getMedian() == 44627.5);
        assertTrue(statistics2.getMedian() == 51113.0);
        assertTrue(statistics3.getMedian() == 43082.0);
    }

    @Test
    public void getMode() throws Exception {
        assertTrue(statistics1.getMode() == 4978);
        assertTrue(statistics2.getMode() == 6392);
        assertTrue(statistics3.getMode() == 878);
    }

    @Test
    public void getQuartile1() throws Exception {
        assertTrue(statistics4.getQuartile1() == 6);
        assertTrue(statistics5.getQuartile1() == 7);
    }

    @Test
    public void getQuartile2() throws Exception {
        assertTrue(statistics4.getQuartile2() == 12.0);
        assertTrue(statistics5.getQuartile2() == 12.5);
    }

    @Test
    public void getQuartile3() throws Exception {
        assertTrue(statistics4.getQuartile3() == 16);
        assertTrue(statistics5.getQuartile3() == 14);
    }

    @Test
    public void getInterQuartileRange() throws Exception {
        assertTrue(statistics4.getInterQuartileRange() == 10);
        assertTrue(statistics5.getInterQuartileRange() == 7);
    }


}
