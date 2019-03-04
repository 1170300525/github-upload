/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
		Instant startInstant = null, endInstant = null;
    	int i = 1, size = tweets.size();
    	if (size <= 1) {
    		System.out.println("错误：tweet数目不够，无法获取时间间隔");
    		System.exit(0);
    	}
    	for (Tweet tweet : tweets) {
    		if (i == 1) {
    			i = 0;
    			startInstant = tweet.getTimestamp();
    			endInstant = tweet.getTimestamp();
    		}
    		else {
    			if (startInstant.isAfter(tweet.getTimestamp()))
    				startInstant = tweet.getTimestamp();
    			else if (endInstant.isBefore(tweet.getTimestamp()))
    				endInstant = tweet.getTimestamp();
			}
    	}
    	Timespan timespan = new Timespan(startInstant, endInstant);
    	return timespan;
        //throw new RuntimeException("not implemented");
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	Set<String> username = new HashSet<String>();
    	String str, ptr;
    	for (Tweet tweet : tweets) {
    		str = tweet.getText().toUpperCase();
    		for (int i = 0; i < str.length(); i++) {
    			if (i == 0 && str.charAt(0) == '@') {
    				ptr = "";
    				i++;
    				while ((str.charAt(i) >= '0' && str.charAt(i) <= '9') || 
    						(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') || str.charAt(i) == '_' || str.charAt(i) == '-') {
    					ptr = ptr + str.charAt(i);
    					i++;
    				}
    				if (ptr != "")
    					username.add(ptr.toLowerCase());
    			}
    			else if (str.charAt(i) == '@' && ((str.charAt(i - 1) < '0' || (str.charAt(i - 1) >'9' && str.charAt(i) < 'A'))
    					|| (str.charAt(i - 1) > 'Z' && str.charAt(i - 1) != '_' && str.charAt(i - 1) != '-'))) {
    				ptr = "";
    				i++;
    				while ((str.charAt(i) >= '0' && str.charAt(i) <= '9') || 
    						(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') || str.charAt(i) == '_' || str.charAt(i) == '-') {
    					ptr = ptr + str.charAt(i);
    					i++;
    				}
    				if (ptr != "")
    					username.add(ptr.toLowerCase());
    			}	
    		}
    	}
    	return username;
    	//throw new RuntimeException("not implemented");
    }


    
}