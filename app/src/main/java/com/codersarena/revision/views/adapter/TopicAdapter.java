package com.codersarena.revision.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codersarena.revision.R;
import com.codersarena.revision.model.Topic;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder> {

    private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<Topic> topicArrayList;
    private Context context;

    public TopicAdapter(ArrayList<Topic> topicArrayList, Context context) {
        this.topicArrayList = topicArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Topic topic = topicArrayList.get(position);
        holder.textView.setText(topic.getLink());
        holder.textView2.setText(topic.getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL,false
                );
        linearLayoutManager.setInitialPrefetchItemCount(topic.getImages().size());
        ImageAdapter imageAdapter = new ImageAdapter(context,topic.getImages());
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(imageAdapter);
        holder.recyclerView.setRecycledViewPool(recycledViewPool);


    }

    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView,textView2;
        public RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewLink);
            textView2 = itemView.findViewById(R.id.textViewLink1);
            recyclerView = itemView.findViewById(R.id.image_recycler_view);
        }
    }
}
