package lol.bai.megane.module.kibe.provider;

import java.util.function.Function;

import lol.bai.megane.api.provider.FluidProvider;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

@SuppressWarnings("UnstableApiUsage")
public class TankFluidProvider<T> extends FluidProvider<T> {

    private final Function<T, SingleVariantStorage<FluidVariant>> tankGetter;
    private SingleVariantStorage<FluidVariant> tank;

    public TankFluidProvider(Function<T, SingleVariantStorage<FluidVariant>> tankGetter) {
        this.tankGetter = tankGetter;
    }

    @Override
    public void setContext(World world, BlockPos pos, PlayerEntity player, T t) {
        super.setContext(world, pos, player, t);

        this.tank = tankGetter.apply(t);
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public @Nullable Fluid getFluid(int slot) {
        return tank.variant.getFluid();
    }

    @Override
    public double getStored(int slot) {
        return droplets(tank.getAmount());
    }

    @Override
    public double getMax(int slot) {
        return droplets(tank.getCapacity());
    }

}
