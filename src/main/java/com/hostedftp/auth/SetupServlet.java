package com.hostedftp.auth;

import com.hostedftp.service.UserAuthService;
import com.hostedftp.config.DbConnector;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/setup")
public class SetupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        try {
            // Test connection first
            out.println("Testing database connection...");
            Connection conn = DbConnector.getConnection();
            out.println("Database connection successful!");
            conn.close();

            // Create user
            out.println("Creating user...");
            UserAuthService service = new UserAuthService();
            service.createUser("hostedftp", "money", "Abdullah Sumbal");
            out.println("User created successfully!");
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}
