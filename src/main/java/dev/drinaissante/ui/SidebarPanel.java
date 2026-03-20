package dev.drinaissante.ui;

import dev.drinaissante.Main;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {

    public SidebarPanel(Main mainFrame) {
        setLayout(new GridLayout(10, 1, 15, 2)); // 10 rows
        setBackground(new Color(45, 45, 60)); // dark bg

        // nav buttons
        JButton dashboardBtn = new JButton("Dashboard");
        JButton equipmentBtn = new JButton("Equipment");

        dashboardBtn.addActionListener(e -> mainFrame.showPanel("Dashboard"));
        equipmentBtn.addActionListener(e -> mainFrame.showPanel("Equipments"));

//        JButton borrowBtn = new JButton("Borrow Requests");
//        JButton checkedOutBtn = new JButton("Checked-Out");
//        JButton reservationsBtn = new JButton("Reservations");
//        JButton historyBtn = new JButton("History Logs");
//        JButton reportsBtn = new JButton("Reports");
//        JButton adminBtn  = new JButton("Admin Settings");
//        JButton logoutBtn = new JButton("Logout");

        add(dashboardBtn);
        add(equipmentBtn);
//        add(borrowBtn);
//        add(checkedOutBtn);
//        add(reservationsBtn);
//        add(historyBtn);
//        add(reportsBtn);
//        add(adminBtn);
//        add(logoutBtn);

    }
}
