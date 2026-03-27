package de.Cooltechno.coollib.api.Utils;

import net.minecraft.util.math.MathHelper;

public class CoolEffect {

    /**
     * Returns a value between 0 and 1 that "breathes" in and out.
     * Great for pulsing icons or glowing text.
     */
    public static float getPulseValue(float speed) {
        return (MathHelper.sin((System.currentTimeMillis() / 1000.0f) * speed) + 1.0f) / 2.0f;
    }

    /**
     * A "Bounce" wave. Instead of a smooth sine wave, it looks like a ball bouncing.
     * Use this for item jump animations in UIs.
     */
    public static float getBounceValue(float speed, float height) {
        float time = (System.currentTimeMillis() / 1000.0f) * speed;
        return Math.abs(MathHelper.sin(time)) * height;
    }
}