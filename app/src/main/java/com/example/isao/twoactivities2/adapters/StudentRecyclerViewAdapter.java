package com.example.isao.twoactivities2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.activities.GithubActivity;
import com.example.isao.twoactivities2.activities.GoogleActivity;
import com.example.isao.twoactivities2.model.Student;

import java.util.ArrayList;

public class StudentRecyclerViewAdapter extends
        RecyclerView.Adapter<StudentRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Student> innerStudentsList;
    private Context context;
    private LayoutInflater inflater;

    public StudentRecyclerViewAdapter(Context context, ArrayList<Student> innerStudentsList) {
        this.context = context;
        this.innerStudentsList = innerStudentsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_students, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Student student = innerStudentsList.get(position);
        holder.studentName.setText(student.getStudentsName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoogleActivity.class);
                intent.putExtra("GOOGLE_LINK", student.getStudentsGooglePlus());
                context.startActivity(intent);
            }
        });
        holder.studentGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GithubActivity.class);
                intent.putExtra("GITHUB_LINK", student.getStudentsGit());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return innerStudentsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView studentName;
        Button studentGit;

        MyViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.wrapper);
            studentName = (TextView) itemView.findViewById(R.id.name_textview);
            studentGit = (Button) itemView.findViewById(R.id.git_button);
        }
    }
}
