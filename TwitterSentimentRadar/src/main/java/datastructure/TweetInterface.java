package datastructure;

public interface TweetInterface {

    /**
     * Get ID of datastructure.Tweet
     * @return long
     */
    long getId();

    /**
     * Get content of tweet
     * @return String
     */
    String getContent();

    /**
     * Get username of the owner
     * @return String
     */
    String getUser();

    /**
     * Get the retweeted tweet, if existing
     * @return datastructure.Tweet
     */
    Tweet getRetweet();

    /**
     * True, if the tweet retweet another tweet.
     * @return boolean
     */
    boolean isRetweet();

    /**
     * Get language of datastructure.Tweet
     * @return String
     */
    String getLanguage();

}
