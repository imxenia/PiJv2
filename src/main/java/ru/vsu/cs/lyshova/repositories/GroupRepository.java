package ru.vsu.cs.lyshova.repositories;

import ru.vsu.cs.lyshova.ConnectJDBC;
import ru.vsu.cs.lyshova.objects.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepository {
    private static GroupRepository instance;
    public static final String GROUP_TABLE = "group";

    public static final String GROUP_ID = "group_id";
    public static final String GROUP_NUMBER = "number";
    public static final String GROUP_DIRECTION = "direction";
    public static final String GROUP_FORM_EDUCATION = "form_education";

    public Group getById(Group get) throws SQLException, ClassNotFoundException {
        int realID = get.getId();
        String ins = "SELECT * FROM mydb." + GROUP_TABLE + " WHERE " + GROUP_ID + "=?";
        ConnectJDBC con = ConnectJDBC.getInstance();

        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setString(1, String.valueOf(realID));
            ResultSet res = prSt.executeQuery();
            res.next();
            Group group = new Group();
            group.setId(res.getInt(1));
            group.setNumber(res.getInt(2));
            group.setDirection(res.getString(3));
            group.setFormEducation(res.getString(4));

            return group;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Group> getAll() {

        String ins = "SELECT * FROM mydb." + GROUP_TABLE;
        ConnectJDBC con = ConnectJDBC.getInstance();
        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            ResultSet res = prSt.executeQuery();
            List<Group> groups = new ArrayList<>();
            while (res.next()) {
                Group group = new Group();
                group.setId(res.getInt(1));
                group.setNumber(res.getInt(2));
                group.setDirection(res.getString(3));
                group.setFormEducation(res.getString(4));
                groups.add(group);
            }
            return groups;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Group create(Group add) throws ClassNotFoundException {
        Group agr = add;
        String ins = "INSERT INTO mydb." + GROUP_TABLE + "(" + GROUP_ID + "," + GROUP_NUMBER + "," + GROUP_DIRECTION + "," + GROUP_FORM_EDUCATION + ")" + "VALUES(?,?,?,?)";
        ConnectJDBC con = ConnectJDBC.getInstance();
        try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
            prSt.setInt(1, agr.getId());
            prSt.setInt(2, agr.getNumber());
            prSt.setString(3, agr.getDirection());
            prSt.setString(4, agr.getFormEducation());
            prSt.executeUpdate();
            return agr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Group update(Group upd) throws ClassNotFoundException, SQLException {
        Group agr = upd;
        if (getById(agr) != null) {
            ConnectJDBC con = ConnectJDBC.getInstance();
            String ins = "UPDATE mydb." + GROUP_TABLE + " SET " + GROUP_ID + " = ?, " + GROUP_NUMBER + " =?, " + GROUP_DIRECTION + " =?, " + GROUP_FORM_EDUCATION + " =? WHERE " + GROUP_ID + "=" + agr.getId();

            try (PreparedStatement prSt = con.getDbConnection().prepareStatement(ins)) {
                prSt.setString(1, String.valueOf(agr.getId()));
                prSt.setInt(2, agr.getNumber());
                prSt.setString(3, agr.getDirection());
                prSt.setString(4, agr.getFormEducation());
                prSt.executeUpdate();
                return agr;
            } catch (SQLException | ClassNotFoundException e) {
                return null;
            }
        } else create(upd);
        return null;
    }

    public boolean delete(Group del) throws SQLException, ClassNotFoundException {
        int realID = del.getId();
        if (getById(del) != null) {
            ConnectJDBC con = ConnectJDBC.getInstance();
            String ins = "DELETE FROM mydb." + GROUP_TABLE + " WHERE " + GROUP_ID + "=?";
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

    public static synchronized GroupRepository getInstance() {
        if (instance == null) instance = new GroupRepository();
        return instance;
    }
}