package com.example.isao.twoactivities2.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.data.Student;

import java.util.ArrayList;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.MyViewHolder> {

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
                context.startActivity(
                        new Intent(
                                Intent.ACTION_VIEW, Uri.parse(
                                student.getStudentsGooglePlus())));
            }
        });
        holder.studentGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(
                        new Intent(
                                Intent.ACTION_VIEW, Uri.parse(
                                student.getStudentsGit())));
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
