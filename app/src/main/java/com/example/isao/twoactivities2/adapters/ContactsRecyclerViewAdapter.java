package com.example.isao.twoactivities2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.model.Contact;

import java.util.ArrayList;


public class ContactsRecyclerViewAdapter extends
        RecyclerView.Adapter<ContactsRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Contact> contactArrayList;
    private Context context;
    private LayoutInflater inflater;

    public ContactsRecyclerViewAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contactArrayList = contacts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Contact contact = contactArrayList.get(position);
        holder.nameView.setText(contact.getContactName());
        holder.numberView.setText(contact.getContactNumber());
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView nameView;
        TextView numberView;

        MyViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.contact_wrapper);
            nameView = (TextView) itemView.findViewById(R.id.contact_name);
            numberView = (TextView) itemView.findViewById(R.id.contact_number);
        }
    }


}
