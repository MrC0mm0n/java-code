package ca;

import java.util.ArrayDeque;
import java.util.Queue;

// https://leetcode.com/problems/01-matrix/
public class MatrixDistanceToOne {

	public static void main(String[] args) {

		// Input
		// 0, 1, 1, 1, 0
		// 1, 1, 0, 1, 1
		// 1, 1, 1, 1, 0
		System.out.println("-------------------- BFS --------------------");
		// updateMatrixUsingBFS(new int[][] { { 0, 1, 1, 1, 0 }, { 1, 1, 0, 1, 1 }, { 1,
		// 1, 1, 1, 0 } });
		System.out.println("---------------------------------------------");
		// Output
		// 0,1,1,1,0
		// 1,1,0,1,1
		// 2,2,1,1,0

		System.out.println("-------------------- DP --------------------");
		// updateMatrixUsingDP(new int[][] { { 0, 1, 1, 1, 0 }, { 1, 1, 0, 1, 1 }, { 1,
		// 1, 1, 1, 0 } });

		updateMatrixUsingDP(new int[][] { { 1, 1, 0, 0, 1, 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 0, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 0, 0, 1, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1 }, { 0, 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 0, 0, 1 }, { 1, 1, 1, 1, 1, 0, 0, 1, 1, 1 },
				{ 0, 1, 0, 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 } });

	}

	public static int[][] updateMatrixUsingBFS(int[][] matrix) {

		printMatrix("input", matrix);

		int rows = matrix.length, cols = matrix[0].length;
		int[][] distance = new int[matrix.length][matrix[0].length];

		Queue<Pair> queue = new ArrayDeque<Pair>();
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				if (matrix[i][j] == 0)
					queue.offer(new Pair(i, j));
				else
					distance[i][j] = -1;

		printMatrix("distance", distance);
		System.out.println("queue: " + queue);

		int[][] direction = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

		while (!queue.isEmpty()) {

			System.out.println("---------------");

			System.out.println("queue: " + queue);
			Pair current = queue.poll();

			System.out.println("current: " + current + ", distance[" + current.row + "][" + current.column + "]: "
					+ distance[current.row][current.column]);
			System.out.println("queue: " + queue);

			for (int i = 0; i < 4; i++) {
				System.out.println("-");

				int next_r = current.row + direction[i][0], next_c = current.column + direction[i][1];
				System.out.println("next_r: " + next_r + ", next_c: " + next_c);

				if (next_r < 0 || next_r == rows || next_c < 0 || next_c == cols || distance[next_r][next_c] != -1) {
					System.out.println("SKIP");
					if (next_r >= 0 && next_r != rows && next_c >= 0 && next_c != cols)
						System.out.println("distance[" + next_r + "][" + next_c + "]: " + distance[next_r][next_c]);
					else
						System.out.println("OUT OF RANGE");
					continue;
				}

				System.out.println("UPDATE");
				System.out.println("distance[" + next_r + "][" + next_c + "]: " + distance[next_r][next_c]);
				distance[next_r][next_c] = distance[current.row][current.column] + 1;
				System.out.println("distance[" + next_r + "][" + next_c + "]: " + distance[next_r][next_c]);

				queue.offer(new Pair(next_r, next_c));
				System.out.println("queue: " + queue);
				printMatrix("distance", distance);
			}

			System.out.println("---");
			System.out.println("queue: " + queue);
			printMatrix("distance", distance);
		}

		printMatrix("distance", distance);

		return distance;
	}

	public static int[][] updateMatrixUsingDP(int[][] matrix) {

		printMatrix("input", matrix);

		int rows = matrix.length, cols = matrix[0].length;
		int[][] distance = new int[matrix.length][matrix[0].length];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				distance[i][j] = Integer.MAX_VALUE - 100000;

		printMatrix("distance", distance);

		// First pass: check for left and top
		System.out.println("First pass");
		for (int i = 0; i < rows; i++) {
			System.out.println("------------");
			for (int j = 0; j < cols; j++) {
				System.out.println("--- i: " + i + ", j: " + j + " ---");
				System.out.println("distance[" + i + "][" + j + "]: " + distance[i][j]);
				if (matrix[i][j] == 0) {
					distance[i][j] = 0;
				} else {
					if (i > 0) {
						System.out.println("TOP: " + (distance[i - 1][j] + 1));
						distance[i][j] = Math.min(distance[i][j], distance[i - 1][j] + 1);
					}
					if (j > 0) {
						System.out.println("LEFT: " + (distance[i][j - 1] + 1));
						distance[i][j] = Math.min(distance[i][j], distance[i][j - 1] + 1);
					}
				}
				System.out.println("distance[" + i + "][" + j + "]: " + distance[i][j]);
				printMatrix("distance", distance);
			}
		}
		System.out.println("---------------------------------");

		// Second pass: check for bottom and right
		System.out.println("Second pass");
		for (int i = rows - 1; i >= 0; i--) {
			System.out.println("------------");
			for (int j = cols - 1; j >= 0; j--) {
				System.out.println("--- i: " + i + ", j: " + j + " ---");
				System.out.println("distance[" + i + "][" + j + "]: " + distance[i][j]);
				if (i < rows - 1) {
					System.out.println("BOTTOM: " + (distance[i + 1][j] + 1));
					distance[i][j] = Math.min(distance[i][j], distance[i + 1][j] + 1);
				}
				if (j < cols - 1) {
					System.out.println("RIGHT: " + (distance[i][j + 1] + 1));
					distance[i][j] = Math.min(distance[i][j], distance[i][j + 1] + 1);
				}
				System.out.println("distance[" + i + "][" + j + "]: " + distance[i][j]);
				printMatrix("distance", distance);
			}
		}

		System.out.println("-----");
		printMatrix("distance", distance);

		return distance;
	}

	private static void printMatrix(String name, int[][] matrix) {
		int rows = matrix.length;
		if (rows == 0)
			return;
		int cols = matrix[0].length;
		String log = "[";
		for (int i = 0; i < rows; i++) {
			log += "{";
			for (int j = 0; j < cols; j++)
				log += matrix[i][j] + ",";
			log = log.substring(0, log.length() - 1);
			log += "},";
		}
		log = log.substring(0, log.length() - 1);
		log += "]";
		System.out.println(name + ": " + log);

	}

}

class Pair {

	int row, column;

	public Pair(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return "(" + row + "," + column + ")";
	}
}