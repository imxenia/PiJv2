package ru.vsu.cs.lyshova.servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.vsu.cs.lyshova.objects.Group;
import ru.vsu.cs.lyshova.services.GroupService;

@WebServlet(name = "groupServlet", value = "/group-servlet")
public class GroupServlet extends HttpServlet {
    private static GroupService groupService = GroupService.getInstance();
//    private static StudentSubjectService studentSubjectService = StudentSubjectService.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/Group.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ServletException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("g_id", req.getParameter("id"));
        req.setAttribute("g_number", req.getParameter("number"));
        req.setAttribute("g_direction", req.getParameter("direction"));
        req.setAttribute("g_form_education", req.getParameter("form_education"));

        if(req.getParameter("get") != null) {
            try {
                Group group = createGroup(req);
                Group agr = groupService.get(group);
                List<Group> list = new ArrayList<>();
                list.add(agr);
                req.setAttribute("groups", list);
                req.setAttribute("successMessage", "Группа была получена!");
            } catch (Exception e){
                e.printStackTrace();
                req.setAttribute("errorMessage", "Задан пустой ID или вне диапазона!");
            }
        } else if (req.getParameter("create") != null) {
            Group group = createGroup(req);
            try {
                if(groupService.create(group)) req.setAttribute("successMessage", "Группа успешно создана!");
                else req.setAttribute("errorMessage", "Группа не была создана!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("createWithoutId") != null) {
            Group group = createGroup(req);
            try {
                if (groupService.createWithoutId(group)) req.setAttribute("successMessage", "Группа успешно создана!");
                else req.setAttribute("errorMessage", "Ошибка!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("update") != null) {
            Group group = createGroup(req);
            try {
                if (groupService.update(group)!=null) req.setAttribute("successMessage", "Успешно обновлено!");
                else req.setAttribute("errorMessage","Группа не была обновлена!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("deleteOther") != null) {
            Group group = createGroup(req);
            try {
                if (groupService.delete(group)) req.setAttribute("successMessage", "Успешно удалено!");
                else req.setAttribute("errorMessage","Группа не была удалена!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("getAll") != null) {
            List<Group> groups = groupService.getAll();
            req.setAttribute("groups", groups);

        } else if (req.getParameter("change") != null) {
            Group group = createGroup(req);
            req.setAttribute("g_id", group.getId());
            req.setAttribute("g_number", group.getNumber());
            req.setAttribute("g_direction", group.getDirection());
            req.setAttribute("g_form_education", group.getFormEducation());
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

        req.getRequestDispatcher("/Group.jsp").forward(req, resp);
    }

    private Group createGroup(HttpServletRequest req) {
        String id = "0";
        String number;
        String direction;
        String form_education;

        if (req.getParameter("deleteOther") != null || req.getParameter("change") != null) {
            if (req.getParameter("createWithoutId") == null) {
                id = req.getParameter("idOther");
            }
            number = req.getParameter("numberOther");
            direction = req.getParameter("directionOther");
            form_education = req.getParameter("form_educationOther");
        } else {
            if (req.getParameter("createWithoutId") == null) {
                id = req.getParameter("id");
            }
            number = req.getParameter("number");
            direction = req.getParameter("direction");
            form_education = req.getParameter("form_education");
        }

        try {
            // Создаем объект студента
            Group group = new Group(
                    Integer.parseInt(id),
                    Integer.parseInt(number),
                    direction,
                    form_education
            );
            return group;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            if (!Objects.equals(req.getParameter("id"), "")) {
                return new Group(Integer.parseInt(req.getParameter("id")), 0, " ", " ");
            } else {
                return new Group(0, 0, "", "");
            }
        }
    }

    public void destroy() {
    }
}