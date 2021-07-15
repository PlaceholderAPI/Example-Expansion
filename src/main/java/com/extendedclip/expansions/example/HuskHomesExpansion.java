package com.extendedclip.expansions.example;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.william278.huskhomes2.api.HuskHomesAPI;
import me.william278.huskhomes2.teleport.points.Home;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.StringJoiner;

public class HuskHomesExpansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "huskhomes";
    }

    @Override
    public String getAuthor() {
        return "William278";
    }

    // This expansion version. Change in POM and here
    @Override
    public String getVersion() {
        return "1.0.3";
    }

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

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (offlinePlayer == null || !offlinePlayer.isOnline()) { return "Player not online"; }

        Player player = offlinePlayer.getPlayer();
        switch (params) {
            case "homes_set":
                return String.valueOf(HuskHomesAPI.getInstance().getHomeCount(player));
            case "homes_max":
                return String.valueOf(HuskHomesAPI.getInstance().getMaxSethomes(player));
            case "homes_free":
                return String.valueOf(HuskHomesAPI.getInstance().getFreeSethomes(player));
            case "homes_set_list":
                StringJoiner homeList = new StringJoiner(", ");
                for (Home h : HuskHomesAPI.getInstance().getHomes(player)) {
                    homeList.add(h.getName());
                }
                return homeList.toString();
            case "homes_set_public":
                int publicHomes = 0;
                for (Home h : HuskHomesAPI.getInstance().getHomes(player)) {
                    if (h.isPublic()) {
                        publicHomes++;
                    }
                }
                return String.valueOf(publicHomes);
            case "homes_set_public_list":
                StringJoiner publicHomeList = new StringJoiner(", ");
                for (Home h : HuskHomesAPI.getInstance().getHomes(player)) {
                    if (h.isPublic()) {
                        publicHomeList.add(h.getName());
                    }
                }
                return publicHomeList.toString();
            default:
                return null;
        }
    }

}
