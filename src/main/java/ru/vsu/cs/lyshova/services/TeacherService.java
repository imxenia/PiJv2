package ru.vsu.cs.lyshova.services;

import ru.vsu.cs.lyshova.objects.Teacher;
import ru.vsu.cs.lyshova.repositories.TeacherRepository;

import java.sql.SQLException;
import java.util.List;

public class TeacherService {
    public static boolean create(Teacher agr) throws ClassNotFoundException {
        return TeacherRepository.getInstance().create(agr) != null;
    }

    public static Teacher get(Teacher agr) throws SQLException, ClassNotFoundException {
        return TeacherRepository.getInstance().getById(agr);
    }

    public static Teacher update(Teacher agr) throws ClassNotFoundException, SQLException {
        return TeacherRepository.getInstance().update(agr);
    }


    public static boolean delete(Teacher agr) throws SQLException, ClassNotFoundException {
        return TeacherRepository.getInstance().delete(agr);
    }

    public static List<Teacher> getAll() {
        return TeacherRepository.getInstance().getAll();
    }
}