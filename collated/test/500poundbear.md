# 500poundbear
###### /java/seedu/address/model/RemarkTest.java
``` java
    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // Return true if same object
        assertTrue(remark.equals(remark));

        // Returns true if remarks have the same value
        Remark remarkSameValue = new Remark(remark.value);
        assertTrue(remark.equals(remarkSameValue));

        // Returns false if different type
        assertFalse(remark.equals(1));

        // Returns false if null
        assertFalse(remark.equals(null));

        // Returns false if different person
        Remark differentRemark = new Remark("Hey");
        assertFalse(remark.equals(differentRemark));
    }
}
```
###### /java/seedu/address/model/StatisticsTest.java
``` java
    @Test
    public void getTotalNumberOfPeopleTest() {

        statistics = new Statistics(allPersonsList1, 12, 2017);
        assertEquals(statistics.getTotalNumberOfPeople().intValue(), 7);

        statistics = new Statistics(allPersonsList2, 12, 2017);
        assertEquals(statistics.getTotalNumberOfPeople().intValue(), 4);
    }

```
###### /java/seedu/address/model/StatisticsTest.java
``` java
    @Test
    public void calculateCountByMonthOffsetTest() {

        Statistics statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.calculateCountByMonthOffset(12, 2015), 0);

        statistics = new Statistics(allPersonsList1, 12, 2016);
        assertEquals(statistics.calculateCountByMonthOffset(12, 2015), 12);

        statistics = new Statistics(allPersonsList1, 12, 2017);
        assertEquals(statistics.calculateCountByMonthOffset(1, 2015), 35);

        statistics = new Statistics(allPersonsList1, 1, 2016);
        assertEquals(statistics.calculateCountByMonthOffset(12, 2015), 1);

        statistics = new Statistics(allPersonsList1, 12, 2018);
        assertEquals(statistics.calculateCountByMonthOffset(12, 2015), 36);

        statistics = new Statistics(allPersonsList1, 3, 2017);
        assertEquals(statistics.calculateCountByMonthOffset(5, 2016), 10);
    }

```
###### /java/seedu/address/model/StatisticsTest.java
``` java
    @Test
    public void getTotalNumberOfNoFacebookRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoFacebook().intValue(), 3);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoFacebook().intValue(), 2);
    }

```
###### /java/seedu/address/model/StatisticsTest.java
``` java
    @Test
    public void getTotalNumberOfNoTwitterRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoTwitter().intValue(), 1);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoTwitter().intValue(), 1);

    }

```
###### /java/seedu/address/model/StatisticsTest.java
``` java
    @Test
    public void getTotalNumberOfNoInstagramRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoInstagram().intValue(), 2);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoInstagram().intValue(), 1);

    }

```
###### /java/seedu/address/model/StatisticsTest.java
``` java
    @Test
    public void getNewPersonsAddByMonthTest() {

        statistics = new Statistics(allPersonsList1, 12, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(2),
                new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 5, 1, 0, 0, 0, 0, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));

        statistics = new Statistics(allPersonsList1, 6, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(2),
                new ArrayList<Integer>(Arrays.asList(5, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));

        statistics = new Statistics(allPersonsList1, 6, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(0),
                new ArrayList<Integer>(Arrays.asList(5)));


        statistics = new Statistics(allPersonsList2, 12, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(1),
                new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 1)));

    }

}
```
