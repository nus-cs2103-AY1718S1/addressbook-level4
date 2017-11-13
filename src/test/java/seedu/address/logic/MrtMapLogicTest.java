package seedu.address.logic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MrtMapLogicTest {
    @Test
    /**
     * This is a trivial test case
     */
    public void testTrivialSortedMrtList() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtStations = new ArrayList<String>();
        mrtStations.add("Dhoby Ghaut");
        ArrayList<String> mrtNameList = mrtMapLogic.getSortedMrtList(mrtStations);
        String mrtStation = mrtNameList.get(0);
        assertEquals("Dhoby Ghaut", mrtStation);
    }

    @Test
    /**
     * Non-trivial test case. A passed test case means that the Dijkstra's algoritms is
     * also correctly implemented
     */
    public void testSortedMrtList() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtStations = new ArrayList<String>();
        mrtStations.add("Dhoby Ghaut");
        mrtStations.add("Jurong East");
        mrtStations.add("Bishan");
        ArrayList<String> mrtNameList = mrtMapLogic.getSortedMrtList(mrtStations);

        //Botanic Gardens is the most convenient meeting point for this case,
        //followed by dhoby ghaut than Newton
        assertEquals("Botanic Gardens", mrtNameList.get(0));
        assertEquals("Dhoby Ghaut", mrtNameList.get(1));
        assertEquals("Newton", mrtNameList.get(2));
    }

    @Test
    /**
     * Check if the method returns the correct mrtStation name list
     */
    public void getMrtStationNames() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtNameList = mrtMapLogic.getMrtStationNames();
        assertEquals("Jurong East", mrtNameList.get(0));
        assertEquals("Bukit Batok", mrtNameList.get(1));
        assertEquals("Bukit Gombak", mrtNameList.get(2));
    }

    @Test
    /**
     * Check if the method returns the correct mrtStation shortName list
     */
    public void getMrtStationShortNames() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> mrtShortNameList = mrtMapLogic.getMrtStationShortNames();
        assertEquals("JUR", mrtShortNameList.get(0));
        assertEquals("BBT", mrtShortNameList.get(1));
        assertEquals("BGB", mrtShortNameList.get(2));
    }

    @Test
    /**
     * Check if it returns the correct mrt line list
     */
    public void getMrtLineNames() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<ArrayList<String>> mrtLineNameList = mrtMapLogic.getMrtLineNames();
        //Jurong East is in both NS (NorthSouth Line) and EW (East West Line)
        assertEquals("NS", mrtLineNameList.get(0).get(0));
        assertEquals("EW", mrtLineNameList.get(0).get(1));

        //Potong Pasir is in NE (North East Line)
        assertEquals("NE", mrtLineNameList.get(70).get(0));

        //Promenade is in DT (DownTownLine), CC (CircleLine) and CE (CircleLine)
        assertEquals("DT", mrtLineNameList.get(122).get(0));
        assertEquals("CC", mrtLineNameList.get(122).get(1));
        assertEquals("CE", mrtLineNameList.get(122).get(2));
    }

    @Test
    /**
     * Check if it returns the correct mrt station code list
     */
    public void getMrtLineNumbers() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<ArrayList<Integer>> mrtLineNumberList = mrtMapLogic.getMrtLineNumbers();
        //Jurong East's station codes are NS1 and EW 24
        assertEquals((long) 1, (long) mrtLineNumberList.get(0).get(0));
        assertEquals((long)24, (long) mrtLineNumberList.get(0).get(1));

        //Potong Pasir's station code is NE10
        assertEquals((long) 10, (long) mrtLineNumberList.get(70).get(0));

        //Promenade's station codes are DT15 and CC4
        assertEquals((long) 15, (long) mrtLineNumberList.get(122).get(0));
        assertEquals((long) 4, (long)mrtLineNumberList.get(122).get(1));
    }

    @Test
    public void isValidMrt() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();

        assertTrue(mrtMapLogic.isValidMrt("Bishan"));
        assertFalse(mrtMapLogic.isValidMrt("noSuchStation"));
    }

    @Test
    public void getTravelTime() throws Exception {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        int travelTime = mrtMapLogic.getTravelTime("Bishan", "Pasir Ris");
        assertEquals((long)36, (long) travelTime);

        travelTime = mrtMapLogic.getTravelTime("HarbourFront", "Pasir Ris");
        assertEquals((long)44, (long) travelTime);
    }

}