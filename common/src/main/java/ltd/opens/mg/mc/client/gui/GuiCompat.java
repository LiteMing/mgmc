package ltd.opens.mg.mc.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

/**
 * 1.20.1 API compatibility utilities.
 * Provides methods that exist in 1.21+ but not in 1.20.1.
 */
public class GuiCompat {

    /**
     * Equivalent to GuiGraphics.renderOutline() which was added in MC 1.20.4.
     * Draws a 1px outline rectangle.
     */
    public static void renderOutline(GuiGraphics graphics, int x, int y, int width, int height, int color) {
        graphics.fill(x, y, x + width, y + 1, color); // top
        graphics.fill(x, y + height - 1, x + width, y + height, color); // bottom
        graphics.fill(x, y, x + 1, y + height, color); // left
        graphics.fill(x + width - 1, y, x + width, y + height, color); // right
    }

    /**
     * Enable scissor using Minecraft GUI-scaled coordinates (y from top).
     * In 1.20.1, RenderSystem.enableScissor uses raw pixel + OpenGL coordinates (y from bottom).
     * This method converts GUI-scaled coords to raw pixel OpenGL coords.
     */
    public static void enableScissor(int x, int y, int width, int height) {
        var window = Minecraft.getInstance().getWindow();
        int scale = (int) window.getGuiScale();
        int pixelHeight = window.getHeight();
        RenderSystem.enableScissor(
            x * scale,
            pixelHeight - (y + height) * scale,
            width * scale,
            height * scale
        );
    }
}
