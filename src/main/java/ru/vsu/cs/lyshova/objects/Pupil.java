package ru.vsu.cs.lyshova.objects;

public class Pupil implements Identifiable {
    private int id;
    private String name;
    private int groupId; // fk
    private String number;

    public Pupil(int id, String name, String number, int groupId) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.groupId = groupId;
    }

    public Pupil() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getNumber() {
        return number;
    }

    public String toString() {
        return "ID: " + id + " | Имя: " + name + " | ID группы: " + groupId + " | Номер телефона: " + number;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
