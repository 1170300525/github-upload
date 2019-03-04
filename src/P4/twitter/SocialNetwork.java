/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

	
	
    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	Map<String, Set<String>> map = new HashMap<String, Set<String>>();
    	Set<String> username = new HashSet<String>();
    	for (Tweet tweet : tweets) {
    		username = Extract.getMentionedUsers(Arrays.asList(tweet));
    		if (map.containsKey(tweet.getAuthor())) 
    			map.get(tweet.getAuthor()).addAll(username);
    		else 
				map.put(tweet.getAuthor(), username);
    	}
    	return map;
    	//throw new RuntimeException("not implemented");
    }

    
    public static Map<String, Set<String>> MyguessFollowsGraph(List<Tweet> tweets) {
    	Map<String, Set<String>> map = new HashMap<String, Set<String>>();
    	Map<String, Set<String>> data = new HashMap<String, Set<String>>();
    	Set<String> username = new HashSet<String>();
    	String str;
    	for (Tweet tweet : tweets) {
    		username = Extract.getMentionedUsers(Arrays.asList(tweet));
    		if (map.containsKey(tweet.getAuthor()))
    			map.get(tweet.getAuthor()).addAll(username);
    		else 
				map.put(tweet.getAuthor(), username);
    	}
    	for (Tweet tweet : tweets) {
    		str = tweet.getText();
    		if (str.charAt(0) == 'R' && str.charAt(1) == 'T') {
    			for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
    				if (entry.getValue().contains(tweet.getAuthor().toLowerCase())) {
    					if (data.containsKey(entry.getKey())) 
    						data.get(entry.getKey()).addAll(Extract.getMentionedUsers(Arrays.asList(tweet)));
    					else 
    						data.put(entry.getKey(), Extract.getMentionedUsers(Arrays.asList(tweet)));
    				}
    			}
    		}
    	}
    	for (Map.Entry<String, Set<String>> entry : data.entrySet())
    		map.get(entry.getKey()).addAll(entry.getValue());
		return map;	
	}
    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Set<String>> map = new TreeMap<>();
        Map<Integer, String> data = new TreeMap<>();
        int j = 0;
        for (Map.Entry<String, Set<String>> entry : followsGraph.entrySet()) {
        	for (String str : entry.getValue()) {
        		Set<String> follow = new HashSet<String>();
        		follow.add(entry.getKey());
        		if (map.containsKey(str)) 
        			map.get(str).addAll(follow);
        		else
        			map.put(str, follow);
        	}
        }
        LinkedList<String> influenceList = new LinkedList<String>();
        for (Map.Entry<String, Set<String>> entry : map.entrySet())
        	data.put(entry.getValue().size(), entry.getKey());
        for (Map.Entry<String, Set<String>> entry : followsGraph.entrySet()) {
        	if (!data.containsValue(entry.getKey().toLowerCase()))
        		data.put(j, entry.getKey());
        	j--;
        }
        for (Map.Entry<Integer, String> entry : data.entrySet()) 
        	influenceList.addFirst(entry.getValue());
        return influenceList;
    	//throw new RuntimeException("not implemented");
    }
    
    public static void main(String[] args) {
    	final Instant d1 = Instant.parse("2018-02-15T10:00:00Z");
        final Instant d2 = Instant.parse("2018-02-17T11:00:00Z");
        final Instant d3 = Instant.parse("2016-02-17T11:00:00Z");
        final Instant d4 = Instant.parse("2017-02-17T11:00:00Z");
        final Instant d5 = Instant.parse("2019-02-17T11:00:00Z");
        
        
        final Tweet tweet1 = new Tweet(1, "angelangulus", "RT @RealJamesWoods: The feminists got "
        		+ "what they asked for, and what they deserved. All future \\u201cwomen\\u2019s\\u201d Olympics events will be tarnished w\\u2026", d1);
        final Tweet tweet2 = new Tweet(2, "jeanOrlando5", "RT @RealJamesWoods: The feminists got "
        		+ "what they asked for, and what they deserved. All future \\u201cwomen\\u2019s\\u201d Olympics events will be tarnished w\\u2026", d2);
        final Tweet tweet3 = new Tweet(2, "jayno_kc", "The Olympics are dead to me this is complete"
        		+ " bullshit!!!!  So the men that always come in 4th or 5th and never get\\u2026 https://t.co/4VtVa1ke4g", d3);
        final Tweet tweet4 = new Tweet(2, "m45mcdan", "@jeanOrlando5 Watch viewership tank.  Real "
        		+ "people are not OK with this nonsense.  Perhaps a left and a right Olympic\\u2026 https://t.co/W59DGe4Qp4", d4);
        final Tweet tweet5 = new Tweet(2, "angelangulus", "@RealJamesWoods I haven\\u2019t cared about "
        		+ "Olympics for a long time. So who cares?", d5);
        Map<String, Set<String>> mentionedUsers = MyguessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    	for (Map.Entry<String, Set<String>> entry : mentionedUsers.entrySet()) {
    		System.out.println(entry.getKey() + "\t" + entry.getValue());
    	}
    	
    }
}