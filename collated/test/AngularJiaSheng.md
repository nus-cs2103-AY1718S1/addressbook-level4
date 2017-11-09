# AngularJiaSheng
###### \java\seedu\address\model\person\ListOfWebLinkByCategoryTest.java
``` java

/**
 * junit test for listOfWebLinkByCategoryTest. Will increase coverage in the future.
 */
public class ListOfWebLinkByCategoryTest {

    @Test
    public void isValidOutputOfWebLinks() {
        ReadOnlyPerson toAdd = AMY;
        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add(VALID_WEB_LINK_DEFAULT);
        assertTrue(isTwoArrayListsWithSameValues(expectedOutput, AMY.listOfWebLinkByCategory(FACEBOOK_TAG)));
    }

    /**
     * compares 2 ArrayLists, irregardless of order of objects in the 2 lists,
     * return boolean whether 2 lists are equals.
     */
    public boolean isTwoArrayListsWithSameValues(ArrayList<String> list1, ArrayList<String> list2) {
        //null checking
        if (list1 == null && list2 == null) {
            return true;
        }
        if ((list1 == null && list2 != null) || (list1 != null && list2 == null)) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (Object itemList1 : list1) {
            if (!list2.contains(itemList1)) {
                return false;
            }
        }
        return true;
    }
}
```
###### \java\seedu\address\model\UniqueWebLinkListTest.java
``` java
public class UniqueWebLinkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueWebLinkList uniqueTagList = new UniqueWebLinkList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
```
