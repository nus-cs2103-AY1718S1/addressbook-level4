package seedu.address.logic.commands.imports;

import java.util.Date;
import java.util.Objects;

/**
 * A Java class representation of module information from NUSMods JSON API.
 *
 * @see <a href="https://github.com/nusmodifications/nusmods-api#get-acadyearsemestermodulesmodulecodejson">
 *     NUSMods API official documentation</a>
 */
public class ModuleInfo {
    private String moduleCode;
    private String moduleTitle;
    private int moduleCredit;
    private Date examDate;

    public Date getExamDate() {
        return examDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof ModuleInfo)) {
            // This handles null as well.
            return false;
        } else {
            ModuleInfo o = (ModuleInfo) other;
            return Objects.equals(moduleCode, o.moduleCode);
        }
    }

    /**
     * Each {@link #moduleCode} should link to <b>one and only one</b> {@link ModuleInfo} object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(moduleCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Module Code: " + moduleCode);
        sb.append("\nModule Title: " + moduleTitle);
        sb.append("\nModule Credit: " + moduleCredit);
        sb.append("\nExamination Date: " + examDate);
        return sb.toString();
    }
}
