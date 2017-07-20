package io.github.codeblocteam.scoreboard.modules;

public class CustomRunnable implements Runnable {

	private int line;
	private boolean isCommon;
	
	public CustomRunnable(int line, boolean isCommon) {
		this.line = line;
		this.isCommon = isCommon;
	}
	
	@Override
	public void run() {
		Printer.updateLineForEveryone(line, isCommon);
	}

}
