package domain.player;

import java.util.List;

import domain.Name;
import domain.ladder.Bar;
import domain.ladder.Floor;
import domain.ladder.Ladder;

public class Player {

	private final Name name;
	private int position;

	public Player(String name, int position) {
		this.name = new Name(name);
		this.position = position;
	}

	public void playLadder(Ladder ladder) {
		List<Floor> floors = ladder.getFloors();
		floors.forEach(this::move);
	}

	private void move(Floor floor) {
		Bar bar = floor.getBar(position);
		if (bar.isConnectedToRight()) {
			position++;
			return;
		}
		if (bar.isConnectedToLeft()) {
			position--;
		}
	}

	public String getName() {
		return name.value();
	}

	public int getPosition() {
		return position;
	}
}