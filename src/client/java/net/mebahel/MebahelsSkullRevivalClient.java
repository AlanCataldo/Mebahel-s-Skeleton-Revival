package net.mebahel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mebahel.entity.ModEntities;
import net.mebahel.entity.skeleton_head.SkeletonHeadRenderer;

public class MebahelsSkullRevivalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.SKELETON_HEAD, SkeletonHeadRenderer::new);
	}
}