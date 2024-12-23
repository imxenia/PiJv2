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
import ru.vsu.cs.lyshova.objects.Pupil;
import ru.vsu.cs.lyshova.services.GroupService;
import ru.vsu.cs.lyshova.services.PupilService;

@WebServlet(name = "pupilServlet", value = "/pupil-servlet")
public class PupilServlet extends HttpServlet {
    private static PupilService pupilService = PupilService.getInstance();
    private static GroupService groupService = GroupService.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/Pupil.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ServletException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("p_id", req.getParameter("id"));
        req.setAttribute("p_name", req.getParameter("name"));
        req.setAttribute("p_number", req.getParameter("number"));
        req.setAttribute("p_group_id", req.getParameter("group_id"));

        if(req.getParameter("get") != null) {
            try {
                Pupil pupil = createPupil(req);
                Pupil agr = pupilService.get(pupil);
                List<Pupil> list = new ArrayList<>();
                list.add(agr);
                req.setAttribute("pupils", list);
                req.setAttribute("successMessage", "Ученик был получен!");
            } catch (Exception e){
                e.printStackTrace();
                req.setAttribute("errorMessage", "Задан пустой ID или вне диапазона!");
            }
        } else if (req.getParameter("create") != null) {
            Pupil pupil = createPupil(req);
            try {
                if(pupilService.create(pupil)) req.setAttribute("successMessage", "Ученик успешно создан!");
                else req.setAttribute("errorMessage", "Ученик не был создан!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("createWithoutId") != null) {
            Pupil pupil = createPupil(req);
            try {
                if (pupilService.createWithoutId(pupil)) req.setAttribute("successMessage", "Ученик успешно создан!");
                else req.setAttribute("errorMessage", "Ошибка!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("update") != null) {
            Pupil pupil = createPupil(req);
            try {
                if (pupilService.update(pupil)!=null) req.setAttribute("successMessage", "Успешно обновлено!");
                else req.setAttribute("errorMessage","Ученик не был обновлен!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("deleteOther") != null) {
            Pupil pupil = createPupil(req);
            try {
                if (pupilService.delete(pupil)) req.setAttribute("successMessage", "Успешно удалено!");
                else req.setAttribute("errorMessage","Ученик не был удален!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("getAll") != null) {
            List<Pupil> pupils = pupilService.getAll();
            req.setAttribute("pupils", pupils);

        } else if (req.getParameter("change") != null) {
            Pupil pupil = createPupil(req);
            req.setAttribute("p_id", pupil.getId());
            req.setAttribute("p_name", pupil.getName());
            req.setAttribute("p_number", pupil.getNumber());
            req.setAttribute("p_group_id", pupil.getGroupId());
        } else if (req.getParameter("view") != null) {
            try {
                Group group = groupService.get(new Group(pupilService.getGroupByPupilId(Integer.parseInt(req.getParameter("idOther"))), 0, "", ""));
                List<Group> groups = new ArrayList<>();
                groups.add(group);
                if (!groups.isEmpty()) {
                    req.setAttribute("groups", groups);
                    req.setAttribute("successMessage", "Успешно!");
                } else {
                    req.setAttribute("errorMessage","Предметы не найдены!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
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

        req.getRequestDispatcher("/Pupil.jsp").forward(req, resp);
    }

    private Pupil createPupil(HttpServletRequest req) {
        String id = "0";
        String name;
        String number;
        String group_id;

        if (req.getParameter("deleteOther") != null || req.getParameter("change") != null) {
            if (req.getParameter("createWithoutId") == null) {
                id = req.getParameter("idOther");
            }
            name = req.getParameter("nameOther");
            number = req.getParameter("numberOther");
            group_id = req.getParameter("group_idOther");
        } else {
            if (req.getParameter("createWithoutId") == null) {
                id = req.getParameter("id");
            }
            name = req.getParameter("name");
            number = req.getParameter("number");
            group_id = req.getParameter("group_id");
        }

        try {
            // Создаем объект студента
            Pupil pupil = new Pupil(
                    Integer.parseInt(id),
                    name,
                    number,
                    Integer.parseInt(group_id)
            );
            return pupil;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            if (!Objects.equals(req.getParameter("id"), "")) {
                return new Pupil(Integer.parseInt(req.getParameter("id")), " ", " ", 0);
            } else {
                return new Pupil(0, "", "", 0);
            }
        }
    }

    public void destroy() {
    }
}