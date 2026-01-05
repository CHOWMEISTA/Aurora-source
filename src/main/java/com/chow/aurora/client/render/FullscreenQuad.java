package com.chow.aurora.client.render;

import com.mojang.blaze3d.vertex.*;

public class FullscreenQuad {
    public static void draw() {
        Tesselator t = Tesselator.getInstance();
        BufferBuilder b = t.getBuilder();
        b.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        b.vertex(-1, -1, 0).uv(0, 1).endVertex();
        b.vertex(1, -1, 0).uv(1, 1).endVertex();
        b.vertex(1, 1, 0).uv(1, 0).endVertex();
        b.vertex(-1, 1, 0).uv(0, 0).endVertex();
        t.end();
    }
}
