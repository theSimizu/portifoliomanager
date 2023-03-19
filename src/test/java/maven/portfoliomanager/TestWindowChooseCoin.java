package maven.portfoliomanager;

import database.CoingeckoData;
import database.CryptoAsset;
import database.WindowChooseCoin;

import java.util.ArrayList;

public class TestWindowChooseCoin {

    private final static ArrayList<CryptoAsset> marketAssets = CoingeckoData.marketCoins();

    public static void main(String[] args) {
        WindowChooseCoin window = new WindowChooseCoin(marketAssets);
    }
}
