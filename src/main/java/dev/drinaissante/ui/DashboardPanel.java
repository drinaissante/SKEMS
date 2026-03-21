package dev.drinaissante.ui;

import dev.drinaissante.util.GradientPanel;
import dev.drinaissante.util.RoundedBorder;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final Dimension dim = new Dimension(200, 50);

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // --- Summary cards ---
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        summaryPanel.add(createCard("Total Equipment:", "124")); // TODO SQL
        summaryPanel.add(createCard("Checked Out:", "18"));
        summaryPanel.add(createCard("Pending Requests:", "5"));
        summaryPanel.add(createCard("Overdue Items:", "2"));
        summaryPanel.setOpaque(false);

        // --- Add Equipment button ---
        JButton addEquipmentBtn = new JButton("+ Add Equipment");
        addEquipmentBtn.setPreferredSize(dim);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.add(summaryPanel, BorderLayout.CENTER);
        topRow.add(addEquipmentBtn, BorderLayout.EAST);
        topRow.setOpaque(false);

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

        // ✅ Add to CENTER so it fills the width below the headerBlock
        add(equipmentsPanel, BorderLayout.CENTER);

    }

    private JPanel createCard(String title, String value) {
        JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(dim);
        cardPanel.setMaximumSize(dim);

        cardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        cardPanel.setBorder(new RoundedBorder(10)); // rounded corners
        cardPanel.setBackground(new Color(230, 230, 250));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));

        cardPanel.add(titleLabel);
        cardPanel.add(valueLabel);

        return cardPanel;
    }

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
