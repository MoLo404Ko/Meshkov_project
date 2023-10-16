package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public Main() throws SQLException, ClassNotFoundException {}


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        Report.create_report();
    }
}