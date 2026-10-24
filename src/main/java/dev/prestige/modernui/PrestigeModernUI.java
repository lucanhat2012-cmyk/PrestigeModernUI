package dev.prestige.modernui;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class PrestigeModernUI implements ClientModInitializer {
    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        ThemeConfig.load();
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.prestige_modern_ui.open_menu", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen instanceof ModernClickScreen) client.setScreen(null);
                else client.setScreen(new ModernClickScreen());
            }
        });
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> renderHud(drawContext));
    }

    private static void renderHud(DrawContext ctx) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!ThemeConfig.hudEnabled || client.player == null || client.options.hudHidden) return;
        int x = 8, y = 8;
        ctx.fill(x, y, x + 142, y + 42, 0xB8171B27);
        ctx.fill(x, y, x + 3, y + 42, ThemeConfig.accent());
        ctx.drawText(client.textRenderer, Text.literal("PRESTIGE"), x + 11, y + 7, 0xFFFFFFFF, false);
        ctx.drawText(client.textRenderer, Text.literal("Modern UI  •  1.21.11"), x + 11, y + 22, 0xFFB9C0D4, false);
    }
}
