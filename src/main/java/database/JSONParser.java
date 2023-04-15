package database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class JSONParser {

    public static String getJSONContent(Reader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            content.append(inputLine);
        }
        bufferedReader.close();
        return content.toString();
    }

    public static JSONArray getJSONArrayFileData(String fileName) {
        JSONArray jsonArray;
        try {
            String content = getJSONContent(new FileReader(fileName));
            jsonArray = new JSONArray(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }

    public static JSONArray getJSONArrayFileData(File file) {
        JSONArray jsonArray;
        try {
            String content = getJSONContent(new FileReader(file));
            jsonArray = new JSONArray(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }

    public static JSONObject getJSONFileData(String fileName) {
        JSONObject json;
        try {
            String content = getJSONContent(new FileReader(fileName));
            json = new JSONObject(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

}
