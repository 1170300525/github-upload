package P3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

public class FriendshipGraphTest {

	FriendshipGraph graph = new FriendshipGraph();
	
	Person p1 = new Person("1");
	Person p2 = new Person("2");
	Person p3 = new Person("3");
	Person p4 = new Person("4");
	Person p5 = new Person("5");
	Person p6 = new Person("6");
	Person p7 = new Person("7");
	Person p8 = new Person("8");
	Person p9 = new Person("9");
	Person p10 = new Person("10");

	
	@Test
	public void addVertexTest() {
		graph.addVertex(p1);
		assertEquals(1, graph.Graph.size());
		graph.addVertex(p2);
		assertEquals(2, graph.Graph.size());
		graph.addVertex(p3);
		assertEquals(3, graph.Graph.size());
		graph.addVertex(p4);
		assertEquals(4, graph.Graph.size());
		graph.addVertex(p5);
		assertEquals(5, graph.Graph.size());
		graph.addVertex(p6);
		assertEquals(6, graph.Graph.size());
		graph.addVertex(p7);
		assertEquals(7, graph.Graph.size());
		graph.addVertex(p8);
		assertEquals(8, graph.Graph.size());
		graph.addVertex(p9);
		assertEquals(9, graph.Graph.size());
		graph.addVertex(p10);
		assertEquals(10, graph.Graph.size());
	}
	
	@Test
	public void addEdgeTest() {
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);
		graph.addVertex(p4);
		graph.addVertex(p5);
		graph.addVertex(p6);
		graph.addVertex(p7);
		graph.addVertex(p8);
		graph.addVertex(p9);
		graph.addVertex(p10);
		assertEquals(0, graph.count());
		graph.addEdge(p1, p2);
		graph.addEdge(p2, p1);
		assertEquals(1, graph.count());
		graph.addEdge(p1, p8);
		graph.addEdge(p8, p1);
		assertEquals(2, graph.count());
		graph.addEdge(p1, p9);
		graph.addEdge(p9, p1);
		assertEquals(3, graph.count());
		graph.addEdge(p1, p6);
		graph.addEdge(p6, p1);
		assertEquals(4, graph.count());
		graph.addEdge(p2, p5);
		graph.addEdge(p5, p2);
		assertEquals(5, graph.count());
		graph.addEdge(p5, p8);
		graph.addEdge(p8, p5);
		assertEquals(6, graph.count());
		graph.addEdge(p6, p7);
		graph.addEdge(p7, p6);
		assertEquals(7, graph.count());
		graph.addEdge(p3, p7);
		graph.addEdge(p7, p3);
		assertEquals(8, graph.count());
		graph.addEdge(p3, p9);
		graph.addEdge(p9, p3);
		assertEquals(9, graph.count());
		graph.addEdge(p4, p7);
		graph.addEdge(p7, p4);
		assertEquals(10, graph.count());
		graph.addEdge(p4, p9);
		graph.addEdge(p9, p4);
		assertEquals(11, graph.count());
		
	}
	
	@Test
	public void getDistance() {
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);
		graph.addVertex(p4);
		graph.addVertex(p5);
		graph.addVertex(p6);
		graph.addVertex(p7);
		graph.addVertex(p8);
		graph.addVertex(p9);
		graph.addVertex(p10);
		graph.addEdge(p1, p2);
		graph.addEdge(p2, p1);
		graph.addEdge(p1, p8);
		graph.addEdge(p8, p1);
		graph.addEdge(p1, p9);
		graph.addEdge(p9, p1);
		graph.addEdge(p1, p6);
		graph.addEdge(p6, p1);
		graph.addEdge(p2, p5);
		graph.addEdge(p5, p2);
		graph.addEdge(p5, p8);
		graph.addEdge(p8, p5);
		graph.addEdge(p6, p7);
		graph.addEdge(p7, p6);
		graph.addEdge(p3, p7);
		graph.addEdge(p7, p3);
		graph.addEdge(p3, p9);
		graph.addEdge(p9, p3);
		graph.addEdge(p4, p7);
		graph.addEdge(p7, p4);
		graph.addEdge(p4, p9);
		graph.addEdge(p9, p4);
		assertEquals(1, graph.getDistance(p1, p2));
		assertEquals(1, graph.getDistance(p2, p5));
		assertEquals(2, graph.getDistance(p3, p4));
		assertEquals(3, graph.getDistance(p4, p2));
		assertEquals(4, graph.getDistance(p5, p7));
		assertEquals(0, graph.getDistance(p6, p6));
		assertEquals(-1, graph.getDistance(p9, p10));
		assertEquals(2, graph.getDistance(p7, p9));
		assertEquals(2, graph.getDistance(p8, p2));
		assertEquals(1, graph.getDistance(p3, p7));
		assertEquals(-1, graph.getDistance(p10, p3));
		assertEquals(3, graph.getDistance(p4, p8));
	}

}
