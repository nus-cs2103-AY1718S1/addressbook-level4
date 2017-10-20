package seedu.address.model.person;

import java.util.HashMap;
import java.util.Vector;

/**
* stores valid mrt names and its corresponding coordinates
 */
public class ValidMrt {

    public static HashMap<String, Vector<Double>> mrt;

    public ValidMrt() {
        init();
    }

    public void init() {
        Vector<Double> coor = new Vector<Double>(); //not sure if array is better or vector
        coor.add(20.0);  //x-coordinates
        coor.add(40.1); //y-coordinates
        mrt.put("North", coor);
        coor.set(0,20.0);
        coor.set(0,1.1);
        mrt.put("South", coor);
        coor.set(0,40.1);
        coor.set(0,20.1);
        mrt.put("East", coor);
        coor.set(0,1.1);
        coor.set(0,20.1);
        mrt.put("West", coor);
    }

}
