package dev.drinaissante.ui.equipments;

import javax.swing.*;
import java.awt.*;

public class EquipmentPanel extends JPanel {

    private static final String[] EQUIPMENTS = new String[] {
      "All", "Camera", "Lens", "Tripod", "Lights"
    };

    private static final String[] STATUS = new String[] {
            "All", "Available", "Checked Out", "Reserved"
    };

    // added: Owner
    private static final String[] COLUMNS = new String[] {
            "Item Name", "Owner", "Category", "Status", "Actions"
    };

    // TODO make this into SQL
    // TODO make status, category, request enum
    //  status - Available, Checked Out, Reserved
    //  category - camera, lens, tripod, lights
    //  actions - request, details
    private static final Object[][] DATA = {
            {"Canon EOS 5D MARK IV", "Trevor Torres", "Camera", "Available", "Request"},
            {"Sony A7 III", "Emman Carson", "Camera", "Reserved", "Details"},
            {"LED Panel Light", "Mikyla Banyada", "Lights", "Available", "Request"}
    };

    public EquipmentPanel() {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Search:"));

        JTextField searchField = new JTextField(20);
        filterPanel.add(searchField);

        filterPanel.add(new JLabel("Category:"));
        JComboBox<String> categoryCombo = new JComboBox<>(EQUIPMENTS);
        filterPanel.add(categoryCombo);

        filterPanel.add(new JLabel("Status:"));
        JComboBox<String> statusCombo = new JComboBox<>(STATUS);
        filterPanel.add(statusCombo);

        JButton addEquipmentBtn = new JButton("+ Add Equipment");
        filterPanel.add(addEquipmentBtn);

        add(filterPanel, BorderLayout.NORTH);

        // equipment table
        JTable equipmentTable = new JTable(DATA, COLUMNS);
        equipmentTable.setRowHeight(30);
        JScrollPane equipmentScrollPane = new JScrollPane(equipmentTable);

        add(equipmentScrollPane, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String status) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.add(new JLabel(title));
        card.add(new JLabel("Status: " + status));

        JButton detailsBtn = new JButton("Details");
        card.add(detailsBtn);

        return card;
    }
}
