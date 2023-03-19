package pages;

import database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PageNewTransaction extends JFrame {
    private static final int width = 650, height = 400;
    private static boolean windowOpened = false;
    private int line = 0;
    private boolean buy, buttonPressed;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final Wallet wallet;
    private JTextField amountInput, valueInput;
    private JLabel totalLabel;
    private final static ArrayList<CryptoAsset> marketAssets = CoingeckoData.marketCoins();
    private final ArrayList<FiatAsset> fiatAssets = FiatAsset.getFiatList();
    private JButton selectCoinButton;
    private JComboBox<Asset> pairComboBox;
    private Asset selectedCoin;
    private String currencySymbol;
    private JPanel test;

    public PageNewTransaction(Wallet wallet, JPanel test) {
        this.test = test;
        this.wallet = wallet;
        setBounds(new Rectangle(width, height));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel screen = screen();
        Arrays.stream(screen.getComponents()).forEach(e -> e.setPreferredSize(e.getPreferredSize()));

        add(screen);
    }

    private void buttonsAction(JButton[] buttons, boolean buy) {
        this.buy = buy;
        this.buttonPressed = true;
        buttons[0].setBackground((buy) ? Color.green : null);
        buttons[1].setBackground((buy) ? null : Color.red);

    }

    private JButton[] buySellButtons() {
        JButton[] buySellButtons = new JButton[]{new JButton("BUY"), new JButton("SELL")};
        Arrays.stream(buySellButtons).forEach(e -> {
            e.setFocusable(false);
            e.setOpaque(true);
            e.setPreferredSize(new Dimension(0, 45));
        });
        buySellButtons[0].addActionListener(e -> buttonsAction(buySellButtons, true));
        buySellButtons[1].addActionListener(e -> buttonsAction(buySellButtons, false));

        return buySellButtons;

    }

    private JComboBox<Asset> pairComboBox(ArrayList<? extends Asset> assets) {
        JComboBox<Asset> comboBox = new JComboBox<>(assets.toArray(new Asset[0]));
        comboBox.addActionListener(e -> updatePair((Asset) Objects.requireNonNull(comboBox.getSelectedItem())));

        return comboBox;
    }



    private void updateTotal() {
        double value, amount, total;
        amount = (!amountInput.getText().equals("")) ? Double.parseDouble(amountInput.getText()) : 0;
        value = (!valueInput.getText().equals("")) ? Double.parseDouble(valueInput.getText()) : 0;
        total = amount * value;
        String result = String.format(currencySymbol + "%.2f", total);
        totalLabel.setText(result);
    }

    private JTextField valueField() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTotal();
            }
        };

        JTextField field = new DoubleTextField();
        field.addKeyListener(keyAdapter);
        return field;
    }


    public void updateSelectedCoin(Asset asset) {
        selectedCoin = asset;
        selectedCoin.setPair(((Objects.requireNonNull(pairComboBox.getSelectedItem())).toString()));
        this.valueInput.setText(selectedCoin.getQuotationValue());
        this.selectCoinButton.setText(selectedCoin.getName());
        updateTotal();
    }

    private void updatePair(Asset pair) {
        currencySymbol = pair.getCurrencySymbol();
        if (selectedCoin != null) {
            selectedCoin.setPair(pair.toString());
            this.valueInput.setText(selectedCoin.getQuotationValue());
        }
        updateTotal();

    }

    private JButton selectCoinButton() {
        JButton button = new JButton("SELECT COIN");
        button.addActionListener(e -> new WindowChooseCoin(this));
        return button;
    }

    private JLabel walletNameLabel() {
        JLabel walletNameLabel = new JLabel(wallet.getName(), JLabel.CENTER);
        walletNameLabel.setFont(new Font("", Font.PLAIN, 16));
        return walletNameLabel;
    }

    private JLabel warningLabel() {
        JLabel warningMessage = new JLabel("PLACEHOLDER", JLabel.CENTER);
        warningMessage.setVisible(false);
        warningMessage.setForeground(Color.red);
        return warningMessage;
    }

    private JButton saveButton(JLabel warningMessage) {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (!buttonPressed) {
                warningMessage.setVisible(true);
                warningMessage.setText("SELECT BUY OR SELL");
            } else if (selectCoinButton.getText().equals("SELECT COIN")) {
                warningMessage.setVisible(true);
                warningMessage.setText("SELECT A COIN");
            } else if (amountInput.getText().equals("")) {
                warningMessage.setVisible(true);
                warningMessage.setText("INSERT THE AMOUNT");
            } else if (valueInput.getText().equals("")) {
                warningMessage.setVisible(true);
                warningMessage.setText("INSERT THE VALUE");
            } else {
                selectedCoin.setValue(Double.parseDouble(valueInput.getText()));
                selectedCoin.setAmount(Double.parseDouble(amountInput.getText()));
                wallet.createTransaction((CryptoAsset) selectedCoin, selectedCoin.getPair(), buy, LocalDateTime.now());
//                test
                this.dispose();
            }
        });

        return saveButton;
    }

    private JPanel screen() {
//        e -> {}
        JPanel screen = new JPanel(new GridBagLayout());

        JLabel walletNameLabel = walletNameLabel();

        JButton[] buySellButtons = buySellButtons();
        JButton buyButton = buySellButtons[0];
        JButton sellButton = buySellButtons[1];

        selectCoinButton = selectCoinButton();
        pairComboBox = pairComboBox(fiatAssets);
        currencySymbol = ((Asset) Objects.requireNonNull(pairComboBox.getSelectedItem())).getCurrencySymbol();
        amountInput = valueField();
        valueInput = valueField();
        totalLabel = new JLabel(currencySymbol + "0.00", JLabel.CENTER);
//        JButton saveButton = new JButton("Save");
        JLabel warningMessage = warningLabel();
        JButton saveButton = saveButton(warningMessage);



        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        gbc.weightx = 1;

        // LINE 0

        addGB(screen, walletNameLabel, 1, 2); // Wallet name
        nextLine();

        // LINE 1

        addGB(screen, buyButton, 1); // Buy button

        addGB(screen, sellButton, 2); // Sell button
        nextLine();





        // LINE 2

        gbc.insets = new Insets(-30, 0, 0, 0);
        addGB(screen, new JLabel("Coin/Pair: ", JLabel.RIGHT), 0); // Coin label


        addGB(screen, selectCoinButton, 1, 2); // Coin combobox

        addGB(screen, pairComboBox, 3); // Pair combobox
        nextLine();


        gbc.insets = new Insets(-40, 0, 0, 0);
        // LINE 3
        addGB(screen, new JLabel("Amount: ", JLabel.RIGHT), 0); // Amount label

        addGB(screen, amountInput, 1); // Amount input
        nextLine();

        gbc.insets = new Insets(-60, 0, 0, 0);
        // LINE 4
        addGB(screen, new JLabel("Value: ", JLabel.RIGHT), 0); // Value label

        addGB(screen, valueInput, 1); // Value input
        nextLine();

        // LINE 5

//        gbc.fill = GridBagConstraints.HORIZONTAL;

        addGB(screen, totalLabel, 1, 2); // Total label
        nextLine();

        // LINE 6
        addGB(screen, saveButton, 1, 2); // Save button
        nextLine();

        gbc.weightx = 0;
        gbc.weighty = 0;

        addGB(screen, warningMessage, 1, 2);

        return screen;
    }

    private void nextLine() {
        this.line++;
    }

    private void addGB(JPanel screen, Component comp, int x, int width) {
        gbc.gridx = x;
        gbc.gridy = this.line;
        gbc.gridwidth = width;
        screen.add(comp, gbc);
        gbc.gridwidth = 1;
    }

    private void addGB(JPanel screen, Component comp, int x) {
        gbc.gridx = x;
        gbc.gridy = this.line;
        screen.add(comp, gbc);
    }

}
