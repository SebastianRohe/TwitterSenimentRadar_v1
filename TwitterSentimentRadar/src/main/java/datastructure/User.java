package datastructure;

import java.util.HashSet;
import java.util.Set;

public class User {

    private String username;
    private Set<Tweet> tweetsByUser = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public Set<Tweet> getTweets() {
        return tweetsByUser;
    }

    public void setTweets(Set<Tweet> tweets) {
        this.tweetsByUser = tweets;
    }

    public User(String username) {
        this.username = username;
    }

    // Method to add a tweet to the tweetsByUser set.
    public void addTweetToUser(Tweet tweet) {
        this.tweetsByUser.add(tweet);
    }

    public void addMultipleTweetsToUser(Set<Tweet> tweetSet) {
        for (Tweet tweet : tweetSet) {
            this.addTweetToUser(tweet);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", tweetsByUser=" + tweetsByUser +
                '}';
    }
}
