package com.extendedclip.expansions.example;

import me.william278.huskhomes2.HuskHomes;
import me.william278.huskhomes2.teleport.points.Home;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class HuskHomesExpansion extends PlaceholderExpansion {
    
    private final String VERSION = getClass().getPackage().getImplementationVersion();
    private HuskHomes huskHomes;

    @Override
    public String getIdentifier() {
        return "huskhomes";
    }

    @Override
    public String getAuthor() {
        return "William278";
    }

    @Override
    public String getVersion() {
        return VERSION;
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
        huskHomes = (HuskHomes) Bukkit.getPluginManager().getPlugin(getRequiredPlugin());
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
                return String.valueOf(huskHomes.getAPI().getHomeCount(player));
            case "homes_max":
                return String.valueOf(huskHomes.getAPI().getMaxSethomes(player));
            case "homes_free":
                return String.valueOf(huskHomes.getAPI().getFreeSethomes(player));
            case "homes_set_list":
                StringBuilder homeList = new StringBuilder();
                for (Home h : huskHomes.getAPI().getHomes(player)) {
                    homeList.append(h.getName()).append(", ");
                }
                return homeList.toString();
            case "homes_set_public":
                int publicHomes = 0;
                for (Home h : huskHomes.getAPI().getHomes(player)) {
                    if (h.isPublic()) {
                        publicHomes++;
                    }
                }
                return String.valueOf(publicHomes);
            case "homes_set_public_list":
                StringBuilder publicHomeList = new StringBuilder();
                for (Home h : huskHomes.getAPI().getHomes(player)) {
                    if (h.isPublic()) {
                        publicHomeList.append(h.getName()).append(", ");
                    }
                }
                return publicHomeList.toString();
            default:
                return null;
        }
    }

}
