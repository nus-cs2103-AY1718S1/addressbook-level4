# justintkj
###### \ParserUtil.java
``` java

public static String parseSortType(String sortType) throws IllegalValueException {
    String toSort = sortType.trim().toLowerCase();
    if (!toSort.equals(SORTNAME_ARG) && !toSort.equals(SORTNUM_ARG)
        && !toSort.equals(SORTADD_ARG) && !toSort.equals(SORTEMAIL_ARG)
        && !toSort.equals(SORTREMARK_ARG) && !toSort.equals(SORTBIRTHDAY_ARG)
        && !toSort.equals(SORTNUMTIMESSEARCHED_ARG) ) {

        throw new IllegalValueException(MESSAGE_INVALID_SORT);
        }
        return toSort;
}
```
