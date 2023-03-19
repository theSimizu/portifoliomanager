package database;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class CoingeckoData {
    private static HttpsURLConnection connection = null;
    private static final JSONArray marketData = JSONParser.getJSONArrayFileData("market_data.json");

    public CoingeckoData() {

    }

    private static void setConnection(String link, String method) {
        try {
            URL url = new URL(link);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(method);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<CryptoAsset> marketCoins() {
        ArrayList<CryptoAsset> assets = new ArrayList<>();
        for (int i = 0; i < marketData.length(); i++) {
            JSONObject json = marketData.getJSONObject(i);
            String id = (String) json.get("id");
            String name = (String) json.get("name");
            String symbol = (String) json.get("symbol");
            Object priceObject = json.get("current_price");
            double price = (priceObject instanceof Integer) ? ((Integer) priceObject).doubleValue() : (Double) priceObject;
            assets.add(new CryptoAsset(id, name, symbol, price));

        }
        return assets;
    }

    public static void updateMarketData() {
        try {
            setConnection("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd", "GET");
            String content = JSONParser.getJSONContent(new InputStreamReader(connection.getInputStream()));
            connection.disconnect();
            FileWriter fw = new FileWriter("market_data.json");
            fw.write(content);
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateIcons() {
        try {
            JSONArray coins = JSONParser.getJSONArrayFileData("market_data.json");
            for(int i = 0; i < coins.length(); i++) {
                JSONObject coin = coins.getJSONObject(i);
                BufferedInputStream in = new BufferedInputStream(new URL(coin.getString("image")).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream("icons/" + coin.get("id") + ".png");
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }

//                setConnection(coin.getString("image"), "GET");
//                FileOutputStream fos = new FileOutputStream("icons/" + coin.get("id"));
//                URL imageURL = new URL(coin.getString("image"));
//                BufferedInputStream bis = new BufferedInputStream(imageURL.openStream());

            }
        } catch (Exception e) {

        }
    }

    public static JSONArray getMarketData() {
        return marketData;
    }






}
