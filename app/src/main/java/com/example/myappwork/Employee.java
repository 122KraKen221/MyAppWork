package com.example.myappwork;

public class Employee {
    private long id;
    private String name;
    private String position;

    Employee(long id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return

          "ФИО: " + name +"\n" +"Должность: " +position;
    }
}