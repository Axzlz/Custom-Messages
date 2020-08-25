package com.starlk.plugins;

import org.cloudburstmc.server.event.EventHandler;
import org.cloudburstmc.server.event.EventPriority;
import org.cloudburstmc.server.event.player.PlayerJoinEvent;
import org.cloudburstmc.server.event.player.PlayerQuitEvent;
import org.cloudburstmc.server.player.Player;
import org.cloudburstmc.server.utils.Config;
import org.cloudburstmc.server.event.Listener;
import org.cloudburstmc.server.plugin.PluginBase;
import org.cloudburstmc.server.utils.TextFormat;

// TODO: Possibly add more placeholders.

public class Main extends PluginBase implements Listener {
    // Config variables
    static int currentConfig = 1;
    public static Config config;

    @Override
    public void onEnable() {

        // Sending a message to the console saying that the plugin has been enabled
        getLogger().info((TextFormat.GREEN + "Custom Messages plugin has been enabled."));
        getServer().getPluginManager().registerEvents(this, this); // Registering the plugin's events

        // Config
        saveDefaultConfig();
        config = getConfig();

        // Checking config's version - Hard coded
        if (config.getInt("Version") != currentConfig){
            getLogger().warn(TextFormat.RED + "Outdated config, please consider updating the plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent joinEvent) {
        // Assigning player into a variable
        Player player = joinEvent.getPlayer();

        String health = String.valueOf(player.getHealth());
        String ping = String.valueOf(player.getPing());

        if (config.getBoolean("JoinEnabled")) {
            joinEvent.setJoinMessage(config.getString("JoinMessage").
                    replace("%player%", player.getDisplayName()).
                    replace("%server%", getServer().getMotd()).
                    replace("%ping%", ping).
                    replace("%health%", health));

            // Checks if the user has put a join message or not
            if (config.getString("JoinMessage").trim().isEmpty() || config.getString("JoinMessage").equals("")) {
                this.getLogger().info("Couldn't define any join message.");
            }
        }
        else this.getLogger().info("Join messages are disabled.");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent quitEvent) {
        // Assigning player into a variable
        Player player = quitEvent.getPlayer();

        String health = String.valueOf(player.getHealth());
        String ping = String.valueOf(player.getPing());

        if (config.getBoolean("QuitEnabled")) {
            quitEvent.setQuitMessage(config.getString("QuitMessage").
            replace("%player%", player.getDisplayName()).
            replace("%server%", getServer().getMotd()).
            replace("%ping%", ping).
            replace("%health%", health));

            // Checks if the user has put a quit message or not
            if (config.getString("QuitMessage").trim().isEmpty() || config.getString("QuitMessage").equals("")) {
                this.getLogger().info("Couldn't define any quit message.");
            }
        }
        else this.getLogger().info("Quit messages are disabled.");
    }

}
