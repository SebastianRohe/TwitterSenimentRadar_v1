package datastructure;

import java.util.Date;
import java.util.Set;

/**
 * Interface to represent a tweet.
 *
 * @author Sebastian Rohe
 * @see java.lang.Comparable
 */
public interface TweetInterface extends Comparable<Tweet> {

    /**
     * Get id of tweet.
     *
     * @return Tweet id as long.
     */
    long getId();

    /**
     * Get date of tweet creation.
     *
     * @return Date of tweet creation.
     */
    Date getDate();

    /**
     * Get username of the author.
     *
     * @return Username string.
     */
    String getUser();

    /**
     * Get language of tweet.
     *
     * @return Language string.
     */
    String getLanguage();

    /**
     * Get content of tweet.
     *
     * @return Tweet content as string.
     */
    String getContent();

    /**
     * Get boolean value to check if retweet or not.
     *
     * @return Boolean value for retweet relation.
     */
    boolean getRetweet();

    /**
     * Get the retweet id.
     *
     * @return Retweet id as long.
     */
    long getRetweetId();

    /**
     * Method to set the retweet id with other value than default value -1.
     *
     * @param id Id to replace default value -1.
     */
    void setRetweetId(long id);


    /**
     * Method to get all hashtags used in a tweet.
     *
     * @return A set of hashtag strings.
     */
    Set<String> getHashtags();

}
