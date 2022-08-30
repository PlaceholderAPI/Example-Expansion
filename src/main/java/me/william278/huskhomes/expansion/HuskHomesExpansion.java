package me.william278.huskhomes.expansion;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.william278.huskhomes2.HuskHomes;
import me.william278.huskhomes2.api.HuskHomesAPI;
import me.william278.huskhomes2.teleport.points.Home;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class HuskHomesExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "huskhomes";
    }

    @Override
    public @NotNull String getAuthor() {
        return "William278";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.4";
    }

    @Override
    public @NotNull String getRequiredPlugin() {
        return "HuskHomes";
    }

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(getRequiredPlugin()) != null;
    }

    @Override
    public boolean register() {
        Plugin huskHomes = Bukkit.getPluginManager().getPlugin(getRequiredPlugin());
        if (huskHomes != null) {
            return super.register();
        }
        return false;
    }

    private String bool(final boolean bool) {
        return bool ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        if (offlinePlayer == null || !offlinePlayer.isOnline()) {
            return "Player not online";
        }

        final var player = offlinePlayer.getPlayer();

        // To get rif of IDE warnings
        if (player == null) {
            return "Player not online";
        }

        final var api = HuskHomesAPI.getInstance();

        return switch (params) {
            case "homes_set" -> String.valueOf(api.getHomeCount(player));
            case "homes_max" -> String.valueOf(api.getMaxSetHomes(player));
            case "homes_free" -> String.valueOf(api.getFreeSetHomes(player));

            case "homes_set_list" -> api.getHomes(player).stream()
                    .map(Home::getName)
                    .collect(Collectors.joining(", "));

            case "homes_set_public" -> String.valueOf(api.getHomes(player).stream().filter(Home::isPublic).count());

            case "homes_set_public_list" -> api.getHomes(player).stream()
                    .filter(Home::isPublic)
                    .map(Home::getName)
                    .collect(Collectors.joining(", "));

            case "ignore_tp" -> bool(HuskHomes.isIgnoringTeleportRequests(player.getUniqueId()));
            default -> null;
        };
    }

}
