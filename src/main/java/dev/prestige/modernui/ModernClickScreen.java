package dev.prestige.modernui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ModernClickScreen extends Screen {
    private static final int PANEL = 0xF0161A26;
    private static final int PANEL_ALT = 0xFF202637;
    private static final int TEXT = 0xFFF4F5FA;
    private static final int MUTED = 0xFFADB5C8;
    private long openedAt = System.currentTimeMillis();

    public ModernClickScreen() { super(Text.literal("Prestige Modern UI")); }

    @Override
    protected void init() { openedAt = System.currentTimeMillis(); }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        int accent = ThemeConfig.accent();
        long elapsed = Math.max(0, System.currentTimeMillis() - openedAt);
        int alpha = (int) (185 * Math.min(1.0, elapsed / 180.0));
        ctx.fill(0, 0, width, height, (alpha << 24) | 0x000A0D14);
        int pw = Math.min(560, width - 32), ph = Math.min(330, height - 32);
        int left = (width - pw) / 2, top = (height - ph) / 2;
        ctx.fill(left, top, left + pw, top + ph, PANEL);
        ctx.fill(left, top, left + 5, top + ph, accent);
        ctx.fill(left + 5, top, left + pw, top + 48, PANEL_ALT);
        ctx.drawText(textRenderer, Text.literal("PRESTIGE"), left + 22, top + 13, TEXT, false);
        ctx.drawText(textRenderer, Text.literal("MODERN CLIENT UI"), left + 22, top + 27, MUTED, false);
        ctx.drawText(textRenderer, Text.literal("Your interface, your style."), left + 22, top + 67, TEXT, false);
        ctx.drawText(textRenderer, Text.literal("Customize the HUD and accent color below."), left + 22, top + 84, MUTED, false);
        drawOption(ctx, left + 22, top + 118, pw - 44, 42, "HUD STATUS", ThemeConfig.hudEnabled ? "ON" : "OFF", mouseX, mouseY, accent);
        drawOption(ctx, left + 22, top + 170, pw - 44, 42, "ACCENT THEME", ThemeConfig.themeName(), mouseX, mouseY, accent);
        drawOption(ctx, left + 22, top + 222, pw - 44, 42, "VANILLA INVENTORY / CONTAINERS", "UNCHANGED", mouseX, mouseY, accent);
        ctx.drawText(textRenderer, Text.literal("RIGHT SHIFT  •  TOGGLE MENU"), left + 22, top + ph - 21, MUTED, false);
        super.render(ctx, mouseX, mouseY, delta);
    }

    private void drawOption(DrawContext ctx, int x, int y, int w, int h, String label, String value, int mx, int my, int accent) {
        boolean hover = mx >= x && mx <= x + w && my >= y && my <= y + h;
        ctx.fill(x, y, x + w, y + h, hover ? 0xFF30394F : PANEL_ALT);
        ctx.drawText(textRenderer, Text.literal(label), x + 12, y + 8, TEXT, false);
        int vw = textRenderer.getWidth(value);
        ctx.drawText(textRenderer, Text.literal(value), x + w - vw - 12, y + 8, accent, false);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubleClick) {
        double mouseX = click.x();
        double mouseY = click.y();
        int button = click.button();
        if (button == 0) {
            int pw = Math.min(560, width - 32), ph = Math.min(330, height - 32);
            int left = (width - pw) / 2, top = (height - ph) / 2;
            if (inside(mouseX, mouseY, left + 22, top + 118, pw - 44, 42)) {
                ThemeConfig.hudEnabled = !ThemeConfig.hudEnabled;
                ThemeConfig.save();
                return true;
            }
            if (inside(mouseX, mouseY, left + 22, top + 170, pw - 44, 42)) {
                ThemeConfig.nextTheme();
                return true;
            }
            if (mouseY >= top + ph - 30 && mouseX >= left) { close(); return true; }
        }
        return super.mouseClicked(click, doubleClick);
    }

    private static boolean inside(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override public boolean shouldPause() { return false; }
}
