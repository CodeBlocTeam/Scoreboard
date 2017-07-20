package io.github.codeblocteam.scoreboard.replacers;

import java.util.ArrayList;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;

import io.github.codeblocteam.scoreboard.PatternReplacer;

public class TestReplacer implements PatternReplacer {
	
	private int count = 0;

	@Override
	public boolean commonToAllPlayers() {
		return false;
	}

	@Override
	public long repeatInterval() {
		return 5;
	}

	@Override
	public ArrayList<Class<? extends Event>> onEvents() {
		ArrayList<Class<? extends Event>> events = new ArrayList<Class<? extends Event>>();
		events.add(ChangeBlockEvent.Break.class);
		return events;
	}

	@Override
	public Text result(Player player) {
		count++;
		return Text.of(count);
	}

}
