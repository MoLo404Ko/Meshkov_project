package application;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.List;

public class ConnectURL {
    private final String url_adres = "http://127.0.0.1:8080/HelloWorld/hello";
    public void connectURL(List text) throws IOException {
        URL url = new URL(url_adres);
        postRequest(text, url);
    }

    private void postRequest(List text, URL url) throws IOException {
        String raw_data = "name=" + text.get(0) + "&date=" + text.get(1) + "&proc=" + text.get(2) + "&ext_fr=" + text.get(3)
                + "&max_fr=" + text.get(4) + "&cur_fr=" + text.get(5) + "&L1=" + text.get(7) + "&L2=" + text.get(8)
                + "&L3=" + text.get(9);

        String type = "application/x-www-form-urlencoded";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty( "Content-Type", type );

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(raw_data.getBytes());

        File file = new File("C:\\Users\\Public\\Everest\\RESPONSES\\report.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Я зашел");

            for (Object o : text) fileOutputStream.write(o.toString().getBytes());

            Runtime.getRuntime().exec("notepad C:\\Users\\Public\\Everest\\RESPONSES\\report.txt");
        }

        else {
            System.out.println("Я не зашел");
        }

        fileOutputStream.close();
        outputStream.close();

//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));

//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
//                response.append(inputLine);
//            }
//            in.close();
        }
    }
