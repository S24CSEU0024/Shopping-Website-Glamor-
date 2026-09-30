import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Main {

    // Stores username and password
    private final Map<String, String> userDatabase = new HashMap<>();

    // Stores completed orders
    private final List<List<String>> orderHistory = new ArrayList<>();
    private final List<List<Double>> orderPrices = new ArrayList<>();

    private JFrame frame;

    private JPanel loginRegisterPanel;
    private JPanel welcomePanel;
    private JPanel productPanel;
    private JPanel checkoutPanel;

    // Current cart
    private final List<String> cartItems = new ArrayList<>();
    private final List<Double> cartPrices = new ArrayList<>();

    private JTextArea cartTextArea;

    // Payment checkbox
    private JCheckBox paidCheckbox;

    public Main() {
        Shop();
    }

    public void Shop() {

        frame = new JFrame("Glamora Cosmetics 💖");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new CardLayout());

        // Create all panels
        createLoginRegisterPanel();
        createWelcomePanel();
        createProductPanel();
        createCheckoutPanel();

        // Add panels to CardLayout
        frame.add(loginRegisterPanel, "login");
        frame.add(welcomePanel, "welcome");
        frame.add(productPanel, "products");
        frame.add(checkoutPanel, "checkout");

        // Show login page first
        CardLayout cardLayout =
                (CardLayout) frame.getContentPane().getLayout();

        cardLayout.show(frame.getContentPane(), "login");

        frame.setVisible(true);
    }

    // =========================================================
    // LOGIN + REGISTER
    // =========================================================

    private void createLoginRegisterPanel() {

        loginRegisterPanel = new GradientPanel();
        loginRegisterPanel.setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        // ---------------- LOGIN TAB ----------------

        JPanel loginTab = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        loginTab.setBackground(new Color(255, 228, 225));

        JTextField loginUsername = new JTextField();
        JPasswordField loginPassword = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginTab.add(new JLabel("Username:"));
        loginTab.add(loginUsername);

        loginTab.add(new JLabel("Password:"));
        loginTab.add(loginPassword);

        loginTab.add(new JLabel());
        loginTab.add(loginButton);

        // ---------------- REGISTER TAB ----------------

        JPanel registerTab = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        registerTab.setBackground(new Color(255, 240, 245));

        JTextField regUsername = new JTextField();
        JPasswordField regPassword = new JPasswordField();
        JPasswordField regConfirm = new JPasswordField();

        JButton registerButton = new JButton("Register");

        registerTab.add(new JLabel("Username:"));
        registerTab.add(regUsername);

        registerTab.add(new JLabel("Password:"));
        registerTab.add(regPassword);

        registerTab.add(new JLabel("Confirm Password:"));
        registerTab.add(regConfirm);

        registerTab.add(new JLabel());
        registerTab.add(registerButton);

        tabs.addTab("Login", loginTab);
        tabs.addTab("Register", registerTab);

        loginRegisterPanel.add(tabs, BorderLayout.CENTER);

        // ---------------- LOGIN ACTION ----------------

        loginButton.addActionListener(e -> {

            String user = loginUsername.getText().trim();
            String pass = new String(loginPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please enter username and password."
                );

                return;
            }

            if (userDatabase.containsKey(user)
                    && userDatabase.get(user).equals(pass)) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Login successful! 💖"
                );

                showPanel("welcome");

                loginUsername.setText("");
                loginPassword.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid username or password!"
                );
            }
        });

        // ---------------- REGISTER ACTION ----------------

        registerButton.addActionListener(e -> {

            String user = regUsername.getText().trim();
            String pass = new String(regPassword.getPassword());
            String confirm =
                    new String(regConfirm.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Fields cannot be empty!"
                );

            } else if (!pass.equals(confirm)) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Passwords do not match!"
                );

            } else if (userDatabase.containsKey(user)) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Username already exists!"
                );

            } else {

                userDatabase.put(user, pass);

                JOptionPane.showMessageDialog(
                        frame,
                        "Registration successful! Please login."
                );

                // Clear fields
                regUsername.setText("");
                regPassword.setText("");
                regConfirm.setText("");

                // Switch to login tab
                tabs.setSelectedIndex(0);
            }
        });
    }

    // =========================================================
    // WELCOME PAGE
    // =========================================================

    private void createWelcomePanel() {

        welcomePanel = new GradientPanel();
        welcomePanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(
                "<html>" +
                        "<div style='text-align:center;'>" +
                        "<span style='font-size:36pt;'>💄</span> " +
                        "<span style='font-family:Lucida Calligraphy;" +
                        "font-size:28pt;" +
                        "color:rgb(220,105,180);'>" +
                        "Welcome to Glamora!" +
                        "</span> " +
                        "<span style='font-size:36pt;'>💖</span>" +
                        "</div>" +
                        "</html>"
        );

        welcomeLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        welcomePanel.add(
                welcomeLabel,
                BorderLayout.NORTH
        );

        JPanel buttonPanel = new JPanel(
                new GridLayout(3, 1, 10, 10)
        );

        buttonPanel.setBackground(
                new Color(255, 182, 193)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        50, 300, 50, 300
                )
        );

        JButton shopButton =
                new JButton("🛍️  Start Shopping");

        JButton prevOrdersButton =
                new JButton("📦  Previous Orders");

        JButton returnOrderButton =
                new JButton("↩️  Return Order");

        JButton[] buttons = {
                shopButton,
                prevOrdersButton,
                returnOrderButton
        };

        for (JButton button : buttons) {
            button.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            20
                    )
            );

            // Force macOS to use our background
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            button.setBackground(
                    new Color(255, 105, 180)
            );

            button.setForeground(Color.WHITE);

            // Make the button text clearly visible
            button.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            button.setVerticalAlignment(
                    SwingConstants.CENTER
            );

            button.setMargin(
                    new Insets(15, 20, 15, 20)
            );

            buttonPanel.add(button);
        }

        // Start shopping
        shopButton.addActionListener(e ->
                showPanel("products")
        );

        // Previous orders
        prevOrdersButton.addActionListener(
                e -> showPreviousOrders()
        );

        // Return order
        returnOrderButton.addActionListener(
                e -> handleReturnOrder()
        );

        welcomePanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );
    }

   

    private void createProductPanel() {

        productPanel = new GradientPanel();
        productPanel.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(
                new GridLayout(0, 3, 15, 15)
        );

        gridPanel.setBackground(
                new Color(255, 240, 245)
        );

        String[] productNames = {

                "Eyeliner",
                "Blush",
                "Lipstick",
                "Mascara",
                "Compact powder",
                "Eyeshadow Palette",
                "Tint",
                "Concealer",
                "Highlighter",
                "Bronzer",
                "Lipliner",
                "Primer",
                "Face Scrub",
                "Face wash",
                "Toner",
                "Face Serum"
        };

        double[] productPrices = {

                349.0,
                399.0,
                449.0,
                449.0,
                799.0,
                760.0,
                549.0,
                409.0,
                749.0,
                349.0,
                649.0,
                799.0,
                899.0,
                699.0,
                599.0,
                699.0
        };

        JCheckBox[] productCheckBoxes =
                new JCheckBox[productNames.length];

        for (int i = 0; i < productNames.length; i++) {

            JPanel productItem = new JPanel(
                    new BorderLayout()
            );

            productItem.setBackground(
                    new Color(255, 240, 245)
            );

            // Image name
            String imageName =
                    productNames[i]
                            .toLowerCase()
                            .replace(" ", "_")
                            + ".png";

            ImageIcon icon = null;

            try {

                var resource =
                        getClass()
                                .getClassLoader()
                                .getResource(
                                        "images/" + imageName
                                );

                if (resource != null) {

                    icon = new ImageIcon(resource);

                    Image img =
                            icon.getImage()
                                    .getScaledInstance(
                                            150,
                                            150,
                                            Image.SCALE_SMOOTH
                                    );

                    icon = new ImageIcon(img);

                } else {

                    System.out.println(
                            "Image not found: "
                                    + imageName
                    );
                }

            } catch (Exception ex) {

                System.out.println(
                        "Error loading image: "
                                + imageName
                );
            }

            JLabel imageLabel = new JLabel();

            if (icon != null) {
                imageLabel.setIcon(icon);
            }

            imageLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            productCheckBoxes[i] =
                    new JCheckBox(
                            productNames[i]
                                    + " (Rs."
                                    + productPrices[i]
                                    + ")"
                    );

            productCheckBoxes[i].setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            14
                    )
            );

            productCheckBoxes[i].setBackground(
                    new Color(255, 240, 245)
            );

            productItem.add(
                    imageLabel,
                    BorderLayout.CENTER
            );

            productItem.add(
                    productCheckBoxes[i],
                    BorderLayout.SOUTH
            );

            gridPanel.add(productItem);
        }

        JScrollPane scrollPane =
                new JScrollPane(gridPanel);

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        productPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        

        JButton addToCartButton =
                new JButton("🛒 Add to Cart");

        addToCartButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        addToCartButton.setBackground(
                new Color(255, 105, 180)
        );

        addToCartButton.setForeground(
                Color.WHITE
        );

        addToCartButton.setFocusPainted(false);

        addToCartButton.addActionListener(e -> {

            boolean itemSelected = false;

            for (int i = 0;
                 i < productNames.length;
                 i++) {

                if (productCheckBoxes[i].isSelected()) {

                    cartItems.add(productNames[i]);
                    cartPrices.add(productPrices[i]);

                    itemSelected = true;

                    // Uncheck after adding
                    productCheckBoxes[i]
                            .setSelected(false);
                }
            }

            // Don't proceed if nothing was selected
            if (!itemSelected) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please select at least one product."
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Products added to cart! 🛒"
            );

            int choice =
                    JOptionPane.showConfirmDialog(
                            frame,
                            "Continue Shopping?",
                            "Continue?",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice == JOptionPane.NO_OPTION) {

                updateCheckoutPanel();

                showPanel("checkout");
            }
        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(
                new Color(255, 240, 245)
        );

        buttonPanel.add(addToCartButton);

        productPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );
    }

    

    private void createCheckoutPanel() {

        checkoutPanel = new GradientPanel();
        checkoutPanel.setLayout(new BorderLayout());

        cartTextArea = new JTextArea();

        cartTextArea.setEditable(false);

        cartTextArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        checkoutPanel.add(
                new JScrollPane(cartTextArea),
                BorderLayout.CENTER
        );

        JPanel actionPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        10
                )
        );

        actionPanel.setBackground(
                new Color(255, 182, 193)
        );

        paidCheckbox =
                new JCheckBox("Pay Now 💳");

        paidCheckbox.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        paidCheckbox.setBackground(
                new Color(255, 182, 193)
        );

        paidCheckbox.setForeground(
                Color.WHITE
        );

        JButton cancelButton =
                new JButton("❌ Cancel Order");

        cancelButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        cancelButton.setBackground(
                new Color(255, 182, 193)
        );

        cancelButton.setForeground(
                Color.WHITE
        );

        // ---------------- PAYMENT ----------------

        paidCheckbox.addActionListener(e -> {

            if (paidCheckbox.isSelected()) {

                if (cartItems.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Your cart is empty!"
                    );

                    paidCheckbox.setSelected(false);

                    return;
                }

                showPaymentOptions();
            }
        });

        // ---------------- CANCEL ORDER ----------------

        cancelButton.addActionListener(e -> {

            if (cartItems.isEmpty()) {

                showPanel("welcome");

                return;
            }

            int confirm =
                    JOptionPane.showConfirmDialog(
                            frame,
                            "Are you sure you want to cancel this order?",
                            "Cancel Order",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm == JOptionPane.YES_OPTION) {

                cartItems.clear();
                cartPrices.clear();

                updateCheckoutPanel();

                paidCheckbox.setSelected(false);

                showPanel("welcome");
            }
        });

        actionPanel.add(paidCheckbox);
        actionPanel.add(cancelButton);

        checkoutPanel.add(
                actionPanel,
                BorderLayout.SOUTH
        );
    }

    

    private void showPaymentOptions() {

        String address =
                JOptionPane.showInputDialog(
                        frame,
                        "Please enter your delivery address:",
                        "Delivery Address",
                        JOptionPane.PLAIN_MESSAGE
                );

        if (address == null
                || address.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Delivery address is required."
            );

            paidCheckbox.setSelected(false);

            return;
        }

        String[] paymentOptions = {

                "UPI",
                "Net Banking",
                "Credit/Debit Card",
                "Cash on Delivery"
        };

        String selectedOption =
                (String) JOptionPane.showInputDialog(

                        frame,

                        "Select Payment Method:",

                        "Payment",

                        JOptionPane.QUESTION_MESSAGE,

                        null,

                        paymentOptions,

                        paymentOptions[0]
                );

        if (selectedOption == null) {

            paidCheckbox.setSelected(false);

            return;
        }

        // UPI
        if (selectedOption.equals("UPI")) {

            showUPIPayment(address);

        } else {

            JOptionPane.showMessageDialog(
                    frame,

                    "Order will be delivered to:\n"
                            + address
                            + "\n\nPayment via "
                            + selectedOption
                            + " processed.\n\n"
                            + "💕 Thank you, visit again!"
            );

            completeOrder();
        }
    }

   

    private void showUPIPayment(String address) {

        try {

            var resource =
                    getClass()
                            .getClassLoader()
                            .getResource("upi_qr.jpeg");

            if (resource == null) {

                JOptionPane.showMessageDialog(
                        frame,
                        "UPI QR code not found!"
                );

                paidCheckbox.setSelected(false);

                return;
            }

            ImageIcon originalIcon =
                    new ImageIcon(resource);

            Image scaledImage =
                    originalIcon.getImage()
                            .getScaledInstance(
                                    400,
                                    400,
                                    Image.SCALE_SMOOTH
                            );

            ImageIcon scaledIcon =
                    new ImageIcon(scaledImage);

            JOptionPane.showMessageDialog(
                    frame,

                    "Scan the QR code to pay:\n"
                            + "Delivery Address:\n"
                            + address,

                    "UPI Payment",

                    JOptionPane.INFORMATION_MESSAGE,

                    scaledIcon
            );

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            frame,
                            "Have you completed the payment?",
                            "Payment Confirmation",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmation == JOptionPane.YES_OPTION) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Payment Successful 💖\n"
                                + "Thank you for shopping with Glamora!"
                );

                completeOrder();

            } else {

                paidCheckbox.setSelected(false);

                JOptionPane.showMessageDialog(
                        frame,
                        "Payment cancelled."
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Error loading QR code."
            );

            paidCheckbox.setSelected(false);
        }
    }

    
    private void completeOrder() {

        // Save order
        orderHistory.add(
                new ArrayList<>(cartItems)
        );

        orderPrices.add(
                new ArrayList<>(cartPrices)
        );

        // Clear cart
        cartItems.clear();
        cartPrices.clear();

        // Reset payment checkbox
        paidCheckbox.setSelected(false);

        // Clear checkout
        updateCheckoutPanel();

        // Go to welcome page
        showPanel("welcome");
    }

   

    private void updateCheckoutPanel() {

        cartTextArea.setText("");

        if (cartItems.isEmpty()) {

            cartTextArea.append(
                    "Your cart is empty.\n"
            );

            return;
        }

        double totalPrice = 0;

        int itemCount =
                Math.min(
                        cartItems.size(),
                        cartPrices.size()
                );

        cartTextArea.append(
                "========== YOUR CART ==========\n\n"
        );

        for (int i = 0;
             i < itemCount;
             i++) {

            cartTextArea.append(
                    (i + 1)
                            + ". "
                            + cartItems.get(i)
                            + " - Rs."
                            + String.format(
                            "%.2f",
                            cartPrices.get(i)
                    )
                            + "\n"
            );

            totalPrice += cartPrices.get(i);
        }

        cartTextArea.append(
                "\n--------------------------------\n"
        );

        cartTextArea.append(
                "Total Price: Rs."
                        + String.format(
                        "%.2f",
                        totalPrice
                )
        );
    }

    

    private void showPreviousOrders() {

        if (orderHistory.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "💌 You have no previous orders yet."
            );

            return;
        }

        StringBuilder history =
                new StringBuilder();

        for (int i = 0;
             i < orderHistory.size();
             i++) {

            history.append(
                    "========== ORDER #"
                            + (i + 1)
                            + " ==========\n"
            );

            List<String> items =
                    orderHistory.get(i);

            List<Double> prices =
                    orderPrices.get(i);

            double total = 0;

            for (int j = 0;
                 j < items.size();
                 j++) {

                history.append(
                        "  - "
                                + items.get(j)
                                + " - Rs."
                                + prices.get(j)
                                + "\n"
                );

                total += prices.get(j);
            }

            history.append(
                    "Total: Rs."
                            + String.format(
                            "%.2f",
                            total
                    )
                            + "\n\n"
            );
        }

        JTextArea textArea =
                new JTextArea(
                        history.toString()
                );

        textArea.setEditable(false);

        textArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(500, 350)
        );

        JOptionPane.showMessageDialog(
                frame,
                scrollPane,
                "📦 Previous Orders",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    

    private void handleReturnOrder() {

        if (orderHistory.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "No orders available to return."
            );

            return;
        }

        String[] orderLabels =
                new String[orderHistory.size()];

        for (int i = 0;
             i < orderLabels.length;
             i++) {

            orderLabels[i] =
                    "Order #" + (i + 1);
        }

        String selected =
                (String) JOptionPane.showInputDialog(

                        frame,

                        "Select an order to return:",

                        "↩️ Return Order",

                        JOptionPane.PLAIN_MESSAGE,

                        null,

                        orderLabels,

                        orderLabels[0]
                );

        if (selected != null) {

            int index =
                    Integer.parseInt(
                            selected
                                    .split("#")[1]
                    ) - 1;

            JOptionPane.showMessageDialog(

                    frame,

                    selected
                            + " return request submitted. 💁‍♀️\n\n"
                            + "Our team will process your return for order #"
                            + (index + 1)
                            + ".",

                    "Return Order",

                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    

    private void showPanel(String panelName) {

        CardLayout cardLayout =
                (CardLayout)
                        frame.getContentPane()
                                .getLayout();

        cardLayout.show(
                frame.getContentPane(),
                panelName
        );
    }

    

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                Main::new
        );
    }

    
    static class GradientPanel extends JPanel {

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2d =
                    (Graphics2D) g;

            Color color1 =
                    new Color(255, 192, 203);

            Color color2 =
                    new Color(255, 240, 245);

            int w = getWidth();
            int h = getHeight();

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            color1,
                            0,
                            h,
                            color2
                    );

            g2d.setPaint(gradient);

            g2d.fillRect(
                    0,
                    0,
                    w,
                    h
            );
        }
    }
}