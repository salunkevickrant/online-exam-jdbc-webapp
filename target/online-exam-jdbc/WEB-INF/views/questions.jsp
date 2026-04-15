<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<div class="page-shell">
    <aside class="sidebar">
        <div class="brand-mark">OE</div>
        <h1>Online Exam Admin</h1>
        <p class="sidebar-lead">Refined control center for question authoring and assessment operations.</p>
        <nav>
            <a class="active" href="${pageContext.request.contextPath}/questions">Questions</a>
            <a href="${pageContext.request.contextPath}/responses">Responses</a>
            <a href="${pageContext.request.contextPath}/reports">Reports</a>
        </nav>
        <div class="sidebar-meta">
            <div class="meta-chip">Pooled JDBC</div>
            <div class="meta-chip">Admin Workspace</div>
        </div>
    </aside>

    <main class="content">
        <section class="hero">
            <div class="hero-copy">
                <p class="eyebrow">Question Bank</p>
                <h2>Add exam questions</h2>
                <p>Create polished question records with clear answer keys and a maintainable structure for future reporting.</p>
            </div>
        </section>

        <section class="panel reveal">
            <h3>New Question</h3>
            <form method="post" class="form-grid">
                <label>Subject
                    <input type="text" name="subject" required>
                </label>
                <label>Difficulty
                    <select name="difficulty" required>
                        <option value="Easy">Easy</option>
                        <option value="Medium">Medium</option>
                        <option value="Hard">Hard</option>
                    </select>
                </label>
                <label class="full-width">Question Text
                    <textarea name="questionText" rows="3" required></textarea>
                </label>
                <label>Option A
                    <input type="text" name="optionA" required>
                </label>
                <label>Option B
                    <input type="text" name="optionB" required>
                </label>
                <label>Option C
                    <input type="text" name="optionC" required>
                </label>
                <label>Option D
                    <input type="text" name="optionD" required>
                </label>
                <label>Correct Option
                    <select name="correctOption" required>
                        <option value="A">A</option>
                        <option value="B">B</option>
                        <option value="C">C</option>
                        <option value="D">D</option>
                    </select>
                </label>
                <div class="full-width actions">
                    <button type="submit">Add Question</button>
                </div>
            </form>
        </section>

        <section class="panel reveal">
            <div class="section-head">
                <div>
                    <p class="section-kicker">Library</p>
                    <h3>Available Questions</h3>
                </div>
            </div>
            <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Subject</th>
                    <th>Difficulty</th>
                    <th>Question</th>
                    <th>Correct</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${questions}" var="question">
                    <tr>
                        <td>${question.id}</td>
                        <td>${question.subject}</td>
                        <td>${question.difficulty}</td>
                        <td>${question.questionText}</td>
                        <td>${question.correctOption}</td>
                        <td>
                            <form method="post" onsubmit="return confirm('Delete this question and its responses?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="questionId" value="${question.id}">
                                <button type="submit" class="danger-button">Delete</button>
                            </form>
                        </td>
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
