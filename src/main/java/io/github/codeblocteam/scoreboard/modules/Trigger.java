package io.github.codeblocteam.scoreboard.modules;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.scheduler.Task;

import io.github.codeblocteam.scoreboard.struct.LineProperties;

public class Trigger {

	@SuppressWarnings("unchecked")
	public static void init(Object plugin, String model) {
		String[] lines = model.split("\n");
		
		LineProperties properties;
		for (int i=0; i<lines.length; i++) {
			properties = Parser.scan(lines[i]);
			for (Class<? extends Event> e : properties.events) {
				Sponge.getEventManager().registerListener(plugin, e, new GenericListener(i, properties.isCommonToAllPlayers));
			}
			if (properties.repeatInterval>0) {
				Task.builder()
				.execute(new CustomRunnable(i, properties.isCommonToAllPlayers))
				.interval(properties.repeatInterval, TimeUnit.SECONDS)
				.submit(plugin);
			}
		}
		
	}
	
}
