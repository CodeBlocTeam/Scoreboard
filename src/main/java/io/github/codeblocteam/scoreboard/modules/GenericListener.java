package io.github.codeblocteam.scoreboard.modules;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventListener;

@SuppressWarnings("rawtypes")
public class GenericListener implements EventListener {

	private int line;
	private boolean isCommon;
	
	public GenericListener(int line, boolean isCommon) {
		this.line = line;
		this.isCommon = isCommon;
	}
	
	@Override
	public void handle(Event event) throws Exception {
		Printer.updateLineForEveryone(line, isCommon);
	}
	
}
