package dev.prestige.modernui;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.WeakHashMap;

public final class PrestigeModernUI implements ClientModInitializer {
    private static KeyBinding openMenuKey;
    private static final Map<Screen, Long> SCREEN_OPEN_TIMES = new WeakHashMap<>();

    @Override
    public void onInitializeClient() {
        ThemeConfig.load();
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.prestige_modern_ui.open_menu", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen instanceof ModernClickScreen) client.setScreen(null);
                else if (client.player != null) client.setScreen(new ModernClickScreen());
            }
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            SCREEN_OPEN_TIMES.put(screen, System.currentTimeMillis());
            if (screen instanceof TitleScreen) setupTitleScreen(client, screen, scaledWidth, scaledHeight);
            else if (screen instanceof GameMenuScreen) setupPauseScreen(client, screen, scaledWidth, scaledHeight);
        });
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> renderHud(drawContext));
    }

    private static void setupTitleScreen(MinecraftClient client, Screen screen, int w, int h) {
        Screens.getButtons(screen).clear();
        ScreenEvents.afterBackground(screen).register((s, ctx, mouseX, mouseY, delta) -> {
            int panelW = Math.max(1, Math.min(390, w - 24));
            int panelH = 260;
            int x = (w - panelW) / 2;
            int y = Math.max(18, (h - panelH) / 2 + 8);
            drawPanel(ctx, x, y, panelW, panelH, "PRESTIGE", "A cleaner Minecraft experience", panelFade(screen));
        });

        int buttonW = Math.max(1, Math.min(260, w - 40));
        int x = (w - buttonW) / 2;
        int y = buttonStartY(h, 5, 34, h / 2 - 36);
        Screens.getButtons(screen).add(button("Singleplayer", x, y, buttonW, () -> client.setScreen(new SelectWorldScreen(screen))));
        Screens.getButtons(screen).add(button("Multiplayer", x, y + 34, buttonW, () -> client.setScreen(new MultiplayerScreen(screen))));
        Screens.getButtons(screen).add(button("Settings", x, y + 68, buttonW, () -> client.setScreen(new OptionsScreen(screen, client.options))));
        Screens.getButtons(screen).add(button("Prestige UI Settings", x, y + 102, buttonW, () -> client.setScreen(new ModernClickScreen())));
        Screens.getButtons(screen).add(button("Quit Game", x, y + 136, buttonW, client::scheduleStop));
    }

    private static void setupPauseScreen(MinecraftClient client, Screen screen, int w, int h) {
        Screens.getButtons(screen).clear();
        ScreenEvents.afterBackground(screen).register((s, ctx, mouseX, mouseY, delta) -> {
            int panelW = Math.min(390, w - 24);
            int panelH = 230;
            int x = (w - panelW) / 2;
            int y = (h - panelH) / 2;
            drawPanel(ctx, x, y, panelW, panelH, "GAME PAUSED", "Your world, your settings", panelFade(screen));
        });

        int buttonW = Math.min(260, w - 40);
        int x = (w - buttonW) / 2;
        int y = buttonStartY(h, 3, 34, h / 2 - 34);
        Screens.getButtons(screen).add(button("Back to Game", x, y, buttonW, () -> client.setScreen(null)));
        Screens.getButtons(screen).add(button("Settings", x, y + 34, buttonW, () -> client.setScreen(new OptionsScreen(screen, client.options))));
        Screens.getButtons(screen).add(button("Prestige UI Settings", x, y + 68, buttonW, () -> client.setScreen(new ModernClickScreen())));
    }

    private static int buttonStartY(int screenHeight, int count, int spacing, int preferredY) {
        int contentHeight = (count - 1) * spacing + 24;
        int latestSafeY = Math.max(4, screenHeight - contentHeight - 4);
        return Math.max(4, Math.min(preferredY, latestSafeY));
    }

    private static ButtonWidget button(String label, int x, int y, int width, Runnable action) {
        return ButtonWidget.builder(Text.literal(label), b -> action.run())
                .dimensions(x, y, width, 24).build();
    }

    private static float panelFade(Screen screen) {
        if (!ThemeConfig.animationsEnabled) return 1.0f;
        long opened = SCREEN_OPEN_TIMES.getOrDefault(screen, System.currentTimeMillis());
        return Math.min(1.0f, Math.max(0.0f, (System.currentTimeMillis() - opened) / 240.0f));
    }

    private static int fadeColor(int color, float progress) {
        int alpha = (color >>> 24) & 0xFF;
        return (color & 0x00FFFFFF) | (Math.round(alpha * progress) << 24);
    }

    private static void drawPanel(DrawContext ctx, int x, int y, int w, int h, String title, String subtitle, float fade) {
        ctx.fill(x, y, x + w, y + h, fadeColor(ThemeConfig.panelColor(), fade));
        ctx.fill(x, y, x + w, y + 3, fadeColor(ThemeConfig.accent(), fade));
        ctx.fill(x + 12, y + 12, x + w - 12, y + 13, fadeColor(0xFF343B50, fade));
        MinecraftClient client = MinecraftClient.getInstance();
        ctx.drawCenteredTextWithShadow(client.textRenderer, Text.literal(title), x + w / 2, y + 22, fadeColor(0xFFF4F5FA, fade));
        ctx.drawCenteredTextWithShadow(client.textRenderer, Text.literal(subtitle), x + w / 2, y + 39, fadeColor(0xFFADB5C8, fade));
    }

    private static void renderHud(DrawContext ctx) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!ThemeConfig.hudEnabled || client.player == null || client.options.hudHidden) return;
        int x = 8, y = 8;
        int hudAlpha = Math.round(255 * ThemeConfig.opacityPercent() / 100.0f);
        ctx.fill(x, y, x + 154, y + 46, (hudAlpha << 24) | 0x00171B27);
        ctx.fill(x, y, x + 3, y + 46, ThemeConfig.accent());
        ctx.drawText(client.textRenderer, Text.literal("PRESTIGE"), x + 11, y + 7, 0xFFFFFFFF, false);
        ctx.drawText(client.textRenderer, Text.literal("Modern UI  •  1.21.11"), x + 11, y + 22, 0xFFB9C0D4, false);
        ctx.drawText(client.textRenderer, Text.literal(client.player.getName().getString()), x + 11, y + 34, ThemeConfig.accent(), false);
    }
}
