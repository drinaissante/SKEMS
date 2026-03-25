package dev.drinaissante;

import dev.drinaissante.ui.DashboardPanel;
import dev.drinaissante.ui.equipments.EquipmentPanel;
import dev.drinaissante.ui.HeaderPanel;
import dev.drinaissante.ui.SidebarPanel;
import dev.drinaissante.ui.equipments.RegisterDialog;
import dev.drinaissante.util.ui.GradientPanel;
import dev.drinaissante.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    public float[] fractions = {0.0f, 0.5f, 1.0f};
    public Color[] colors = {
            Color.decode("#0f2027"),   // cyan top
            Color.decode("#203a43"),   // slate blue middle
            Color.decode("#2c5364")    // navy bottom
    };

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().start());
    }

    public void start() {
        // initiailize frame
        setTitle("SKEMS");
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = ImageUtil.loadIcon("/sk_icon.jpg", 24, 24);
        setIconImage(icon.getImage());

        setContentPane(new GradientPanel(colors, fractions, false));

        buildUI();

        setupReload(); // TODO remove this when done (FOR RELOADING/REFERSHING WITHOUT RERUNNING)

        setVisible(true);
    }

    private void buildUI() {
        getContentPane().setLayout(new BorderLayout());

        if (cardLayout == null)
            cardLayout = new CardLayout();

        if (mainPanel == null)
            mainPanel = new JPanel(cardLayout);

        mainPanel.setOpaque(false);

        HeaderPanel headerPanel = new HeaderPanel(this);

        getContentPane().add(headerPanel, BorderLayout.NORTH);

        SidebarPanel sidebarPanel = new SidebarPanel(this);
        sidebarPanel.setOpaque(false); // let gradient show
        getContentPane().add(sidebarPanel, BorderLayout.WEST);

        DashboardPanel dashboardPanel = new DashboardPanel(this);
        dashboardPanel.setOpaque(false);
        mainPanel.add(dashboardPanel, "Dashboard");

        EquipmentPanel equipmentPanel = new EquipmentPanel();
        equipmentPanel.setOpaque(false);
        mainPanel.add(equipmentPanel, "Equipment");

        getContentPane().add(mainPanel, BorderLayout.CENTER);

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