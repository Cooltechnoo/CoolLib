package de.Cooltechno.coollib.api.Math;

import net.minecraft.util.math.MathHelper;

public class CoolMath {
    /**
     * Smoothly eases a value. Great for "pop-up" animations.
     */
    public static float easeOutBack(float x) {
        float c1 = 1.70158f;
        float c3 = c1 + 1;
        return (float) (1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2));
    }

    /**
     * Clamps a value between 0 and 1.
     */
    public static float saturate(float value) {
        return MathHelper.clamp(value, 0.0f, 1.0f);
    }

    /**
     * Smoothly rotates from one angle to another without "flicking" at 360 degrees.
     */
    public static float lerpAngle(float start, float end, float delta) {
        float target = end - start;
        while (target < -180.0f) target += 360.0f;
        while (target >= 180.0f) target -= 360.0f;
        return start + target * delta;
    }
}