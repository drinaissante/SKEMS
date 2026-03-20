package dev.drinaissante.ui;

import dev.drinaissante.util.RoundedBorder;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final Dimension dim = new Dimension(200, 50);

    public DashboardPanel() {
        setLayout(new BorderLayout());

        // --- Summary cards ---
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        summaryPanel.add(createCard("Total Equipment:", "124")); // TODO SQL
        summaryPanel.add(createCard("Checked Out:", "18"));
        summaryPanel.add(createCard("Pending Requests:", "5"));
        summaryPanel.add(createCard("Overdue Items:", "2"));

        // --- Add Equipment button ---
        JButton addEquipmentBtn = new JButton("+ Add Equipment");
        addEquipmentBtn.setPreferredSize(dim);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.add(summaryPanel, BorderLayout.CENTER);
        topRow.add(addEquipmentBtn, BorderLayout.EAST);

        // --- Search bar ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        // Rounded text field
        JTextField searchField = getSearchBar();

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // --- Combine topRow + searchPanel vertically ---
        JPanel headerBlock = new JPanel();
        headerBlock.setLayout(new BoxLayout(headerBlock, BoxLayout.Y_AXIS));
        headerBlock.add(topRow);
        headerBlock.add(searchPanel);

        // Place header block at the top
        add(headerBlock, BorderLayout.NORTH);

        // --- Equipment inventory section ---
        JPanel equipmentsPanel = new JPanel(new BorderLayout());
        equipmentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding

        // Title aligned left
        JLabel equipmentsLabel = new JLabel("Equipments:");
        equipmentsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        equipmentsPanel.add(equipmentsLabel, BorderLayout.NORTH);

        // Table for inventory
        JTable equipmentTable = getTable();
        equipmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        equipmentsPanel.add(scrollPane, BorderLayout.CENTER);

        // ✅ Add to CENTER so it fills the width below the headerBlock
        add(equipmentsPanel, BorderLayout.CENTER);

    }

    private JTable getTable() {
        String[] columns = {"Item Name", "Category", "Status", "Actions"};
        Object[][] data = {
                {"Canon EOS 5D Mark IV", "Camera", "Available", "Request"},
                {"Sony A7 III", "Camera", "Checked Out", "Details"},
                {"Manfrotto Tripod", "Tripod", "Reserved", "Request"},
                {"LED Panel Light", "Lights", "Available", "Request"}
        };

        JTable equipmentTable = new JTable(data, columns);
        equipmentTable.setRowHeight(28);
        equipmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        return equipmentTable;
    }

    private JTextField getSearchBar() {
        JTextField searchField = new JTextField(25) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background fill
                g2.setColor(new Color(245, 245, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Border
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                g2.dispose();
            }
        };
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding inside
        return searchField;
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
}
