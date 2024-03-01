package com.codersarena.revision.views.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codersarena.revision.R;
import com.codersarena.revision.databinding.GroupListBinding;
import com.codersarena.revision.model.SubjectGroup;
import com.codersarena.revision.views.DisplayActivity;
import com.codersarena.revision.views.SubTopic;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {
    private ArrayList<SubjectGroup> groupArrayList;


    public GroupAdapter(ArrayList<SubjectGroup> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.group_list,
                parent,
                false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SubjectGroup group = groupArrayList.get(position);
        holder.groupListBinding.setSubjectGroup(group);

    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private GroupListBinding groupListBinding;

        public MyViewHolder(GroupListBinding groupListBinding) {
            super(groupListBinding.getRoot());
            this.groupListBinding = groupListBinding;
            groupListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();
                    SubjectGroup subjectGroup = groupArrayList.get(position);
                    Intent i = new Intent(itemView.getContext(), SubTopic.class);
                    i.putExtra("GroupName",subjectGroup.getGroupName());
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
