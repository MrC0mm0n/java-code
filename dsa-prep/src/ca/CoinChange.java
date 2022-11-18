package ca;

// https://leetcode.com/problems/coin-change/
public class CoinChange {

	public static void main(String[] args) {

		System.out.println("min. coin change: " + coinChange(new int[] { 1, 2, 3 }, 6));
		// System.out.println("min. coin change: " + coinChange(new int[] { 1, 2, 5 },
		// 11));

	}

	public static int coinChange(int[] coins, int amount) {

		// table[i] will be storing the minimum number of coins required for i value. So
		// table[V] will have result
		int table[] = new int[amount + 1];

		// Base case (If given value V is 0)
		table[0] = 0;

		// Initialize all table values as Infinite
		for (int i = 1; i <= amount; i++)
			table[i] = Integer.MAX_VALUE;

		printTable(table);

		// Compute minimum coins required for all values from 1 to V
		for (int i = 1; i <= amount; i++) {
			System.out.println("**************** i: " + i + " ****************");
			// Go through all coins smaller than i
			for (int j = 0; j < coins.length; j++) {
				System.out.println("--- j: " + j + " ---");
				System.out.println("coins[j]: " + coins[j] + " (denomination)");
				printTable(table);
				if (coins[j] <= i) {
					int sub_res = table[i - coins[j]];
					System.out.println("table[i - coins[j]] (table[" + (i - coins[j]) + "]): " + sub_res);
					if (sub_res != Integer.MAX_VALUE && sub_res + 1 < table[i]) {
						table[i] = sub_res + 1;
						System.out.println("UPDATE: table[" + i + "]: " + table[i]);
					}
					printTable(table);
				}

			}

		}

		System.out.println("********************************");

		printTable(table);

		System.out.println("********************************");

		if (table[amount] == Integer.MAX_VALUE)
			return -1;

		return table[amount];

	}

	private static void printTable(int[] table) {

		String log = "table: ";
		for (int i = 0; i < table.length; i++)
			log += table[i] + ",";
		log = log.substring(0, log.length() - 1);
		System.out.println(log);

	}

}