package com.remodstudios.endless_fabric.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.remodstudios.endless_fabric.fluid.EndlessModFluids;
import com.remodstudios.endless_fabric.mixin.BucketItemAccessor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidCauldronBlock extends CauldronBlock {
	public static final EnumProperty<FluidType> FLUID_TYPE = EnumProperty.of("fluid", FluidType.class);

	public FluidCauldronBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		Item handItem = player.getStackInHand(hand).getItem();
		if (handItem instanceof BucketItem && handItem instanceof BucketItemAccessor) {
			if (EndlessModFluids.CAULDRON_FLUIDS.contains(((BucketItemAccessor) handItem).getFluid())) {
				world.setBlockState(pos, state.with(FLUID_TYPE, FluidType.valueOf(((BucketItemAccessor) handItem).getFluid())).with(LEVEL, 3));
				return ActionResult.SUCCESS;
			}
			return ActionResult.CONSUME;
		}
		return ActionResult.PASS;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FLUID_TYPE);
		super.appendProperties(builder);
	}

	public enum FluidType implements StringIdentifiable {
		NONE("none", false, null),
		MOLTEN_TOPAZ("molten_topaz", true, EndlessModFluids.MOLTEN_TOPAZ);

		private static final BiMap<Fluid, FluidType> FLUID_MAP;
		private final String name;
		private final boolean lit;
		private final Fluid fluid;

		FluidType(String name, boolean lit, Fluid fluid) {
			this.name = name;
			this.lit = lit;
			this.fluid = fluid;
		}

		@Override
		public String asString() {
			return this.name;
		}

		public Fluid getFluid() {
			return this.fluid;
		}

		public boolean isLit() {
			return this.lit;
		}

		public static FluidType valueOf(Fluid fluid) {
			return FLUID_MAP.get(fluid);
		}

		static {
			ImmutableBiMap.Builder<Fluid, FluidType> builder = ImmutableBiMap.builder();
			for (FluidType type : values()) {
				builder.put(type.getFluid(), type);
			}
			FLUID_MAP = builder.build();
		}
	}
}
