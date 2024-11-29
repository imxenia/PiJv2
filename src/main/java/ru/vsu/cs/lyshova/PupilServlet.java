package ru.vsu.cs.lyshova;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.vsu.cs.lyshova.objects.Pupil;
import ru.vsu.cs.lyshova.services.PupilService;

@WebServlet(name = "pupilServlet", value = "/pupil-servlet")
public class PupilServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/Pupil.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ServletException {
        req.setCharacterEncoding("UTF-8");
        if(req.getParameter("get") != null) {
            try {
                Pupil pupil = new Pupil(Integer.parseInt(req.getParameter("id")), "", "", 0);
                Pupil agr = PupilService.get(pupil);
                req.setAttribute("id", agr.getId());
                req.setAttribute("name", agr.getName());
                req.setAttribute("number", agr.getNumber());
                req.setAttribute("group_id", agr.getGroupId());
                req.setAttribute("successMessage", "Ученик был получен!");
            } catch (Exception e){
                e.printStackTrace();
                req.setAttribute("errorMessage", "Задан пустой ID или вне диапазона!");
            }
        } else if (req.getParameter("create") != null) {
            Pupil pupil = createPupil(req);
            try {
                if(PupilService.create(pupil)) req.setAttribute("successMessage", "Ученик успешно создан!");
                else req.setAttribute("errorMessage", "Ученик не был создан!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("update") != null) {
            Pupil pupil = createPupil(req);
            try {
                if (PupilService.update(pupil)!=null) req.setAttribute("successMessage", "Успешно обновлено!");
                else req.setAttribute("errorMessage","Ученик не был обновлен!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("delete") != null) {
            //нужно подтверждение
            Pupil pupil = new Pupil(Integer.parseInt(req.getParameter("id")), " ", " ", 0);
            try {
                if (PupilService.delete(pupil)) req.setAttribute("successMessage", "Успешно удалено!");
                else req.setAttribute("errorMessage","Ученик не был удален!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (req.getParameter("getAll") != null) {
            req.setAttribute("pupils", PupilService.getAll());
        }

        req.getRequestDispatcher("/Pupil.jsp").forward(req, resp);
    }

    private Pupil createPupil(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String number = req.getParameter("number");
        String group_id = req.getParameter("group_id");

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
            return new Pupil(0, " ", " ", 0); // default sqlDate
        }
    }

    public void destroy() {
    }
}