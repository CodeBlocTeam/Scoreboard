package io.github.codeblocteam.scoreboard;

import java.util.List;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

public interface PatternReplacer {
	
	public boolean commonToAllPlayers();
	public long repeatInterval();
	public List<Class<? extends Event>> onEvents();
	
	public Text result(Player player);
	
}
