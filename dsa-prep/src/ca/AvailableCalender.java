package ca;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AvailableCalender {

	public static void main(String[] args) {

		case1();

	}

	private static void case1() {
		System.out.println(">> case1()");

		List<Interval> intervals = new ArrayList<Interval>();

		// Person A
		intervals.add(new Interval(8, 10));
		intervals.add(new Interval(10, 11));
		intervals.add(new Interval(13, 14));
		intervals.add(new Interval(16, 17));

		// Person B
		intervals.add(new Interval(9, 10));
		intervals.add(new Interval(11, 12));
		intervals.add(new Interval(13, 14));

		// Person C
		intervals.add(new Interval(9, 10));
		intervals.add(new Interval(10, 11));

		// Person D
		intervals.add(new Interval(10, 11));
		intervals.add(new Interval(13, 15));

		findOverlap(intervals);

		// 08--09--10--11--12--13--14--15--16--17 : Time
		// ||**||**||**||--||--||**||--||--||**|| : Person A
		// ||--||**||--||**||--||**||--||--||--|| : Person B
		// ||--||**||**||--||--||--||--||--||--|| : Person C
		// ||--||--||**||--||--||**||**||--||--|| : Person D
		// ||**||**||**||**||--||**||**||--||**|| : Merge
		// Busy - (8,12),(13,15),(16,17); Free - (12,13),(15,16)

		System.out.println("<< case1()");
	}

	private static void findOverlap(List<Interval> intervals) {

		System.out.println("intervals: " + intervals);

		// sort: O(N*logN)
		intervals.sort((a, b) -> a.start - b.start);
		System.out.println("intervals (sorted): " + intervals);

		findAvailabilities(useListToMerge(intervals));

		findAvailabilities(useStackToMerge(intervals));

		findAvailabilities(useMyApproachToMerge(intervals));

	}

	private static List<Interval> useListToMerge(List<Interval> intervals) {
		System.out.println(">> useListToMerge()");

		// merge intervals: O(N-2)
		List<Interval> unavailabilities = new ArrayList<Interval>();
		unavailabilities.add(new Interval(intervals.get(0).start, intervals.get(0).end));
		for (int i = 1; i < intervals.size(); i++) {

			Interval previous = unavailabilities.get(unavailabilities.size() - 1);
			Interval next = intervals.get(i);

			if ((previous.end > next.start || previous.end == next.start)
					&& (previous.end < next.end || previous.end == next.end)) {
				/*
				 * System.out.println(previous + ", " + next + ": ( " + previous.end + " > " +
				 * next.start + " || " + previous.end + " == " + next.start + " ) " + " && ( " +
				 * previous.end + " < " + next.end + " || " + previous.end + " == " + next.end +
				 * " ): UPDATE - previous.end = " + next.end);
				 */
				previous.end = next.end;
			} else {
				/*
				 * System.out.println(previous + ", " + next + ": ( " + previous.end + " > " +
				 * next.start + " || " + previous.end + " == " + next.start + " ) " + " && ( " +
				 * previous.end + " < " + next.end + " || " + previous.end + " == " + next.end +
				 * " ): ADD - " + next);
				 */
				unavailabilities.add(new Interval(next.start, next.end));
			}

			// System.out.println("unavailabilities: " + unavailabilities);
		}
		System.out.println("unavailabilities: " + unavailabilities);

		System.out.println("<< useListToMerge()");
		return unavailabilities;
	}

	private static List<Interval> useStackToMerge(List<Interval> intervals) {
		System.out.println(">> useStackToMerge()");

		Stack<Interval> stack = new Stack<>();
		stack.push(new Interval(intervals.get(0).start, intervals.get(0).end));

		for (int i = 1; i < intervals.size(); i++) {

			Interval previous = stack.peek();
			Interval next = intervals.get(i);

			if (previous.end < next.start)
				stack.push(new Interval(next.start, next.end));
			else if (previous.end < next.end) {
				previous.end = next.end;
				stack.pop();
				stack.push(previous);
			}

		}

		List<Interval> unavailabilities = new ArrayList<Interval>(stack);
		System.out.println("unavailabilities: " + unavailabilities);

		System.out.println("<< useStackToMerge()");
		return unavailabilities;
	}

	private static List<Interval> useMyApproachToMerge(List<Interval> intervals) {
		System.out.println(">> useMyApproachToMerge()");

		List<Interval> unavailabilities = new ArrayList<Interval>();

		unavailabilities.add(new Interval(intervals.get(0).start, intervals.get(0).end));
		for (int i = 1; i < intervals.size(); i++) {

			Interval previous = unavailabilities.get(unavailabilities.size() - 1);
			Interval next = intervals.get(i);

			if (previous.end >= next.start)
				previous.end = next.end;
			else if (previous.end < next.start)
				unavailabilities.add(new Interval(next.start, next.end));

		}
		System.out.println("unavailabilities: " + unavailabilities);

		System.out.println("<< useMyApproachToMerge()");
		return unavailabilities;
	}

	private static void findAvailabilities(List<Interval> unavailabilities) {

		List<Interval> availabilities = new ArrayList<Interval>();
		for (int i = 1; i < unavailabilities.size(); i++) {
			availabilities.add(new Interval(unavailabilities.get(i - 1).end, unavailabilities.get(i).start));
		}
		System.out.println("availability: " + availabilities);

	}

}

class Interval {
	Integer start, end;

	public Interval(Integer start, Integer end) {
		this.start = start;
		this.end = end;
	}

	public boolean equals(Interval interval) {
		if (this.start == interval.start && this.end == interval.end)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "[" + this.start + ", " + this.end + "]";
	}
}