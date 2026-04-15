<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exam Reports</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<div class="page-shell">
    <aside class="sidebar">
        <div class="brand-mark">OE</div>
        <h1>Online Exam Admin</h1>
        <p class="sidebar-lead">Elegant reporting surface for exam activity and student performance.</p>
        <nav>
            <a href="${pageContext.request.contextPath}/questions">Questions</a>
            <a href="${pageContext.request.contextPath}/responses">Responses</a>
            <a class="active" href="${pageContext.request.contextPath}/reports">Reports</a>
        </nav>
        <div class="sidebar-meta">
            <div class="meta-chip">Executive View</div>
            <div class="meta-chip">Performance Metrics</div>
        </div>
    </aside>

    <main class="content">
        <section class="hero">
            <div class="hero-copy">
                <p class="eyebrow">Reports</p>
                <h2>Performance dashboard</h2>
                <p>Summaries reveal participation, performance quality, and top achievers in a presentation-ready layout.</p>
            </div>
        </section>

        <section class="stats-grid reveal">
            <article class="stat-card accent-card">
                <span>Total Questions</span>
                <strong>${summary.totalQuestions}</strong>
            </article>
            <article class="stat-card accent-card">
                <span>Total Responses</span>
                <strong>${summary.totalResponses}</strong>
            </article>
            <article class="stat-card accent-card">
                <span>Average Score</span>
                <strong>${summary.averageScorePercent}%</strong>
            </article>
            <article class="stat-card accent-card">
                <span>Top Performer</span>
                <strong>${summary.topPerformer} (${summary.topScore} correct)</strong>
            </article>
        </section>

        <section class="panel reveal">
            <div class="section-head">
                <div>
                    <p class="section-kicker">Leaderboard</p>
                    <h3>Student Performance</h3>
                </div>
            </div>
            <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Answered</th>
                    <th>Correct</th>
                    <th>Score %</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${performances}" var="performance">
                    <tr>
                        <td>${performance.studentName}</td>
                        <td>${performance.answeredQuestions}</td>
                        <td>${performance.correctAnswers}</td>
                        <td>${performance.scorePercent}</td>
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
