package seedu.address.logic.statistics;

import java.util.Arrays;
import java.util.stream.DoubleStream;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Calculates statistics of the persons inside the addressbook
 */
public class StatisticsUtils {

    /**
     * Prints to the console the first, second, third quartiles and interquartile range
     * of all the scores of all persons in the personList
     *
     * @param personList an ObservableList of ReadOnlyPersons
     */
    public static void printPercentiles(ObservableList<ReadOnlyPerson> personList) {

        int numPerson = personList.size();
        if (numPerson > 0) {
            double[] scoreArray = new double[numPerson];
            for (int i = 0; i < numPerson; i++) {
                Person person = (Person) personList.get(i);
                scoreArray[i] = Double.parseDouble((person.getGrades().value));
            }
            Arrays.sort(scoreArray);

            double quartile1 = getMedian(scoreArray, 0, numPerson / 2 - 1);
            double quartile2 = getMedian(scoreArray, 0, numPerson - 1);
            double quartile3 = (numPerson % 2 == 0)
                    ? getMedian(scoreArray, numPerson / 2, numPerson - 1)
                    : getMedian(scoreArray, numPerson / 2 + 1, numPerson - 1);
            double interquartileRange = quartile3 - quartile1;

            System.out.println("First Quartile: " + quartile1);
            System.out.println("Second Quartile: " + quartile2);
            System.out.println("Third Quartile: " + quartile3);
            System.out.println("Interquartile Range: " + interquartileRange);

        } else {
            System.out.println("There are no persons in the list to calculate percentiles with");
        }


    }

    /**
     * Prints to the console the mean, median and mode scores of all persons in the personList
     *
     * @param personList an ObservableList of ReadOnlyPersons
     */
    public static void printAverages(ObservableList<ReadOnlyPerson> personList) {

        int numPerson = personList.size();
        if (numPerson > 0) {
            double[] scoreArray = new double[numPerson];
            for (int i = 0; i < numPerson; i++) {
                Person person = (Person) personList.get(i);
                scoreArray[i] = Double.parseDouble((person.getGrades().value));
            }
            Arrays.sort(scoreArray);

            double sum = DoubleStream.of(scoreArray).sum();
            double mean = sum / numPerson;
            double median = (numPerson % 2 == 1)
                    ? scoreArray[(numPerson - 1) / 2]
                    : (scoreArray[(numPerson - 1) / 2] + scoreArray[((numPerson - 1) / 2) + 1]) / 2;
            double mode = 0;
            double currPersonScore;
            int maxCount = 0;
            for (int i = 0; i < numPerson; i++) {
                currPersonScore = scoreArray[i];
                int currCount = 0;
                for (int j = 0; j < numPerson; j++) {
                    if (currPersonScore == scoreArray[j]) {
                        currCount++;
                    }
                    if (currCount > maxCount) {
                        maxCount = currCount;
                        mode = currPersonScore;
                    }
                }
            }
            System.out.println("Mean score: " + mean);
            System.out.println("Median score: " + median);
            System.out.println("Mode score: " + mode);

        } else {
            System.out.println("There are no persons in the list to calculate averages with");
        }

    }


    /**
     * @param doubleArray an array of scores
     * @param startIndex  the start index to evaluate
     * @param endIndex    the end index to evaluate
     * @return the median value in the doubleArray from startIndex to endIndex inclusive
     */
    private static double getMedian(double[] doubleArray, int startIndex, int endIndex) {
        int size = endIndex - startIndex + 1;
        double median = (size % 2 == 0)
                ? (doubleArray[startIndex + size / 2 - 1] + doubleArray[startIndex + size / 2]) / 2
                : doubleArray[startIndex + (size - 1) / 2];

        return median;
    }

}
