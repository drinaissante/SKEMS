package dev.drinaissante.util.ui;

import javax.swing.*;
import java.awt.*;

public class RoundedLabel extends JLabel {
    private int arc = 12;
    private final Color dropShadow = new Color(0, 0, 0, 60);

    public RoundedLabel(String text) {
        super(text);
    }

    public void setArc(int arc) {
        this.arc = arc;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // drop shadow
        g2.setColor(dropShadow);
        g2.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, arc, arc);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.dispose();

        super.paintComponent(g); // draw text
    }
}
