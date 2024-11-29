package ru.vsu.cs.lyshova.objects;

public class Teacher implements Identifiable {
    private int id;
    private String name;
    private int experience;
    private String direction;

    public Teacher(int id, String name, String direction, int experience) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.experience = experience;
    }

    public Teacher() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public String getDirection() {
        return direction;
    }

    public String toString() {
        return "ID: " + id + " | Имя: " + name + " | Опыт работы: " + experience + " | Направление: " + direction;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
