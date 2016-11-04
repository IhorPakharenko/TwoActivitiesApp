package com.example.isao.twoactivities2.listView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.isao.twoactivities2.GithubActivity;
import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.data.Student;

import java.util.ArrayList;



public class StudentListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Student> innerStudentsList;

    public StudentListViewAdapter (Context context, ArrayList<Student> innerStudentsList) {
        this.context = context;
        this.innerStudentsList = innerStudentsList;
    }

    @Override
    public int getCount() {
        return innerStudentsList.size();
    }

    @Override
    public Object getItem(int i) {
        return innerStudentsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, final View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_students, viewGroup, false);
        }

        final Student student = (Student) getItem(i);

        TextView studentName = (TextView) view.findViewById(R.id.name_textview);
        studentName.setText(student.getStudentsName());
        studentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                context.startActivity(
                        new Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                        student.getStudentsGooglePlus())));
            }
        });

        Button gitLink = (Button) view.findViewById(R.id.git_button);
        gitLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/**
 *                context.startActivity(
 *                        new Intent(
 *                        Intent.ACTION_VIEW, Uri.parse(student.getStudentsGit())));
 */
                Intent intent = new Intent(context, GithubActivity.class);
                intent.putExtra("GITHUB_LINK", student.getStudentsGit());
                //TODO: check if intent isnt null
                context.startActivity(intent);
            }
        });
        return view;
    }
}

