package net.william278.huskhomes.placeholders;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.william278.huskhomes.api.HuskHomesAPI;
import net.william278.huskhomes.player.OnlineUser;
import net.william278.huskhomes.player.UserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

/**
 * PlaceholderAPI expansion for HuskHomes v3.x
 */
public class HuskHomesExpansion extends PlaceholderExpansion {

    @NotNull
    @Override
    public String getIdentifier() {
        return "huskhomes";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "William278";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "1.0.4";
    }

    @NotNull
    @Override
    public String getRequiredPlugin() {
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

    @NotNull
    private String getBooleanValue(final boolean bool) {
        return bool ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
    }

    @Override
    public String onRequest(@Nullable OfflinePlayer offlinePlayer, @NotNull String params) {
        if (offlinePlayer == null || !offlinePlayer.isOnline()) {
            return "Player not online";
        }

        // Adapt the player to an OnlineUser
        final HuskHomesAPI huskHomesAPI = HuskHomesAPI.getInstance();
        final OnlineUser player = huskHomesAPI.adaptUser(Bukkit.getPlayer(offlinePlayer.getUniqueId()));

        // Return the requested data
        return switch (params) {
            case "homes_count" -> String.valueOf(huskHomesAPI.getUserHomes(player).join().size());
            //todo case "max_homes" -> String.valueOf(huskHomesAPI.getMaxSetHomes(player));
            //todo case "homes_free" -> String.valueOf(huskHomesAPI.getFreeSetHomes(player));

            case "homes_list" -> huskHomesAPI.getUserHomes(player).join()
                    .stream()
                    .map(home -> home.meta.name)
                    .collect(Collectors.joining(", "));

            case "homes_set_public" -> String.valueOf(huskHomesAPI.getUserHomes(player).join()
                    .stream()
                    .filter(home -> home.isPublic)
                    .count());

            case "public_homes_count" -> huskHomesAPI.getUserHomes(player).join()
                    .stream()
                    .filter(home -> home.isPublic)
                    .map(home -> home.meta.name)
                    .collect(Collectors.joining(", "));

            case "ignoring_tp_requests" -> getBooleanValue(huskHomesAPI.getUserData(player.uuid).join()
                    .map(UserData::ignoringTeleports)
                    .orElse(false));

            default -> null;
        };
    }

}
