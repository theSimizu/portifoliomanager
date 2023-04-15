package pages;

import assets.Wallet;
import assets.crypto.CryptoWallet;
import pages.components.WalletBoxPanel;
import pages.windows.WindowNewWallet;

import java.awt.*;

public class PageCrypto extends Page{

	private static final long serialVersionUID = 1L;

	public PageCrypto() {
		super(CryptoWallet.getWallets());
		setBackground(new Color(0x01afc7));
	}

	@Override
	public void newWalletUpdate() {
		portfoliosPanel.removeAll();
		wallets = CryptoWallet.getWallets();
		for (Wallet wallet : wallets) { portfoliosPanel.add(new WalletBoxPanel(wallet)); }
		portfoliosPanel.add(newWalletButton);
		portfoliosPanel.add(rigidArea);
		portfoliosPanel.revalidate();
		portfoliosPanel.repaint();
	}

	@Override
	protected void setNewWalletButton() {
		super.setNewWalletButton(e -> new WindowNewWallet(this));
	}

}
