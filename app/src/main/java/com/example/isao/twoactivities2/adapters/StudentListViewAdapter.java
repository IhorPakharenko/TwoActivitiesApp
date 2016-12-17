package com.example.isao.twoactivities2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.activities.GithubActivity;
import com.example.isao.twoactivities2.activities.GoogleActivity;
import com.example.isao.twoactivities2.model.Student;

import io.realm.RealmResults;


public class StudentListViewAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<Student> innerStudentsList;

    public StudentListViewAdapter(Context context, RealmResults<Student> innerStudentsList) {
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
                Intent intent = new Intent(context, GoogleActivity.class);
                intent.putExtra("GOOGLE_LINK", student.getStudentsGooglePlus());
                context.startActivity(intent);
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
                context.startActivity(intent);
            }
        });
        return view;
    }
}

