package io.github.thebusybiscuit.cscorelib2.protection.modules;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;

import io.github.thebusybiscuit.cscorelib2.protection.ProtectableAction;
import io.github.thebusybiscuit.cscorelib2.protection.ProtectionModule;
import lombok.NonNull;

public class ASkyBlockProtectionModule implements ProtectionModule {

    private ASkyBlockAPI api;
    private final Plugin plugin;

    public ASkyBlockProtectionModule(@NonNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void load() {
        api = ASkyBlockAPI.getInstance();
    }

    @Override
    public boolean hasPermission(OfflinePlayer p, Location l, ProtectableAction action) {
        Island island = api.getIslandAt(l);

        if (island == null) {
            return true;
        }

        if (p.getUniqueId().equals(island.getOwner())) {
            return true;
        }

        for (UUID member : island.getMembers()) {
            if (p.getUniqueId().equals(member)) {
                return true;
            }
        }

        return false;
    }

}
