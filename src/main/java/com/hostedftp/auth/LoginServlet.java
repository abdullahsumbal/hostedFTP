package com.hostedftp.auth;

import com.hostedftp.model.entity.User;
import com.hostedftp.service.UserAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    private final UserAuthService service = new UserAuthService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect("login.jsp");
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        if (u == null || p == null || u.isBlank() || p.isBlank()) {
            req.setAttribute("error", "Username and password are required.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        try {
            boolean ok = service.validate(u, p.toCharArray());
            if (!ok) {
                req.setAttribute("error", "Invalid credentials.");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
            User user = service.findUser(u);
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect("welcome.jsp");
        } catch (Exception e) {
            req.setAttribute("error", "Server error: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
