<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Responses</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<div class="page-shell">
    <aside class="sidebar">
        <div class="brand-mark">OE</div>
        <h1>Online Exam Admin</h1>
        <p class="sidebar-lead">Operational workspace for capturing student submissions accurately.</p>
        <nav>
            <a href="${pageContext.request.contextPath}/questions">Questions</a>
            <a class="active" href="${pageContext.request.contextPath}/responses">Responses</a>
            <a href="${pageContext.request.contextPath}/reports">Reports</a>
        </nav>
        <div class="sidebar-meta">
            <div class="meta-chip">Live Responses</div>
            <div class="meta-chip">SQL Scoring</div>
        </div>
    </aside>

    <main class="content">
        <section class="hero">
            <div class="hero-copy">
                <p class="eyebrow">Exam Attempts</p>
                <h2>Record student responses</h2>
                <p>Every submission is recorded with an auditable answer trail and a correctness flag ready for reporting.</p>
            </div>
        </section>

        <section class="panel reveal">
            <h3>Record Response</h3>
            <form method="post" class="form-grid">
                <label>Student Name
                    <input type="text" name="studentName" required>
                </label>
                <label>Question
                    <select name="questionId" required>
                        <c:forEach items="${questions}" var="question">
                            <option value="${question.id}">${question.subject} - ${question.questionText}</option>
                        </c:forEach>
                    </select>
                </label>
                <label>Selected Option
                    <select name="selectedOption" required>
                        <option value="A">A</option>
                        <option value="B">B</option>
                        <option value="C">C</option>
                        <option value="D">D</option>
                    </select>
                </label>
                <div class="full-width actions">
                    <button type="submit">Submit Response</button>
                </div>
            </form>
        </section>

        <section class="panel reveal">
            <div class="section-head">
                <div>
                    <p class="section-kicker">Activity</p>
                    <h3>Recent Responses</h3>
                </div>
            </div>
            <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Question</th>
                    <th>Selected</th>
                    <th>Correct</th>
                    <th>Submitted</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${responses}" var="responseRow">
                    <tr>
                        <td>${responseRow.studentName}</td>
                        <td>${responseRow.questionText}</td>
                        <td>${responseRow.selectedOption}</td>
                        <td>
                            <span class="${responseRow.correct ? 'status-ok' : 'status-bad'}">
                                ${responseRow.correct ? 'Yes' : 'No'}
                            </span>
                        </td>
                        <td>${responseRow.submittedAt}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>
        </section>
    </main>
</div>
</body>
</html>
