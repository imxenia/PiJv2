package ru.vsu.cs.lyshova.services;

import ru.vsu.cs.lyshova.objects.Group;
import ru.vsu.cs.lyshova.objects.Teacher;
import ru.vsu.cs.lyshova.repositories.GroupRepository;
import ru.vsu.cs.lyshova.repositories.TeacherRepository;

import java.sql.SQLException;
import java.util.List;

public class TeacherService {
    private static TeacherService instance;
    private static TeacherRepository teacherRepository = TeacherRepository.getInstance();

    public boolean create(Teacher agr) throws ClassNotFoundException {
        return teacherRepository.create(agr) != null;
    }

    public boolean createWithoutId(Teacher agr) throws ClassNotFoundException {
        return teacherRepository.createWithoutId(agr) != null;
    }

    public Teacher get(Teacher agr) throws SQLException, ClassNotFoundException {
        return teacherRepository.getById(agr);
    }

    public Teacher update(Teacher agr) throws ClassNotFoundException, SQLException {
        return teacherRepository.update(agr);
    }

    public boolean delete(Teacher agr) throws SQLException, ClassNotFoundException {
        return teacherRepository.delete(agr);
    }

    public List<Teacher> getAll() {
        return teacherRepository.getAll();
    }

    public static synchronized TeacherService getInstance() {
        if (instance == null) instance = new TeacherService();
        return instance;
    }
}