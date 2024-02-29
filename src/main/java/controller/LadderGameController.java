package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import domain.ladder.Ladder;
import domain.ladder.LadderHeight;
import domain.player.Player;
import domain.player.Players;
import domain.prize.Prizes;
import generator.RandomLadderGenerator;
import view.InputView;
import view.OutputView;

public class LadderGameController {

	private final InputView inputView;
	private final OutputView outputView;

	public LadderGameController(InputView inputView, OutputView outputView) {
		this.inputView = inputView;
		this.outputView = outputView;
	}

	public void run() {
		Players players = retryOnException(this::getPlayers);
		int playerCount = players.getPlayerCount();
		Prizes prizes = retryOnException(() -> getPrizes(playerCount));
		LadderHeight height = retryOnException(this::getHeight);

		RandomLadderGenerator ladderGenerator = new RandomLadderGenerator(new Random());
		Ladder ladder = ladderGenerator.generate(height.value(), playerCount);

		printResult(players, prizes, ladder);
	}

	private void printResult(Players players, Prizes prizes, Ladder ladder) {
		printCreatedLadder(players, prizes, ladder);

		String playerNameToGetResult = retryOnException(() -> getPlayerNameToShowResult(players.getNames()));

		if ("all".equals(playerNameToGetResult)) {
			outputView.printAllPlayerResults(ladder.getAllPlayerResults(players, prizes));
			return;
		}

		Player player = players.findPlayerFromName(playerNameToGetResult);
		outputView.printOnePlayerResult(ladder.getOnePlayerResult(player, prizes));
	}

	private void printCreatedLadder(Players players, Prizes prizes, Ladder ladder) {
		outputView.printCurrentLadder(
			players.getNames(),
			ladder.createLadderConnectionStatus(),
			prizes.names()
		);
	}

	private Players getPlayers() {
		outputView.printRequestNamesMessage();
		List<String> names = inputView.readPlayerNames();

		List<Player> players = new ArrayList<>();
		for (int i = 0; i < names.size(); i++) {
			Player player = new Player(names.get(i), i);
			players.add(player);
		}

		return new Players(players);
	}

	private Prizes getPrizes(int playerCount) {
		outputView.printRequestPrizesMessage();
		List<String> prizeNames = inputView.readPrizes(playerCount);

		return new Prizes(prizeNames);
	}

	private LadderHeight getHeight() {
		outputView.printRequestLadderHeightMessage();
		int height = inputView.readLadderHeight();

		return new LadderHeight(height);
	}

	private String getPlayerNameToShowResult(List<String> allPlayerNames) {
		outputView.printRequestPlayerToGetResultMessage();
		return inputView.readPlayersToShowResult(allPlayerNames);
	}

	private <T> T retryOnException(Supplier<T> function) {
		try {
			T result = function.get();
			outputView.printEmptyLine();
			return result;
		} catch (IllegalArgumentException e) {
			outputView.printErrorMessage(e.getMessage());
			return retryOnException(function);
		}
	}
}
