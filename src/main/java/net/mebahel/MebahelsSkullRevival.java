package net.mebahel;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.mebahel.entity.SkeletonHeadModEntities;
import net.mebahel.entity.SkeletonHeadEntity;
import net.mebahel.util.config.SkeletonHeadModConfig;
import net.mebahel.util.config.SkullEntityListConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MebahelsSkullRevival implements ModInitializer {
	public static final String MOD_ID = "mebahels-skeleton-revival";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		File configDir = new File(FabricLoader.getInstance().getConfigDir().toFile(), MOD_ID);
		SkeletonHeadModConfig.loadConfig(configDir);
		SkullEntityListConfig.loadConfig(configDir);
		FabricDefaultAttributeRegistry.register(SkeletonHeadModEntities.SKELETON_HEAD, SkeletonHeadEntity.setAttributes());
		LOGGER.info("[" +  MebahelsSkullRevival.MOD_ID  +  "] " + "Mebahel's Skeleton Revival is registered.");
	}
}