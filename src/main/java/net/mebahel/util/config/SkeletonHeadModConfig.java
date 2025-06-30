package net.mebahel.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.mebahel.MebahelsSkullRevival;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SkeletonHeadModConfig {
    private static final String CONFIG_FILE_NAME = MebahelsSkullRevival.MOD_ID + "_config.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static int skeletonHeadSpawnRate = 12;
    public static int witherSkeletonHeadSpawnRate = 12;
    public static int timeBeforeRevival = 140;
    public static boolean skeletonHeadShouldDropExperience = true;
    public static boolean respawnedEntityShouldDropExperience = true;
    public static boolean respawnedEntityShouldDropItem = true;
    public static boolean canSpawnFromSpawner = false;

    public static void loadConfig(File configDir) {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        File configFile = new File(configDir, CONFIG_FILE_NAME);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);

                boolean updated = false;

                if (data.skeletonHeadSpawnRate == null || data.skeletonHeadSpawnRate < 0 || data.skeletonHeadSpawnRate > 100) {
                    data.skeletonHeadSpawnRate = 12;
                    updated = true;
                }

                if (data.witherSkeletonHeadSpawnRate == null || data.witherSkeletonHeadSpawnRate < 0 || data.witherSkeletonHeadSpawnRate > 100) {
                    data.witherSkeletonHeadSpawnRate = 12;
                    updated = true;
                }

                if (data.timeBeforeRevival == null || data.timeBeforeRevival < 30) {
                    data.timeBeforeRevival = 140;
                    updated = true;
                }

                if (data.skeletonHeadShouldDropExperience == null) {
                    data.skeletonHeadShouldDropExperience = false;
                    updated = true;
                }

                if (data.respawnedEntityShouldDropExperience == null) {
                    data.respawnedEntityShouldDropExperience = false;
                    updated = true;
                }

                if (data.respawnedEntityShouldDropItem == null) {
                    data.respawnedEntityShouldDropItem = false;
                    updated = true;
                }

                if (data.canSpawnFromSpawner == null) {
                    data.canSpawnFromSpawner = false;
                    updated = true;
                }

                skeletonHeadSpawnRate = data.skeletonHeadSpawnRate;
                witherSkeletonHeadSpawnRate = data.witherSkeletonHeadSpawnRate;
                timeBeforeRevival = data.timeBeforeRevival;
                skeletonHeadShouldDropExperience = data.skeletonHeadShouldDropExperience;
                respawnedEntityShouldDropExperience = data.respawnedEntityShouldDropExperience;
                respawnedEntityShouldDropItem = data.respawnedEntityShouldDropItem;
                canSpawnFromSpawner = data.canSpawnFromSpawner;

                if (updated) {
                    saveConfig(configDir);
                }
            } catch (IOException e) {
                System.err.println("[" +  MebahelsSkullRevival.MOD_ID  +  "] " + "Failed to load config file: " + e.getMessage());
            }
        } else {
            saveConfig(configDir);
        }
    }

    public static void saveConfig(File configDir) {
        File configFile = new File(configDir, CONFIG_FILE_NAME);
        ConfigData data = new ConfigData(skeletonHeadSpawnRate, witherSkeletonHeadSpawnRate, timeBeforeRevival, skeletonHeadShouldDropExperience,
                respawnedEntityShouldDropExperience, respawnedEntityShouldDropItem, canSpawnFromSpawner);
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("[" +  MebahelsSkullRevival.MOD_ID  +  "] " + "Failed to save config file: " + e.getMessage());
        }
    }

    private static class ConfigData {
        Integer skeletonHeadSpawnRate;
        Integer witherSkeletonHeadSpawnRate;
        Integer timeBeforeRevival;
        Boolean skeletonHeadShouldDropExperience;
        Boolean respawnedEntityShouldDropExperience;
        Boolean respawnedEntityShouldDropItem;
        Boolean canSpawnFromSpawner;

        ConfigData(int skeletonHeadSpawnRate, int witherSkeletonHeadSpawnRate, int timeBeforeRevival, Boolean skeletonHeadShouldDropExperience,
                   Boolean respawnedEntityShouldDropExperience, Boolean respawnedEntityShouldDropItem,
                   Boolean canSpawnFromSpawner) {
            this.skeletonHeadSpawnRate = skeletonHeadSpawnRate;
            this.witherSkeletonHeadSpawnRate = witherSkeletonHeadSpawnRate;
            this.timeBeforeRevival = timeBeforeRevival;
            this.skeletonHeadShouldDropExperience = skeletonHeadShouldDropExperience;
            this.respawnedEntityShouldDropExperience = respawnedEntityShouldDropExperience;
            this.respawnedEntityShouldDropItem = respawnedEntityShouldDropItem;
            this.canSpawnFromSpawner = canSpawnFromSpawner;
        }
    }
}
