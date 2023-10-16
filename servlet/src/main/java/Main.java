import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;

public class Main extends HttpServlet {
    private Connection connection = null;
    private Statement statement = null;

    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12653699",
                    "sql12653699", "Maze7MNHhE");
            statement = connection.createStatement();
        }
        catch (SQLException e) { throw new RuntimeException(e); }
        catch (ClassNotFoundException e) { throw new RuntimeException(e); }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String insert_summary_info = "INSERT INTO summary_info VALUES (\'" + req.getParameter("name") + "\'," + "\'" +
                req.getParameter("date") + "\')";

        String insert_processor = "INSERT INTO processor VALUES (" +
                "\'" + req.getParameter("name") + "\'," +
                "\'" + req.getParameter("proc") + "\'," +
                "\'" + req.getParameter("ext_fr") + "\'," +
                "\'" + req.getParameter("max_fr") + "\'," +
                "\'" + req.getParameter("cur_fr") + "\'," +
                "\'" + req.getParameter("L1") + "\'," +
                "\'" + req.getParameter("L2") + "\'," +
                "\'" + req.getParameter("L3") + "\')";
        try {
            statement.executeUpdate(insert_processor);
            statement.executeUpdate(insert_summary_info);
        } catch (SQLException e) { throw new RuntimeException(e); }

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        writer.print(req.getParameter("name"));
        writer.print(req.getParameter("date"));
    }

    @Override
    public void destroy() {
        try { connection.close(); }
        catch (SQLException e) { throw new RuntimeException(e); }
    }
}