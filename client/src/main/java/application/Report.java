package application;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Report {

    public Report() throws SQLException, ClassNotFoundException {}

    // ------------------------------------------------ CREATE REPORT --------------------------------------------------
    /**
     * The method create a report for to be sent to the DB
     * 1. Launch Everest to create a report
     * 2. Get computer name
     * 3. Prepare info to sent database
     * 4. Convert String to List
     * 5. Sent info
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void create_report() throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        // ----- 1
//        String command = "cmd /c " + Constants.EVEREST_PATH + " EVEREST /R /HW /ANGEN /TEXT";
//        Runtime runtime = Runtime.getRuntime();
//        runtime.exec(command).waitFor();

        // ----- 2
        String computerName = getComputerName();
        // ----- 3
        String text = prepareInfoToDB(computerName);

        // ----- 4
        List<String> result_text = new ArrayList<>();

        try {
            String line;
            BufferedReader bfr = new BufferedReader(new StringReader(text));
            line = bfr.readLine();

            while (line != null) {
                result_text.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {}

        // ------ 5
        ConnectURL connectURL = new ConnectURL();
        connectURL.connectURL(result_text);
    }
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The method gets the PC name
     * @return computer name
     */
    private static String getComputerName() {
        String[] commands = {"cmd.exe", "/c", "echo %computername%"};
        String line;
        String computerName = "";

        try {
            Process process = Runtime.getRuntime().exec(commands);
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) computerName = line;
        }
        catch (Exception e) { e.printStackTrace(); }

        return computerName;
    }

    /**
     * The method prepares data for to be sent to the database
     * KeyWords:
     * Summary_info table #1
     * Processor table #2
     * @param computerName
     * @return data to db
     * @throws IOException
     */
    private static String prepareInfoToDB(String computerName) throws IOException {
        long start_time = System.currentTimeMillis();
        ArrayList<String> key_words = new ArrayList<>();

        ArrayList<String> information = new ArrayList();
        information.add(computerName);

        // KeyWords summary_info table
        key_words.add("Дата"); // #1
        key_words.add("Процессоры /"); // #1
        key_words.add("Внешняя частота"); // #2
        key_words.add("Максимальная частота"); // #2
        key_words.add("Текущая частота"); // #2
        key_words.add("Свойства батареи"); // #1
        key_words.add("Кэш L1 кода"); // #2
        key_words.add("Кэш L2"); // #2
        key_words.add("Кэш L3"); // #2
        key_words.add("Свойства видеоадаптера"); // #1

        File file = new File(Constants.REPORT_PATH + computerName + ".txt");
        String line;
        String text = computerName + "&";
        int kernels = 0;

        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (new FileInputStream(file), "cp1251"));

            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < key_words.size(); i++) {
                    if (line.contains(key_words.get(i))) {
                        switch (key_words.get(i)) {
                            case "Свойства видеоадаптера", "Свойства батареи": {
                                line = reader.readLine();
                                information.add(line.replaceAll(key_words.get(i), "").trim() + "&");
                                key_words.remove(i);
                                break;
                            }

                            default: {
                                information.add(line.replaceAll(key_words.get(i), "").trim() + "&");
                                key_words.remove(i);
                                break;
                            }
                        }
                    }
                }
            }

            for (String s: information) System.out.println(s);
//
//            // Summary_info
            text = text.replace("Дата",  "");
            text = text.replace("Процессоры / ", "");
            text = text.replace("Имя устройства", "");
            text = text.replace("Описание устройства", "");

            // Processor
            text = text.replaceAll("Внешняя частота", "");
            text = text.replaceAll("Максимальная частота", "");
            text = text.replaceAll("Текущая частота", "");
            text = text.replaceAll("Кэш L1 кода", "");
            text = text.replaceAll("Кэш L2", "");
            text = text.replaceAll("Кэш L3", "");

            text = text.replaceAll("\\s+", " ");
            text = text.replaceAll("\\[", "");
            text = text.replaceAll("\\]", "");
            text = text.replaceAll("&", "\n");
            text = text.replaceAll("/", " ");
            text = text.replaceAll("МГц", "");
            text = text.replaceAll("Мб", "");
            text = text.replaceAll("Кб", "");

//            System.out.println(text);
            reader.close();

            System.out.println(System.currentTimeMillis() - start_time);
        }
        else Runtime.getRuntime().exec("notepad C:\\Users\\Public\\Project\\Errors\\FileNotFound");

        return text;
    }
}
