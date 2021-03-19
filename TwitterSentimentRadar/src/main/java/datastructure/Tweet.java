package datastructure;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class describes a tweet object.
 *
 * @author Sebastian Rohe
 */
public class Tweet implements TweetInterface {

    // Tweet attributes. All final because they will not be changed expect for retweet id.
    private final long id;
    private final Date date;
    private final String user;
    private final String language;
    private final String content;
    private final Boolean retweet;

    // Retweet id has default value -1, because not every line of the CSV file includes a retweet id 'field'.
    private long retweetId = -1;

    // All getter methods. See further information in TweetInterface interface.
    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getLanguage() {
        return language;
    }

    public String getContent() {
        return content;
    }

    public boolean getRetweet() {
        return retweet;
    }

    public long getRetweetId() {
        return retweetId;
    }

    // Setter method to set the retweet id with other value than -1. See further information in TweetInterface interface.
    public void setRetweetId(long id) {
        this.retweetId = id;
    }

    /**
     * Constructor. The retweet id of every object is later added by setRetweetId() method or id keeps its default value -1.
     *
     * @param id Tweet id.
     * @param date Date of creation.
     * @param user Name of tweet author.
     * @param language Language of tweet text.
     * @param content Content of tweet.
     * @param retweet Boolean value to decide if retweet or not.
     */
    public Tweet(long id, Date date, String user, String language, String content, boolean retweet) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.language = language;
        this.content = content;
        this.retweet = retweet;
    }

    /**
     * Overridden toString() method to get readable representation of tweets for console output.
     *
     * @return Readable string representation of tweet object.
     */
    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                ", language='" + language + '\'' +
                ", content='" + content + '\'' +
                ", retweet=" + retweet +
                ", retweetId=" + retweetId +
                '}';
    }

    /**
     * Overridden compareTo() method to compare tweets by their date of creation.
     *
     * @param tweet Tweet object.
     * @return Integer value for comparison.
     */
    @Override
    public int compareTo(Tweet tweet) {
        return this.getDate().compareTo(tweet.getDate());
    }

    /**
     * Method to get a set of strings representing all hashtags of a tweet.
     *
     * @return A set of strings representing all used hashtags in a tweet.
     */
    public Set<String> getHashtags() {
        Set<String> allHashtagsSet = new HashSet<>();
        // Regex to get valid hashtag pattern.
        String hashtagRegex = "(#[A-Za-z0-9-_]+)(?:#[A-Za-z0-9-_]+)*\\b";

        // Start pattern matching. Check in tweet content for matching hashtag string parts.
        Pattern tagMatcher = Pattern.compile(hashtagRegex);
        Matcher m = tagMatcher.matcher(this.getContent());

        // If matcher finds valid hashtag string it will get added to set of hashtags.
        while (m.find()) {
            String tag = m.group(1);
            allHashtagsSet.add(tag);
        }

        return allHashtagsSet;
    }

}
