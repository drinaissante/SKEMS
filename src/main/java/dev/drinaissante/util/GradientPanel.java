package dev.drinaissante.util;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private final Color[] colors;
    private final float[] fractions;
    private final boolean horizontal; // true = left→right, false = top→bottom

    // Constructor for 2 colors (backward compatibility)
    public GradientPanel(Color startColor, Color endColor, boolean horizontal) {
        this(new Color[]{startColor, endColor}, new float[]{0.0f, 1.0f}, horizontal);
    }

    // Constructor for multiple colors
    public GradientPanel(Color[] colors, float[] fractions, boolean horizontal) {
        this.colors = colors;
        this.fractions = fractions;
        this.horizontal = horizontal;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        LinearGradientPaint lgp;
        if (horizontal) {
            lgp = new LinearGradientPaint(
                    0, 0, getWidth(), 0, fractions, colors);
        } else {
            lgp = new LinearGradientPaint(
                    0, 0, 0, getHeight(), fractions, colors);
        }

        g2d.setPaint(lgp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
