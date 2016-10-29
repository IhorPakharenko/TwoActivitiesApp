package com.example.isao.twoactivities2.data;


import java.util.ArrayList;

public class Student {
    private String studentsName;
    private String studentsGit;
    private String studentsGooglePlus;

    public Student(String studentsName, String studentsGit, String studentsGooglePlus) {
        this.studentsName = studentsName;
        this.studentsGit = studentsGit;
        this.studentsGooglePlus = studentsGooglePlus;
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

    public static ArrayList<Student> makeStudentsList() {
        ArrayList<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student("Евгеній Жданов", "https://github.com/zhdanov-ek", "https://plus.google.com/u/0/113264746064942658029"));
        studentsList.add(new Student("Едгар Кімич", "https://github.com/lyfm", "https://plus.google.com/u/0/102197104589432395674"));
        studentsList.add(new Student("Олександр Сторчак", "https://github.com/new15", "https://plus.google.com/u/0/106553086375805780685"));
        studentsList.add(new Student("Євгеній Ситник", "https://github.com/YevheniiSytnyk", "https://plus.google.com/u/0/101427598085441575303"));
        studentsList.add(new Student("Альона Prelestnaya", "https://github.com/HelenCool", "https://plus.google.com/u/0/107382407687723634701"));
        studentsList.add(new Student("Богдан Рибак", "https://github.com/BogdanRybak1996", "https://plus.google.com/u/0/103145064185261665176"));
        studentsList.add(new Student("Ірина Смалько", "https://github.com/IraSmalko", "https://plus.google.com/u/0/113994208318508685327"));
        studentsList.add(new Student("Владислав Винник", "https://github.com/vlads0n", "https://plus.google.com/u/0/117765348335292685488"));
        studentsList.add(new Student("Ігор Пахаренко", "https://github.com/IhorPakharenko", "https://plus.google.com/u/0/108231952557339738781"));
        studentsList.add(new Student("Андрій Рябко", "https://github.com/RyabkoAndrew", "https://plus.google.com/u/0/110288437168771810002"));
        studentsList.add(new Student("Іван Левченко", "https://github.com/ivleshch", "https://plus.google.com/u/0/111088051831122657934"));
        studentsList.add(new Student("Микола Піхманець", "https://github.com/NikPikhmanets", "https://plus.google.com/u/0/110087894894730430086"));
        studentsList.add(new Student("Руслан Мігал", "https://github.com/rmigal", "https://plus.google.com/u/0/106331812587299981536"));
        studentsList.add(new Student("Руслан Воловик", "https://github.com/RuslanVolovyk", "https://plus.google.com/u/0/109719711261293841416"));
        studentsList.add(new Student("Валерій Губський", "https://github.com/gvv-ua", "https://plus.google.com/u/0/107910188078571144657"));
        studentsList.add(new Student("Иван Сергеенко", "https://github.com/dogfight81", "https://plus.google.com/u/0/111389859649705526831"));
        studentsList.add(new Student("Вова Лимар", "https://github.com/VovanNec", "https://plus.google.com/u/0/109227554979939957830"));
        studentsList.add(new Student("Даша Кириченко", "https://github.com/dashakdsr", "https://plus.google.com/u/0/103130382244571139113"));
        studentsList.add(new Student("Міхель Тьоплий", "https://github.com/RedGeekPanda", "https://plus.google.com/u/0/110313151428733681846"));
        studentsList.add(new Student("Павло Сакуров", "https://github.com/sakurov/Sunshine", "https://plus.google.com/u/0/108482088578879737406"));
        return studentsList;
    }
}
