package org.inria.restlet.mta.database;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;

/**
 * In-memory database
 *
 * @author ctedeschi
 * @author msimonin
 */
public class InMemoryDatabase {

    /** User count (next id to give). */
    private int userCount_;
    private int tweetCount_;

    /** User HashMap. */
    private final Map<Integer, User> users_;
    /** Tweet HashMap. */
    private final Map<Integer, Tweet> tweets_;

    /**
     * Constructor.
     */
    public InMemoryDatabase() {
        userCount_ = 0;
        tweetCount_ = 0;
        users_ = new HashMap<>();
        tweets_ = new HashMap<>();
    }

    /**
     * Synchronized user creation.
     *
     * @param name Name of the user.
     * @param age  Age of the user.
     * @return The created user.
     */
    public synchronized User createUser(String name, int age) {
        User user = new User(name, age);
        user.setId(userCount_);
        users_.put(userCount_, user);
        userCount_++;
        return user;
    }

    /**
     * Synchronized tweet creation.
     *
     * @param userId  ID of the user creating the tweet.
     * @param content Content of the tweet.
     * @return The created tweet.
     * @throws IllegalArgumentException if the user does not exist.
     */
    public synchronized Tweet createTweet(int userId, String content) {
        User user = users_.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        // Générer un nouvel ID unique pour le tweet
        int tweetId = generateNewTweetId();
        Tweet tweet = new Tweet(tweetId, content);

        // Ajouter le tweet à la base de données et à l'utilisateur
        tweets_.put(tweetId, tweet);
        user.addTweet(tweet);

        return tweet;
    }


    /**
     * Retrieves all tweets.
     *
     * @return An unmodifiable collection of all tweets.
     */
    public Collection<Tweet> getAllTweets() {
        return Collections.unmodifiableCollection(tweets_.values());
    }

    /**
     * Retrieves all users.
     *
     * @return An unmodifiable collection of all users.
     */
    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users_.values());
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user.
     * @return The user with the given ID, or null if not found.
     */
    public User getUser(int id) {
        return users_.get(id);
    }

    /**
     * Deletes a user by ID and removes their tweets.
     *
     * @param userId The ID of the user to delete.
     * @return True if the user was deleted, false if not found.
     */
    public synchronized boolean deleteUser(int userId) {
        User user = users_.remove(userId);
        if (user != null) {
            // Remove the user's tweets from the database
            List<Tweet> userTweets = user.getTweets();
            for (Tweet tweet : userTweets) {
                tweets_.remove(tweet.getId());
            }
            return true;
        }
        return false;
    }

    /**
     * Generates a new unique ID for tweets.
     *
     * @return A unique ID for the next tweet.
     */
    public synchronized int generateNewTweetId() {
        return tweetCount_++;
    }

}
