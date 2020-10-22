package com.remodstudios.endless_fabric.client;

import java.util.function.Function;

import com.remodstudios.endless_fabric.fluid.EndlessModFluids;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;

public enum FluidRenderHelper implements ClientSpriteRegistryCallback, SimpleSynchronousResourceReloadListener, FluidRenderHandler {
	MOLTEN_TOPAZ("molten_topaz", EndlessModFluids.MOLTEN_TOPAZ, EndlessModFluids.FLOWING_MOLTEN_TOPAZ);

	private final String name;
	private final Identifier stillSpriteId;
	private final Identifier flowingSpriteId;
	private final FlowableFluid still;
	private final FlowableFluid flowing;
	private final Sprite[] fluidSprites = {null, null};

	FluidRenderHelper(String name, FlowableFluid still, FlowableFluid flowing) {
		this.name = name;
		this.stillSpriteId = new Identifier("endless_fabric", String.format("block/%s_still", name));
		this.flowingSpriteId = new Identifier("endless_fabric", String.format("block/%s_flow", name));
		this.still = still;
		this.flowing = flowing;
	}

	public void init() {
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(this);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(this);
		FluidRenderHandlerRegistry.INSTANCE.register(this.still, this);
		FluidRenderHandlerRegistry.INSTANCE.register(this.flowing, this);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), this.still, this.flowing);
	}

	@Override
	public void registerSprites(SpriteAtlasTexture atlasTexture, Registry registry) {
		registry.register(this.stillSpriteId);
		registry.register(this.flowingSpriteId);
	}

	@Override
	public Identifier getFabricId() {
		return new Identifier("endless_fabric", this.name + "_reload_listener");
	}

	@Override
	public void apply(ResourceManager manager) {
		Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
		this.fluidSprites[0] = atlas.apply(this.stillSpriteId);
		this.fluidSprites[1] = atlas.apply(this.flowingSpriteId);
	}

	@Override
	public Sprite[] getFluidSprites(BlockRenderView blockRenderView, BlockPos blockPos, FluidState fluidState) {
		return this.fluidSprites;
	}

	@Override
	public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
		return 0xFFFF00;
	}
}
