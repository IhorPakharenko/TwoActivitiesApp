package com.example.isao.twoactivities2.model;


import io.realm.RealmObject;

public class Student extends RealmObject {
    private String studentsName;
    private String studentsGit;
    private String studentsGooglePlus;

    public Student(String studentsName, String studentsGit, String studentsGooglePlus) {
        this.studentsName = studentsName;
        this.studentsGit = studentsGit;
        this.studentsGooglePlus = studentsGooglePlus;
    }

    public Student() {

    }

    public String getStudentsName() {
        return studentsName;
    }
    public String getStudentsGit() {
        return studentsGit;
    }
    public String getStudentsGooglePlus() {
        return studentsGooglePlus;
    }

}
