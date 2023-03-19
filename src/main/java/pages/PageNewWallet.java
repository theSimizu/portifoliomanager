package pages;

import database.Wallet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PageNewWallet extends JFrame {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private static boolean windowOpened = false;
    private final Page mainPage;
    public PageNewWallet(Page mainPage) {
        this.mainPage = mainPage;
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowOpened = false;
            }
        });

        int width = 500;
        int height = 150;
        setBounds(new Rectangle(width, height));
        setLocationRelativeTo(null);
        setVisible(true);
        add(screen());
    }

    private void save(JTextField walletName) {
        Wallet.createWallet(walletName.getText());
        mainPage.update();
        dispose();
    }
    private JPanel screen() {
        JPanel screen = new JPanel();
        JTextField walletName = new JTextField(16);
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> save(walletName));
        screen.setLayout(new GridBagLayout());
        addGB(screen, new JLabel("Wallet name: "), 0, 0);
        addGB(screen, walletName, 1, 0);
        addGB(screen, saveButton, 0, 1, 3, 1);
        return screen;
    }

    private void addGB(JPanel screen, Component comp, int x, int y, int width, int height) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        screen.add(comp, gbc);
    }

    private void addGB(JPanel screen, Component comp, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        screen.add(comp, gbc);
    }

}
