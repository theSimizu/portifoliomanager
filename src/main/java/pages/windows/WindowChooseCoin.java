package pages.windows;

import assets.Asset;
import assets.FiatAsset;
import assets.data.CoingeckoData;
import assets.data.FiatCoinsData;
import wallets.BankWallet;
import wallets.CryptoWallet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WindowChooseCoin {
    private ArrayList<? extends Asset> assets;
    private final WindowNewTransaction page;
    private JFrame frame;
    private JPanel screen;
    private static boolean windowAlreadyOpened = false;

    public WindowChooseCoin(WindowNewTransaction page) {
        this.page = page;
        if (windowAlreadyOpened) return;
        else windowAlreadyOpened = true;
        screen = screen();
        frame = frame();
        for (Component comp: screen.getComponents()) { comp.setPreferredSize(comp.getPreferredSize()); }
    }

    private JFrame frame() {
        JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowAlreadyOpened = false;
            }
        });
        frame.add(screen);
        frame.setBounds(new Rectangle(400, 400));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    private JPanel coinIcon(Asset asset) {
        JPanel coinIconPanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon("icons/" + asset.getId() + ".png");
        Image newImg = imageIcon.getImage().getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        coinIconPanel.add(new JLabel(new ImageIcon(newImg)));
        coinIconPanel.setPreferredSize(new Dimension(75, 0));
        coinIconPanel.setBackground(Color.red);
        return coinIconPanel;
    }

    private JPanel coinNameArea(Asset asset) {
        JPanel coinNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15)); coinNamePanel.setBackground(Color.green);
        JLabel name = new JLabel(asset.getName() + " (" + asset.getSymbol().toUpperCase() + ")"); name.setFont(new Font("", Font.BOLD, 13));
        coinNamePanel.add(name);
        return coinNamePanel;
    }

    private JPanel coinBoxPanel(Asset asset) {
        JPanel coinBoxPanel = new JPanel(new BorderLayout()); coinBoxPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        coinBoxPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                page.updateSelectedCoin(asset);
                windowAlreadyOpened = false;
                frame.dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e)
            { for (Component comp : coinBoxPanel.getComponents()) comp.setBackground(Color.black); }

            @Override
            public void mouseExited(MouseEvent e) {
                coinBoxPanel.getComponent(0).setBackground(Color.red);
                coinBoxPanel.getComponent(1).setBackground(Color.green);
            }
        });

        JPanel coinNamePanel = coinNameArea(asset);
        JPanel coinIconPanel = coinIcon(asset);

        coinBoxPanel.add(coinIconPanel, BorderLayout.WEST);
        coinBoxPanel.add(coinNamePanel, BorderLayout.CENTER);
        coinBoxPanel.setPreferredSize(new Dimension(0, 50));

//        coinBoxPanel
        return coinBoxPanel;
    }

    private JPanel coinsBoxesPanel() {
        if (this.page.getWallet() instanceof CryptoWallet) assets = CoingeckoData.marketCoins;
        else if (this.page.getWallet() instanceof BankWallet) assets = FiatCoinsData.getFiats();
        JPanel coinsBoxesPanel = new JPanel(); coinsBoxesPanel.setLayout(new BoxLayout(coinsBoxesPanel, BoxLayout.Y_AXIS));
        JPanel rigidArea = new JPanel(); rigidArea.setPreferredSize(new Dimension(0, 300));
        for (Asset asset : assets) coinsBoxesPanel.add(coinBoxPanel(asset));
        coinsBoxesPanel.add(rigidArea);
        return coinsBoxesPanel;
    }

    private JScrollPane panelToScrollPane(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().isVisible();
        return scroll;
    }

    private DocumentListener textFieldFilter(JPanel coinsPanel, JTextField textField) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void filter() {
                String filterString = textField.getText().toLowerCase();

                for (int i = 0; i < coinsPanel.getComponentCount() - 1; i++) {
                    JPanel coinPanel = (JPanel) coinsPanel.getComponent(i);
                    JPanel iconPanel = (JPanel) coinPanel.getComponent(1);
                    JLabel coinName = (JLabel) iconPanel.getComponent(0);
                    String coinNameText = coinName.getText().toLowerCase();
                    coinPanel.setVisible(coinNameText.contains(filterString));
                }
            }
        };
    }

    private JPanel textFieldPanel(JPanel coinsPanel) {
        JTextField textField = new JTextField();
        DocumentListener textFieldFilter = textFieldFilter(coinsPanel, textField);
        textField.getDocument().addDocumentListener(textFieldFilter);
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        textFieldPanel.add(textField);
        textField.setPreferredSize(new Dimension(250, 20));
        textFieldPanel.setPreferredSize(new Dimension(0, 65));

        return textFieldPanel;
    }


    private JPanel screen() {
        JPanel screen = new JPanel(new BorderLayout());
        JPanel coinsPanel = coinsBoxesPanel();
        JScrollPane coinsScrollPane = panelToScrollPane(coinsPanel);
        JPanel textFieldPanel = textFieldPanel(coinsPanel);
        screen.add(textFieldPanel, BorderLayout.NORTH);
        screen.add(coinsScrollPane, BorderLayout.CENTER);

        return screen;
    }



}
