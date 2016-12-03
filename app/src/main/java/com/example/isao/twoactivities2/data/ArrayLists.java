package com.example.isao.twoactivities2.data;

import com.example.isao.twoactivities2.model.Student;

import java.util.ArrayList;

public class ArrayLists {
    public static ArrayList<Student> makeStudentsList() {
        ArrayList<Student> studentsList = new ArrayList<>();
        studentsList.add(new Student("Евгеній Жданов", "zhdanov-ek", "113264746064942658029"));
        studentsList.add(new Student("Едгар Кімич", "lyfm", "102197104589432395674"));
        studentsList.add(new Student("Олександр Сторчак", "new15", "106553086375805780685"));
        studentsList.add(new Student("Євгеній Ситник", "YevheniiSytnyk", "101427598085441575303"));
        studentsList.add(new Student("Альона Prelestnaya", "HelenCool", "107382407687723634701"));
        studentsList.add(new Student("Богдан Рибак", "BogdanRybak1996", "103145064185261665176"));
        studentsList.add(new Student("Ірина Смалько", "IraSmalko", "113994208318508685327"));
        studentsList.add(new Student("Владислав Винник", "vlads0n", "117765348335292685488"));
        studentsList.add(new Student("Ігор Пахаренко", "IhorPakharenko", "108231952557339738781"));
        studentsList.add(new Student("Андрій Рябко", "RyabkoAndrew", "110288437168771810002"));
        studentsList.add(new Student("Іван Левченко", "ivleshch", "111088051831122657934"));
        studentsList.add(new Student("Микола Піхманець", "NikPikhmanets", "110087894894730430086"));
        studentsList.add(new Student("Руслан Мігал", "rmigal", "106331812587299981536"));
        studentsList.add(new Student("Руслан Воловик", "RuslanVolovyk", "109719711261293841416"));
        studentsList.add(new Student("Валерій Губський", "gvv-ua", "107910188078571144657"));
        studentsList.add(new Student("Иван Сергеенко", "dogfight81", "111389859649705526831"));
        studentsList.add(new Student("Вова Лимар", "VovanNec", "109227554979939957830"));
        studentsList.add(new Student("Даша Кириченко", "dashakdsr", "103130382244571139113"));
        studentsList.add(new Student("Міхель Тьоплий", "RedGeekPanda", "110313151428733681846"));
        studentsList.add(new Student("Павло Сакуров", "sakurov", "108482088578879737406"));
        return studentsList;
    }
}
