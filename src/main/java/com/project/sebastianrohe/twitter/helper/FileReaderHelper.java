package com.project.sebastianrohe.twitter.helper;

import com.project.sebastianrohe.twitter.data.Tweet;

import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This class manages the read process for data from the CSV file.
 *
 * @author Sebatian Rohe
 */
public class FileReaderHelper {

    /**
     * This method reads all lines from a CSV file and converts them to a set of strings.
     * Every string in the set represents a line of the original CSV file. Empty lines will be ignored.
     *
     * @param filePath Path to the CSV file.
     * @return Set of strings representing every read line.
     */
    public static Set<String> readInLineByLine(String filePath) {
        // Line strings. Every string represents a read line from the CSV file.
        Set<String> allReadInLines = new HashSet<>();

        // Try to read CSV file from given file path and save every line as string in a set and return it.
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line = reader.readLine();

            // While line is not empty, read lines, convert them and add strings to set of all lines.
            while (line != null) {
                // Add line string to readInLines set.
                allReadInLines.add(line);
                // Read next line.
                line = reader.readLine();
            }
            // Close reader when all lines are read.
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the set of all read lines as set of strings.
        return allReadInLines;
    }

    /**
     * This method requires the path to a CSV file, processes the resulting set of line strings (every line represents
     * a tweet) from the readInLineByLine() method and converts every line string to an actual tweet object. Every tweet
     * is stored in a set.
     *
     * @param filePath Path to the CSV file.
     * @return A set of all resulting tweet objects.
     */
    public static Set<Tweet> convertReadInLines(String filePath) {
        // Empty set will get filled with tweets.
        Set<Tweet> actualTweets = new HashSet<>();

        // Set with read line strings from readInLineByLine() method.
        Set<String> tweetStrings = FileReaderHelper.readInLineByLine(filePath);

        // For each line string in the set we will execute following code.
        for (String tweetString : tweetStrings) {
            // If length of the line string is not 0 and the string contains tabs it is split in different string parts.
            if (tweetString.length() != 0 && tweetString.contains("\t")) {
                String[] splitLine = tweetString.split("\t");
                // Create tweet objects with string values from split method. String value for date is converted via Date class.
                Tweet createdTweet = new Tweet(Long.parseLong(splitLine[0]), new Date(Long.parseLong(splitLine[1])),
                        splitLine[2], splitLine[3], splitLine[4], Boolean.parseBoolean(splitLine[5]));

                // If boolean value of retweet variable is true, retweet id default value -1 is changed to string value.
                if (Boolean.parseBoolean(splitLine[5])) {
                    createdTweet.setRetweetId(Long.parseLong(splitLine[6]));
                }
                // Created tweet object is added to actualTweets set.
                actualTweets.add(createdTweet);
            }
        }
        // Finally a set of all resulting tweets is returned.
        return actualTweets;
    }

}




