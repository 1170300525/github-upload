/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
	private static final Instant d1 = Instant.parse("2018-02-15T10:00:00Z");
    private static final Instant d2 = Instant.parse("2018-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d4 = Instant.parse("2017-02-17T11:00:00Z");
    private static final Instant d5 = Instant.parse("2019-02-17T11:00:00Z");
    
    
    private static final Tweet tweet1 = new Tweet(1, "angelangulus", "RT @RealJamesWoods: The feminists got "
    		+ "what they asked for, and what they deserved. All future \\u201cwomen\\u2019s\\u201d Olympics events will be tarnished w\\u2026", d1);
    private static final Tweet tweet2 = new Tweet(2, "jeanOrlando5", "RT @RealJamesWoods: The feminists got "
    		+ "what they asked for, and what they deserved. All future \\u201cwomen\\u2019s\\u201d Olympics events will be tarnished w\\u2026", d2);
    private static final Tweet tweet3 = new Tweet(2, "jayno_kc", "The Olympics are dead to me this is complete"
    		+ " bullshit!!!!  So the men that always come in 4th or 5th and never get\\u2026 https://t.co/4VtVa1ke4g", d3);
    private static final Tweet tweet4 = new Tweet(2, "angelangulus", "@jeanOrlando5 Watch viewership tank.  Real "
    		+ "people are not OK with this nonsense.  Perhaps a left and a right Olympic\\u2026 https://t.co/W59DGe4Qp4", d4);
    private static final Tweet tweet5 = new Tweet(2, "m45mcdan", "@RealJamesWoods I haven\\u2019t cared about "
    		+ "Olympics for a long time. So who cares?", d5);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "angelangulus");
        List<Tweet> auther = new ArrayList<Tweet>();
        auther.add(tweet1);
        auther.add(tweet4);
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertEquals("expected singleton list", auther, writtenBy);
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2017-02-10T11:00:00Z");
        Instant testEnd = Instant.parse("2018-02-18T11:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertTrue("expected list to contain tweets", inTimespan.contains(tweet4));
        assertEquals("expected same order", 1, inTimespan.indexOf(tweet2));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("RT", "complete"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
        assertEquals("expected same order", 3, containing.size());
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
