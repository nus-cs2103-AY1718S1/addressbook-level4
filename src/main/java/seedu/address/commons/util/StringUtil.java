package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    private static final String GRAVATAR_SIZE = "90";
    private static final String GRAVATAR_URL_FORMAT = "https://www.gravatar.com/avatar/%1$s.jpg?d=%2$s&s="
            + GRAVATAR_SIZE;
    private static final String GRAVATAR_DEFAULT_FALLBACK = "person";
    private static final HashMap<String, String> defaultGravatar = new HashMap<>();

    static {
        defaultGravatar.put("person", "mm");
        defaultGravatar.put("geometric", "identicon");
        defaultGravatar.put("monster", "monsterid");
        defaultGravatar.put("face", "wavatar");
        defaultGravatar.put("retro", "retro");
        defaultGravatar.put("robot", "robohash");
        defaultGravatar.put("blank", "blank");
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //@@authur liuhang0213
    /**
     * Returns the md5 hash of a string
     * @param s String to be hashed
     */
    public static String generateMd5(String s) {
        try {
            byte[] bytes = s.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            return DatatypeConverter.printHexBinary(md.digest(bytes));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // Should not happen since they are fixed (UTF-8/MD5)
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Generates the Gravatar link with given email address and default picture
     * @param email
     * @return
     */
    public static String generateGravatarUrl(String email, String def) {
        String emailTrimmed = email.trim().toLowerCase();
        String hash = generateMd5(emailTrimmed);
        String defParam = defaultGravatar.getOrDefault(def,
                defaultGravatar.get(GRAVATAR_DEFAULT_FALLBACK));
        return String.format(GRAVATAR_URL_FORMAT, hash.toLowerCase(), defParam);
    }

    /**
     * Generates the Gravatar link with default default picture (mystery-man)
     */
    public static String generateGravatarUrl(String email) {
        return generateGravatarUrl(email, GRAVATAR_DEFAULT_FALLBACK);
    }
}
