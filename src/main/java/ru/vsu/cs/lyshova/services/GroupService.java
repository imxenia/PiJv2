package ru.vsu.cs.lyshova.services;

import ru.vsu.cs.lyshova.objects.Group;
import ru.vsu.cs.lyshova.repositories.GroupRepository;

import java.sql.SQLException;
import java.util.List;

public class GroupService {
    private static GroupService instance;
    private static GroupRepository groupRepository = GroupRepository.getInstance();

    public boolean create(Group agr) throws ClassNotFoundException {
        return groupRepository.create(agr) != null;
    }

    public boolean createWithoutId(Group agr) throws ClassNotFoundException {
        return groupRepository.createWithoutId(agr) != null;
    }

    public Group get(Group agr) throws SQLException, ClassNotFoundException {
        return groupRepository.getById(agr);
    }

    public Group update(Group agr) throws ClassNotFoundException, SQLException {
        return groupRepository.update(agr);
    }

    public boolean delete(Group agr) throws SQLException, ClassNotFoundException {
        return groupRepository.delete(agr);
    }

    public List<Group> getAll() {
        return groupRepository.getAll();
    }

    public static synchronized GroupService getInstance() {
        if (instance == null) instance = new GroupService();
        return instance;
    }
}