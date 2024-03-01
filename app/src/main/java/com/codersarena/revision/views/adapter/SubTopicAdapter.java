package com.codersarena.revision.views.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codersarena.revision.R;
import com.codersarena.revision.databinding.SubtopicListBinding;

import com.codersarena.revision.model.SubTopicM;
import com.codersarena.revision.views.DisplayActivity;

import java.util.ArrayList;

public class SubTopicAdapter extends RecyclerView.Adapter<SubTopicAdapter.MyViewHolder>{
    private ArrayList<SubTopicM> subTopicArrayList;

    public SubTopicAdapter(ArrayList<SubTopicM> subTopicArrayList) {
        this.subTopicArrayList = subTopicArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubtopicListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.subtopic_list,
                parent,
                false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SubTopicM topic = subTopicArrayList.get(position);
        holder.subTopicBinding.setSubTopic(topic);

    }

    @Override
    public int getItemCount() {
        return subTopicArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SubtopicListBinding subTopicBinding;

        public MyViewHolder( SubtopicListBinding subTopicBinding) {
            super(subTopicBinding.getRoot());
            this.subTopicBinding = subTopicBinding;
            this.subTopicBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    SubTopicM subTopic = subTopicArrayList.get(position);
                    Intent i = new Intent(itemView.getContext(), DisplayActivity.class);
                    i.putExtra("GroupName",subTopic.getGroup());
                    i.putExtra("TopicName",subTopic.getTopic());
                    itemView.getContext().startActivity(i);

                }
            });
        }
    }
}
