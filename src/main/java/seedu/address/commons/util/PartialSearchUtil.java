package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PartialSearchUtil {

    private final ArrayList<String> baseList; // the list of words that is going to be find about
    private final ArrayList<String> targetList; // the list of search words

    //constructor
    public PartialSearchUtil(List<String> baseListOri, List<String> targetListOri) {
        baseList = new ArrayList<>(baseListOri);
        targetList = new ArrayList<>(targetListOri);
    }

    public boolean compare() {
        boolean flag = false;
        ListIterator<String> baseListItr = baseList.listIterator();
        while (baseListItr.hasNext()) {
            String baseString = baseListItr.next();
            ListIterator<String> targetListItr = targetList.listIterator();
            while (targetListItr.hasNext()) {
                if(targetListItr.next().contains(baseString)) {
                    flag = true;
                    break;
                }
            }
            break;
        }
        return flag;
    }

}
