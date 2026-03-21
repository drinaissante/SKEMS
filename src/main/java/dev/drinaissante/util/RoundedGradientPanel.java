package dev.drinaissante.util;

import javax.swing.*;
import java.awt.*;

public class RoundedGradientPanel extends JPanel {
    private final Color dropShadow = new Color(0, 0, 0, 60);

    private final Color[] colors;
    private final int cornerRadius;
    private final float[] fractions;
    private final boolean horizontal;

    public RoundedGradientPanel(Color[] colors, int cornerRadius, boolean horizontal) {
        this(colors, new float[]{0.0f, 1.0f}, cornerRadius, horizontal);
    }

    public RoundedGradientPanel(Color[] colors, float[] fractions, int cornerRadius, boolean horizontal) {
        this.colors = colors;
        this.cornerRadius = cornerRadius;
        this.fractions = fractions;
        this.horizontal = horizontal;
        setOpaque(false); // we’ll handle painting
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- Drop shadow ---
        g2d.setColor(dropShadow);
        g2d.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, cornerRadius, cornerRadius);

        LinearGradientPaint lgp;
        if (horizontal) {
            lgp = new LinearGradientPaint(
                    0, 0, getWidth(), 0, fractions, colors);
        } else {
            lgp = new LinearGradientPaint(
                    0, 0, 0, getHeight(), fractions, colors);
        }

        g2d.setPaint(lgp);
        g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, cornerRadius, cornerRadius);

        g2d.dispose();
    }
}
