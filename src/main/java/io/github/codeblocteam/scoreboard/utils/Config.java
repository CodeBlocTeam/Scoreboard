package io.github.codeblocteam.scoreboard.utils;

import java.nio.file.Path;

import javax.inject.Inject;

import org.spongepowered.api.config.DefaultConfig;

public class Config {
	@Inject
	@DefaultConfig(sharedRoot = true)
	Path configPath;
	
	public static void init() {
		
	}
}
