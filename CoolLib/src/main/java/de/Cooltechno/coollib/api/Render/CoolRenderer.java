package de.Cooltechno.coollib.api.Render;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class CoolRenderer {

    /**
     * Draws a 1-pixel thick rectangle outline using basic fill calls.
     * This replaces the need for DrawContext.drawBorder and works on all versions.
     */
    public static void drawCustomBorder(DrawContext context, int x, int y, int width, int height, int color) {
        context.fill(x, y, x + width, y + 1, color);
        context.fill(x, y + height - 1, x + width, y + height, color);
        context.fill(x, y + 1, x + 1, y + height - 1, color);
        context.fill(x + width - 1, y + 1, x + width, y + height - 1, color);
    }

    /**
     * Draws a soft outer glow around a region by stacking fading borders.
     * @param steps How many layers the glow has (3-5 is usually best).
     */
    public static void drawOuterGlow(DrawContext context, int x, int y, int width, int height, int color, int steps) {
        for (int i = 1; i <= steps; i++) {
            int alpha = (int) (180 / (i * 2.0f));
            int fadedColor = ColorHelper.withAlpha(alpha, color);

            drawCustomBorder(context, x - i, y - i, width + (i * 2), height + (i * 2), fadedColor);
        }
    }

    /**
     * Solid at the bottom, fading to transparent towards the top.
     */
    public static void drawFadeBorder(DrawContext context, int x, int y, int width, int height, int color) {
        int x2 = x + width;
        int y2 = y + height;
        int transparent = ColorHelper.withAlpha(0, color);

        context.fill(x, y2 - 1, x2, y2, color);

        context.fillGradient(x, y + 4, x + 1, y2, transparent, color);
        context.fillGradient(x2 - 1, y + 4, x2, y2, transparent, color);
    }

    /**
     * Draws an item with a "ghosting" effect (multiple transparent layers).
     * @param stack The ItemStack to draw.
     */
    public static void drawGhostedItem(DrawContext context, ItemStack stack, int x, int y, float speed) {
        float offset = (MathHelper.sin((System.currentTimeMillis() / 500.0f) * speed) + 1.0f) * 2.0f;

        context.drawItem(stack, x, y);

        context.getMatrices().pushMatrix();
        context.getMatrices().translate(offset, 0.0f);
        context.drawItem(stack, x, y);
        context.getMatrices().translate(-offset * 2, 0.0f);
        context.drawItem(stack, x, y);
        context.getMatrices().popMatrix();
    }

    /**
     * A helper to safely enable scissoring (clipping) without crashing.
     * Useful for making sure your glows don't bleed out of a specific UI frame.
     */
    public static void beginClip(DrawContext context, int x, int y, int width, int height) {
        context.enableScissor(x, y, x + width, y + height);
    }

    public static void endClip(DrawContext context) {
        context.disableScissor();
    }

    /**
     * Draws text with a shadow that matches the text color (glow effect).
     */
    public static void drawGlowText(DrawContext context, TextRenderer textRenderer, String text, int x, int y, int color) {
        int shadowColor = ColorHelper.withAlpha(100, color);
        context.drawText(textRenderer, text, x + 1, y + 1, shadowColor, false);
        context.drawText(textRenderer, text, x, y, color, false);
    }

    /**
     * Draws a rectangle with "rounded" corners by skipping the very edge pixels.
     */
    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int color) {
        context.fill(x + 1, y, x + width - 1, y + height, color);
        context.fill(x, y + 1, x + 1, y + height - 1, color);
        context.fill(x + width - 1, y + 1, x + width, y + height - 1, color);
    }

    /**
     * Draws a simple progress bar (Health bar style).
     * @param progress 0.0 to 1.0
     */
    public static void drawProgressBar(DrawContext context, int x, int y, int width, int height, float progress, int color) {
        context.fill(x, y, x + width, y + height, 0xFF444444);

        int progressWidth = (int) (width * MathHelper.clamp(progress, 0, 1));
        context.fill(x, y, x + progressWidth, y + height, color);

        drawCustomBorder(context, x, y, width, height, 0xFF000000);
    }

    /**
     * Applies a random "shake" offset to the current render matrix.
     * @param intensity How far the UI jumps (e.g., 2.0f)
     */
    public static void applyShake(DrawContext context, float intensity) {
        float shakeX = (float) (Math.random() - 0.5) * intensity;
        float shakeY = (float) (Math.random() - 0.5) * intensity;
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(shakeX, shakeY);
    }

    /**
     * Rotates the current 2D matrix.
     * @param degrees The angle to rotate in degrees.
     */
    public static void rotate(DrawContext context, float degrees) {
        context.getMatrices().rotate((float) Math.toRadians(degrees));
    }

    /**
     * Scales the matrix from the center of a coordinate.
     * Useful for making buttons "grow" when hovered.
     */
    public static void applyScale(DrawContext context, int centerX, int centerY, float scale) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate((float)centerX, (float)centerY);
        context.getMatrices().scale(scale, scale);
        context.getMatrices().translate((float)-centerX, (float)-centerY);
    }
}