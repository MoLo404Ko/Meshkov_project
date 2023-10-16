package application;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectURL {
    private final String url_adres = "http://127.0.0.1:8080/HelloWorld/hello";
    public void connectURL(List text) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        URL url = new URL(url_adres);
        postRequest(text, url);
    }

    private void postRequest(List text, URL url) throws IOException {
        String raw_data = "name=" + text.get(0) + "&date=";

        String type = "application/x-www-form-urlencoded";


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty( "Content-Type", type );

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(raw_data.getBytes());

        outputStream.flush();
        outputStream.close();

        FileWriter fileWriter = new FileWriter("report.txt");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Я зашел");
            System.out.println(text);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) response.append(inputLine);

            in.close();

            fileWriter.write("sdsds");
        }

        else {
            System.out.println("Я не зашел");

            fileWriter.write("Соединение с сервером не было установлено");
        }
    }
}
// процессоры
// Вн частота, макс, текущая
// Кэш L1 кода, кэш L2...

// Устройства памяти / (количество)
