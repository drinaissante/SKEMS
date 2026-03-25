package dev.drinaissante.util.ui;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private final Color[] colors;
    private final float[] fractions;
    private final double angle; // in degrees

    // Constructor for 2 colors (backward compatibility)
    public GradientPanel(Color startColor, Color endColor, double angle) {
        this(new Color[]{startColor, endColor}, new float[]{0.0f, 1.0f}, angle);
    }

    public GradientPanel(Color[] colors, float[] fractions, double angle) {
        this.colors = colors;
        this.fractions = fractions;
        this.angle = angle;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Convert angle to radians
        double rad = Math.toRadians(angle);

        // Compute gradient vector based on angle
        float x1 = (float) ((double) w / 2 + Math.cos(rad) * w / 2);
        float y1 = (float) ((double) h / 2 + Math.sin(rad) * h / 2);
        float x0 = (float) ((double) w / 2 - Math.cos(rad) * w / 2);
        float y0 = (float) ((double) h / 2 - Math.sin(rad) * h / 2);

        LinearGradientPaint lgp = new LinearGradientPaint(
                x1, y1, x0, y0, fractions, colors);

        g2d.setPaint(lgp);
        g2d.fillRect(0, 0, w, h);
    }
}

