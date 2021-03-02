package program;

import datastructure.Tweet;
import datastructure.User;
import helper.FileReaderHelper;
import services.TweetService;

import java.util.*;
import java.util.stream.Collectors;

public class TwitterSentimentRadar {

    private static String filePath = "";

    // Create instance variable for sets of tweet objects and user objects.
    private static Set<User> userObjectsSet = new HashSet<>();
    private static Set<Tweet> tweetObjectsSet = new HashSet<>();

    /**
     * Main() method.
     * @param args
     */
    public static void main(String[] args) {

        // Paths to CSV file.
        filePath = "src\\main\\resources\\twitter.csv";

        // Create instance of classes to access all required methods and variables.
        TwitterSentimentRadar twitterSentimentRadar = new TwitterSentimentRadar(filePath);
        TweetService services = new TweetService();

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

                        // Get all usernames in alphabetical order.
                        case "users":

                            // Method to get all user from TweetService class to get a unsorted set of user strings.
                            Set<String> unsortedSetOfUsers = services.getAllUsers(tweetObjectsSet);
                            // Convert set of all users strings to list of users strings.
                            List<String> listOfUsers = new ArrayList<>(unsortedSetOfUsers);
                            // Sort list of user strings alphabetically. Comparing strings in list with each other.
                            listOfUsers.sort(String::compareTo);

                            // Print out every user for each user string in the list of all user strings.
                            for (String userString : listOfUsers) {
                                System.out.println(userString);
                            }

                            break;

                        // Get all tweets by a user for entered username.
                        case "tweets":

                            // Empty set to fill in all tweets by a user.
                            Set<Tweet> tweetsByUserSet = new HashSet<>();

                            System.out.println("filter?");
                            System.out.println("\t" + "username");
                            System.out.println("\t" + "all");

                            Scanner inputScanner = new Scanner(System.in);
                            // User input is the user string to look for.
                            String userNameToLookFor = inputScanner.next();

                            // Filter for the username string the program user entered. Look in Username field of every tweet.
                            twitterSentimentRadar.getUserObjectsSet().stream().filter(user -> user.getUsername().equals(userNameToLookFor))
                                    // Use stream and lambda expression to sort tweets of user by date. Comparing by date.
                                    .forEach(user -> user.getTweets().stream().sorted(Comparator.comparing(Tweet::getDate))
                                            // Add found tweets sorted by date to the set of all tweets by a user.
                                            .forEach(tweetsByUserSet::add));

                            // If user input equals 'all' get all tweets printed out regardless of user.
                            if (userNameToLookFor.equals("all")) {

                                // If input is 'all' all tweets sorted by date will be returned.
                                List<Tweet> allTweetsList = new ArrayList<>(tweetObjectsSet);

                                // Sort resulting list by date. Use of the Comparable interface via Tweet class.
                                Collections.sort(allTweetsList);

                                for (Tweet tweet : allTweetsList)
                                    System.out.println(tweet);

                            // If the set of tweets by user is empty return that no tweets for this username were found.
                            } else if (tweetsByUserSet.size() == 0) {
                                System.err.println("No tweets found for username " + "'" + userNameToLookFor + "'");

                            // Otherwise print out every tweet for the specific user.
                            } else {
                                // Print out every tweet for each tweet in the set of all tweets by a user.
                                for (Tweet userTweet : tweetsByUserSet) {
                                    System.out.println(userTweet);
                                }
                            }

                            break;

                        // Get all used hashtags without duplicates.
                        case "hashtags":

                            System.out.println("All used hashtags in alphanumerical order: ");

                            // Sort the list of all hashtags via stream() method by using a linked hashset.
                            Set<String> sortedSetOfAllHashtags = services.getAllHashtags(tweetObjectsSet).stream()
                                    .sorted().collect(Collectors.toCollection(LinkedHashSet::new));

                            // Print out every element from the resulting sorted list of all hashtags.
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

                            double tweetContent = services.getAverageTweetLength(tweetObjectsSet);
                            System.out.println("Average tweet length: " + tweetContent);
                            break;

                        // Get average number of words in tweets.
                        case "2":

                            double averageNumberOfWordsInTweets = services.getAverageNumberOfWords(tweetObjectsSet);
                            System.out.println("Average number of words in tweets: " + averageNumberOfWordsInTweets);

                            break;

                        // Get all occurrences of hashtags. Without checking for retweet relation (implemented later maybe).
                        case "3":

                            // Get the unsorted map of all hashtags.
                            Map<String, Integer> unsortedMapOfHashtags = services.getOccurrencesOfHashtags(services.getAllHashtags(tweetObjectsSet));

                            int finalIMin = 1;

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

                            // Method to get average number of hashtags.
                            double averageNumberOfHashtags = services.getAverageNumberOfHashtags(services.getAllHashtags(tweetObjectsSet), tweetObjectsSet);

                            System.out.println("Average number of hashtags in tweets: " + averageNumberOfHashtags);

                            break;

                        case "5":

                            Set<String> usersTwitteringLongTweetsStrings = services.getUsersTwitteringLongTweets(tweetObjectsSet);

                            System.out.println("Count of users with tweets above average length: " + usersTwitteringLongTweetsStrings.size());
                            System.out.println("Users twittering long tweets in alphabetical order: ");

                            for (String userString : usersTwitteringLongTweetsStrings)
                                System.out.println(userString);

                            break;

                        case "6":

                            // Listing and sorting of all tweets based on their use of hashtags. Tweets must have at least one hashtag
                            tweetObjectsSet.stream().filter(t -> t.getHashTags().size() > 0)
                                    .sorted((t1, t2) -> Integer.compare(t1.getHashTags().size(), t2.getHashTags().size()) * -1)
                                    .forEach(tweet -> System.out.println("(" + tweet.getHashTags().size() + ") \t " + tweet));

                            break;

                        default:

                            // If not valid integer value is entered loop starts from the beginning.
                            System.err.println("No information available for this integer input. Try again.");

                    }

                    break;

                case "search":

                    // Print all tweets that contain the entered search string in username or tweet content field.
                    System.out.print("Enter a string to search for in username or content field: ");

                    Scanner scanner = new Scanner(System.in);
                    String searchString = scanner.next();

                    // For each tweet it will be checked.
                    for (Tweet tweet : tweetObjectsSet) {

                        // Print out a tweet when it contains search string in username or content.
                        if (tweet.getUser().contains(searchString) || tweet.getContent().contains(searchString)) {
                            System.out.println(tweet);
                        }
                    }

                    break;
            }
        }
    }

    // Constructor expects file path and runs init() method to read in tweets and users.
    public TwitterSentimentRadar(String filePath) {

        this.filePath = filePath;
        init();

    }

    /**
     * Method to fill set of tweet objects with tweets and set of user objects with created users.
     */
    public void init() {

        // Read in all tweets from the csv file with the help of the helper.FileReaderHelper class.
        tweetObjectsSet = FileReaderHelper.convertReadInLines(filePath);

        // Empty set of all username strings
        Set<String> usersNameStrings = new HashSet<>();

        // Get every username string for every tweet from the set of all tweet objects.
        for (Tweet tweet : tweetObjectsSet) {
            usersNameStrings.add(tweet.getUser());
        }

        // For each username string in the set of all username strings do the following.
        for (String userNameString : usersNameStrings) {

            // Filter out all tweets by a user from the set of all tweet objects which were read in with the help of
            // the helper.FileReaderHelper class before.
            Set<Tweet> tweetsOfUserSet = tweetObjectsSet.stream().filter
                    (t -> t.getUser().equals(userNameString)).collect(Collectors.toSet());

            // Initialize a new user object everytime and insert the username string as parameter for username.
            User user = new User(userNameString);

            // Add the set of all tweets by a user to the user object.
            user.addMultipleTweetsToUser(tweetsOfUserSet);

            // Insert every single user object with a username and a set of tweets in the set of all user objects.
            userObjectsSet.add(user);

        }

        // Inform user about the number of read in tweets and users.
        System.out.println("Application is ready.");
        System.out.println("Number of read in tweets: " + getTweetObjectsSet().size());
        System.out.println("Number of users: " + getUserObjectsSet().size());
        System.out.println("==============================================");

    }

    // Getter methods for the attributes of the TwitterSentimentRadar class.
    public Set<User> getUserObjectsSet() {
        return userObjectsSet;
    }

    public Set<Tweet> getTweetObjectsSet() {
        return tweetObjectsSet;
    }

}
