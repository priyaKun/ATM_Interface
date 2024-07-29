import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public String withdraw(double amount) {
        if (amount > 0 && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            return "Withdrawal successful. New balance: " + account.getBalance();
        } else {
            return "Insufficient balance or invalid amount.";
        }
    }

    public String deposit(double amount) {
        if (amount > 0) {
            account.setBalance(account.getBalance() + amount);
            return "Deposit successful. New balance: " + account.getBalance();
        } else {
            return "Invalid deposit amount.";
        }
    }

    public String checkBalance() {
        return "Current balance: " + account.getBalance();
    }
}

public class ATMInterface extends Application {
    private ATM atm;
    private Label messageLabel;

    @Override
    public void start(Stage primaryStage) {
        BankAccount account = new BankAccount(1000);
        atm = new ATM(account);

        primaryStage.setTitle("ATM Machine");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Create UI components
        Label welcomeLabel = new Label("Welcome to the ATM");
        messageLabel = new Label();
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button checkBalanceButton = new Button("Check Balance");
        Button exitButton = new Button("Exit");

        // Add components to the grid
        grid.add(welcomeLabel, 0, 0, 2, 1);
        grid.add(amountField, 0, 1, 2, 1);
        grid.add(withdrawButton, 0, 2);
        grid.add(depositButton, 1, 2);
        grid.add(checkBalanceButton, 0, 3);
        grid.add(exitButton, 1, 3);
        grid.add(messageLabel, 0, 4, 2, 1);

        // Set button actions
        withdrawButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            String message = atm.withdraw(amount);
            messageLabel.setText(message);
            amountField.clear();
        });

        depositButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            String message = atm.deposit(amount);
            messageLabel.setText(message);
            amountField.clear();
        });

        checkBalanceButton.setOnAction(e -> {
            String message = atm.checkBalance();
            messageLabel.setText(message);
        });

        exitButton.setOnAction(e -> primaryStage.close());

        // Create and set the scene
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
