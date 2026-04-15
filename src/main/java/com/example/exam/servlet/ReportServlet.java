package com.example.exam.servlet;

import com.example.exam.dao.ReportDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reports")
public class ReportServlet extends BaseServlet {
    private final ReportDao reportDao = new ReportDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("summary", reportDao.getSummary());
        request.setAttribute("performances", reportDao.getStudentPerformance());
        render(request, response, "/WEB-INF/views/reports.jsp");
    }
}
