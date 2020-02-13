package me.swirtzly.angels.utils;

import me.swirtzly.angels.client.PlayerMovingSound;
import me.swirtzly.angels.client.renders.entities.RenderAnomaly;
import me.swirtzly.angels.client.renders.entities.RenderCG;
import me.swirtzly.angels.client.renders.entities.RenderWeepingAngel;
import me.swirtzly.angels.client.renders.tileentities.RenderTileEntityCG;
import me.swirtzly.angels.client.renders.tileentities.RenderTileEntityPlinth;
import me.swirtzly.angels.client.renders.tileentities.RenderTileEntitySnowArm;
import me.swirtzly.angels.common.entities.EntityAnomaly;
import me.swirtzly.angels.common.entities.EntityChronodyneGenerator;
import me.swirtzly.angels.common.entities.EntityWeepingAngel;
import me.swirtzly.angels.common.tileentities.TileEntityChronodyneGenerator;
import me.swirtzly.angels.common.tileentities.TileEntityPlinth;
import me.swirtzly.angels.common.tileentities.TileEntitySnowArm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Supplier;

public class ClientUtil {

	public static <T extends TileEntity> void bindTESR(Class<T> tileEntityClass, TileEntityRenderer<? super T> specialRenderer) {
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
	}

	public static <T extends Entity> void bindEntityRender(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}


    @OnlyIn(Dist.CLIENT)
    public static void playSound(Object object, SoundEvent soundIn, SoundCategory categoryIn, boolean repeat, Supplier<Boolean> stopCondition, float volumeSfx) {
        Minecraft.getInstance().getSoundHandler().play(new PlayerMovingSound(object, soundIn, categoryIn, repeat, stopCondition, volumeSfx));
    }

    @OnlyIn(Dist.CLIENT)
    public static void playSound(PlayerMovingSound playerMovingSound) {
        Minecraft.getInstance().getSoundHandler().play(playerMovingSound);
    }

    public static void doClientStuff() {
        ClientUtil.bindTESR(TileEntitySnowArm.class, new RenderTileEntitySnowArm());
        ClientUtil.bindTESR(TileEntityChronodyneGenerator.class, new RenderTileEntityCG());
        ClientUtil.bindTESR(TileEntityPlinth.class, new RenderTileEntityPlinth());

        ClientUtil.bindEntityRender(EntityWeepingAngel.class, RenderWeepingAngel::new);
        ClientUtil.bindEntityRender(EntityAnomaly.class, RenderAnomaly::new);
        ClientUtil.bindEntityRender(EntityChronodyneGenerator.class, (EntityRendererManager p_i50956_1_) -> new RenderCG(p_i50956_1_, Minecraft.getInstance().getItemRenderer(), 12));
    }
}
