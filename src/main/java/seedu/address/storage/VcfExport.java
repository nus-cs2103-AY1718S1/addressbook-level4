package seedu.address.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author freesoup
/**
 * Parses a list of {@code ReadOnlyPerson} into a .vcf file.
 */
public class VcfExport {
    private static final Logger logger = LogsCenter.getLogger(VcfExport.class);

    /**
     * Parses a .vcf file into a list of {@code ReadOnlyPerson}.
     * Throws a IOException if file is not found.
     */
    public static void saveDataToFile(File file, List<ReadOnlyPerson> list) throws IOException {
        logger.fine("Attempting to write to data file: " + file.getPath());
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        for (ReadOnlyPerson person : list) {
            Set<Tag> tagList = person.getTags();
            bw.write("BEGIN:VCARD\n");
            bw.write("VERSON:2.1\n");
            bw.write("FN:" + person.getName() + "\n");
            bw.write("EMAIL:" + person.getEmail() + "\n");
            bw.write("TEL:" + person.getPhone() + "\n");
            bw.write("ADR:" + person.getAddress() + "\n");

            if (!person.getRemark().isEmpty()) {
                bw.write("NOTE:" + person.getRemark() + "\n");
            }

            if (!tagList.isEmpty()) {
                bw.write("CATEGORIES:");
                for (Tag tag : tagList) {
                    bw.write(tag.getTagName());
                }
                bw.write("\n");
            }

            bw.write("END:VCARD\n");
        }
        bw.close();
    }
}
