package view;

import java.util.List;
import java.util.Map;

public class OutputView {

	private static final String CAR_LOCATION_FORMAT = "%s : %s\n";
	private static final String WINNER_NAMES_DELIMITER = ", ";

	public void printResultPrefix() {
		System.out.println();
		System.out.println("실행 결과");
	}

	public void printCurrentLocations(Map<String, Integer> carLocations) {
		carLocations.forEach((name, location) -> {
			String locationToDisplay = "-".repeat(location);
			System.out.printf(CAR_LOCATION_FORMAT, name, locationToDisplay);
		});
		System.out.println();
	}

	public void printWinners(List<String> winnerNames) {
		String suffix = "가 최종 우승했습니다.";
		System.out.println(String.join(WINNER_NAMES_DELIMITER, winnerNames) + suffix);
	}
}
