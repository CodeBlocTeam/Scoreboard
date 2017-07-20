package io.github.codeblocteam.scoreboard.replacers;

import java.util.ArrayList;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import io.github.codeblocteam.scoreboard.PatternReplacer;

public class PlayerReplacer implements PatternReplacer {

	@Override
	public boolean commonToAllPlayers() {
		return false;
	}
	
	@Override
	public long repeatInterval() {
		return 0;
	}
	
	@Override
	public ArrayList<Class<? extends Event>> onEvents() {
		return new ArrayList<Class<? extends Event>>();
	}
	
	@Override
	public Text result(Player player) {
		return Text.of(player.getName());
	}

}
