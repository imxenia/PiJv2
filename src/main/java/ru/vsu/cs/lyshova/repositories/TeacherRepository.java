package ru.vsu.cs.lyshova.repositories;

import ru.vsu.cs.lyshova.ConnectJDBC;
import ru.vsu.cs.lyshova.objects.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {
    private static TeacherRepository instance;
    public static final String TEACHER_TABLE = "teacher";

    public static final String TEACHER_ID = "teacher_id";
    public static final String TEACHER_NAME = "name";
    public static final String TEACHER_EXPERIENCE = "experience";
    public static final String TEACHER_DIRECTION = "direction";

    public Teacher getById(Teacher get) throws SQLException, ClassNotFoundException {
        int realID = get.getId();
        String ins = "SELECT * FROM " + TEACHER_TABLE + " WHERE " + TEACHER_ID + "=?";
        ConnectJDBC con = ConnectJDBC.getInstance();

        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setString(1, String.valueOf(realID));
            ResultSet res = prSt.executeQuery();
            res.next();
            Teacher teacher = new Teacher();
            teacher.setId(res.getInt(1));
            teacher.setName(res.getString(2));
            teacher.setExperience(res.getInt(3));
            teacher.setDirection(res.getString(4));

            return teacher;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Teacher> getAll() {

        String ins = "SELECT * FROM " + TEACHER_TABLE;
        ConnectJDBC con = ConnectJDBC.getInstance();
        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            ResultSet res = prSt.executeQuery();
            List<Teacher> pupils = new ArrayList<>();
            while (res.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(res.getInt(1));
                teacher.setName(res.getString(2));
                teacher.setExperience(res.getInt(3));
                teacher.setDirection(res.getString(4));
                pupils.add(teacher);
            }
            return pupils;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Teacher create(Teacher add) throws ClassNotFoundException {
        Teacher agr = add;
        String ins = "INSERT INTO " + TEACHER_TABLE + "(" + TEACHER_ID + "," + TEACHER_NAME + "," + TEACHER_EXPERIENCE + "," + TEACHER_DIRECTION + ")" + "VALUES(?,?,?,?)";
        ConnectJDBC con = ConnectJDBC.getInstance();
        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setInt(1, agr.getId());
            prSt.setString(2, agr.getName());
            prSt.setInt(3, agr.getExperience());
            prSt.setString(4, agr.getDirection());
            prSt.executeUpdate();
            return agr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Teacher update(Teacher upd) throws ClassNotFoundException, SQLException {
        Teacher agr = upd;
        if (getById(agr) != null) {
            ConnectJDBC con = ConnectJDBC.getInstance();
            String ins = "UPDATE " + TEACHER_TABLE + " SET " + TEACHER_ID + " = ?, " + TEACHER_NAME + " =?, " + TEACHER_EXPERIENCE + " =?, " + TEACHER_DIRECTION + " =? WHERE " + TEACHER_ID + "=" + agr.getId();

            try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
                prSt.setString(1, String.valueOf(agr.getId()));
                prSt.setString(2, agr.getName());
                prSt.setInt(3, agr.getExperience());
                prSt.setString(4, agr.getDirection());
                prSt.executeUpdate();
                return agr;
            } catch (SQLException | ClassNotFoundException e) {
                return null;
            }
        } else create(upd);
        return null;
    }

    public boolean delete(Teacher del) throws SQLException, ClassNotFoundException {
        int realID = del.getId();
        if (getById(del) != null) {
            ConnectJDBC con = ConnectJDBC.getInstance();
            String ins = "DELETE FROM " + TEACHER_TABLE + " WHERE " + TEACHER_ID + "=?";
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

    public static synchronized TeacherRepository getInstance() {
        if (instance == null) instance = new TeacherRepository();
        return instance;
    }
}