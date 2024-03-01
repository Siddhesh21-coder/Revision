package com.codersarena.revision.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codersarena.revision.R;
import com.codersarena.revision.model.Image;
import com.codersarena.revision.model.Topic;
import com.codersarena.revision.viewmodel.MyViewModel;
import com.codersarena.revision.views.adapter.TopicAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyViewModel myViewModel;
    private ArrayList<Topic> topicArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String value ="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        recyclerView = findViewById(R.id.recyclerCardView);
        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getStringExtra("GroupName") + intent.getStringExtra("TopicName");
        }

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DisplayActivity.this);
        FloatingActionButton fab = findViewById(R.id.fabId2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName="";
                String topicName = "";
                Intent ii = getIntent();
                Bundle b = ii.getExtras();
                if (b!=null){
                    groupName = (String)b.get("GroupName");
                    topicName = (String)b.get("TopicName");
                }
                Intent i = new Intent(DisplayActivity.this,AddTopicActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("TopicName",topicName);
                startActivity(i);


            }
        });

        myViewModel.getTopicList(value).observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(List<Topic> topics) {
                topicArrayList = new ArrayList<>();
                topicArrayList.addAll(topics);
                TopicAdapter topicAdapter = new TopicAdapter(topicArrayList,DisplayActivity.this);
                recyclerView.setAdapter(topicAdapter);
                topicAdapter.notifyDataSetChanged();

            }
        });

        recyclerView.setLayoutManager(layoutManager);
    }
//    private List<Topic> TopicItemList(){
//        List<Topic> topic = new ArrayList<>();
//        Topic t1 = new Topic("www.google.com/",new ArrayList<>(ImageItemList()));
//        topic.add(t1);
//        Topic t2 = new Topic("Link2",new ArrayList<>(ImageItemList()));
//        topic.add(t2);
//        return topic;
//
//    }
//    private List<Image> ImageItemList()
//    {
//        List<Image> imageList = new ArrayList<>();
//        imageList.add(new Image("https://imgd.aeplcdn.com/1056x594/n/cw/ec/44686/activa-6g-right-front-three-quarter.jpeg"));
//        imageList.add(new Image("https://i.pinimg.com/736x/ee/5f/88/ee5f88d99c43b7fad0ebe85518ea1fa1.jpg"));
//        return imageList;
//    }
}