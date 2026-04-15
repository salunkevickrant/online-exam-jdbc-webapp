package com.example.exam.servlet;

import com.example.exam.dao.QuestionDao;
import com.example.exam.dao.ResponseDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/responses")
public class ResponseServlet extends BaseServlet {
    private final ResponseDao responseDao = new ResponseDao();
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("questions", questionDao.findAll());
        request.setAttribute("responses", responseDao.findRecentResponses());
        render(request, response, "/WEB-INF/views/responses.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String studentName = request.getParameter("studentName");
        long questionId = Long.parseLong(request.getParameter("questionId"));
        String selectedOption = request.getParameter("selectedOption");
        responseDao.recordResponse(studentName, questionId, selectedOption);
        redirect(response, request.getContextPath() + "/responses");
    }
}
