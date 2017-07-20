package io.github.codeblocteam.scoreboard.modules;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

public class Printer {
	
	private static String title;
	private static String[] lines;
	
	public static void initScheme(String title, String model) {
		Printer.lines = model.split("\n");
		Printer.title = title;
	}
	
	public static void initPlayer(Player player) {
		Scoreboard scoreboard = Scoreboard.builder().build();
		Objective objective = Objective.builder()
				.displayName(Parser.parse(title, player))
				.name("objsbcb")
				.criterion(Criteria.DUMMY)
				.build();
		
		for (int i=0; i<lines.length; i++) {
			objective.getOrCreateScore(Parser.parse(lines[i], player)).setScore(lines.length - i - 1);
		}
		
		scoreboard.addObjective(objective);
		scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);
		
		player.setScoreboard(scoreboard);
	}
	
	private static void removeLine(int line, Objective objective) {
		Iterator<Score> iterator = objective.getScores().values().iterator();
		Score buffer;
		int scr = lines.length - line - 1;
		while(iterator.hasNext()) {
			buffer = iterator.next();
			if (buffer.getScore() == scr) {
				objective.removeScore(buffer);
				break;
			}
		}
	}
	
	public static void updateLine(int line, Player player) throws NoSuchElementException {
		Optional<Objective> optionalObjective = player.getScoreboard().getObjective("objsbcb");
		if (! optionalObjective.isPresent()) {
			throw new NoSuchElementException();
		}
		Objective objective = optionalObjective.get();
		removeLine(line, objective);
		objective.getOrCreateScore(Parser.parse(lines[line], player)).setScore(lines.length - line - 1);
	}
	
	public static void updateLineForEveryone(int line, boolean isCommon) throws NoSuchElementException {
		Iterator<Player> iterator = Sponge.getServer().getOnlinePlayers().iterator();
		if (! iterator.hasNext())
			return;
		
		if (isCommon) {
			Player player = iterator.next();
			Text text = Parser.parse(lines[line], player);
			Optional<Objective> optionalObjective = player.getScoreboard().getObjective("objsbcb");
			if (! optionalObjective.isPresent()) throw new NoSuchElementException();
			Objective objective = optionalObjective.get();
			removeLine(line, objective);
			objective.getOrCreateScore(text).setScore(lines.length - line - 1);
			while (iterator.hasNext()) {
				player = iterator.next();
				optionalObjective = player.getScoreboard().getObjective("objsbcb");
				if (! optionalObjective.isPresent()) throw new NoSuchElementException();
				objective = optionalObjective.get();
				removeLine(line, objective);
				objective.getOrCreateScore(text).setScore(lines.length - line - 1);
			}
		} else {
			while(iterator.hasNext()) {
				updateLine(line, iterator.next());
			}
		}
	}
}
