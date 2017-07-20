package io.github.codeblocteam.scoreboard.modules;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import io.github.codeblocteam.scoreboard.PatternReplacer;
import io.github.codeblocteam.scoreboard.struct.LineProperties;

public class Parser {
	
	private static String patternStart = "~";
	private static String patternEnd = "~";
	private static Pattern pattern = Pattern.compile(Parser.patternStart + "([a-zA-Z0-9_]+)" + Parser.patternEnd);
	
	private static HashMap<String, PatternReplacer> patterns = new HashMap<String, PatternReplacer>();
	
	public static void setPatternStart(String pattern) {
		Parser.patternStart = pattern;
		Parser.pattern = Pattern.compile(Parser.patternStart + "([a-zA-Z0-9_]+)" + Parser.patternEnd);
	}
	
	public static void setPatternEnd(String pattern) {
		Parser.patternEnd = pattern;
		Parser.pattern = Pattern.compile(Parser.patternStart + "([a-zA-Z0-9_]+)" + Parser.patternEnd);
	}
	
	public static void setPatternStartAndEnd(String pattern) {
		Parser.patternStart = pattern;
		Parser.patternEnd = pattern;
		Parser.pattern = Pattern.compile(Parser.patternStart + "([a-zA-Z0-9_]+)" + Parser.patternEnd);
	}
	
	public static Text parse(String text, Player player) {
		Text result = Text.of("");
		int index = 0;
		int ls = patternStart.length();
		int le = patternStart.length();
		
		Matcher matcher = Parser.pattern.matcher(text);
		
		while (matcher.find()) {
			result = result.concat(Text.of(text.substring(index, matcher.start(1)-ls)));
			String pattern = matcher.group(1);
			result = result.concat(patterns.get(pattern).result(player));
			index = matcher.end(1)+le;
		}
		result = result.concat(Text.of(text.substring(index)));
		
		return result;
	}
	
	public static LineProperties scan(String text) {
		LineProperties properties = new LineProperties();
		Matcher matcher = Parser.pattern.matcher(text);
		
		PatternReplacer buffer;
		while (matcher.find()) {
			buffer = patterns.get(matcher.group(1));
			if (! buffer.commonToAllPlayers()) {
				properties.isCommonToAllPlayers = false;
			}
			for (Class<? extends Event> event : buffer.onEvents()) {
				properties.events.add(event);
			}
			if (buffer.repeatInterval() > 0) {
				if (properties.repeatInterval > 0) {
					properties.repeatInterval = Long.min(properties.repeatInterval, buffer.repeatInterval());
				} else {
					properties.repeatInterval = buffer.repeatInterval();
				}
			}
		}
		return properties;
	}
	
	public static PatternReplacer registerPattern(String pattern, PatternReplacer replacer) {
		return patterns.put(pattern, replacer);
	}
}
