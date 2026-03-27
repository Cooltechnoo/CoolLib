package de.Cooltechno.coollib.api.Color;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import static de.Cooltechno.coollib.api.Render.CoolRenderer.drawOuterGlow;


public class CoolColor {

    /**
     * Shifting rainbow color for Legendary/Epic items.
     * @param speed How fast the color cycles (e.g., 0.5f for slow, 2.0f for fast)
     */
    public static int getChroma(float speed) {
        float safeSpeed = Math.max(speed, 0.0001f);

        long time = System.currentTimeMillis();
        int cycle = (int)(2000 / safeSpeed);

        float hue = (Math.floorMod(time, cycle)) / (float)cycle;

        return MathHelper.hsvToArgb(hue, 0.7f, 1.0f, 255);
    }
    /**
     * Makes a color "breathe" by pulsing its alpha/brightness.
     */
    public static int getPulse(int color, float speed) {
        float pulse = (MathHelper.sin((System.currentTimeMillis() / 1000.0f) * speed) + 1.0f) / 2.0f;
        return ColorHelper.withAlpha((int)(150 + (pulse * 105)), color);
    }
    /**
     * Smoothly blends between two ARGB colors.
     * @param delta 0.0 returns colorA, 1.0 returns colorB, 0.5 returns a perfect mix.
     */
    public static int lerp(int colorA, int colorB, float delta) {
        return ColorHelper.lerp(delta, colorA, colorB);
    }

    /**
     * Draws a glowing background "slot" for an item.
     * @param intensity 0.0 to 1.0 (how bright the glow is)
     */
    public static void drawItemGlow(DrawContext context, int x, int y, int color, float intensity) {
        int alpha = (int)(intensity * 100);
        int glowColor = ColorHelper.withAlpha(alpha, color);

        context.fill(x, y, x + 16, y + 16, glowColor);
        drawOuterGlow(context, x, y, 16, 16, color, 3);
    }

    /**
     * Checks if the mouse is inside a specific area.
     */
    public static boolean isHovered(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    /**
     * Returns either Black (0xFF000000) or White (0xFFFFFFFF)
     * depending on which is easier to read against the input color.
     */
    public static int getContrastColor(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        double luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;
        return luminance > 0.5 ? 0xFF000000 : 0xFFFFFFFF;
    }

    /**
     * Creates a "flicker" effect, like a dimming torch or a failing machine.
     * @param color The base color
     * @param intensity How much it flickers (0.0 to 1.0)
     */
    public static int getFlicker(int color, float intensity) {
        float noise = (float) Math.random() * intensity;
        return ColorHelper.withAlpha((int) (255 * (1.0f - noise)), color);
    }

    /**
     * Returns a color that transitions from Green (1.0) to Red (0.0) based on progress.
     * Uses HSV to keep the colors vibrant while they change.
     */
    public static int getHealthColor(float progress) {
        float f = MathHelper.clamp(progress, 0.0f, 1.0f);
        return MathHelper.hsvToArgb(f * 0.33f, 0.9f, 1.0f, 255);
    }

    /**
     * Blends through a list of colors based on progress.
     * Example: 0.0 is Red, 0.5 is Yellow, 1.0 is Green.
     */
    public static int getMultiLerp(float progress, int... colors) {
        if (colors.length == 0) return 0xFFFFFFFF;
        if (colors.length == 1) return colors[0];

        float f = progress * (colors.length - 1);
        int index = (int) f;
        float delta = f - index;

        if (index >= colors.length - 1) return colors[colors.length - 1];

        return ColorHelper.lerp(delta, colors[index], colors[index + 1]);
    }
}