package datastructure;

import java.util.Set;

/**
 * Interface to represent a user.
 *
 * @author Sebastian Rohe
 */
public interface UserInterface {

    /**
     * Get username.
     *
     * @return User name as string.
     */
    String getUsername();

    /**
     * Get tweets by a user.
     *
     * @return A set of tweet objects representing the tweets by a user.
     */
    Set<Tweet> getTweetsByUser();

    /**
     * Setting up the tweetsByUser set.
     *
     * @param tweetsByUser A set of tweets.
     */
    void setTweetsByUser(Set<Tweet> tweetsByUser);


    /**
     * Method to add a tweet to the tweetsByUser set.
     *
     * @param tweet tweet object.
     */
    void addTweetToUser(Tweet tweet);

    /**
     * Method to add multiple tweets as set to a user. Calls addTweetToUser() method in body.
     *
     * @param tweetSet A set of tweets.
     */
    void addMultipleTweetsToUser(Set<Tweet> tweetSet);

}
