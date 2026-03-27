# CoolLib

CoolLib is a lightweight rendering and mathematical utility library for Minecraft Fabric (1.21.11). It is designed to bridge the gap between standard Minecraft code and high-quality user interfaces by providing a simplified API for animations, dynamic colors, and 2D matrix transformations.

The primary goal of this library is to allow developers to add "juice" and polish to their mods with minimal boilerplate.

## Core Modules

### 1. CoolRenderer
Optimized for the modern 2D `Matrix3x2fStack` rendering system used in 1.21.2 and beyond.
* **Shapes**: Draw rounded rectangles, custom 1-pixel borders, and fading borders.
* **Transformations**: Native support for UI shaking, scaling from specific anchor points, and soft outer glows.
* **State Management**: Built-in helpers for scissoring (clipping) and matrix stack safety.

### 2. CoolColor
Advanced color manipulation and accessibility tools.
* **Dynamic Gradients**: Multi-step lerping (e.g., Red to Yellow to Green) for health bars and power levels.
* **Chroma**: Easy-to-use rainbow/chroma cycling with adjustable speed and saturation.
* **Contrast Awareness**: Logic to automatically toggle text between black and white based on background brightness.
* **Visual Effects**: Methods for flickering and pulsing colors based on game state.

### 3. CoolMath & CoolEffect
The engine behind the motion.
* **Smooth Interpolation**: Specialized `lerpAngle` functions that handle 360-degree wraps without "glitching" or flipping.
* **Animation Curves**: Pre-built Sin, Pulse, and Bounce waves to create "living" UI elements.

---

## Implementation Example

To create an item that bounces and smoothly rotates to face the mouse cursor:

```java
// Inside your render method
int x = 200;
int y = 100;

// Use CoolEffect to get a time-based bounce value
float bounce = CoolEffect.getBounceValue(2.0f, 5.0f);

// Calculate the angle to the mouse
float target = (float) Math.toDegrees(Math.atan2(mouseY - y, mouseX - x));

// Smooth the rotation so it doesn't snap
currentAngle = CoolMath.lerpAngle(currentAngle, target, 0.1f);

context.getMatrices().pushMatrix();

// Apply the transformations
context.getMatrices().translate(x, y - bounce);
CoolRenderer.rotate(context, currentAngle);

// Render the item centered
context.drawItem(new ItemStack(Items.COMPASS), -8, -8);

context.getMatrices().popMatrix();
```

### Driving Animations
If you want to create your own custom progress bars or fading effects, you can use Minecraft's `MathHelper` alongside `CoolLib`:

```java
// Creates a value that smoothly cycles between 0.0 and 1.0
float progress = (MathHelper.sin(System.currentTimeMillis() / 1500.0f) + 1.0f) / 2.0f;

// Pass that progress into CoolLib for a dynamic health bar
int color = CoolColor.getHealthColor(progress);
CoolRenderer.drawProgressBar(context, x, y, width, height, progress, color);
```

## License

This project is licensed under the MIT License. You are free to use, modify, and distribute this library in any mod or modpack.
