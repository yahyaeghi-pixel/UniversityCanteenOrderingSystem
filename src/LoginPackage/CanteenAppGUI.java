package LoginPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CanteenAppGUI {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private User loggedInUser;

    // Shared for student menu
    private java.util.List<MenuItem> currentMenuItems = new ArrayList<>();
    private JList<String> studentMenuList;
    private DefaultListModel<String> studentMenuModel;

    // Shared for lecturer menu
    private JList<String> lecturerMenuList;
    private DefaultListModel<String> lecturerMenuModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanteenAppGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("University Canteen Ordering System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createStartPanel(), "start");
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegisterPanel(), "register");
        mainPanel.add(createStudentPanel(), "student");
        mainPanel.add(createLecturerPanel(), "lecturer");
        mainPanel.add(createStaffPanel(), "staff");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "start");
    }

    // --------------------------------------------------------------------
    // START PANEL
    // --------------------------------------------------------------------
    private JPanel createStartPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JLabel title = new JLabel("University Canteen Ordering System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        p.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Create Account");
        JButton staffBtn = new JButton("Staff Dashboard (no login)");
        JButton exitBtn = new JButton("Exit");

        buttons.add(loginBtn);
        buttons.add(registerBtn);
        buttons.add(staffBtn);
        buttons.add(exitBtn);

        p.add(buttons, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        registerBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        staffBtn.addActionListener(e -> {
            loggedInUser = null;
            cardLayout.show(mainPanel, "staff");
        });
        exitBtn.addActionListener(e -> System.exit(0));

        return p;
    }

    // --------------------------------------------------------------------
    // LOGIN PANEL
    // --------------------------------------------------------------------
    private JPanel createLoginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel emailLbl = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passLbl = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        p.add(title, c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1;
        p.add(emailLbl, c);
        c.gridx = 1;
        p.add(emailField, c);

        c.gridx = 0; c.gridy = 2;
        p.add(passLbl, c);
        c.gridx = 1;
        p.add(passField, c);

        c.gridx = 0; c.gridy = 3;
        p.add(loginBtn, c);
        c.gridx = 1;
        p.add(backBtn, c);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword());

            LoginService service = new LoginService();
            User u = service.login(email, password);

            if (u == null) {
                JOptionPane.showMessageDialog(frame, "Invalid email or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            loggedInUser = u;
            JOptionPane.showMessageDialog(frame, "Welcome " + u.getEmail() +
                    " (" + u.getRole() + ")");

            switch (u.getRole().toLowerCase()) {
                case "student":
                    refreshStudentMenu();
                    cardLayout.show(mainPanel, "student");
                    break;
                case "lecturer":
                    refreshLecturerMenu();
                    cardLayout.show(mainPanel, "lecturer");
                    break;
                case "staff":
                    cardLayout.show(mainPanel, "staff");
                    break;
                default:
                    JOptionPane.showMessageDialog(frame,
                            "Unknown role: " + u.getRole());
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "start"));

        return p;
    }

    // --------------------------------------------------------------------
    // REGISTER PANEL
    // --------------------------------------------------------------------
    private JPanel createRegisterPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel emailLbl = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passLbl = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JLabel roleLbl = new JLabel("Role (student/lecturer/staff):");
        JTextField roleField = new JTextField(20);

        JButton createBtn = new JButton("Create");
        JButton backBtn = new JButton("Back");

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        p.add(title, c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1;
        p.add(emailLbl, c);
        c.gridx = 1;
        p.add(emailField, c);

        c.gridx = 0; c.gridy = 2;
        p.add(passLbl, c);
        c.gridx = 1;
        p.add(passField, c);

        c.gridx = 0; c.gridy = 3;
        p.add(roleLbl, c);
        c.gridx = 1;
        p.add(roleField, c);

        c.gridx = 0; c.gridy = 4;
        p.add(createBtn, c);
        c.gridx = 1;
        p.add(backBtn, c);

        createBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword());
            String role = roleField.getText().trim().toLowerCase();

            if (!role.equals("student") && !role.equals("lecturer") && !role.equals("staff")) {
                JOptionPane.showMessageDialog(frame,
                        "Role must be student, lecturer, or staff.");
                return;
            }

            AccountCreationService service = new AccountCreationService();
            boolean ok = service.createAccount(email, password, role);

            if (ok) {
                JOptionPane.showMessageDialog(frame, "Account created.");
                cardLayout.show(mainPanel, "start");
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Could not create account. Check console for message.");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "start"));

        return p;
    }

    // --------------------------------------------------------------------
    // STUDENT PANEL
    // --------------------------------------------------------------------
    private JPanel createStudentPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Student Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(title, BorderLayout.NORTH);

        studentMenuModel = new DefaultListModel<>();
        studentMenuList = new JList<>(studentMenuModel);
        JScrollPane scroll = new JScrollPane(studentMenuList);
        p.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        JButton refreshBtn = new JButton("Refresh Menu");
        JButton orderBtn = new JButton("Order Selected Item");
        JButton myOrdersBtn = new JButton("View My Orders");
        JButton backBtn = new JButton("Logout");

        bottom.add(refreshBtn);
        bottom.add(orderBtn);
        bottom.add(myOrdersBtn);
        bottom.add(backBtn);

        p.add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshStudentMenu());

        orderBtn.addActionListener(e -> {
            if (loggedInUser == null) {
                JOptionPane.showMessageDialog(frame, "Not logged in.");
                return;
            }
            int idx = studentMenuList.getSelectedIndex();
            if (idx < 0 || idx >= currentMenuItems.size()) {
                JOptionPane.showMessageDialog(frame, "Select an item first.");
                return;
            }
            MenuItem item = currentMenuItems.get(idx);
            String datetime = java.time.LocalDateTime.now().toString();
            Order order = new Order(loggedInUser.getEmail(),
                                    item.getName(),
                                    item.getPrice(),
                                    datetime,
                                    "Pending");
            OrderDatabase.saveOrder(order);
            JOptionPane.showMessageDialog(frame,
                    "Order placed for " + item.getName() + " (status: Pending)");
        });

        myOrdersBtn.addActionListener(e -> {
            if (loggedInUser == null) return;
            java.util.List<Order> orders = OrderDatabase.loadOrders();
            StringBuilder sb = new StringBuilder();
            sb.append("Orders for ").append(loggedInUser.getEmail()).append(":\n\n");
            for (Order o : orders) {
                if (o.getEmail().equalsIgnoreCase(loggedInUser.getEmail())) {
                    sb.append(o.getItemName()).append(" €")
                      .append(o.getItemPrice())
                      .append(" | ").append(o.getDate())
                      .append(" | Status: ").append(o.getStatus())
                      .append("\n");
                }
            }
            JOptionPane.showMessageDialog(frame,
                    sb.length() == 0 ? "No orders yet." : sb.toString());
        });

        backBtn.addActionListener(e -> {
            loggedInUser = null;
            cardLayout.show(mainPanel, "start");
        });

        return p;
    }

    private void refreshStudentMenu() {
        currentMenuItems = MenuDatabase.loadMenu();
        studentMenuModel.clear();
        for (MenuItem m : currentMenuItems) {
            studentMenuModel.addElement(m.getName() + " - €" + m.getPrice());
        }
    }

    // --------------------------------------------------------------------
    // LECTURER PANEL
    // --------------------------------------------------------------------
    private JPanel createLecturerPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Lecturer Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(title, BorderLayout.NORTH);

        lecturerMenuModel = new DefaultListModel<>();
        lecturerMenuList = new JList<>(lecturerMenuModel);
        JScrollPane scroll = new JScrollPane(lecturerMenuList);
        p.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton refreshBtn = new JButton("Refresh Menu");
        JButton addBtn = new JButton("Add Item");
        JButton removeBtn = new JButton("Remove Selected");
        JButton backBtn = new JButton("Logout");

        bottom.add(refreshBtn);
        bottom.add(addBtn);
        bottom.add(removeBtn);
        bottom.add(backBtn);

        p.add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshLecturerMenu());

        addBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame,
                    "Enter item name:");
            if (name == null || name.trim().isEmpty()) return;

            String priceStr = JOptionPane.showInputDialog(frame,
                    "Enter price (e.g. 6.50):");
            if (priceStr == null) return;
            try {
                double price = Double.parseDouble(priceStr);
                ArrayList<MenuItem> menu = MenuDatabase.loadMenu();
                menu.add(new MenuItem(name.trim(), price));
                MenuDatabase.saveAllMenu(menu);
                refreshLecturerMenu();
                JOptionPane.showMessageDialog(frame, "Item added.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price.");
            }
        });

        removeBtn.addActionListener(e -> {
            int idx = lecturerMenuList.getSelectedIndex();
            if (idx < 0) {
                JOptionPane.showMessageDialog(frame, "Select an item first.");
                return;
            }
            ArrayList<MenuItem> menu = MenuDatabase.loadMenu();
            if (idx >= menu.size()) return;
            MenuItem removed = menu.remove(idx);
            MenuDatabase.saveAllMenu(menu);
            refreshLecturerMenu();
            JOptionPane.showMessageDialog(frame,
                    "Removed: " + removed.getName());
        });

        backBtn.addActionListener(e -> {
            loggedInUser = null;
            cardLayout.show(mainPanel, "start");
        });

        return p;
    }

    private void refreshLecturerMenu() {
        java.util.List<MenuItem> menu = MenuDatabase.loadMenu();
        lecturerMenuModel.clear();
        for (MenuItem m : menu) {
            lecturerMenuModel.addElement(m.getName() + " - €" + m.getPrice());
        }
    }

    // --------------------------------------------------------------------
    // STAFF PANEL
    // --------------------------------------------------------------------
    private JPanel createStaffPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Staff Order Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(title, BorderLayout.NORTH);

        JTextArea ordersArea = new JTextArea();
        ordersArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(ordersArea);
        p.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton refreshBtn = new JButton("Refresh Orders");
        JButton updateStatusBtn = new JButton("Update Order Status");
        JButton backBtn = new JButton("Back");

        bottom.add(refreshBtn);
        bottom.add(updateStatusBtn);
        bottom.add(backBtn);
        p.add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            java.util.List<Order> orders = OrderDatabase.loadOrders();
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (Order o : orders) {
                sb.append(i++).append(". ")
                  .append(o.getEmail()).append(" - ")
                  .append(o.getItemName()).append(" €").append(o.getItemPrice())
                  .append(" | ").append(o.getDate())
                  .append(" | Status: ").append(o.getStatus())
                  .append("\n");
            }
            if (sb.length() == 0) sb.append("No orders.");
            ordersArea.setText(sb.toString());
        });

        updateStatusBtn.addActionListener(e -> {
            java.util.List<Order> orders = OrderDatabase.loadOrders();
            if (orders.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No orders.");
                return;
            }
            String indexStr = JOptionPane.showInputDialog(frame,
                    "Enter order number to update:");
            if (indexStr == null) return;
            try {
                int idx = Integer.parseInt(indexStr);
                if (idx < 1 || idx > orders.size()) {
                    JOptionPane.showMessageDialog(frame, "Invalid number.");
                    return;
                }
                Order o = orders.get(idx - 1);
                String[] options = {"Pending", "Preparing", "Ready", "Completed"};
                String newStatus = (String) JOptionPane.showInputDialog(
                        frame,
                        "Current: " + o.getStatus() + "\nChoose new status:",
                        "Update Status",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        o.getStatus()
                );
                if (newStatus == null) return;
                o.setStatus(newStatus);
                OrderDatabase.saveAllOrders(new ArrayList<>(orders));
                JOptionPane.showMessageDialog(frame, "Status updated.");
                refreshBtn.doClick();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number.");
            }
        });

        backBtn.addActionListener(e -> {
            loggedInUser = null;
            cardLayout.show(mainPanel, "start");
        });

        return p;
    }
}
