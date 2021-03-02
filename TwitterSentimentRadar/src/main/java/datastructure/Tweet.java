package datastructure;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tweet implements Comparable <Tweet> {

    // All important attributes every tweet has.
    private long id;
    private Date date;
    private String user;
    private String language;
    private String content;
    private Boolean retweet;

    // Retweet id has default value -1, because not every line of the csv file includes a retweet id.
    // A tweet with retweet id -1 is not a retweet (in this case the retweet value is false).
    private long retweetId = -1;
    private int contentLength;

    // All getter methods.
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

    public Boolean getRetweet() {
        return retweet;
    }

    public long getRetweetId() {
        return retweetId;
    }

    // Setter method to set the retweet id with other value than -1.
    public void setRetweetId(long id) {
        this.retweetId = id;
    }

    // Retweet id is not included in constructor because not every line in the csv file has an entry for retweet id.
    // The retweet id of every object is later added with call of setRetweetId() method or remains its default value -1.
    public Tweet(long id, Date date, String user, String language, String content, boolean retweet) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.language = language;
        this.content = content;
        this.retweet = retweet;
    }

    // Override toString() method to get readable representation of tweets for console.
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

    // Override compareTo() method to sort for tweet date.
    @Override
    public int compareTo(Tweet tweet) {
        return this.getDate().compareTo(tweet.getDate());
   }

    public Set<String> getHashTags(){

        Set<String> allHashtagsSet = new HashSet<>();
        String hashtagRegex = "(#[A-Za-z0-9-_]+)(?:#[A-Za-z0-9-_]+)*\\b";

        Pattern tagMatcher = Pattern.compile(hashtagRegex);
        Matcher m = tagMatcher.matcher(this.getContent());

        while (m.find()) {
            String tag = m.group(1);
            allHashtagsSet.add(tag);
        }

        return allHashtagsSet;
    }

}
