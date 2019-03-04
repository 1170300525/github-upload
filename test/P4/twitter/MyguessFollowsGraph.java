package P4.twitter;

import static org.junit.Assert.assertEquals;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class MyguessFollowsGraph {

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
    private static final Tweet tweet4 = new Tweet(2, "m45mcdan", "@jeanOrlando5 Watch viewership tank.  Real "
    		+ "people are not OK with this nonsense.  Perhaps a left and a right Olympic\\u2026 https://t.co/W59DGe4Qp4", d4);
    private static final Tweet tweet5 = new Tweet(2, "angelangulus", "@RealJamesWoods I haven\\u2019t cared about "
    		+ "Olympics for a long time. So who cares?", d5);
	
	@Test
	void test() {
		Map<String, Set<String>> followsGraph = SocialNetwork.MyguessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        Set<String> follow1 = new HashSet<String>();
        follow1.add("realjameswoods");
        map.put("jeanOrlando5", follow1);
        map.put("angelangulus", follow1);
        Set<String> follow2 = new HashSet<String>();
        map.put("jayno_kc", follow2);
        Set<String> follow3 = new HashSet<String>();
        follow3.add("jeanorlando5");
        follow3.add("realjameswoods");
        map.put("m45mcdan", follow3);
        assertEquals(map, followsGraph);
	}

}
