# WM2 – Lab8 (University Management System)

## Overview

In this lab, you will continue extending the university system project developed during the previous weeks.

Unlike previous labs, **no template repository will be provided**.

You must:
1. Clone the empty GitHub Classroom repository assigned to you.
2. Take the existing project from the following repository:

https://github.com/nsadili/wm2_spring_2026/tree/main/university-system

3. Copy/import the project into your own local repository.
4. Make an initial commit.
5. Implement the required features incrementally.

---

# Important Rules

## Video Recording Requirement

You must provide a **full uncut screen recording** of your development process.

The recording must include:
- project setup
- coding
- debugging
- searching the web
- reading documentation
- usage of AI/LLM tools
- and testing the application

The purpose is to observe:
- your problem-solving process
- how you approach software engineering tasks
- and how you use external resources responsibly

The recording should clearly show:
- your screen
- terminal usage
- IDE usage
- browser usage
- commits
- and application execution

Submission without a full recording may result in major grade reduction.

---

# AI Usage Policy

AI tools (ChatGPT, Copilot, Claude, Gemini, etc.) are allowed.

However:
- You are fully responsible for understanding all submitted code.
- You must be able to explain your implementation decisions.
- Blind copy-pasting is strongly discouraged.
- The submitted recording must clearly demonstrate your own development process.

If you use AI tools:
- use them as assistants
- not as replacement developers

Academic dishonesty rules still apply.

---

# Git Commit Requirements

You must have at least:
- 1 initial commit
- +4 feature commits

Minimum total:
- 5 commits

Expected structure (you can decide the order):

1. Initial project import commit
2. Enrollment date feature
3. Prerequisite validation feature
4. Course retrieval by student name feature
5. Swagger Azerbaijani documentation feature

You may create additional commits if needed.

Commits should be meaningful and incremental.

---

# Required Features and Grading (you can decide the order)

Total: 10 points

---

## 1. README.md file – 1 points

A proper `README.md` file must be added.

It should include:
- project overview
- technologies used
- how to run the project
- how to start databases/services
- how to test endpoints
- Swagger URL
- example requests
- and any important notes

The README should allow another developer to easily run and experiment with the system.

---

## 2. Swagger API Documentation in Azerbaijani – 1 points

All API endpoints must be documented using Swagger/OpenAPI.

Requirements:
- Azerbaijani descriptions,
- meaningful endpoint explanations,
- DTO field descriptions where applicable.

---

## 3. Enrollment Date – 2 points

When a student is enrolled into a course the system must store the enrollment date

Requirements:
- proper field modeling
- enrollment date returned in responses where appropriate

Students must decide:
- where this information should be stored
- how it should be modeled
- and how it should be exposed

---

## 4. Prerequisite Check During Enrollment – 4 points

Courses may optionally contain a prerequisite course ID.

Requirements:
- if there is no prerequisite, the value should be `null`
- before enrollment, the system must validate prerequisites
- invalid enrollments must be rejected with meaningful error responses

Students are expected to design:
- where prerequisite information is stored
- how validation works
- and which service/module is responsible for the checks

There is no single mandatory architecture.

---

## 5. Get Courses by Student Name – 2 points

The system must support retrieving courses associated with a student using the student's name.

Students must decide:
- which endpoint(s) should be added
- how searching should work
- and which service should expose the functionality

---

# Architectural Expectations

You are expected to make your own engineering decisions.

This includes:
- adding fields,
- designing DTOs,
- creating endpoints,
- choosing service responsibilities,
- and organizing inter-service communication.

Reasonable and well-structured solutions will be accepted.

---

# Submission Requirements

Submission must include:

- GitHub repository link
- Full video recording link
- Working project (.zip)

Projects that do not compile/run may receive significant penalties.

---

# Notes

- Keep the project structure clean.
- Follow REST principles where possible.
- Use meaningful commit messages.
- Test your APIs properly.
- Handle error cases gracefully.
