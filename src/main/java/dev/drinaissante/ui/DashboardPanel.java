package dev.drinaissante.ui;

import dev.drinaissante.Main;
import dev.drinaissante.util.ImageUtil;
import dev.drinaissante.util.RoundedGradientPanel;

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

    public DashboardPanel(Main mainFrame) {
        setLayout(new BorderLayout());
        setOpaque(false);

        Color redish = new Color(245, 150, 75);

        // --- Summary cards ---
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        summaryPanel.add(createCard("Total Equipment:", "124", "total_equipments.png", Color.green, Color.green)); // TODO SQL
        summaryPanel.add(createCard("Checked Out:", "18", "summary_checked_out.png", Color.orange, Color.orange));
        summaryPanel.add(createCard("Pending Requests:", "5", "pending.png", Color.YELLOW, Color.yellow));
        summaryPanel.add(createCard("Overdue Items:", "2", "overdue.png", null, redish));
        summaryPanel.setOpaque(false);

        // --- Add Equipment button ---
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

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topRow.setOpaque(false);

        topRow.add(summaryPanel);
        topRow.add(addEquipmentPanel);

        // --- Search bar ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        // Rounded text field
        JTextField searchField = new JTextField(25);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // --- Combine topRow + searchPanel vertically ---
        JPanel headerBlock = new JPanel();
        headerBlock.setLayout(new BoxLayout(headerBlock, BoxLayout.Y_AXIS));
        headerBlock.add(topRow);
        headerBlock.add(searchPanel);
        headerBlock.setOpaque(false);

        // Place header block at the top
        add(headerBlock, BorderLayout.NORTH);

        // --- Equipment inventory section ---
        JPanel equipmentsPanel = new JPanel(new BorderLayout());
        equipmentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding
        equipmentsPanel.setOpaque(false);

        // Title aligned left
        JLabel equipmentsLabel = new JLabel("Equipments:");
        equipmentsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        equipmentsPanel.add(equipmentsLabel, BorderLayout.NORTH);

        // Table for inventory
        JTable equipmentTable = getTable();
        equipmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        equipmentTable.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        equipmentsPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setOpaque(false);

        scrollPane.getViewport().setOpaque(false);
        equipmentTable.setOpaque(false);

        add(equipmentsPanel, BorderLayout.CENTER);
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

    // TODO SQL
    private JTable getTable() {
        String[] columns = {"Item Name", "Category", "Status", "Actions"};
        Object[][] data = {
                {"link", "Canon EOS 5D Mark IV", "Camera", "Available", "Request"},
                {"link", "Sony A7 III", "Camera", "Checked Out", "Details"},
                {"link", "Manfrotto Tripod", "Tripod", "Reserved", "Request"},
                {"link", "LED Panel Light", "Lights", "Available", "Request"}
        };

        JTable equipmentTable = new JTable(data, columns);
        equipmentTable.setRowHeight(28);
        equipmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        return equipmentTable;
    }
}
