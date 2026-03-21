package dev.drinaissante.ui;

import dev.drinaissante.Main;
import dev.drinaissante.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class HeaderPanel extends JPanel {

    public HeaderPanel(Main mainFrame) {
        setLayout(new BorderLayout(10, 5));
        setOpaque(true);
        setBackground(new Color(30, 30, 30)); // solid dark bar

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


        // CONTROLS
        JPanel controlPanel = setupControlButtons(mainFrame);

        add(controlPanel, BorderLayout.EAST);

        // TODO inbox, notification, profile (arrow down -> profile settings -> logout (?))

        // setup the mouse adapters
        setupMouseController(mainFrame);


    }

    private JPanel setupControlButtons(Main mainFrame) {
        ImageIcon close = ImageUtil.loadTintedIcon("/close.png", 24, 24, Color.WHITE);
        // Close button
        JButton closeBtn = createTitleButton(close, "Close", () -> System.exit(0));

        ImageIcon minimize = ImageUtil.loadTintedIcon("/minimize.png", 24, 24, Color.WHITE);
        // Minimize button
        JButton minimizeBtn = createTitleButton(minimize, "Minimize", () -> {
            mainFrame.setExtendedState(JFrame.ICONIFIED); // minimize to taskbar
        });

        ImageIcon maximize = ImageUtil.loadTintedIcon("/maximize.png", 24, 24, Color.WHITE);
        // Restore button
        JButton restoreBtn = createTitleButton(maximize, "Restore", () -> {
            if (mainFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                mainFrame.setExtendedState(JFrame.NORMAL);   // restore
            } else {
                mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximize
            }
        });

        // Align both to the right
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlPanel.setOpaque(false);

        controlPanel.add(minimizeBtn);
        controlPanel.add(restoreBtn);
        controlPanel.add(closeBtn);

        return controlPanel;
    }

    private void setupMouseController(Main mainFrame) {
        final Point[] initialClick = new Point[1];

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick[0] = e.getPoint();

                // Detect double-click for restore
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    if (mainFrame.getExtendedState() == JFrame.NORMAL) {
                        // Restore to normal size
                        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    } else if (mainFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                        mainFrame.setExtendedState(JFrame.NORMAL);
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mainFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    // Restore before dragging
                    mainFrame.setExtendedState(JFrame.NORMAL);
                }

                if (mainFrame.getExtendedState() == JFrame.NORMAL) {
                    Point frameLocation = mainFrame.getLocation();

                    int xMoved = e.getX() - initialClick[0].x;
                    int yMoved = e.getY() - initialClick[0].y;

                    int newX = frameLocation.x + xMoved;
                    int newY = frameLocation.y + yMoved;

                    mainFrame.setLocation(newX, newY); // move the whole window
                }
            }
        });

    }

    private JButton createTitleButton(ImageIcon icon, String text, Runnable action) {
        JButton btn = new JButton(icon);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(30, 30));
        btn.setToolTipText(text);

        btn.setOpaque(false);

        if (text.equals("Close"))
            btn.setBackground(new Color(30, 30, 30));
        else
            btn.setBackground(new Color(60, 60, 60));


        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setOpaque(true);

                if (text.equals("Close"))
                    btn.setBackground(new Color(200, 50, 50));
                else
                    btn.setBackground(new Color(90, 90, 90)); // lighter gray
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(60, 60, 60));
                btn.setOpaque(false);
            }
        });

        btn.addActionListener(e -> action.run());
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw subtle shadow line
        GradientPaint shadow = new GradientPaint(
                0, getHeight() - 2, new Color(100, 100, 100, 80), // darker at top
                0, getHeight(), new Color(100, 100, 100, 0)       // fade out
        );
        g2d.setPaint(shadow);
        g2d.fillRect(0, getHeight() - 2, getWidth(), 2);

        g2d.dispose();
    }

}
