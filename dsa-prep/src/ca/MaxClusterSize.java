package ca;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaxClusterSize {

	public static void main(String[] args) {

		case1();
		System.out.println("\n");
		case2();

	}

	private static void case1() {
		System.out.println(">> case1()");

		Integer[] bootingPower = { 3, 6, 1, 3, 4 };
		Integer[] processingPower = { 2, 1, 3, 4, 5 };
		findMaxSustainableClusterSize(Arrays.asList(processingPower), Arrays.asList(bootingPower), 25);

		System.out.println("<< case1()");
	}

	private static void case2() {
		System.out.println(">> case2()");

		Integer[] bootingPower = { 3, 6, 1, 3, 4, 3, 6, 1, 3, 4 };
		Integer[] processingPower = { 2, 1, 3, 4, 5, 2, 1, 3, 4, 5 };
		findMaxSustainableClusterSize(Arrays.asList(processingPower), Arrays.asList(bootingPower), 45);

		System.out.println("<< case2()");
	}

	static int findMaxSustainableClusterSize(List<Integer> processingPower, List<Integer> bootingPower, long powerMax) {
		System.out.println(">> findMaxSustainableClusterSize()");

		System.out.println("processing power - " + processingPower);
		System.out.println("booting power - " + bootingPower);
		System.out.println("--");

		Integer viableClusterSize = 0, innerIterations = 0, outerIterations = 0;

		// grouping: Less than O(N)
		for (int k = 2; k < processingPower.size(); k++) {

			for (int i = 0; i < processingPower.size(); i++) {
				outerIterations++;

				int clusterRange = i + k;
				if (clusterRange <= processingPower.size()) {
					innerIterations++;

					List<Integer> processingPowerCluster = processingPower.subList(i, clusterRange);
					List<Integer> bootingPowerCluster = bootingPower.subList(i, clusterRange);

					Integer sumOfProcessingPower = processingPowerCluster.stream().reduce(0, Integer::sum);
					Integer maxBootPower = Collections.max(bootingPowerCluster);
					Integer netPowerConsumed = maxBootPower + sumOfProcessingPower * k;

					String log = "sum of processing power - " + processingPowerCluster + ": " + sumOfProcessingPower
							+ " ; " + "max boot power - " + bootingPowerCluster + ": " + maxBootPower + " ; "
							+ "net power consumed - " + netPowerConsumed;

					if (netPowerConsumed <= powerMax) {
						viableClusterSize = k;
						log += " ; viable - YES";
						System.out.println(log);
						break;
					} else {
						log += " ; viable - NO";
						System.out.println(log);
						break;
					}
				}
			}
		}

		System.out.println("viable cluster size - " + viableClusterSize);
		System.out.println("inner iterations - " + innerIterations + ", outer iterations - " + outerIterations);

		System.out.println("<< findMaxSustainableClusterSize()");
		return viableClusterSize;
	}

}