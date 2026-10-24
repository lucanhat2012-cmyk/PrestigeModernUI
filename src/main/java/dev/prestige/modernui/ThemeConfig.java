package dev.prestige.modernui;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Small persistent client-side settings file. */
public final class ThemeConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("prestige-modern-ui.properties");
    private static final int[] ACCENTS = { 0xFF8B7CFF, 0xFF55D6BE, 0xFFFFB86B, 0xFFFF7A90, 0xFF79A8FF };
    private static final String[] NAMES = { "VIOLET", "MINT", "AMBER", "ROSE", "BLUE" };

    public static boolean hudEnabled = true;
    public static int themeIndex = 0;

    private ThemeConfig() {}

    public static void load() {
        if (!Files.exists(FILE)) return;
        Properties p = new Properties();
        try (InputStream in = Files.newInputStream(FILE)) {
            p.load(in);
            hudEnabled = Boolean.parseBoolean(p.getProperty("hudEnabled", "true"));
            themeIndex = Math.floorMod(Integer.parseInt(p.getProperty("themeIndex", "0")), ACCENTS.length);
        } catch (IOException | NumberFormatException ignored) {
            hudEnabled = true;
            themeIndex = 0;
        }
    }

    public static void save() {
        Properties p = new Properties();
        p.setProperty("hudEnabled", Boolean.toString(hudEnabled));
        p.setProperty("themeIndex", Integer.toString(themeIndex));
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream out = Files.newOutputStream(FILE)) {
                p.store(out, "Prestige Modern UI settings");
            }
        } catch (IOException ignored) {
            // Keep the game usable if the config directory is read-only.
        }
    }

    public static int accent() { return ACCENTS[Math.floorMod(themeIndex, ACCENTS.length)]; }
    public static String themeName() { return NAMES[Math.floorMod(themeIndex, NAMES.length)]; }
    public static void nextTheme() { themeIndex = (themeIndex + 1) % ACCENTS.length; save(); }
}
