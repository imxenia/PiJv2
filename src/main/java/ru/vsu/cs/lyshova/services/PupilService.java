package ru.vsu.cs.lyshova.services;

import ru.vsu.cs.lyshova.objects.Group;
import ru.vsu.cs.lyshova.objects.Pupil;
import ru.vsu.cs.lyshova.repositories.GroupRepository;
import ru.vsu.cs.lyshova.repositories.PupilRepository;

import java.sql.SQLException;
import java.util.List;

public class PupilService {
    private static PupilService instance;
    private static PupilRepository pupilRepository = PupilRepository.getInstance();
    public boolean create(Pupil agr) throws ClassNotFoundException {
        return pupilRepository.create(agr) != null;
    }

    public boolean createWithoutId(Pupil agr) throws ClassNotFoundException {
        return pupilRepository.createWithoutId(agr) != null;
    }

    public Integer getGroupByPupilId(Integer realID) throws SQLException, ClassNotFoundException {
        return pupilRepository.getGroupByPupilId(realID);
    }

    public Pupil get(Pupil agr) throws SQLException, ClassNotFoundException {
        return pupilRepository.getById(agr);
    }

    public Pupil update(Pupil agr) throws ClassNotFoundException, SQLException {
        return pupilRepository.update(agr);
    }

    public boolean delete(Pupil agr) throws SQLException, ClassNotFoundException {
        return pupilRepository.delete(agr);
    }

    public List<Pupil> getAll() {
        return pupilRepository.getAll();
    }

    public static synchronized PupilService getInstance() {
        if (instance == null) instance = new PupilService();
        return instance;
    }
}