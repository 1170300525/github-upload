package P3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class FriendshipGraph {


	public List<LinkedList<Person>> Graph = new ArrayList<>();
	
	public void addVertex(Person str) {
		boolean flag = true;
		for (LinkedList<Person> p : Graph) {
			if (p.getFirst().name == str.name)
				flag = false;
		}
		if (flag) {
			Person nature = new Person(str.toString());
			LinkedList<Person> e = new LinkedList<>();
			nature = str;
			e.addFirst(nature);
			Graph.add(e);
		}
		else {
			System.out.println("输入无效，该人已存在");
			System.exit(0);
		}
			
	}
	
	public void addEdge(Person str, Person ptr) {
		
		if (str == ptr)
			return;
		for (LinkedList<Person> p : Graph) {
			if (p.getFirst() == str) 
				p.addLast(ptr);
		}	
	}
	
	
	
	public int getDistance(Person str, Person ptr) {
		
		int count = 1, i = 0, j = 1, flag = 1, distance = 1;
		ArrayDeque<Person> queue = new ArrayDeque<>();
		for (LinkedList<Person> p : Graph) 
			p.getFirst().visited = false;
		queue.add(str);
		if (str == ptr) {
			j = 0;
			distance = 0;
		}
		else {
			while (!queue.isEmpty() && j == 1) {
				for (LinkedList<Person> p : Graph) {
					if (p.getFirst() == queue.getFirst() && p.getFirst().visited == false) {
						for (Person nature : p) {
							if (nature == ptr) {
								i--;
								j = 0;
							}
							else if (nature != p.getFirst() && nature.visited == false) {
								queue.addLast(nature);
								count++;
							}	
						}
					}
				}
				queue.getFirst().visited = true;
				queue.remove();
				i++;
				if (flag == i) {
					distance++;
					flag = count;
				}
			}
		}
		if (j == 1)
			distance = -1;
		return distance;
		
	}
	
	public int count() {
		int sum = 0;
		for (LinkedList<Person> p : Graph) 
			sum = sum + p.size() - 1;
		return sum / 2;
	}

	public static void main(String[] args) {
		
	}

	
}