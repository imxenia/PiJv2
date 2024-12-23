package ru.vsu.cs.lyshova.servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.vsu.cs.lyshova.objects.Pupil;
import ru.vsu.cs.lyshova.objects.Teacher;
import ru.vsu.cs.lyshova.services.PupilService;
import ru.vsu.cs.lyshova.services.TeacherService;

@WebServlet(name = "teacherServlet", value = "/teacher-servlet")
public class TeacherServlet extends HttpServlet {
    private static TeacherService teacherService = TeacherService.getInstance();
//    private static StudentSubjectService studentSubjectService = StudentSubjectService.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/Teacher.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ServletException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("t_id", req.getParameter("id"));
        req.setAttribute("t_name", req.getParameter("name"));
        req.setAttribute("t_direction", req.getParameter("direction"));
        req.setAttribute("t_experience", req.getParameter("experience"));

        if(req.getParameter("get") != null) {
            try {
                Teacher teacher = createTeacher(req);
                Teacher agr = teacherService.get(teacher);
                List<Teacher> list = new ArrayList<>();
                list.add(agr);
                req.setAttribute("teachers", list);
                req.setAttribute("successMessage", "Учитель был получен!");
            } catch (Exception e){
                e.printStackTrace();
                req.setAttribute("errorMessage", "Задан пустой ID или вне диапазона!");
            }
        } else if (req.getParameter("create") != null) {
            Teacher teacher = createTeacher(req);
            try {
                if(teacherService.create(teacher)) req.setAttribute("successMessage", "Учитель успешно создан!");
                else req.setAttribute("errorMessage", "Учитель не был создан!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("createWithoutId") != null) {
            Teacher teacher = createTeacher(req);
            try {
                if (teacherService.createWithoutId(teacher)) req.setAttribute("successMessage", "Учитель успешно создан!");
                else req.setAttribute("errorMessage", "Ошибка!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("update") != null) {
            Teacher teacher = createTeacher(req);
            try {
                if (teacherService.update(teacher)!=null) req.setAttribute("successMessage", "Успешно обновлено!");
                else req.setAttribute("errorMessage","Учитель не был обновлен!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("deleteOther") != null) {
            Teacher teacher = createTeacher(req);
            try {
                if (teacherService.delete(teacher)) req.setAttribute("successMessage", "Успешно удалено!");
                else req.setAttribute("errorMessage","Учитель не был удален!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("getAll") != null) {
            List<Teacher> teachers = teacherService.getAll();
            req.setAttribute("teachers", teachers);

        } else if (req.getParameter("change") != null) {
            Teacher teacher = createTeacher(req);
            req.setAttribute("t_id", teacher.getId());
            req.setAttribute("t_name", teacher.getName());
            req.setAttribute("t_direction", teacher.getDirection());
            req.setAttribute("t_experience", teacher.getExperience());
//        } else if (req.getParameter("view") != null) {
//            try {
//                List<Subject> subjects = groupSubjectService.getByStudentId(Integer.parseInt(req.getParameter("idOther")));
//                if (!subjects.isEmpty()) {
//                    req.setAttribute("subjects", subjects);
//                    req.setAttribute("successMessage", "Успешно!");
//                } else {
//                    req.setAttribute("errorMessage","Предметы не найдены!");
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        } else if (req.getParameter("search_surname") != null) {
//            try {
//                List<Student> students = studentService.getStudentsBySurname(req.getParameter("surname"));
//                if (!students.isEmpty()) {
//                    req.setAttribute("students", students);
//                    req.setAttribute("successMessage", "Успешно!");
//                } else {
//                    req.setAttribute("errorMessage","Студенты не найдены!");
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
        }

        req.getRequestDispatcher("/Teacher.jsp").forward(req, resp);
    }

    private Teacher createTeacher(HttpServletRequest req) {
        String id = "0";
        String name;
        String direction;
        String experience;

        if (req.getParameter("deleteOther") != null || req.getParameter("change") != null) {
            if (req.getParameter("createWithoutId") == null) {
                id = req.getParameter("idOther");
            }
            name = req.getParameter("nameOther");
            direction = req.getParameter("directionOther");
            experience = req.getParameter("experienceOther");
        } else {
            if (req.getParameter("createWithoutId") == null) {
                id = req.getParameter("id");
            }
            name = req.getParameter("name");
            direction = req.getParameter("direction");
            experience = req.getParameter("experience");
        }

        try {
            // Создаем объект студента
            Teacher teacher = new Teacher(
                    Integer.parseInt(id),
                    name,
                    direction,
                    Integer.parseInt(experience)
            );
            System.out.println(id);
            System.out.println(name);
            System.out.println(direction);
            System.out.println(experience);
            return teacher;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            if (!Objects.equals(req.getParameter("id"), "")) {
                return new Teacher(Integer.parseInt(req.getParameter("id")), " ", " ", 0);
            } else {
                return new Teacher(0, "", "", 0);
            }
        }
    }

    public void destroy() {
    }
}