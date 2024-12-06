package org.inria.restlet.mta.resources;

import java.util.Collection;
import java.util.List;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing tweets for a user.
 */
public class TweetResource extends ServerResource {

    /** Database. */
    private InMemoryDatabase db_;

    /** User managed by this resource. */
    private User user_;

    /**
     * Constructor.
     */
    public TweetResource() {
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes().get("database");
    }

    @Override
    protected void doInit() throws ResourceException {
        // Get the userId from the request and retrieve the user from the database
        String userIdString = (String) getRequest().getAttributes().get("userId");
        try {
            int userId = Integer.parseInt(userIdString);
            user_ = db_.getUser(userId);

            if (user_ == null) {
                throw new ResourceException(org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND, "User not found");
            }
        } catch (NumberFormatException e) {
            throw new ResourceException(org.restlet.data.Status.CLIENT_ERROR_BAD_REQUEST, "Invalid user ID format");
        }
    }

    /**
     * GET method: Retrieve the list of tweets for the user.
     */
    @Get("json")
    public Representation getTweets() throws JSONException {
        // Retrieve tweets for the user
        List<Tweet> tweets = user_.getTweets();

        // Convert tweets to JSON array
        JSONArray tweetsArray = new JSONArray();
        for (Tweet tweet : tweets) {
            JSONObject tweetObject = new JSONObject();
            tweetObject.put("id", tweet.getId());
            tweetObject.put("content", tweet.getContent());
            tweetsArray.put(tweetObject);
        }

        // Return the JSON representation
        return new JsonRepresentation(tweetsArray);
    }

    /**
     * POST method: Add a new tweet for the user.
     */
    @Post("json")
    public Representation addTweet(Representation entity) throws Exception {
        JsonRepresentation jsonRepresentation = new JsonRepresentation(entity);
        JSONObject jsonObject = jsonRepresentation.getJsonObject();

        // Validate the input
        if (!jsonObject.has("content") || jsonObject.getString("content").trim().isEmpty()) {
            throw new ResourceException(org.restlet.data.Status.CLIENT_ERROR_BAD_REQUEST,
                    "Content is required and cannot be empty");
        }

        String content = jsonObject.getString("content").trim();

        // Generate a new tweet ID (assuming a method in the database for this purpose)
        int tweetId = db_.generateNewTweetId();
        Tweet tweet = new Tweet(tweetId, content);

        // Add the tweet to the user's list of tweets
        user_.addTweet(tweet);

        // Return the created tweet as JSON
        JSONObject tweetObject = new JSONObject();
        tweetObject.put("id", tweet.getId());
        tweetObject.put("content", tweet.getContent());

        return new JsonRepresentation(tweetObject);
    }

    /**
     * GET method: Return all tweets.
     */
    @Get("json")
    public JsonRepresentation getAllTweets() {
        Collection<Tweet> tweets = db_.getAllTweets();

        JSONArray tweetsArray = new JSONArray();
        for (Tweet tweet : tweets) {
            JSONObject tweetObject = new JSONObject();
            tweetObject.put("id", tweet.getId());
            tweetObject.put("content", tweet.getContent());
            tweetObject.put("userId", tweet.getId());
            tweetsArray.put(tweetObject);
        }

        return new JsonRepresentation(tweetsArray);
    }
}
