package assets;

import assets.data.FiatCoinsData;
import database.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiatAsset extends Asset {
    private double quotation;

    public FiatAsset(String symbol) {
        this.symbol = symbol;
        FiatCoinsData.setCoinData(this);
        FiatCoinsData.setCoinFromUSDQuotation(this);
    }

    public FiatAsset(String coinName, String coinSymbol, double amount, double value) {
        super(coinName, coinSymbol, amount, value);
    }

    public void setQuotation(double quotation) { this.quotation = quotation; }

    public double getQuotation() {
        return this.quotation;
    }

    @Override
    public String toString() {
        return this.getSymbol();
    }
}
