package io.github.codeblocteam.scoreboard.struct;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.event.Event;

public class LineProperties {
	public boolean isCommonToAllPlayers = true;
	public Set<Class<? extends Event>> events = new HashSet<Class<? extends Event>>();
	public long repeatInterval = (long) 0;
}
