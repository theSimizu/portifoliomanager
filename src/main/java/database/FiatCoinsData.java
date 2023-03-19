package database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiatCoinsData {
    private String symbol;
    private String name;
    private String currencySymbol;
    private double quotation;

//    public FiatCoinsData(String symbol) {
//        this.symbol = symbol;
//        setCoinData();
//        setCoinFromUSDQuotation();
//    }

//    private void setCoinData() {
//        try {
//            JSONObject fiatsCodesInfo = (JSONObject) JSONParser.getJSONFileData("fiat_codes.json").get("fiats_info");
//            JSONObject fiatCodeInfo = (JSONObject) fiatsCodesInfo.get(this.symbol);
//            this.name = (String) fiatCodeInfo.get("name");
//            this.currencySymbol = (String) fiatCodeInfo.get("code");
//        } catch (JSONException ignored) {}
//    }
//
//    private void setCoinFromUSDQuotation() {
//        try {
//            JSONObject fiatsCodesInfo = JSONParser.getJSONFileData("fiats.json");
//            JSONObject quotationData = (JSONObject) fiatsCodesInfo.get("USD" + this.symbol);
//            this.quotation = Double.parseDouble((String) quotationData.get("bid"));
//        } catch (JSONException ignored) {}
//    }
//
//    public FiatAsset getCoin() {
//        return new FiatAsset(this.name, this.symbol, this.quotation);
//    }
//
//
//
//
//
//    public static ArrayList<FiatAsset> getFiatList() {
//        ArrayList<FiatAsset> fiatList = new ArrayList<>();
//        JSONObject fiatSymbols = JSONParser.getJSONFileData("fiat_codes.json");
//        JSONArray symbols = (JSONArray)fiatSymbols.get("fiat_codes");
//        for (int i = 0; i < symbols.length(); i++) {
//            FiatAsset asset;
//            if ((asset = new FiatCoinsData((String) symbols.get(i)).getCoin()).getName() != null) {
//                fiatList.add(asset);
//            }
//
//        }
//
//        return fiatList;
//    }

}
