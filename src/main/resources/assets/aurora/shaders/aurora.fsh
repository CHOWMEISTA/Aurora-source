#version 150
in vec2 uv;
out vec4 FragColor;
uniform float time;

void main() {
    float glow = sin(time + uv.x * 8.0) * 0.5 + 0.5;
    FragColor = vec4(glow * 0.4, glow * 0.7, 1.0, 1.0);
}
