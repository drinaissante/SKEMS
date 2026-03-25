package dev.drinaissante.ui.equipments;

import dev.drinaissante.Main;
import dev.drinaissante.api.RegisterResponse;
import dev.drinaissante.api.RegisterService;
import dev.drinaissante.model.EquipmentStatus;
import dev.drinaissante.util.QRGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RegisterDialog extends JDialog {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // TODO the image picker for cam

    public RegisterDialog(Main parent) {
        super(parent, "Register", true);

        setSize(350, 450);
        setLayout(null);

        int labelX = 20;
        int fieldX = 120;
        int y = 20;
        int gap = 35;

        // 🧩 Name
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        nameLabel.setBounds(labelX, y, 100, 25);
        nameField.setBounds(fieldX, y, 180, 25);
        y += gap;

        // 🧩 Type
        JLabel typeLabel = new JLabel("Type:");
        JTextField typeField = new JTextField();

        typeLabel.setBounds(labelX, y, 100, 25);
        typeField.setBounds(fieldX, y, 180, 25);
        y += gap;

        // 🧩 Owner
        JLabel ownerLabel = new JLabel("Owner:");
        JTextField ownerField = new JTextField();

        ownerLabel.setBounds(labelX, y, 100, 25);
        ownerField.setBounds(fieldX, y, 180, 25);
        y += gap;

        // 🧩 Date Picker
        JLabel dateLabel = new JLabel("Date:");
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd HH:mm");
        dateSpinner.setEditor(editor);

        dateLabel.setBounds(labelX, y, 100, 25);
        dateSpinner.setBounds(fieldX, y, 180, 25);
        y += gap;

        // 🧩 Status Dropdown
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<EquipmentStatus> statusDropdown = new JComboBox<>(EquipmentStatus.values());

        statusDropdown.setSelectedIndex(0);
        statusDropdown.setEditable(false);

        statusLabel.setBounds(labelX, y, 100, 25);
        statusDropdown.setBounds(fieldX, y, 180, 25);
        y += gap + 10;

        // 🧩 Submit Button
        JButton submit = new JButton("Submit");
        submit.setBounds(120, y, 120, 30);
        y += gap;

        // 🧩 Loading Label
        JLabel loadingLabel = new JLabel("Processing...");
        loadingLabel.setBounds(120, y, 150, 25);
        loadingLabel.setVisible(false);
        y += gap;

        JLabel qrLabel = new JLabel();
        qrLabel.setBounds(75, y, 200, 200);

        JTextField uuidField = new JTextField();
        uuidField.setBounds(50, y + 210, 250, 25);
        uuidField.setEditable(false);
        uuidField.setBorder(null); // optional: remove border to make it look like a label
        uuidField.setBackground(null); // optional: transparent background


        add(nameLabel);
        add(nameField);
        add(typeLabel);
        add(typeField);
        add(ownerLabel);
        add(ownerField);
        add(dateLabel);
        add(dateSpinner);
        add(statusLabel);
        add(statusDropdown);
        add(submit);
        add(loadingLabel);
        add(qrLabel);
        add(uuidField);

        submit.addActionListener(e -> new Thread(() -> {

            SwingUtilities.invokeLater(() -> {
                submit.setEnabled(false);
                loadingLabel.setVisible(true);
            });

            try {
                Date selectedDate = (Date) dateSpinner.getValue();

                String name = nameField.getText();
                String type = typeField.getText();
                String owner = ownerField.getText();
                String date_given = sdf.format(selectedDate);
                String status = ((EquipmentStatus) Objects.requireNonNull(statusDropdown.getSelectedItem())).name();

                RegisterResponse response = RegisterService.register(
                        name, type, owner, date_given, status
                );

                SwingUtilities.invokeLater(() -> {
                    uuidField.setText(response.uuid);

                    ImageIcon icon = new ImageIcon(QRGenerator.qrCodesPath + File.separator + response.uuid + ".png");
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    qrLabel.setIcon(new ImageIcon(img));

                    // Increase dialog height
                    setSize(350, 600); // new height to fit QR

                    JOptionPane.showMessageDialog(
                            this,
                            "QR Generated Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(
                                this,
                                "Error: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        )
                );
            } finally {
                SwingUtilities.invokeLater(() -> {
                    submit.setEnabled(true);
                    loadingLabel.setVisible(false);

                    nameField.setText("");
                    typeField.setText("");
                    ownerField.setText("");
                    statusDropdown.setSelectedIndex(0);
                });
            }

        }).start());

        setLocationRelativeTo(parent);
    }


}
