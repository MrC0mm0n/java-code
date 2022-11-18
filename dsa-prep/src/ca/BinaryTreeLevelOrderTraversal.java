package ca;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeLevelOrderTraversal {

	public static void main(String[] args) {
		TreeNode tree = new TreeNode(3);
		tree.left = new TreeNode(9);
		tree.left.right = new TreeNode(14);
		tree.right = new TreeNode(20);
		tree.right.left = new TreeNode(15);
		tree.right.left.left = new TreeNode(10);
		tree.right.left.right = new TreeNode(12);
		tree.right.right = new TreeNode(7);

		SolutionBinaryTreeLevelOrderTraversal sol = new SolutionBinaryTreeLevelOrderTraversal();
		System.out.println(sol.levelOrder(tree));
	}

}

class SolutionBinaryTreeLevelOrderTraversal {

	public List<List<Integer>> levelOrder(TreeNode tree) {
		System.out.println(">> levelOrder()");

		System.out.println(tree);

		int height = height(tree);
		System.out.println("-------------- height: " + height);

		List<List<Integer>> result = new ArrayList<>();
		for (int i = 1; i <= height; i++)
			result.add(level(tree, i));

		System.out.println("<< levelOrder()");
		return result;
	}

	private List<Integer> level(TreeNode tree, int level) {
		System.out.println(">> level()");

		System.out.println("level: " + level + ", tree: " + tree);

		List<Integer> result = new ArrayList<>();

		if (tree == null)
			return result;
		if (level == 1)
			result.add(tree.val);
		else if (level > 1) {
			result.addAll(level(tree.left, level - 1));
			result.addAll(level(tree.right, level - 1));
		}

		System.out.println("<< level()");
		return result;
	}

	private int height(TreeNode tree) {
		System.out.println(">> height()");

		System.out.println(tree);

		if (tree == null)
			return 0;

		int lHeight = height(tree.left) + 1;
		int rHeight = height(tree.right) + 1;
		System.out.println("lh: " + lHeight + ", rh: " + rHeight);

		System.out.println("<< height()");
		return (lHeight > rHeight) ? lHeight : rHeight;
	}

}

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode() {
	}

	TreeNode(int val) {
		this.val = val;
	}

	TreeNode(int val, TreeNode left, TreeNode right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "{ v: " + val + ", l: " + left + ", r: " + right + " }";
	}
}