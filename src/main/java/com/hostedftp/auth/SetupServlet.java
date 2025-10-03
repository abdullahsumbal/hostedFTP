package com.hostedftp.auth;

import com.hostedftp.service.UserAuthService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/setup")
public class SetupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        try {
            UserAuthService service = new UserAuthService();
            service.createUser("hostedftp", "money", "Abdullah Sumbal");
            out.println("User created successfully!");
        } catch (Exception e) {
            out.println("Error creating user: " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}
