package com.chow.aurora.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL20;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ShaderProgram {

    private final int program;

    public ShaderProgram(String vert, String frag) {
        int v = compile(GL20.GL_VERTEX_SHADER, vert);
        int f = compile(GL20.GL_FRAGMENT_SHADER, frag);
        program = GL20.glCreateProgram();
        GL20.glAttachShader(program, v);
        GL20.glAttachShader(program, f);
        GL20.glLinkProgram(program);
    }

    private int compile(int type, String path) {
        int id = GL20.glCreateShader(type);
        String src = load(path);
        GL20.glShaderSource(id, src);
        GL20.glCompileShader(id);
        return id;
    }

    private String load(String path) {
        try {
            ResourceLocation rl = new ResourceLocation(path);
            InputStream in = Minecraft.getInstance().getResourceManager().open(rl);
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void bind() { GL20.glUseProgram(program); }
    public void unbind() { GL20.glUseProgram(0); }
    public void uniform1f(String name, float v) {
        GL20.glUniform1f(GL20.glGetUniformLocation(program, name), v);
    }
}
