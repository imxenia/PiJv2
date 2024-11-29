package ru.vsu.cs.lyshova.services;

import ru.vsu.cs.lyshova.objects.Pupil;
import ru.vsu.cs.lyshova.repositories.PupilRepository;

import java.sql.SQLException;
import java.util.List;

public class PupilService {
    public static boolean create(Pupil agr) throws ClassNotFoundException {
        return PupilRepository.getInstance().create(agr) != null;
    }

    public static Pupil get(Pupil agr) throws SQLException, ClassNotFoundException {
        return PupilRepository.getInstance().getById(agr);
    }

    public static Pupil update(Pupil agr) throws ClassNotFoundException, SQLException {
        return PupilRepository.getInstance().update(agr);
    }


    public static boolean delete(Pupil agr) throws SQLException, ClassNotFoundException {
        return PupilRepository.getInstance().delete(agr);
    }

    public static List<Pupil> getAll() {
        return PupilRepository.getInstance().getAll();
    }
}