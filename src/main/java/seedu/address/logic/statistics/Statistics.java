package seedu.address.logic.statistics;

import java.util.Arrays;
import java.util.stream.DoubleStream;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Calculates statistics of the persons inside the addressbook
 */
public class Statistics {

    private double[] scoreArray;
    private int size;

    public Statistics(ObservableList<ReadOnlyPerson> personList) {
        int listSize = personList.size();
        double[] listArray = new double[listSize];
        for (int i = 0; i < listSize; i++) {
            Person person = (Person) personList.get(i);
            listArray[i] = Double.parseDouble((person.getGrades().value));
        }
        initScore(listArray);
    }

    protected Statistics(double[] scoreArray) {
        initScore(scoreArray);
    }

    /**
     * Sorts an array and assigns the appropriate values to the Statistics instance
     *
     * @param scoreArray the array of doubles used fo calculating statistics
     */
    public void initScore(double[] scoreArray) {
        Arrays.sort(scoreArray);
        this.scoreArray = scoreArray;
        this.size = scoreArray.length;
    }

    public double getMean() {
        return DoubleStream.of(scoreArray).sum() / size;
    }

    public double getMedian() {
        return (size % 2 == 1)
                ? scoreArray[(size - 1) / 2]
                : (scoreArray[(size - 1) / 2] + scoreArray[((size - 1) / 2) + 1]) / 2;
    }

    public double getMedianWithIndexes(double[] arr, int startIndex, int endIndex) {
        int currSize = endIndex - startIndex + 1;
        return (currSize % 2 == 0)
                ? (arr[startIndex + currSize / 2 - 1] + arr[startIndex + currSize / 2]) / 2
                : arr[startIndex + (currSize - 1) / 2];
    }

    public double getMode() {
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

    public double getQuartile1() {
        return getMedianWithIndexes(scoreArray, 0, size / 2 - 1);
    }

    public double getQuartile2() {
        return getMedianWithIndexes(scoreArray, 0, size - 1);
    }

    public double getQuartile3() {
        return (size % 2 == 0)
                ? getMedianWithIndexes(scoreArray, size / 2, size - 1)
                : getMedianWithIndexes(scoreArray, size / 2 + 1, size - 1);
    }

    public double getInterQuartileRange() {
        return getQuartile3() - getQuartile1();
    }

    public double getVariance() {
        double temp = 0;
        double mean = getMean();
        for (double score : scoreArray) {
            temp += (score - mean) * (score - mean);
        }
        return temp / (size - 1);
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }

    /**
     * Prints to the console the mean, median, mode, variance and standard deviation
     * of all the scores of all persons in the personList
     **/
    public void printAverages() {
        if (size > 0) {
            System.out.println("Mean score: " + getMean());
            System.out.println("Median score: " + getMedian());
            System.out.println("Mode score: " + getMode());
            if (size > 1) {
                System.out.println("Variance: " + getVariance());
                System.out.println("Standard Deviation: " + getStdDev());
            } else {
                System.out.println("Too few data to calculate Variance and StD");
            }
        } else {
            System.out.println("There are no persons in the list to calculate averages with");
        }
    }

    /**
     * Prints to the console the first, second, third quartiles and interquartile range
     * of all the scores of all persons in the personList
     */
    public void printPercentiles() {
        if (size > 0) {
            if (size > 1) {
                System.out.println("First Quartile: " + getQuartile1());
                System.out.println("Second Quartile: " + getQuartile2());
                System.out.println("Third Quartile: " + getQuartile3());
                System.out.println("Interquartile Range: " + getInterQuartileRange());
            } else {
                System.out.println("Too few data to calculate deciles");
            }
        } else {
            System.out.println("There are no persons in the list to calculate percentiles with");
        }
    }


}
