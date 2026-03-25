package dev.drinaissante.ui;

import dev.drinaissante.Main;
import dev.drinaissante.util.ui.GradientPanel;
import dev.drinaissante.util.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends GradientPanel {

    private final Main mainFrame;

    public SidebarPanel(Main mainFrame) {
        super(Color.decode("#0f2027"), Color.decode("#0f2035"), 0);
        this.mainFrame = mainFrame;

        setOpaque(false);

        setLayout(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(15, 5, 20, 5));

        JPanel navPanel = new JPanel(new GridLayout(0, 1, 0, 20));
        navPanel.setOpaque(false);

        // nav buttons
        JToggleButton dashboardBtn = buildButton("Dashboard", "dashboard.png");
        dashboardBtn.setSelected(true);

        JToggleButton equipmentBtn = buildButton("Equipment", "equipments.png");
        JToggleButton borrowBtn = buildButton("Borrow Requests", "borrow_requests.png");
        JToggleButton checkedOutBtn = buildButton("Checked Out", "checked_out.png");
        JToggleButton reservationsBtn = buildButton("Reservations", "reservation.png");
        JToggleButton historyBtn = buildButton("History Logs", "history.png");
        JToggleButton reportsBtn = buildButton("Reports", "reports.png");
        JToggleButton adminBtn = buildButton("Admin Settings", "admin_reports.png");
        JToggleButton logoutBtn = buildButton("Logout", "logout.png");

        ButtonGroup group = new ButtonGroup();
        group.add(dashboardBtn);
        group.add(equipmentBtn);
        group.add(borrowBtn);
        group.add(checkedOutBtn);
        group.add(reservationsBtn);
        group.add(historyBtn);
        group.add(reportsBtn);
        group.add(adminBtn);
        group.add(logoutBtn);

        navPanel.add(dashboardBtn);
        navPanel.add(equipmentBtn);
        navPanel.add(borrowBtn);
        navPanel.add(checkedOutBtn);
        navPanel.add(reservationsBtn);
        navPanel.add(historyBtn);
        navPanel.add(reportsBtn);
        navPanel.add(adminBtn);

        add(navPanel, BorderLayout.NORTH);
        add(logoutBtn, BorderLayout.SOUTH);
    }

    private JToggleButton buildButton(String label, String iconFile) {
        JToggleButton btn = new JToggleButton(label) {
            private final Timer fadeTimer;
            private boolean hover = false;
            private float hoverAlpha = 0f; // 0 = invisible, 1 = fully visible

            {
                // Timer for smooth fade
                fadeTimer = new Timer(15, e -> {
                    float target = hover ? 1f : 0f;
                    if (hoverAlpha < target) {
                        hoverAlpha = Math.min(hoverAlpha + 0.1f, target);
                        repaint();
                    } else if (hoverAlpha > target) {
                        hoverAlpha = Math.max(hoverAlpha - 0.1f, target);
                        repaint();
                    }
                });

                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hover = true;
                        fadeTimer.start();
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hover = false;
                        fadeTimer.start();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected()) {
                    // Gradient when selected
                    LinearGradientPaint lgp = new LinearGradientPaint(
                            0, 0, getWidth(), 0,
                            new float[]{0f, 1f},
                            new Color[]{
                                    Color.decode("#0f2027"),
                                    Color.decode("#2c5364")
                            }
                    );

                    g2d.setPaint(lgp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                    setForeground(Color.WHITE);
                } else if (hoverAlpha > 0f) {
                    // Gradient when hovered (fades in/out)
                    LinearGradientPaint lgp = new LinearGradientPaint(
                            0, 0, getWidth(), 0,
                            new float[]{0f, 1f},
                            new Color[]{
                                    new Color(15, 32, 39, (int) (200 * hoverAlpha)),
                                    new Color(15, 32, 53, (int) (200 * hoverAlpha))
                            }
                    );

                    g2d.setPaint(lgp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                    // Fade text color from gray → white
                    int gray = 200 + (int) (55 * hoverAlpha); // 200→255
                    setForeground(new Color(gray, gray, gray));
                } else {
                    setForeground(Color.GRAY);
                }

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        // Transparent baseline style
        btn.setFont(new Font("Poppins", Font.BOLD, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);

        ImageIcon icon = ImageUtil.loadTintedIcon("/nav/" + iconFile, 24, 24, Color.WHITE);
        btn.setIcon(icon);
        btn.setIconTextGap(10);

        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> mainFrame.showPanel(label));

        return btn;
    }


}
