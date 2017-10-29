package seedu.address.google;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

import seedu.address.model.person.ReadOnlyPerson;

public class SyncTable {
    private static HashMap< ReadOnlyPerson, String> map;
    static {

        try {
            FileInputStream fileInputStream = new FileInputStream("myMap.whateverExtension");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            map = (HashMap<ReadOnlyPerson, String>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            map = new HashMap<>();
        }

    }

    public String put (ReadOnlyPerson person, String str) {
        return map.put(person, str);
    }

    public String get (Object o) {
        return map.get(o);
    }


    public boolean containsKey (Object o) {
        return map.containsKey(o);
    }

    public boolean containsValue (Object o) {
        return map.containsKey(o);
    }

    public String replace (ReadOnlyPerson key, String value) {
        return map.replace(key, value);
    }

    public String remove (ReadOnlyPerson key) {
        return map.remove(key);
    }

    public static HashMap<ReadOnlyPerson, String> getMap() {
        return map;
    }
}
