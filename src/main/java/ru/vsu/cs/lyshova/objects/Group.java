package ru.vsu.cs.lyshova.objects;

public class Group implements Identifiable {
    private int id;
    private int number;
    private String direction;
    private String formEducation;

    public Group(int id, int number, String direction, String formEducation) {
        this.id = id;
        this.number = number;
        this.direction = direction;
        this.formEducation = formEducation;
    }

    public Group() {
    }

    public int getId() {
        return id;
    }

    public String getDirection() {
        return direction;
    }

    public String getFormEducation() {
        return formEducation;
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        return "ID: " + id + " | Номер группы: " + Integer.toString(number) + " | Направление: " +
                direction + " | Форма обучения: " + formEducation;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setFormEducation(String formEducation) {
        this.formEducation = formEducation;
    }
}
