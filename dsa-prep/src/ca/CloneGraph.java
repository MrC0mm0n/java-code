package ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

public class CloneGraph {

	public static void main(String[] args) {

		Node source = buildGraph();

		BFS.cloneGraph(source);
		DFS.cloneGraph(source);

	}

	private static Node buildGraph() {

		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Vector<Node> v = new Vector<Node>();
		v.add(node2);
		v.add(node4);
		node1.neighbors = v;
		v = new Vector<Node>();
		v.add(node1);
		v.add(node3);
		node2.neighbors = v;
		v = new Vector<Node>();
		v.add(node2);
		v.add(node4);
		node3.neighbors = v;
		v = new Vector<Node>();
		v.add(node3);
		v.add(node1);
		node4.neighbors = v;

		return node1;
	}

}

class BFS {
	public static Node cloneGraph(Node node) {

		if (node == null)
			return null;
		Map<Node, Node> map = new HashMap<Node, Node>(); // to store nodes visited

		Queue<Node> q = new LinkedList<Node>(); // to traverse each neighbour of a node and traverse the neighbour
		map.put(node, new Node(node.val, new ArrayList<Node>()));
		q.add(node);

		while (!q.isEmpty()) {
			Node top = q.remove();
			for (Node neighbor : top.neighbors) {
				// visited
				if (!map.containsKey(neighbor)) { // check if node is visited
					map.put(neighbor, new Node(neighbor.val, new ArrayList<Node>()));
					q.add(neighbor); // add neighbour to visit for next level of traversal
				}
				map.get(top).neighbors.add(map.get(neighbor));
			}
		}

		return map.get(node);
	}
}

class DFS {
	static Map<Node, Node> map = new HashMap<Node, Node>();

	public static Node cloneGraph(Node node) {
		if (node == null)
			return null;
		if (map.containsKey(node)) // return node is visited
			return map.get(node);

		Node temp = new Node(node.val, new ArrayList<Node>());
		map.put(node, temp);
		for (Node neighbor : node.neighbors) {
			temp.neighbors.add(cloneGraph(neighbor)); // recursively traverse each child node
		}
		return map.get(node);
	}
}

class Node {
	public int val;
	public List<Node> neighbors;

	public Node() {
		val = 0;
		neighbors = new ArrayList<Node>();
	}

	public Node(int _val) {
		val = _val;
		neighbors = new ArrayList<Node>();
	}

	public Node(int _val, ArrayList<Node> _neighbors) {
		val = _val;
		neighbors = _neighbors;
	}
}