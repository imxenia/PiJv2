package ru.vsu.cs.lyshova.services;

import ru.vsu.cs.lyshova.objects.Group;
import ru.vsu.cs.lyshova.repositories.GroupRepository;

import java.sql.SQLException;
import java.util.List;

public class GroupService {
    public static boolean create(Group agr) throws ClassNotFoundException {
        return GroupRepository.getInstance().create(agr) != null;
    }

    public static Group get(Group agr) throws SQLException, ClassNotFoundException {
        return GroupRepository.getInstance().getById(agr);
    }

    public static Group update(Group agr) throws ClassNotFoundException, SQLException {
        return GroupRepository.getInstance().update(agr);
    }


    public static boolean delete(Group agr) throws SQLException, ClassNotFoundException {
        return GroupRepository.getInstance().delete(agr);
    }

    public static List<Group> getAll() {
        return GroupRepository.getInstance().getAll();
    }
}