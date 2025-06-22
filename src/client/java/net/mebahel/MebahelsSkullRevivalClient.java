package net.mebahel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mebahel.entity.SkeletonHeadModEntities;
import net.mebahel.entity.skeleton_head.SkeletonHeadRenderer;

public class MebahelsSkullRevivalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(SkeletonHeadModEntities.SKELETON_HEAD, SkeletonHeadRenderer::new);
	}
}