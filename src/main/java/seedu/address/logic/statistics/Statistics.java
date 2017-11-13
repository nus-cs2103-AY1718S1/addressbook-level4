package seedu.address.logic.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.DoubleStream;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

//@@author lincredibleJC

/**
 * Calculates statistics of the persons inside an ObservableList
 */
public class Statistics {

    static final String NO_PERSONS_MESSAGE = "There are no persons";
    static final String INSUFFICIENT_DATA_MESSAGE = "Insufficient Data";
    private final int numDecimalPlace = 2;

    private double[] scoreArray;
    private int size;

    public Statistics(ObservableList<ReadOnlyPerson> personList) {
        initScore(personList);
    }

    protected Statistics(double[] scoreArray) {
        initScore(scoreArray);
    }

    /**
     * Takes in a PersonList, extracts every person's grades, sorts it and stores it in the current Statistics instance
     *
     * @param personList the list of persons to extract the scores from
     */
    public void initScore(ObservableList<ReadOnlyPerson> personList) {
        int listSize = personList.size();
        double[] listArray = new double[listSize];
        for (int i = 0; i < listSize; i++) {
            Person person = (Person) personList.get(i);
            listArray[i] = Double.parseDouble((person.getGrades().value));
        }
        initScore(listArray);
    }

    /**
     * Takes in an array of scores, sorts it and stores it in the current Statistics instance
     *
     * @param scoreArray the array of doubles used for calculating statistics
     */
    public void initScore(double[] scoreArray) {
        Arrays.sort(scoreArray);
        this.scoreArray = scoreArray;
        this.size = scoreArray.length;
    }

    /**
     * @return the average value in scoreArray
     */
    private double getMean() {
        return DoubleStream.of(scoreArray).sum() / size;
    }

    public String getMeanString() {
        if (size > 0) {
            return getRoundedStringFromDouble(getMean(), numDecimalPlace);
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * @return Returns the middle number in scoreArray. If scoreArray is odd, the middle number is returned.
     * If scoreArray is even, the average of the two numbers in the middle is returned
     */
    private double getMedian() {
        return (size % 2 == 1)
                ? scoreArray[(size - 1) / 2]
                : (scoreArray[(size - 1) / 2] + scoreArray[((size - 1) / 2) + 1]) / 2;
    }

    public String getMedianString() {
        if (size > 0) {
            return getRoundedStringFromDouble(getMedian(), numDecimalPlace);
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * Returns the middle number in the array starting at startIndex and ending at endIndex in scoreArray
     * If scoreArray is odd, the middle number is returned
     * If scoreArray is even, the average of the two numbers in the middle is returned
     *
     * @param arr        the array of numbers
     * @param startIndex the starting index of the desired array in scoreArray
     * @param endIndex   the ending index of teh desired array in scoreArray
     * @return the middle value of the array starting at startIndex and ending at endIndex in scoreArray
     */
    private double getMedianWithIndexes(double[] arr, int startIndex, int endIndex) throws NegativeArraySizeException {
        int currSize = endIndex - startIndex + 1;
        if (currSize >= 0) {
            return (currSize % 2 == 0)
                    ? (arr[startIndex + currSize / 2 - 1] + arr[startIndex + currSize / 2]) / 2
                    : arr[startIndex + (currSize - 1) / 2];
        }
        throw new NegativeArraySizeException("endIndex must not be smaller than startIndex!");
    }

    /**
     * Returns the smaller number if there is a tie
     *
     * @return the double value that appears most frequently in scoreArray
     */
    private double getMode() {
        double mode = 0;
        double currPersonScore;
        int maxCount = 0;
        for (int i = 0; i < size; i++) {
            currPersonScore = scoreArray[i];
            int currCount = 0;
            for (int j = 0; j < size; j++) {
                if (currPersonScore == scoreArray[j]) {
                    currCount++;
                }
                if (currCount > maxCount) {
                    maxCount = currCount;
                    mode = currPersonScore;
                }
            }
        }
        return mode;
    }

    public String getModeString() {
        if (size > 0) {
            return getRoundedStringFromDouble(getMode(), numDecimalPlace);
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * @return the 25th percontile score in the scoreArray
     */
    private double getQuartile1() {
        return getMedianWithIndexes(scoreArray, 0, size / 2 - 1);
    }

    public String getQuartile1String() {
        if (size > 1) {
            return getRoundedStringFromDouble(getQuartile1(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * @return the 75th percentile score in the scoreArray
     */
    private double getQuartile3() {
        return (size % 2 == 0)
                ? getMedianWithIndexes(scoreArray, size / 2, size - 1)
                : getMedianWithIndexes(scoreArray, size / 2 + 1, size - 1);
    }

    public String getQuartile3String() {
        if (size > 1) {
            return getRoundedStringFromDouble(getQuartile3(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * @return the difference between the 75th and 25th percentile scores in the scoreArray
     */
    private double getInterQuartileRange() {
        return getQuartile3() - getQuartile1();
    }

    public String getInterquartileRangeString() {
        if (size > 1) {
            return getRoundedStringFromDouble(getInterQuartileRange(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * @return the variance of the values in the scoreArray
     */
    private double getVariance() {
        double temp = 0;
        double mean = getMean();
        for (double score : scoreArray) {
            temp += (score - mean) * (score - mean);
        }
        return temp / (size - 1);
    }

    public String getVarianceString() {
        if (size > 1) {
            return getRoundedStringFromDouble(getVariance(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * @return the standard deviations of the values in the scoreArray
     */
    private double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public String getStdDevString() {
        if (size > 1) {
            return getRoundedStringFromDouble(getStdDev(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * Formats a double into a fixed number of decimal places and returns it as a string
     *
     * @param value  the double to be formatted
     * @param places number of decimal places of the output string
     * @return value formatted to places decimal places in a String
     */
    private static String getRoundedStringFromDouble(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return Double.toString(bd.doubleValue());
    }

}
