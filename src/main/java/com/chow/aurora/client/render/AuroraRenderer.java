package com.chow.aurora.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTarget;
import org.lwjgl.opengl.GL11;

public class AuroraRenderer {

    private static RenderTarget bloomBuffer1;
    private static RenderTarget bloomBuffer2;
    private static ShaderProgram bloomShaderH;
    private static ShaderProgram bloomShaderV;

    public static void renderPost(float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        if (bloomBuffer1 == null || bloomBuffer1.width != mc.getWindow().getWidth()) {
            initBuffers(mc);
        }

        if (bloomShaderH == null) {
            bloomShaderH = new ShaderProgram(
                ""aurora:shaders/bloom_h.vsh"",
                ""aurora:shaders/bloom_h.fsh""
            );
        }
        if (bloomShaderV == null) {
            bloomShaderV = new ShaderProgram(
                ""aurora:shaders/bloom_v.vsh"",
                ""aurora:shaders/bloom_v.fsh""
            );
        }

        // Copy main framebuffer ? bloomBuffer1
        mc.getMainRenderTarget().bindRead();
        bloomBuffer1.bindWrite(true);
        RenderSystem.blitFramebuffer(
                0, 0,
                mc.getWindow().getWidth(), mc.getWindow().getHeight(),
                0, 0,
                mc.getWindow().getWidth(), mc.getWindow().getHeight(),
                GL11.GL_COLOR_BUFFER_BIT,
                GL11.GL_NEAREST
        );

        // Horizontal blur pass
        bloomBuffer2.bindWrite(true);
        RenderSystem.disableDepthTest();
        bloomShaderH.bind();
        bloomShaderH.uniform1f(""horizontal"", 1.0);
        RenderSystem.setShaderTexture(0, bloomBuffer1.getColorTextureId());
        FullscreenQuad.draw();
        bloomShaderH.unbind();

        // Vertical blur pass ? main framebuffer
        mc.getMainRenderTarget().bindWrite(true);
        bloomShaderV.bind();
        bloomShaderV.uniform1f(""horizontal"", 0.0);
        RenderSystem.setShaderTexture(0, bloomBuffer2.getColorTextureId());
        FullscreenQuad.draw();
        bloomShaderV.unbind();
        RenderSystem.enableDepthTest();
    }

    private static void initBuffers(Minecraft mc) {
        if (bloomBuffer1 != null) bloomBuffer1.destroyBuffers();
        if (bloomBuffer2 != null) bloomBuffer2.destroyBuffers();

        int w = mc.getWindow().getWidth();
        int h = mc.getWindow().getHeight();
        bloomBuffer1 = new RenderTarget(w, h, true, Minecraft.ON_OSX);
        bloomBuffer2 = new RenderTarget(w, h, true, Minecraft.ON_OSX);
    }
}
