package com.chow.aurora.client;

import com.chow.aurora.client.render.AuroraRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class AuroraClient {

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelLastEvent event) {
        AuroraRenderer.renderPost(event.getPartialTick());
    }
}
