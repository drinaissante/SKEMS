package dev.drinaissante;

import dev.drinaissante.ui.DashboardPanel;
import dev.drinaissante.ui.HeaderPanel;
import dev.drinaissante.ui.SidebarPanel;
import dev.drinaissante.ui.equipments.EquipmentPanel;
import dev.drinaissante.util.ImageUtil;
import dev.drinaissante.util.ui.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

    //    public float[] fractions = {
//            0.0f, 0.5f, 1.0f
//    };
    /*
 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);
     */
//    public Color[] colors = {
//            Color.decode("#0f2027"),   // cyan top
//            Color.decode("#203a43"),   // slate blue middle
//            Color.decode("#2c5364")    // navy bottom
//    };

    public float[] fractions = {
            0.000f,   // 0 / 100
            0.083f,   // 8.333 / 100
            0.167f,   // 16.667 / 100
            0.250f,   // 25.000 / 100
            0.333f,   // 33.333 / 100
            0.417f,   // 41.667 / 100
            0.500f,   // 50.000 / 100
            0.583f,   // 58.333 / 100
            0.667f,   // 66.667 / 100
            0.750f,   // 75.000 / 100
            0.833f,   // 83.333 / 100
            0.917f,   // 91.667 / 100
            1.000f    // 100.000 / 100
    };


    public Color[] colors = {
            Color.decode("#292832"),
            Color.decode("#272530"),
            Color.decode("#24222e"),
            Color.decode("#201f2b"),
            Color.decode("#1c1b27"),
            Color.decode("#181624"),
            Color.decode("#141220"),
            Color.decode("#100e1c"),
            Color.decode("#0c0a18"),
            Color.decode("#080614"),
            Color.decode("#050311"),
            Color.decode("#02000e"),
            Color.decode("#00000b")
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

        setContentPane(new GradientPanel(colors, fractions, 225));

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
        sidebarPanel.setOpaque(false);
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