package com.project.sebastianrohe.twitter.program;

import com.project.sebastianrohe.twitter.data.Tweet;
import com.project.sebastianrohe.twitter.data.User;
import com.project.sebastianrohe.twitter.helper.FileReaderHelper;
import com.project.sebastianrohe.twitter.services.TweetService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class includes and provides all functionalities of the application.
 *
 * @author Sebastian Rohe
 */
public class TwitterSentimentRadar {

    private static String filePath = "";

    // Create instance variables for set of tweet objects and user objects.
    private static final Set<User> userObjectsSet = new HashSet<>();
    private static Set<Tweet> tweetObjectsSet = new HashSet<>();

    /**
     * Main() method to run full application.
     *
     * @param args Passed parameters at program start.
     */
    public static void main(String[] args) {
        // Path to the CSV file.
        filePath = "src\\main\\resources\\twitter.csv";

        // Create instances of classes to access all required methods and variables.
        TwitterSentimentRadar twitterSentimentRadar = new TwitterSentimentRadar(filePath);
        TweetService tweetService = new TweetService();

        String userInput = "";
        Scanner userInputScanner = new Scanner(System.in);

        // Program runs in loop until user enters 'close' to end the program.
        while (!userInput.equalsIgnoreCase("close")) {

            // Main menu.
            System.out.println("\n" + "function?");
            System.out.println("\t" + "list");
            System.out.println("\t" + "count");
            System.out.println("\t" + "search");
            System.out.println("\t" + "close");

            // User input gets converted to lower case for further processing.
            userInput = userInputScanner.nextLine().toLowerCase();

            // Switch statement to handle valid inputs.
            switch (userInput) {

                case "list":

                    System.out.println("\t" + "users");
                    System.out.println("\t" + "tweets");
                    System.out.println("\t" + "hashtags");

                    userInput = userInputScanner.nextLine().toLowerCase();

                    switch (userInput) {

                        // Get all usernames in alphanumerical order.
                        case "users":

                            // Method to get all users from instance of TweetService class to get a unsorted set of user strings.
                            Set<String> unsortedSetOfUsers = tweetService.getAllUsers(tweetObjectsSet);
                            // Convert set of all users strings to list of users strings.
                            List<String> listOfUsers = new ArrayList<>(unsortedSetOfUsers);
                            // Sort list of user strings alphanumerically. Comparing strings in list with each other.
                            listOfUsers.sort(String::compareTo);

                            // Print out every user for each user string in the list of all user strings.
                            for (String userString : listOfUsers) {
                                System.out.println(userString);
                            }

                            break;

                        // Get all tweets by a user for entered username string.
                        case "tweets":

                            // Empty set to 'fill' in all tweets by a user.
                            Set<Tweet> tweetsByUserSet = new HashSet<>();

                            // Filter menu.
                            System.out.println("filter?");
                            System.out.println("\t" + "username");
                            System.out.println("\t" + "all");

                            Scanner inputScanner = new Scanner(System.in);
                            // User input is the user string to look for.
                            String userNameToLookFor = inputScanner.next();

                            // Filter for the username string the program user entered. Look in username 'field' of every tweet.
                            twitterSentimentRadar.getUserObjectsSet().stream().filter(user -> user.getUsername().equals(userNameToLookFor))
                                    // Use stream() method and lambda expression to sort tweets of user by date. Comparing dates.
                                    .forEach(user -> user.getTweetsByUser().stream().sorted(Comparator.comparing(Tweet::getDate))
                                            // Add found tweets sorted by date to the set of all tweets by a user.
                                            .forEach(tweetsByUserSet::add));

                            // If user input equals 'all' get all tweets printed out regardless of user.
                            if (userNameToLookFor.equals("all")) {
                                // If input is 'all' all tweets sorted by date will be returned.
                                List<Tweet> allTweetsList = new ArrayList<>(tweetObjectsSet);

                                // Sort resulting list by date. Use of the Comparable interface via TweetInterface interface.
                                Collections.sort(allTweetsList);

                                for (Tweet tweet : allTweetsList) {
                                    System.out.println(tweet);
                                }

                            // If the set of tweets by user is empty return that no tweets for this username were found.
                            } else if (tweetsByUserSet.size() == 0) {
                                System.err.println("No tweets found for username " + "'" + userNameToLookFor + "'");

                            // Otherwise print out every tweet for the specific user.
                            } else {
                                // Print out every tweet from the set of all tweets by a user.
                                for (Tweet userTweet : tweetsByUserSet) {
                                    System.out.println(userTweet);
                                }
                            }

                            break;

                        // Get all used hashtags without duplicates.
                        case "hashtags":

                            System.out.println("All used hashtags in alphanumerical order: ");

                            // Sort the list of all hashtags via stream() method and convert it to set by using LinkedHashSet.
                            Set<String> sortedSetOfAllHashtags = tweetService.getAllHashtags(tweetObjectsSet).stream()
                                    .sorted().collect(Collectors.toCollection(LinkedHashSet::new));

                            // Print out every element from the resulting sorted set of all hashtags.
                            sortedSetOfAllHashtags.forEach(System.out::println);

                            break;

                        default:

                            System.err.println("No information available for this input. Try again.");

                    }

                    break;

                case "count":

                    // Submenu for count.
                    System.out.println("\t" + "Press '1' for average tweet length");
                    System.out.println("\t" + "Press '2' for average number of words in tweets");
                    System.out.println("\t" + "Press '3' for number of occurrences of every hashtag");
                    System.out.println("\t" + "Press '4' for average number of used hashtags");
                    System.out.println("\t" + "Press '5' to see users twittering long tweets");
                    System.out.println("\t" + "Press '6' to see which tweets contain the most hashtags");

                    userInput = userInputScanner.nextLine().toLowerCase();

                    switch (userInput) {

                        // Get average tweet length.
                        case "1":

                            double tweetContent = tweetService.getAverageTweetLength(tweetObjectsSet);
                            System.out.println("Average tweet length: " + tweetContent);
                            break;

                        // Get average number of words in tweets.
                        case "2":

                            double averageNumberOfWordsInTweets = tweetService.getAverageNumberOfWords(tweetObjectsSet);
                            System.out.println("Average number of words in tweets: " + averageNumberOfWordsInTweets);

                            break;

                        // Get all occurrences of hashtags. Without checking for retweet relation (implemented later maybe).
                        case "3":

                            // Get the unsorted map of all hashtags.
                            Map<String, Integer> unsortedMapOfHashtags = tweetService.getOccurrencesOfHashtags(tweetService.getAllHashtags(tweetObjectsSet));

                            // Set minimum.
                            int finalIMin = 1;

                            // Sort the unsorted map of all hashtags.
                            unsortedMapOfHashtags.entrySet().stream().filter(e -> e.getValue() >= finalIMin).sorted((e1, e2) ->
                            {
                                int rInt = e1.getValue().compareTo(e2.getValue()) * -1;

                                if (rInt == 0) {
                                    rInt = e1.getKey().compareTo(e2.getKey());
                                }
                                return rInt;

                            }).forEach(t -> System.out.println(t.getKey() + " (" + t.getValue() + ")"));

                            break;

                        // Get average number of hashtags in tweets.
                        case "4":

                            // Use method to get average number of hashtags from instance of TweetService class.
                            double averageNumberOfHashtags = tweetService.getAverageNumberOfHashtags(tweetService.getAllHashtags(tweetObjectsSet), tweetObjectsSet);

                            System.out.println("Average number of hashtags in tweets: " + averageNumberOfHashtags);

                            break;

                        // Get users twittering tweets above average length.
                        case "5":

                            // Use method to get users twittering long tweets from instance of TweetService class.
                            Set<String> usersTwitteringLongTweetsStrings = tweetService.getUsersTwitteringLongTweets(tweetObjectsSet);

                            System.out.println("Count of users with tweets above average length: " + usersTwitteringLongTweetsStrings.size());
                            System.out.println("Users twittering long tweets in alphanumerical order: ");

                            for (String userString : usersTwitteringLongTweetsStrings){
                                System.out.println(userString);
                            }

                            break;

                        // Get tweets with most used hashtags. Tweets will get listed in descending order with their number of hashtags.
                        case "6":

                            // Listing and sorting of all tweets based on their number of hashtags. Tweets must have at least one hashtag.
                            tweetObjectsSet.stream().filter(t -> t.getHashtags().size() > 0)
                                    .sorted((t1, t2) -> Integer.compare(t1.getHashtags().size(), t2.getHashtags().size()) * -1)
                                    .forEach(tweet -> System.out.println("(" + tweet.getHashtags().size() + ") \t " + tweet));

                            break;

                        default:

                            // If no valid integer value is entered loop starts from the beginning.
                            System.err.println("No information available for this integer input. Try again.");

                    }

                    break;

                case "search":

                    // Print out all tweets that contain the entered search string in username or tweet content 'field'.
                    System.out.print("Enter a string to search for in username or content field: ");

                    Scanner scanner = new Scanner(System.in);
                    String searchString = scanner.next();

                    // For each tweet in the set it will be checked.
                    for (Tweet tweet : tweetObjectsSet) {
                        // Print out a tweet if it contains the search string in username or content.
                        if (tweet.getUser().contains(searchString) || tweet.getContent().contains(searchString)) {
                            System.out.println(tweet);
                        }
                    }

                    break;
            }
        }
    }

    /**
     * Constructor. Requires path to the CSV file and runs the init() method to read CSV data.
     *
     * @param filePath Path to the CSV file.
     */
    public TwitterSentimentRadar(String filePath) {
        this.filePath = filePath;
        init();
    }

    /**
     * Method to 'fill' set of tweet objects with tweets and set of user objects with users by using CSV data.
     */
    public void init() {
        // Read all tweets from the CSV file with FileReaderHelper class method.
        tweetObjectsSet = FileReaderHelper.convertReadInLines(filePath);

        // Empty set of all username strings.
        Set<String> usersNameStrings = new HashSet<>();

        // Get every username string for every tweet from the set of all tweet objects.
        for (Tweet tweet : tweetObjectsSet) {
            usersNameStrings.add(tweet.getUser());
        }

        // For each username string in the set of all username strings do the following.
        for (String userNameString : usersNameStrings) {
            // Filter out all tweets by a user from the set of all tweet objects which were read.
            Set<Tweet> tweetsOfUserSet = tweetObjectsSet.stream().filter(t -> t.getUser().equals(userNameString)).collect(Collectors.toSet());

            // Initialize a new user object everytime with specific username string as parameter.
            User user = new User(userNameString);

            // Add the set of all tweets by a user to the user object.
            user.addMultipleTweetsToUser(tweetsOfUserSet);

            // Insert every single user object with a username and a set of tweets in the set of all user objects.
            userObjectsSet.add(user);
        }

        // Inform user about the number of read tweets and users.
        System.out.println("Application is ready.");
        System.out.println("Number of read tweets: " + getTweetObjectsSet().size());
        System.out.println("Number of read users: " + getUserObjectsSet().size());
        System.out.println("==============================================");
    }

    /**
     * Get set of user objects.
     *
     * @return Set of user objects.
     */
    public Set<User> getUserObjectsSet() {
        return userObjectsSet;
    }

    /**
     * Get set of tweet objects.
     *
     * @return Set of tweet objects.
     */
    public Set<Tweet> getTweetObjectsSet() {
        return tweetObjectsSet;
    }

}
