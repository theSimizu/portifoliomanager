package database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiatAsset extends Asset {
    private double quotation;

    public FiatAsset(String symbol) {
        this.symbol = symbol;
        this.setCoinData();
        this.setCoinFromUSDQuotation();
    }

    private void setCoinData() {
        try {
            JSONObject fiatsCodesInfo = (JSONObject) JSONParser.getJSONFileData("fiat_codes.json").get("fiats_info");
            JSONObject fiatCodeInfo = (JSONObject) fiatsCodesInfo.get(this.symbol);
            this.name = (String) fiatCodeInfo.get("name");
            this.currencySymbol = (String) fiatCodeInfo.get("code");
        } catch (JSONException ignored) {}
    }

    private void setCoinFromUSDQuotation() {
        if (this.symbol.equals("USD")) { this.quotation = 1; return;}
        JSONObject fiatsCodesInfo = JSONParser.getJSONFileData("fiats.json");
        JSONObject quotationData = (JSONObject) fiatsCodesInfo.get("USD" + this.symbol);
        this.quotation = Double.parseDouble((String) quotationData.get("bid"));
    }

    public static ArrayList<FiatAsset> getFiatList() {
        ArrayList<FiatAsset> fiatList = new ArrayList<>();
        JSONObject fiatSymbols = JSONParser.getJSONFileData("fiat_codes.json");
        JSONArray symbols = (JSONArray)fiatSymbols.get("fiat_codes");
        for (int i = 0; i < symbols.length(); i++) {
            FiatAsset asset;
            if ((asset = new FiatAsset((String) symbols.get(i))).getName() != null) fiatList.add(asset);

        }
        return fiatList;
    }

    public double getQuotation() {
        return this.quotation;
    }

    @Override
    public String toString() {
        return this.getSymbol();
    }
}
