package com.antilootsteal.models;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class ProtectedLoot {

    private final UUID ownerUUID;
    private final String ownerName;
    private final Item itemEntity;
    private ArmorStand hologram;
    private final long expirationTime;
    private BukkitTask timerTask;

    public ProtectedLoot(UUID ownerUUID, String ownerName, Item itemEntity, long expirationTime) {
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.itemEntity = itemEntity;
        this.expirationTime = expirationTime;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expirationTime;
    }

    public int getRemainingSeconds() {
        long remaining = expirationTime - System.currentTimeMillis();
        return remaining > 0 ? (int) Math.ceil(remaining / 1000.0) : 0;
    }

    public void cleanup() {
        if (hologram != null && hologram.isValid()) {
            hologram.remove();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    public UUID getOwnerUUID() { return ownerUUID; }
    public String getOwnerName() { return ownerName; }
    public Item getItemEntity() { return itemEntity; }
    public ArmorStand getHologram() { return hologram; }
    public void setHologram(ArmorStand hologram) { this.hologram = hologram; }
    public long getExpirationTime() { return expirationTime; }
    public BukkitTask getTimerTask() { return timerTask; }
    public void setTimerTask(BukkitTask timerTask) { this.timerTask = timerTask; }
}
