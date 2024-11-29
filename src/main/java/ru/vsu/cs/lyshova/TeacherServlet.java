package ru.vsu.cs.lyshova;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.vsu.cs.lyshova.objects.Teacher;
import ru.vsu.cs.lyshova.services.GroupService;
import ru.vsu.cs.lyshova.services.TeacherService;

@WebServlet(name = "teacherServlet", value = "/teacher-servlet")
public class TeacherServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/Teacher.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ServletException {
        req.setCharacterEncoding("UTF-8");
        if(req.getParameter("get") != null) {
            try {
                Teacher teacher = new Teacher(Integer.parseInt(req.getParameter("id")), " ", " ", 0);
                Teacher agr = TeacherService.get(teacher);
                req.setAttribute("id", agr.getId());
                req.setAttribute("name", agr.getName());
                req.setAttribute("direction", agr.getDirection());
                req.setAttribute("experience", agr.getExperience());
                req.setAttribute("successMessage", "Учитель был получен!");
            } catch (Exception e){
                e.printStackTrace();
                req.setAttribute("errorMessage", "Задан пустой ID или вне диапазона!");
            }
        } else if (req.getParameter("create") != null) {
            Teacher teacher = createTeacher(req);
            try {
                if(TeacherService.create(teacher)) req.setAttribute("successMessage", "Учитель успешно создан!");
                else req.setAttribute("errorMessage", "Учитель не был создан!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("update") != null) {
            Teacher teacher = createTeacher(req);
            try {
                if (TeacherService.update(teacher)!=null) req.setAttribute("successMessage", "Успешно обновлено!");
                else req.setAttribute("errorMessage","Учитель не был обновлен!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("delete") != null) {
            //нужно подтверждение
            Teacher teacher = new Teacher(Integer.parseInt(req.getParameter("id")), " ", " ", 0);
            try {
                if (TeacherService.delete(teacher)) req.setAttribute("successMessage", "Успешно удалено!");
                else req.setAttribute("errorMessage","Учитель не был удален!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("getAll") != null) {
            req.setAttribute("teachers", TeacherService.getAll());
        }

        req.getRequestDispatcher("/Teacher.jsp").forward(req, resp);
    }

    private Teacher createTeacher(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String direction = req.getParameter("direction");
        String experience = req.getParameter("experience");

        try {
            // Создаем объект студента
            Teacher teacher = new Teacher(
                    Integer.parseInt(id),
                    name,
                    direction,
                    Integer.parseInt(experience)
            );
            return teacher;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new Teacher(0, " ", " ", 0); // default sqlDate
        }
    }

    public void destroy() {
    }
}