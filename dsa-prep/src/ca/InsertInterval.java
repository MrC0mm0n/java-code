package ca;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/insert-interval/
public class InsertInterval {

	public static void main(String[] args) {

	}

	public int[][] insert(int[][] intervals, int[] newInterval) {

		boolean addedNewInterval = false;

		if (intervals.length == 0) {
			int[][] result = new int[1][2];
			result[0][0] = newInterval[0];
			result[0][1] = newInterval[1];
			return result;
		}

		List<Interval> unmerged = new ArrayList<>();
		for (int i = 0; i < intervals.length; i++)
			unmerged.add(new Interval(intervals[i][0], intervals[i][1]));

		if (newInterval[0] < intervals[0][0]) {
			unmerged.add(0, new Interval(newInterval[0], newInterval[1]));
			addedNewInterval = true;
		}

		if (intervals[intervals.length - 1][0] < newInterval[0]) {
			unmerged.add(unmerged.size(), new Interval(newInterval[0], newInterval[1]));
			addedNewInterval = true;
		}

		if (!addedNewInterval) {
			for (int i = 1; i < unmerged.size(); i++) {
				Interval previous = unmerged.get(i - 1);
				Interval now = unmerged.get(i);
				if (previous.start <= newInterval[0] && newInterval[0] <= now.start) {
					unmerged.add(i, new Interval(newInterval[0], newInterval[1]));
					break;
				}

			}
		}

		System.out.println("unmerged: " + unmerged);

		List<Interval> merged = new ArrayList<>();
		merged.add(new Interval(unmerged.get(0).start, unmerged.get(0).end));
		for (int i = 1; i < unmerged.size(); i++) {
			Interval previous = merged.get(merged.size() - 1);
			Interval now = unmerged.get(i);

			if (previous.end >= now.start && previous.end < now.end)
				previous.end = now.end;
			else if (previous.end < now.start)
				merged.add(new Interval(now.start, now.end));
		}

		if (merged.get(merged.size() - 1).end < newInterval[1])
			merged.get(merged.size() - 1).end = newInterval[1];

		System.out.println("merged: " + merged);

		int[][] result = new int[merged.size()][2];
		for (int i = 0; i < merged.size(); i++) {
			result[i][0] = merged.get(i).start;
			result[i][1] = merged.get(i).end;
		}

		return result;
	}

}