package dev.drinaissante.util;

import dev.drinaissante.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ImageUtil {

    public static ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource(path)));
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static ImageIcon loadTintedIcon(String path, int width, int height, Color desiredColor) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource(path)));

        // Scale into a BufferedImage for better quality
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(icon.getImage(), 0, 0, width, height, null);
        g2.dispose();

        // Apply tint overlay
        Graphics2D g2d = scaled.createGraphics();
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(desiredColor);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        return new ImageIcon(scaled);
    }

}
