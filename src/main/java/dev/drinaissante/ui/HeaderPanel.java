package dev.drinaissante.ui;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setLayout(new BorderLayout(10, 5));

        JLabel titleLabel = new JLabel("SKEMS Dashboard", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        add(titleLabel, BorderLayout.WEST);

        // TODO inbox, notification, profile (arrow down -> profile settings -> logout (?))
    }
}
