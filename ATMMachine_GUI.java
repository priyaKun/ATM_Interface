import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMMachine_GUI {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.0);
        ATMGUI atmGUI = new ATMGUI(userAccount);
        atmGUI.setVisible(true);
    }
}

class ATMGUI extends JFrame implements ActionListener {
    private BankAccount account;
    private JTextField balanceField;
    private JTextField amountField;
    private JTextArea outputArea;

    public ATMGUI(BankAccount account) {
        this.account = account;

        setTitle("ATM Machine");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel balanceLabel = new JLabel("Balance:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(balanceLabel, gbc);

        balanceField = new JTextField(10);
        balanceField.setText(Double.toString(account.getBalance()));
        balanceField.setEditable(false);
        gbc.gridx = 1;
        panel.add(balanceField, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(amountLabel, gbc);

        amountField = new JTextField(10);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(checkBalanceButton, gbc);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        gbc.gridx = 1;
        panel.add(depositButton, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        gbc.gridx = 2;
        panel.add(withdrawButton, gbc);

        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(scrollPane, gbc);

        add(panel);

        // Custom styling
        panel.setBackground(new Color(50, 50, 50));
        balanceLabel.setForeground(Color.WHITE);
        amountLabel.setForeground(Color.WHITE);
        checkBalanceButton.setBackground(new Color(70, 130, 180));
        checkBalanceButton.setForeground(Color.WHITE);
        depositButton.setBackground(new Color(34, 139, 34));
        depositButton.setForeground(Color.WHITE);
        withdrawButton.setBackground(new Color(178, 34, 34));
        withdrawButton.setForeground(Color.WHITE);
        outputArea.setBackground(new Color(230, 230, 230));
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                outputArea.setText("Invalid amount. Please enter a positive number.");
                return;
            }

            switch (command) {
                case "Check Balance":
                    outputArea.setText("Your balance is: Rs. " + account.getBalance());
                    break;
                case "Deposit":
                    account.deposit(amount);
                    balanceField.setText(Double.toString(account.getBalance()));
                    outputArea.setText("Deposit successful.");
                    break;
                case "Withdraw":
                    if (account.withdraw(amount)) {
                        balanceField.setText(Double.toString(account.getBalance()));
                        outputArea.setText("Withdrawal successful.");
                    } else {
                        outputArea.setText("Insufficient balance.");
                    }
                    break;
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter a valid amount.");
        }
    }
}
