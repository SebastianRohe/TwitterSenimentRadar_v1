package datastructure;

import java.util.HashSet;
import java.util.Set;

/**
 * This class describes a user object.
 *
 * @author Sebastian Rohe
 */
public class User {

    // Username is final because it will not be changed.
    private final String username;

    // Set of tweets per user is empty at the start.
    private Set<Tweet> tweetsByUser = new HashSet<>();

    // All getter methods. See further information in UserInterface interface.
    public String getUsername() {
        return username;
    }

    public Set<Tweet> getTweetsByUser() {
        return tweetsByUser;
    }

    // Setter method to set tweetsByUser. See further information in UserInterface interface.
    public void setTweetsByUser(Set<Tweet> tweetsByUser) {
        this.tweetsByUser = tweetsByUser;
    }

    /**
     * Constructor. Set of tweets per user gets added later.
     *
     * @param username Name of user.
     */
    public User(String username) {
        this.username = username;
    }

    // Method to add tweet to tweets by user set. See further information in UserInterface interface.
    public void addTweetToUser(Tweet tweet) {
        this.tweetsByUser.add(tweet);
    }

    // Method to add multiple tweets. See further information in UserInterface interface.
    public void addMultipleTweetsToUser(Set<Tweet> tweetSet) {
        // For every tweet in the set of tweets use addTweetToUser() method to add tweet.
        for (Tweet tweet : tweetSet) {
            this.addTweetToUser(tweet);
        }
    }

    /**
     * Overridden toString() method to get readable representation of users for console output.
     *
     * @return Readable string representation of user object.
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", tweetsByUser=" + tweetsByUser +
                '}';
    }
}
