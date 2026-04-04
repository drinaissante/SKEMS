package dev.drinaissante.util;

import dev.drinaissante.Main;
import javafx.scene.text.Font;

public class Fonts {
    // TODO ALL STATIC
    public static final String COMIC_NEUE =
            Font.loadFont(Main.class.getResourceAsStream("/fonts/Comic_Neue/ComicNeue-Regular.ttf"), 20).getFamily();

    public static final String COMIC_NEUE_BOLD =
            Font.loadFont(Main.class.getResourceAsStream("/fonts/Comic_Neue/ComicNeue-Bold.ttf"), 20).getFamily();
}
