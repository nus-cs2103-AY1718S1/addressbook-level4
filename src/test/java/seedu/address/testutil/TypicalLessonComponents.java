package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Code;

public class TypicalLessonComponents {

    public static final Code MA1101R = initCode("MA1101R");
    public static final Code CS2101 = initCode("CS2101");
    public static final Code GEQ1000 = initCode("GEQ1000");

    private static Code initCode(String name){
        Code code = null;
        try{
            code = new Code(name);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return code;
    }


}
