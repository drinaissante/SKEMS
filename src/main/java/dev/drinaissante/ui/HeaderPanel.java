package dev.drinaissante.ui;

import dev.drinaissante.util.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setLayout(new BorderLayout(10, 5));
        setOpaque(false);

        // padding
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setPreferredSize(new Dimension(1400, 50));

        ImageIcon icon = ImageUtil.loadIcon("/camera_1.png", 24, 24);

        JLabel titleLabel = new JLabel("Sine Kultura Equipment Management System", JLabel.LEFT);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(10);

        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        add(titleLabel, BorderLayout.WEST);

        // TODO inbox, notification, profile (arrow down -> profile settings -> logout (?))
    }
}
