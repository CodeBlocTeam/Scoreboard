package io.github.codeblocteam.scoreboard;

import java.nio.file.Path;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import io.github.codeblocteam.scoreboard.modules.Parser;
import io.github.codeblocteam.scoreboard.modules.Printer;
import io.github.codeblocteam.scoreboard.modules.Trigger;
import io.github.codeblocteam.scoreboard.replacers.PlayerReplacer;
import io.github.codeblocteam.scoreboard.replacers.TestReplacer;

@Plugin(id = "scoreboard_codebloc", name = "Scoreboard Manager", version = "0.1")
public class ScoreboardManager {

	@Inject
	@DefaultConfig(sharedRoot = true)
	Path configPath;
	
	@Inject
	private Logger logger;
	
	public Logger getLogger() {
		return this.logger;
	}
	
	@Listener
	public void onPreInitialization(GamePreInitializationEvent event) {
		// Register pre-configured pattern replacers
		Parser.registerPattern("player", new PlayerReplacer());
		Parser.registerPattern("test", new TestReplacer());
		
		// A CHANGER
		Printer.initScheme("Oh oui c'est le sexe", "Sexe sexe sexe\nTEST : ~test~\nprout\n~player~ dit coucou\n~player~ ~player~");
		
	}
	
	@Listener
	public void onInitialization(GameInitializationEvent event) {
		/*
		PluginContainer pluginContainer = Sponge.getPluginManager().fromInstance(this).get();
		
		// If config does not exist, create it
		if (Files.notExists(configPath)) {
			try {
				pluginContainer.getAsset("default.conf").get().copyToFile(configPath);
			} catch (IOException e) {
				logger.error("An error occured while setting default config file");
			};
		}
		
		*/
		
		// TODO : fournir le service pour que les autres plugins puissent changer le modèle / ajouter des patterns.
		// Sponge.getServiceManager().setProvider(this, service, provider);
	}
	
	@Listener
	public void onServerStarted(GameStartedServerEvent e) {
		Trigger.init(this, "Sexe sexe sexe\nTEST : ~test~\nprout\n~player~ dit coucou\n~player~ ~player~");
	}
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event) {
		Printer.initPlayer(event.getTargetEntity());
	}
}
