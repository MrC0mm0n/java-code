package ca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// https://leetcode.com/problems/k-closest-points-to-origin/
public class KClosestPointsToOrigin {

	public static void main(String[] args) {

		kClosestQuickSelect(new int[][] { { 5, -4 }, { 3, 3 }, { 5, -1 }, { 1, -2 }, { -2, 4 }, { 1, 1 }, { 3, -1 },
				{ -4, 5 }, { -1, 0 }, { 2, 8 }, { -1, -1 }, { 8, 1 } }, 4);

	}

	// NlogN - My solution
	public static int[][] kClosest(int[][] points, int k) {

		List<Point> lPoints = new ArrayList<>();
		for (int i = 0; i < points.length; i++)
			lPoints.add(new Point(Math.sqrt(points[i][0] * points[i][0] + points[i][1] * points[i][1]), points[i][0],
					points[i][1]));
		System.out.println(lPoints);

		lPoints.sort((a, b) -> {
			return Double.compare(a.distance, b.distance);
		});
		System.out.println(lPoints);

		int[][] result = new int[k][2];
		for (int i = 0; i < k; i++) {
			result[i][0] = lPoints.get(i).x;
			result[i][1] = lPoints.get(i).y;
		}

		StringBuilder log = new StringBuilder();
		log.append("[ ");
		for (int i = 0; i < result.length; i++)
			log.append("[" + result[i][0] + ", " + result[i][1] + "], ");
		log = new StringBuilder(log.subSequence(0, log.length() - 2));
		log.append(" ]");
		System.out.println(log);

		return result;
	}

	// https://leetcode.com/problems/k-closest-points-to-origin/discuss/220235/Java-Three-solutions-to-this-classical-K-th-problem.

	// NlogN
	public static int[][] kClosestSort(int[][] points, int k) {
		// (x1^2 + y1^2) - (x2^2 + y2^2) : difference is for comparator
		Arrays.sort(points, (p1, p2) -> p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1]);
		return Arrays.copyOfRange(points, 0, k);
	}

	// NlogK
	public static int[][] kClosestMaxHeap(int[][] points, int k) {
		PriorityQueue<int[]> pq = new PriorityQueue<int[]>(
				(p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);
		for (int[] p : points) {
			pq.offer(p);
			if (pq.size() > k)
				pq.poll();
			System.out.println(p[0] + ", " + p[1]);
		}

		int[][] result = new int[k][2];
		while (k > 0)
			result[--k] = pq.poll();
		System.out.println("--- result ---");
		for (int i = 0; i < result.length; i++)
			System.out.println(result[i][0] + ", " + result[i][1]);

		return result;
	}

	// ------------------ quick select --------------------
	public static int[][] kClosestQuickSelect(int[][] points, int k) {

		print2DArray("points", points);

		int len = points.length, l = 0, r = len - 1;
		while (l <= r) {
			System.out.println("---------------");
			System.out.println("---- l: " + l + ", r: " + r);

			int mid = helper(points, l, r);

			System.out.println("---- mid: " + mid + ", k: " + k);

			if (mid == k)
				break;
			if (mid < k) {
				l = mid + 1;
			} else {
				r = mid - 1;
			}

			print2DArray("points", points);
		}
		int[][] result = Arrays.copyOfRange(points, 0, k);

		print2DArray("result", result);

		return result;
	}

	private static int helper(int[][] A, int l, int r) {
		System.out.println(">> helper()");

		print2DArray("A-start", A);

		int[] pivot = A[l];
		System.out.println("l: " + l + ", pivot: {" + pivot[0] + ", " + pivot[1] + "}");
		while (l < r) {

			System.out.println("-");
			System.out.println("l: " + l + ", r: " + r);

			while (l < r && compare(A[r], pivot) >= 0) {
				r--;
				System.out.println("r: " + r);
			}

			System.out.println("A[" + l + "]: {" + A[l][0] + ", " + A[l][1] + "}, A[" + r + "]: {" + A[r][0] + ", "
					+ A[r][1] + "}");
			A[l] = A[r];
			print2DArray("A-s1", A);

			while (l < r && compare(A[l], pivot) <= 0) {
				l++;
				System.out.println("l: " + l);
			}

			System.out.println("A[" + r + "]: {" + A[r][0] + ", " + A[r][1] + "}, A[" + l + "]: {" + A[l][0] + ", "
					+ A[l][1] + "}");
			A[r] = A[l];
			print2DArray("A-s2", A);
		}
		A[l] = pivot;

		print2DArray("A-end", A);
		System.out.println("l: " + l);

		System.out.println("<< helper()");
		return l;
	}

	private static int compare(int[] p1, int[] p2) {
		int diff = p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
		System.out.println(
				"compare - p1: {" + p1[0] + ", " + p1[1] + "}, p2: {" + p2[0] + ", " + p2[1] + "}, diff: " + diff);
		return diff;
	}
	// --------------------------------------

	private static void print2DArray(String label, int[][] points) {
		StringBuilder log = new StringBuilder();
		log.append("[ ");
		for (int i = 0; i < points.length; i++)
			log.append("{" + points[i][0] + ", " + points[i][1] + "}, ");
		log = new StringBuilder(log.substring(0, log.length() - 2));
		log.append(" ]");
		System.out.println(label + ": " + log);
	}

}

class Point {

	double distance;
	int x, y;

	public Point(double distance, int x, int y) {
		this.distance = distance;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "distance: " + distance + ", x: " + x + ", y: " + y;
	}

}