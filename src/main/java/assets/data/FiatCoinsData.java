package assets.data;

import assets.FiatAsset;
import database.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class FiatCoinsData extends APIsData {
    private static final File fiatDataFile = new File("fiats.json");
    private static final File fiatCodesFile = new File("fiat_codes.json");
    private static JSONObject fiatData;
    private static final JSONArray fiatSymbols = fiatCodes();
    private static ArrayList<FiatAsset> fiats = new ArrayList<>();// = fiats();

    public static void start() {
        try {
//            fiats = new ArrayList<>();
            updateFiatsDataFile();
            fiatData = JSONParser.getJSONFileData(fiatDataFile);
            updateFiatsArrayList();

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setCoinData(FiatAsset asset) {
        try {
            JSONObject fiatsCodesInfo = (JSONObject) JSONParser.getJSONFileData(fiatCodesFile).get("fiats_info");
            JSONObject fiatCodeInfo = (JSONObject) fiatsCodesInfo.get(asset.getSymbol());
            asset.setName((String) fiatCodeInfo.get("name"));
            asset.setCurrencySymbol((String) fiatCodeInfo.get("code"));
        } catch (JSONException ignored) {}
    }

    public static void setCoinFromUSDQuotation(FiatAsset asset) {
        if (asset.getSymbol().equals("USD")) { asset.setQuotation(1); return;}
        JSONObject quotationData = (JSONObject) fiatData.get("USD" + asset.getSymbol());
        asset.setQuotation(Double.parseDouble((String) quotationData.get("bid")));
    }

    public static void updateFiatsArrayList() throws IOException, URISyntaxException {
        fiats.clear();
        for (int i = 0; i < fiatSymbols.length(); i++) {
            FiatAsset asset;
            if ((asset = new FiatAsset((String) fiatSymbols.get(i))).getName() != null) fiats.add(asset);
        }
    }

    private static void updateFiatsDataFile() throws IOException, URISyntaxException {
        if (!timeToUpdate()) return;
        System.out.println("kkkkkk");
        ArrayList<String> pairCodes = new ArrayList<>();
        for (int i = 0; i < fiatSymbols.length(); i++) {
            String cur = ((String)fiatSymbols.get(i)).toLowerCase();
            if (cur.equals("usd")) continue;
            pairCodes.add("usd-" + cur);
        }
        String codes = String.join(",", pairCodes);
        updateFile("https://economia.awesomeapi.com.br/json/last/" + codes, "GET", fiatDataFile);
    }


    private static JSONArray fiatCodes() {
        JSONObject fiatSymbols = JSONParser.getJSONFileData(fiatCodesFile);
        return (JSONArray)fiatSymbols.get("fiat_codes");
    }

    public static ArrayList<FiatAsset> getFiats() {
        return fiats;
    }



    public static double getCoinCurrentDollarPrice(String symbol) {
        if (symbol.equals("USD")) return 1;
        JSONObject quotationData = (JSONObject) fiatData.get("USD" + symbol);
        return 1 / quotationData.getDouble("bid");
    }
}