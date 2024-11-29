package ru.vsu.cs.lyshova;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.vsu.cs.lyshova.objects.Group;
import ru.vsu.cs.lyshova.services.GroupService;

@WebServlet(name = "groupServlet", value = "/group-servlet")
public class GroupServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/Group.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ServletException {
        req.setCharacterEncoding("UTF-8");
        if(req.getParameter("get") != null) {
            try {
                Group group = new Group(Integer.parseInt(req.getParameter("id")), 0, " ", " ");
                Group agr = GroupService.get(group);
                req.setAttribute("id", agr.getId());
                req.setAttribute("number", agr.getNumber());
                req.setAttribute("direction", agr.getDirection());
                req.setAttribute("form_education", agr.getFormEducation());
                req.setAttribute("successMessage", "Группа была получена!");
            } catch (Exception e){
                e.printStackTrace();
                req.setAttribute("errorMessage", "Задан пустой ID или вне диапазона!");
            }
        } else if (req.getParameter("create") != null) {
            Group group = createGroup(req);
            try {
                if(GroupService.create(group)) req.setAttribute("successMessage", "Группа успешно создана!");
                else req.setAttribute("errorMessage", "Группа не была создана!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("update") != null) {
            Group group = createGroup(req);
            try {
                if (GroupService.update(group)!=null) req.setAttribute("successMessage", "Успешно обновлено!");
                else req.setAttribute("errorMessage","Группа не была обновлена!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("delete") != null) {
            //нужно подтверждение
            Group group = new Group(Integer.parseInt(req.getParameter("id")), 0, " ", " ");
            try {
                if (GroupService.delete(group)) req.setAttribute("successMessage", "Успешно удалено!");
                else req.setAttribute("errorMessage","Группа не была удалена!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("getAll") != null) {
            req.setAttribute("groups", GroupService.getAll());
        }

        req.getRequestDispatcher("/Group.jsp").forward(req, resp);
    }

    private Group createGroup(HttpServletRequest req) {
        String id = req.getParameter("id");
        String number = req.getParameter("number");
        String direction = req.getParameter("direction");
        String form_education = req.getParameter("form_education");

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
            return new Group(0, 0, " ", " "); // default sqlDate
        }
    }

    public void destroy() {
    }
}