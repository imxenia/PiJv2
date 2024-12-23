package ru.vsu.cs.lyshova.repositories;

import ru.vsu.cs.lyshova.ConnectJDBC;
import ru.vsu.cs.lyshova.objects.Group;
import ru.vsu.cs.lyshova.objects.Pupil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PupilRepository {
    private static PupilRepository instance;
    public static final String PUPIL_TABLE = "pupil";
    public static final String PUPIL_ID = "pupil_id";
    public static final String PUPIL_NAME = "name";
    public static final String PUPIL_PHONE_NUMBER = "phone_number";
    public static final String PUPIL_GROUP_ID = "group_id";

    public Pupil getById(Pupil get) throws SQLException, ClassNotFoundException {
        int realID = get.getId();
        String ins = "SELECT * FROM " + PUPIL_TABLE + " WHERE " + PUPIL_ID + "=?";
        ConnectJDBC con = ConnectJDBC.getInstance();

        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setString(1, String.valueOf(realID));
            ResultSet res = prSt.executeQuery();
            res.next();
            Pupil pupil = new Pupil();
            pupil.setId(res.getInt(1));
            pupil.setName(res.getString(2));
            pupil.setNumber(res.getString(3));
            pupil.setGroupId(res.getInt(4));

            return pupil;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer getGroupByPupilId(Integer realID) throws SQLException, ClassNotFoundException {
        String ins = "SELECT group_id FROM " + PUPIL_TABLE + " WHERE " + PUPIL_ID + "=?";
        ConnectJDBC con = ConnectJDBC.getInstance();

        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setInt(1, realID); // Используем setInt вместо преобразования в строку
            ResultSet res = prSt.executeQuery();

            if (res.next()) { // Проверяем, есть ли данные в результате
                return res.getInt("group_id"); // Возвращаем значение group_id
            } else {
                return null; // Возвращаем null, если запись не найдена
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e; // Повторно выбрасываем исключение для обработки выше
        }
    }

    public List<Pupil> getAll() {

        String ins = "SELECT * FROM " + PUPIL_TABLE;
        ConnectJDBC con = ConnectJDBC.getInstance();
        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            ResultSet res = prSt.executeQuery();
            List<Pupil> pupils = new ArrayList<>();
            while (res.next()) {
                Pupil pupil = new Pupil();
                pupil.setId(res.getInt(1));
                pupil.setName(res.getString(2));
                pupil.setNumber(res.getString(3));
                pupil.setGroupId(res.getInt(4));
                pupils.add(pupil);
            }
            return pupils;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Pupil create(Pupil add) throws ClassNotFoundException {
        Pupil agr = add;
        String ins = "INSERT INTO " + PUPIL_TABLE + "(" + PUPIL_ID + "," + PUPIL_NAME + "," + PUPIL_PHONE_NUMBER + "," + PUPIL_GROUP_ID + ")" + "VALUES(?,?,?,?)";
        ConnectJDBC con = ConnectJDBC.getInstance();
        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setInt(1, agr.getId());
            prSt.setString(2, agr.getName());
            prSt.setString(3, agr.getNumber());
            prSt.setInt(4, agr.getGroupId());
            prSt.executeUpdate();
            return agr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pupil createWithoutId(Pupil add) throws ClassNotFoundException {
        Pupil agr = add;
        String ins = "INSERT INTO " + PUPIL_TABLE + "(" + PUPIL_NAME + "," + PUPIL_PHONE_NUMBER + "," + PUPIL_GROUP_ID + ")" + "VALUES(?,?,?)";
        ConnectJDBC con = ConnectJDBC.getInstance();
        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setString(1, agr.getName());
            prSt.setString(2, agr.getNumber());
            prSt.setInt(3, agr.getGroupId());
            prSt.executeUpdate();
            return agr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pupil update(Pupil upd) throws ClassNotFoundException, SQLException {
        Pupil agr = upd;
        if (getById(agr) != null) {
            ConnectJDBC con = ConnectJDBC.getInstance();
            String ins = "UPDATE " + PUPIL_TABLE + " SET " + PUPIL_ID + " = ?, " + PUPIL_NAME + " =?, " + PUPIL_PHONE_NUMBER + " =?, " + PUPIL_GROUP_ID + " =? WHERE " + PUPIL_ID + "=" + agr.getId();

            try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
                prSt.setString(1, String.valueOf(agr.getId()));
                prSt.setString(2, agr.getName());
                prSt.setString(3, agr.getNumber());
                prSt.setInt(4, agr.getGroupId());
                prSt.executeUpdate();
                return agr;
            } catch (SQLException | ClassNotFoundException e) {
                return null;
            }
        } else create(upd);
        return null;
    }

    public boolean delete(Pupil del) throws SQLException, ClassNotFoundException {
        int realID = del.getId();
        if (getById(del) != null) {
            ConnectJDBC con = ConnectJDBC.getInstance();
            String ins = "DELETE FROM " + PUPIL_TABLE + " WHERE " + PUPIL_ID + "=?";
            try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
                prSt.setString(1, String.valueOf(realID));
                prSt.executeUpdate();
                return true;
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else System.out.println("Nothing to delete.");
        return false;
    }

    public static synchronized PupilRepository getInstance() {
        if (instance == null) instance = new PupilRepository();
        return instance;
    }
}