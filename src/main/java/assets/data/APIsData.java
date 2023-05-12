package assets.data;

import database.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Scanner;

public class APIsData {
    protected static HttpsURLConnection connection = null;
    private static final File updateTime = new File("resources/update_time.txt");
    protected static boolean timeToUpdate = false;
    private static final long currentTime = currentTime();
    private static final long lastUpdate = lastUpdate();

    private static long currentTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    private static long lastUpdate() {
        Scanner reader;
        try {
            if (updateTime.createNewFile()) {
                FileWriter writer = new FileWriter(updateTime);
                writer.write(Long.toString(currentTime));
                writer.close();
                timeToUpdate = true;
            }
            reader = new Scanner(updateTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String lastTime = reader.nextLine();
        reader.close();
        return Long.parseLong(lastTime);
    }

    protected static void setConnection(String link, String method) throws IOException, URISyntaxException {
        URL url = new URI(link).toURL();
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(method);
    }

    protected static boolean timeToUpdate() throws IOException {
        if (currentTime - lastUpdate > 60000) {
            PrintWriter writer = new PrintWriter(updateTime);
            writer.print("");
            writer.write(Long.toString(currentTime));
            writer.close();
            return true;
        }
        return timeToUpdate;
    }

    protected static void updateFile(String link, String method, File file) throws IOException, URISyntaxException {
        setConnection(link, method);
        String content = JSONParser.getJSONContent(new InputStreamReader(connection.getInputStream()));
        connection.disconnect();
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

//    protected static void updateFile(String link, String method, OutputStream file) throws IOException, URISyntaxException {
//        setConnection(link, method);
//        String content = JSONParser.getJSONContent(new InputStreamReader(connection.getInputStream()));
//        connection.disconnect();
//        BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(file));
//        fw.write(content);
//        fw.close();
//    }
}
