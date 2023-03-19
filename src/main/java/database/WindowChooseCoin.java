package database;

import pages.PageNewTransaction;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WindowChooseCoin {
    ArrayList<? extends Asset> assets = CoingeckoData.marketCoins();
//    private final JPanel screen;
    private JPanel exampleCoin = null;
    private Asset selctedCoin = null;
    private PageNewTransaction page;
    private JFrame frame;
//    private


    public WindowChooseCoin(PageNewTransaction page) {
//        this.assets = ;
        this.page = page;
        this.frame = new JFrame();
        JPanel screen = screen();
        for (Component comp: screen.getComponents()) { comp.setPreferredSize(comp.getPreferredSize()); }

        frame.add(screen);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(new Rectangle(400, 400));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


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
        JLabel name = new JLabel(asset.name + " (" + asset.symbol.toUpperCase() + ")"); name.setFont(new Font("", Font.BOLD, 13));
        coinNamePanel.add(name);
        return coinNamePanel;
    }



    private JPanel coinBoxPanel(Asset asset) {
        JPanel coinBoxPanel = new JPanel(new BorderLayout()); coinBoxPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        exampleCoin = coinBoxPanel;
        coinBoxPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                page.updateSelectedCoin(asset);
                frame.dispose();
//                selctedCoin = asset;
//                System.out.println();

            }
            @Override
            public void mouseEntered(MouseEvent e)
            { for (Component comp : coinBoxPanel.getComponents()) comp.setBackground(Color.black); }

            @Override
            public void mouseExited(MouseEvent e)
            { for (int i = 0; i < exampleCoin.getComponentCount(); i++) coinBoxPanel.getComponent(i).setBackground(exampleCoin.getComponent(i).getBackground()); }
        });

        JPanel coinNamePanel = coinNameArea(asset);
        JPanel coinIconPanel = coinIcon(asset);




        coinBoxPanel.add(coinIconPanel, BorderLayout.WEST);
        coinBoxPanel.add(coinNamePanel, BorderLayout.CENTER);
        coinBoxPanel.setPreferredSize(new Dimension(0, 50));

//        coinBoxPanel
        return coinBoxPanel;
    }


//    private JPanel coinBoxPanel(Asset asset) {
////        JPanel coinBoxPanel = new JPanel(new BorderLayout()); coinBoxPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
//
//
//        JPanel coinBoxPanel = new JPanel(new BorderLayout()); coinBoxPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
//
//        JPanel coinNamePanel = coinNameArea(asset);
//        JPanel coinIconPanel = coinIcon(asset);
//
//        coinBoxPanel.add(coinIconPanel, BorderLayout.WEST);
//        coinBoxPanel.add(coinNamePanel, BorderLayout.CENTER);
//        coinBoxPanel.setPreferredSize(new Dimension(0, 50));
//
////        coinBoxPanel
//        return coinBoxPanel;
//    }



    private JPanel coinsBoxesPanel() {
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
