package maven.portfoliomanager;

import database.CoingeckoData;
import database.FiatCoinsData;
import org.json.JSONArray;

public class TestCoingecko {
    public static void main(String[] args) {
        CoingeckoData coingecko = new CoingeckoData();
        CoingeckoData.updateIcons();
//        FiatCoinsData.getFiatList();
//        FiatCoinsData.setCoinData();
//        coingecko.saveMarketData();
//        JSONArray data = coingecko.getMarketData();
//        coingecko.marketCoins(data);
    }
}
