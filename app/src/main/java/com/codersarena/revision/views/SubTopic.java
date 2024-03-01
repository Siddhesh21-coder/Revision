package com.codersarena.revision.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codersarena.revision.R;

import com.codersarena.revision.databinding.ActivitySubTopicBinding;
import com.codersarena.revision.model.SubTopicM;
import com.codersarena.revision.model.SubjectGroup;
import com.codersarena.revision.viewmodel.MyViewModel;
import com.codersarena.revision.views.adapter.GroupAdapter;
import com.codersarena.revision.views.adapter.SubTopicAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SubTopic extends AppCompatActivity {

    private ArrayList<SubTopicM> subTopicList;
    private ProgressBar loadingIndicator;
    private SubTopicAdapter adapter;
    private Dialog groupDialog;
    private RecyclerView recyclerView;
    private ActivitySubTopicBinding binding;
    private MyViewModel myViewModel;
    private TextView textViewG1,textViewG2;
    FloatingActionButton fab;
    String group="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_topic);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sub_topic);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        recyclerView = binding.recyclerView1;
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        textViewG1 = findViewById(R.id.textViewG11);
        textViewG2 = findViewById(R.id.textViewG21);
        String group = getIntent().getStringExtra("GroupName");
        textViewG2.setText(group);
        fab = findViewById(R.id.fabid0011);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        Intent i = getIntent();
        String groupName = i.getStringExtra("GroupName");
        group = groupName.trim();
        myViewModel.getSubTopicList(groupName).observe(this, new Observer<List<SubTopicM>>() {
            @Override
            public void onChanged(List<SubTopicM> subTopicMS) {
                subTopicList = new ArrayList<>();
                subTopicList.addAll(subTopicMS);
                adapter = new SubTopicAdapter(subTopicList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


    }

    private void showDialog() {
        groupDialog = new Dialog(this);
        groupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        group = getIntent().getStringExtra("GroupName").trim();

        View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout,
                        null);
        groupDialog.setContentView(view);
        groupDialog.show();
        Button button = view.findViewById(R.id.submit_btn);
        EditText edt = view.findViewById(R.id.chat_group_edt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = edt.getText().toString().trim();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.v("Group",group);
                Toast.makeText(SubTopic.this,"Your SubTopic name "+ topic,Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SubTopic/"+userId+"/"+group);
                String randomKey = ref.push().getKey();
                SubTopicM subTopic = new SubTopicM(group,topic);
                ref.child(randomKey).setValue(subTopic);
                groupDialog.dismiss();
            }
        });

    }
}