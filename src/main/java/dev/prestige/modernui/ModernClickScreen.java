package dev.prestige.modernui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ModernClickScreen extends Screen {
    private static final int PANEL = 0xF0161A26;
    private static final int PANEL_ALT = 0xFF202637;
    private static final int ACCENT = 0xFF8B7CFF;
    private static final int TEXT = 0xFFF4F5FA;
    private static final int MUTED = 0xFFADB5C8;

    public ModernClickScreen() { super(Text.literal("Prestige Modern UI")); }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, width, height, 0xB90A0D14);
        int pw = Math.min(560, width - 32), ph = Math.min(330, height - 32);
        int left = (width - pw) / 2, top = (height - ph) / 2;
        ctx.fill(left, top, left + pw, top + ph, PANEL);
        ctx.fill(left, top, left + 5, top + ph, ACCENT);
        ctx.fill(left + 5, top, left + pw, top + 48, PANEL_ALT);
        ctx.drawText(textRenderer, Text.literal("PRESTIGE"), left + 22, top + 13, TEXT, false);
        ctx.drawText(textRenderer, Text.literal("MODERN CLIENT UI"), left + 22, top + 27, MUTED, false);
        ctx.drawText(textRenderer, Text.literal("Your interface, your style."), left + 22, top + 67, TEXT, false);
        ctx.drawText(textRenderer, Text.literal("A lightweight starting point for a modern client UI."), left + 22, top + 84, MUTED, false);
        drawOption(ctx, left + 22, top + 118, pw - 44, 42, "HUD STATUS", "ON", mouseX, mouseY);
        drawOption(ctx, left + 22, top + 170, pw - 44, 42, "THEME", "VIOLET / MIDNIGHT", mouseX, mouseY);
        drawOption(ctx, left + 22, top + 222, pw - 44, 42, "VANILLA SCREENS", "UNCHANGED", mouseX, mouseY);
        ctx.drawText(textRenderer, Text.literal("RIGHT SHIFT  •  TOGGLE MENU"), left + 22, top + ph - 21, MUTED, false);
        super.render(ctx, mouseX, mouseY, delta);
    }

    private void drawOption(DrawContext ctx, int x, int y, int w, int h, String label, String value, int mx, int my) {
        boolean hover = mx >= x && mx <= x + w && my >= y && my <= y + h;
        ctx.fill(x, y, x + w, y + h, hover ? 0xFF30394F : PANEL_ALT);
        ctx.drawText(textRenderer, Text.literal(label), x + 12, y + 8, TEXT, false);
        int vw = textRenderer.getWidth(value);
        ctx.drawText(textRenderer, Text.literal(value), x + w - vw - 12, y + 8, ACCENT, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int pw = Math.min(560, width - 32), ph = Math.min(330, height - 32);
        int left = (width - pw) / 2, top = (height - ph) / 2;
        if (button == 0 && mouseY >= top + ph - 30) { close(); return true; }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override public boolean shouldPause() { return false; }
}
