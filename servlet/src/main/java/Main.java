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
            connection = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6637822",
                    "sql6637822", "XgbwmMMdk7");
            statement = connection.createStatement();
        }
        catch (SQLException e) { throw new RuntimeException(e); }
        catch (ClassNotFoundException e) { throw new RuntimeException(e); }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "INSERT INTO test VALUES (\'" + req.getParameter("data") + "\')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        writer.print(req.getParameter("data"));
        writer.println(req.getHeaderNames().toString());
    }

    @Override
    public void destroy() {
        try { connection.close(); }
        catch (SQLException e) { throw new RuntimeException(e); }
    }
}