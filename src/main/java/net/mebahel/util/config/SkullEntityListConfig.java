package net.mebahel.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import net.minecraft.util.Identifier;
import net.mebahel.MebahelsSkullRevival;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SkullEntityListConfig {

    private static final String CONFIG_FILE_NAME = "entity-type-that-should-spawn-head_config.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final Set<Identifier> skeletonHeadEntities = new HashSet<>();
    public static final Set<Identifier> witherSkeletonHeadEntities = new HashSet<>();

    public static void loadConfig(File configDir) {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        File configFile = new File(configDir, CONFIG_FILE_NAME);

        if (!configFile.exists()) {
            saveDefaultConfig(configFile);
        }

        try (Reader reader = new FileReader(configFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);

            skeletonHeadEntities.clear();
            witherSkeletonHeadEntities.clear();

            if (json.has("skeletonHeadEntities")) {
                JsonArray skeletonArray = json.getAsJsonArray("skeletonHeadEntities");
                for (int i = 0; i < skeletonArray.size(); i++) {
                    skeletonHeadEntities.add(new Identifier(skeletonArray.get(i).getAsString()));
                }
            }

            if (json.has("witherSkeletonHeadEntities")) {
                JsonArray witherArray = json.getAsJsonArray("witherSkeletonHeadEntities");
                for (int i = 0; i < witherArray.size(); i++) {
                    witherSkeletonHeadEntities.add(new Identifier(witherArray.get(i).getAsString()));
                }
            }

        } catch (IOException e) {
            System.err.println("[" + MebahelsSkullRevival.MOD_ID + "] Failed to load skull entity config: " + e.getMessage());
        }
    }

    private static void saveDefaultConfig(File configFile) {
        JsonObject json = new JsonObject();

        JsonArray defaultSkeleton = new JsonArray();
        defaultSkeleton.add("minecraft:skeleton");

        JsonArray defaultWither = new JsonArray();
        defaultWither.add("minecraft:wither_skeleton");

        json.add("skeletonHeadEntities", defaultSkeleton);
        json.add("witherSkeletonHeadEntities", defaultWither);

        try (Writer writer = new FileWriter(configFile)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            System.err.println("[" + MebahelsSkullRevival.MOD_ID + "] Failed to write default skull entity config: " + e.getMessage());
        }
    }

    public static boolean isSkeletonHeadEntity(Identifier id) {
        return skeletonHeadEntities.contains(id);
    }

    public static boolean isWitherSkeletonHeadEntity(Identifier id) {
        return witherSkeletonHeadEntities.contains(id);
    }
}
