package com.example.exam.servlet;

import com.example.exam.dao.QuestionDao;
import com.example.exam.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questions")
public class QuestionServlet extends BaseServlet {
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("questions", questionDao.findAll());
        render(request, response, "/WEB-INF/views/questions.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            long questionId = Long.parseLong(request.getParameter("questionId"));
            questionDao.deleteQuestion(questionId);
            redirect(response, request.getContextPath() + "/questions");
            return;
        }

        Question question = new Question(
                null,
                request.getParameter("subject"),
                request.getParameter("difficulty"),
                request.getParameter("questionText"),
                request.getParameter("optionA"),
                request.getParameter("optionB"),
                request.getParameter("optionC"),
                request.getParameter("optionD"),
                request.getParameter("correctOption"),
                null
        );
        questionDao.addQuestion(question);
        redirect(response, request.getContextPath() + "/questions");
    }
}
