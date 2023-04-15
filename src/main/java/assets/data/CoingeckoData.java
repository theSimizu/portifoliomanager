package assets.data;

import assets.crypto.CryptoAsset;
import database.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class CoingeckoData {
    private HttpsURLConnection connection = null;
    private final File marketDataFile = new File("market_data.json");
    private JSONArray marketData;
    public ArrayList<CryptoAsset> marketCoins;
    public static CoingeckoData coinGecko = new CoingeckoData();

    private CoingeckoData() {
        try {
            this.update();
        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
        this.marketData = JSONParser.getJSONArrayFileData(marketDataFile);
        this.marketCoins = marketCoins();
    }

    private void setConnection(String link, String method) throws IOException {
        URL url = new URL(link);
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(method);
    }

    private ArrayList<CryptoAsset> marketCoins() {
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

    public double getCoinCurrentDollarPrice(String coinGeckoID) {
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

    public void updateMarketData() throws IOException {
        setConnection("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd", "GET");
        String content = JSONParser.getJSONContent(new InputStreamReader(connection.getInputStream()));
        connection.disconnect();
        FileWriter fw = new FileWriter(marketDataFile);
        fw.write(content);
        fw.close();
    }

    public void updateIcons() throws IOException {
        JSONArray coins = JSONParser.getJSONArrayFileData("market_data.json");
        for(int i = 0; i < coins.length(); i++) {
            JSONObject coin = coins.getJSONObject(i);
            BufferedInputStream in = new BufferedInputStream(new URL(coin.getString("image")).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("icons/" + coin.get("id") + ".png");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) fileOutputStream.write(dataBuffer, 0, bytesRead);
            fileOutputStream.close();
        }

    }

    public JSONArray getMarketData() {
        return marketData;
    }


    private void update() throws IOException {
        File updateTime = new File("update_time.txt");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long currentTime = timestamp.getTime();
        if (updateTime.createNewFile()) {
            FileWriter writer = new FileWriter(updateTime);
            writer.write(Long.toString(currentTime));
            writer.close();
            return;
        }
        Scanner reader = new Scanner(updateTime);
        String lastTime = reader.nextLine();
        reader.close();
        if (currentTime - Long.parseLong(lastTime) > 600) {
            PrintWriter writer = new PrintWriter(updateTime);
            writer.print("");
            writer.write(Long.toString(currentTime));
            writer.close();
//            updateIcons();
            updateMarketData();
        }



    }




}
