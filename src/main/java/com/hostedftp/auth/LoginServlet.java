package com.hostedftp.auth;

import com.hostedftp.model.entity.User;
import com.hostedftp.service.UserAuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        // Handle logout
        if (req.getParameter("logout") != null) {
            req.getSession().invalidate();
            resp.sendRedirect("login.jsp");
            return;
        }

        String u = req.getParameter("username");
        String p = req.getParameter("password");

        if (u == null || p == null || u.trim().isEmpty() || p.trim().isEmpty()) {
            req.setAttribute("error", "Username and password are required.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        try {
            boolean ok = service.validatePassword(u, p.toCharArray());
            if (!ok) {
                req.setAttribute("error", "Invalid credentials.");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
            User user = service.findByUsername(u);
            if (user == null) {
                req.setAttribute("error", "User not found.");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect("welcome.jsp");
        } catch (Exception e) {
            req.setAttribute("error", "Server error: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
