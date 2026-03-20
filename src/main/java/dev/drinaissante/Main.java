package dev.drinaissante;

import dev.drinaissante.ui.DashboardPanel;
import dev.drinaissante.ui.EquipmentPanel;
import dev.drinaissante.ui.HeaderPanel;
import dev.drinaissante.ui.SidebarPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().start());
    }

    public void start() {
        // initiailize frame
        setTitle("SKEMS – Sine Kultura Equipment Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        buildUI();

        setupReload(); // TODO remove this when done (FOR RELOADING/REFERSHING WITHOUT RERUNNING)

        setVisible(true);
    }

    private void buildUI() {
        getContentPane().removeAll();

        // header
        HeaderPanel headerPanel = new HeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        SidebarPanel sidebarPanel = new SidebarPanel(this);
        add(sidebarPanel, BorderLayout.WEST);

        if (cardLayout == null)
            cardLayout = new CardLayout();

        if (mainPanel == null)
            mainPanel = new JPanel(cardLayout);

        mainPanel.add(new DashboardPanel(), "Dashboard");
        mainPanel.add(new EquipmentPanel(), "Equipments");

        add(mainPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // TODO remove this when done (FOR RELOADING/REFERSHING WITHOUT RERUNNING)
    private void setupReload() {
        JRootPane rootPane = getRootPane();

        KeyStroke keyStroke = KeyStroke.getKeyStroke("F5");

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "reloadUI");

        rootPane.getActionMap().put("reloadUI", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildUI();
            }
        });
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}