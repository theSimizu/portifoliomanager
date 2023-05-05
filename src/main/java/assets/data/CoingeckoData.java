package assets.data;

import assets.crypto.CryptoAsset;
import database.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class CoingeckoData extends APIsData {
    private static final File marketDataFile = new File("market_data.json");
    private static JSONArray marketData;
    public static ArrayList<CryptoAsset> marketCoins;

    public static void start() {
        try {
            update();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        marketData = JSONParser.getJSONArrayFileData(marketDataFile);
        marketCoins = new ArrayList<>();
        updateMarketCoinsArrayList();
    }

    public static void updateMarketCoinsArrayList() {
        marketCoins.clear();
        for (int i = 0; i < marketData.length(); i++) {
            JSONObject json = marketData.getJSONObject(i);
            String id = (String) json.get("id");
            String name = (String) json.get("name");
            String symbol = (String) json.get("symbol");
            Object priceObject = json.get("current_price");
            double price = (priceObject instanceof Integer) ? ((Integer) priceObject).doubleValue() : (Double) priceObject;
            marketCoins.add(new CryptoAsset(id, name, symbol, price));
        }
    }

    public static double getCoinCurrentDollarPrice(String coinGeckoID) {
        double price = 0;
        for (int i = 0; i < marketData.length(); i++) {
            JSONObject json = marketData.getJSONObject(i);
            if (!json.get("id").equals(coinGeckoID)) continue;
            Object priceObject = json.get("current_price");
            price = (priceObject instanceof Integer) ? ((Integer) priceObject).doubleValue() : (Double) priceObject;
            break;
        }
        return price;
    }

    public static void updateIcons() throws IOException, URISyntaxException {
        JSONArray coins = JSONParser.getJSONArrayFileData(marketDataFile);
        for(int i = 0; i < coins.length(); i++) {
            JSONObject coin = coins.getJSONObject(i);
            BufferedInputStream in = new BufferedInputStream(new URI(coin.getString("image")).toURL().openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("icons/" + coin.get("id") + ".png");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) fileOutputStream.write(dataBuffer, 0, bytesRead);
            fileOutputStream.close();
        }
    }


    private static void update() throws IOException, URISyntaxException {
        if (timeToUpdate()) {
            updateFile("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd", "GET", marketDataFile);
            System.out.println("kkk");
        }
    }
}
