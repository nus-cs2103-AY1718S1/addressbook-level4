package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_01;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_02;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_03;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_04;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_05;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_06;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_07;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_08;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_09;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_10;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_11;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_12;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_13;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_14;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_15;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_16;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_17;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_18;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_19;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_20;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_21;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_22;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_23;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_24;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_25;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_26;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_27;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_28;
import static seedu.address.model.util.ClusterUtil.CLUSTER_POSTAL_DISTRICT_UNKNOWN;
import static seedu.address.model.util.ClusterUtil.getCluster;

import org.junit.Test;

//@@author khooroko
public class ClusterUtilTest {

    private static final String SAMPLE_POSTAL_LAST_4_DIGITS = "1234";

    @Test
    public void allDistrictsTest() {

        int i = 99;
        while (i > 82) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_UNKNOWN);
            i--;
        }
        while (i > 81) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_19);
            i--;
        }
        while (i > 80) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_17);
            i--;
        }
        while (i > 78) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_28);
            i--;
        }
        while (i > 76) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_26);
            i--;
        }
        while (i > 74) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_27);
            i--;
        }
        while (i > 73) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_UNKNOWN);
            i--;
        }
        while (i > 71) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_25);
            i--;
        }
        while (i > 68) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_24);
            i--;
        }
        while (i > 64) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_23);
            i--;
        }
        while (i > 59) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_22);
            i--;
        }
        while (i > 57) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_21);
            i--;
        }
        while (i > 55) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_20);
            i--;
        }
        while (i > 52) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_19);
            i--;
        }
        while (i > 50) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_18);
            i--;
        }
        while (i > 48) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_17);
            i--;
        }
        while (i > 45) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_16);
            i--;
        }
        while (i > 41) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_15);
            i--;
        }
        while (i > 37) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_14);
            i--;
        }
        while (i > 33) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_13);
            i--;
        }
        while (i > 30) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_12);
            i--;
        }
        while (i > 27) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_11);
            i--;
        }
        while (i > 23) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_10);
            i--;
        }
        while (i > 21) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_09);
            i--;
        }
        while (i > 19) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_08);
            i--;
        }
        while (i > 17) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_07);
            i--;
        }
        while (i > 16) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_06);
            i--;
        }
        while (i > 13) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_03);
            i--;
        }
        while (i > 10) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_05);
            i--;
        }
        while (i > 9) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_04);
            i--;
        }
        while (i > 8) {
            assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_04);
            i--;
        }
        while (i > 6) {
            assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_02);
            i--;
        }
        while (i > 0) {
            assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_01);
            i--;
        }
        assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_UNKNOWN);
    }
}
