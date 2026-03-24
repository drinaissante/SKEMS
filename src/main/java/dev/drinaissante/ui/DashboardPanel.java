package dev.drinaissante.ui;

import dev.drinaissante.Main;
import dev.drinaissante.util.ImageUtil;
import dev.drinaissante.util.RoundedGradientPanel;
import dev.drinaissante.util.RoundedLabel;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final Dimension dim = new Dimension(220, 50);

    private final Color[] colors = {
            new Color(30, 55, 65),   // lighter than #0f2027, still deep teal
            new Color(45, 75, 85),   // blend, muted teal
            new Color(60, 95, 110),  // softened slate blue
            new Color(80, 115, 125), // blend, muted navy
            new Color(85, 115, 125) // darker navy end (pulled down)
    };

    private final float[] fractions = {0.0f, 0.25f, 0.5f, 0.75f, 1.0f};
    private final Dimension cardSize = new Dimension(Integer.MAX_VALUE, 120);
    private final Object[][] data = {
            {"camera_1.png", "Canon EOS 5D Mark IV", "Camera", "Available", "Request"},
            {"cctv.png", "Sony A7 III", "Camera", "Checked Out", "Details"},
            {"sk_header.png", "Manfrotto Tripod", "Tripod", "Reserved", "Request"},
            {"sk_icon.jpg", "LED Panel Light", "Lights", "Available", "Request"},
            {"camera_1.png", "123", "Camera", "Available", "Request"},
            {"cctv.png", "456", "Camera", "Checked Out", "Details"},
            {"sk_header.png", "M7987", "Tripod", "Reserved", "Request"},
            {"sk_icon.jpg", "LLP", "Lights", "Available", "Request"}
    };

    public DashboardPanel(Main mainFrame) {
        setLayout(new BorderLayout());
        setOpaque(false);

        Color redish = new Color(245, 150, 75);

        // --- Summary cards ---
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        summaryPanel.add(createCard("Total Equipment:", "124", "total_equipments.png", Color.green, Color.green)); // TODO SQL
        summaryPanel.add(createCard("Checked Out:", "18", "summary_checked_out.png", Color.lightGray, Color.white));
        summaryPanel.add(createCard("Pending Requests:", "5", "pending.png", Color.YELLOW, Color.yellow));
        summaryPanel.add(createCard("Overdue Items:", "2", "overdue.png", null, redish));
        summaryPanel.setOpaque(false);

        // --- Add Equipment button ---
        RoundedGradientPanel addEquipmentPanel = getEquipmentPanel();

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topRow.setOpaque(false);

        topRow.add(summaryPanel);
        topRow.add(addEquipmentPanel);

        // --- Search bar ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JTextField searchField = new JTextField(25);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        JPanel headerBlock = new JPanel();
        headerBlock.setLayout(new BoxLayout(headerBlock, BoxLayout.Y_AXIS));
        headerBlock.add(topRow);
        headerBlock.add(searchPanel);
        headerBlock.setOpaque(false);

        add(headerBlock, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setOpaque(false);

        JPanel equipmentsRow = setupEquipmentsRow();

        JPanel bottomPanel = setupBottomPanel();
        JPanel bottomPanel2 = setupBottomPanel();

        centerWrapper.add(equipmentsRow);
        centerWrapper.add(bottomPanel);
        centerWrapper.add(bottomPanel2);

        add(centerWrapper, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String value, String path, Color iconColor, Color foreground) {
        JPanel cardPanel = new RoundedGradientPanel(colors, fractions, 10, true);
        cardPanel.setPreferredSize(dim);
        cardPanel.setMaximumSize(dim);

        // todo make it VAL over label

        cardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        ImageIcon icon;

        if (iconColor == null)
            icon = ImageUtil.loadIcon("/summaryPanel/" + path, 15, 15);
        else
            icon = ImageUtil.loadTintedIcon("/summaryPanel/" + path, 15, 15, iconColor);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(10);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        valueLabel.setForeground(foreground);

        cardPanel.add(titleLabel);
        cardPanel.add(valueLabel);

        return cardPanel;
    }

    private RoundedGradientPanel getEquipmentPanel() {
        RoundedGradientPanel addEquipmentPanel = new RoundedGradientPanel(colors, fractions, 12, false);
        addEquipmentPanel.setLayout(new GridBagLayout());

        Dimension addBtnDim = new Dimension(150, 50);
        addEquipmentPanel.setPreferredSize(addBtnDim);
        addEquipmentPanel.setMaximumSize(addBtnDim);

        JButton addEquipmentBtn = new JButton("+ Add Equipment");
        addEquipmentBtn.setFocusPainted(false);
        addEquipmentBtn.setFocusable(false);
        addEquipmentBtn.setBorderPainted(false);
        addEquipmentBtn.setContentAreaFilled(false);

        addEquipmentPanel.add(addEquipmentBtn);
        return addEquipmentPanel;
    }

    private JPanel setupEquipmentsRow() {
        // card
        //  image (card: Available (green), Checked Out (Gray), pending (Orange), Reserved (Green)

        // TODO get all equipment (available - priority then checked out then pending then reserved) –> an arrow (add "View more" -> open equipments)

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        container.setOpaque(false);

        // --- Label ---
        JLabel equipmentsLabel = new JLabel("Equipments");
        equipmentsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        equipmentsLabel.setForeground(Color.WHITE);
        equipmentsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(equipmentsLabel);
        container.add(Box.createVerticalStrut(10));

        // --- Scrollable equipment cards ---
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (Object[] item : data) {
            String image_path = (String) item[0];
            String name = (String) item[1];
            String status = (String) item[3];

            JPanel card = createEquipmentsCard(image_path, name, status, getStatusColor(status));
            wrapper.add(card);
            wrapper.add(Box.createHorizontalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(0, 230));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));

        container.add(scrollPane);
        container.add(Box.createVerticalStrut(20)); // spacing before bottom panel

        return container;
    }

    // TODO
    private JPanel setupBottomPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        container.setOpaque(false);

        JPanel bottomPanel = new RoundedGradientPanel(colors, fractions, 12, true);
        bottomPanel.setPreferredSize(new Dimension(100, 100));

        container.add(bottomPanel, BorderLayout.NORTH);

        return container;
    }

    private JPanel createEquipmentsCard(String filePath, String title, String status, Color statusColor) {
        JPanel card = new RoundedGradientPanel(colors, fractions, 12, false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(200, 150));

        ImageIcon icon = ImageUtil.loadIcon("/" + filePath, 150, 150);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(0, 0, 150, 150);

        RoundedLabel statusLabel = new RoundedLabel(status);
        statusLabel.setArc(20);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(statusColor.darker().darker());
        statusLabel.setSize(90, 22);
        statusLabel.setLocation(10, 10);

        layeredPane.add(statusLabel, 0);
        layeredPane.add(imageLabel, 1);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.setPreferredSize(new Dimension(200, 200));
        card.setMaximumSize(new Dimension(200, 200));
        card.setMinimumSize(new Dimension(200, 200));

        card.setAlignmentY(Component.TOP_ALIGNMENT);

        card.add(layeredPane);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);

        return card;
    }

    private Color getStatusColor(String status) {
        return switch (status) {
            case "Available" -> new Color(0, 180, 0);
            case "Checked Out" -> Color.GRAY;
            case "Pending" -> Color.ORANGE;
            case "Reserved" -> new Color(0, 120, 255);
            default -> Color.BLACK;
        };
    }
}
