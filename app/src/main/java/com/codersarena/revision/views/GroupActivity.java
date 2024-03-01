package com.codersarena.revision.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import com.codersarena.revision.databinding.ActivityGroupBinding;
import com.codersarena.revision.model.SubjectGroup;
import com.codersarena.revision.model.User;
import com.codersarena.revision.viewmodel.MyViewModel;
import com.codersarena.revision.views.adapter.GroupAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GroupActivity extends AppCompatActivity {
    private ArrayList<SubjectGroup> groupArrayList;
    private ProgressBar loadingIndicator;
    private GroupAdapter adapter;
    private Dialog groupDialog;
    private RecyclerView recyclerView;
    private ActivityGroupBinding binding;
    private MyViewModel myViewModel;
    private TextView textViewG1,textViewG2;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.INVISIBLE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textViewG1 = findViewById(R.id.textViewG1);
        textViewG2 = findViewById(R.id.textViewG2);

        fab = findViewById(R.id.fabid001);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users/"+userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                loadingIndicator.setVisibility(View.VISIBLE);
                User user1 = snapshot.getValue(User.class);
                textViewG1.setText("Hey,\t\t\t");
                textViewG2.setText(user1.getName());
//                loadingIndicator.setVisibility(View.INVISIBLE);



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingIndicator.setVisibility(View.GONE);
            }
        });



        myViewModel.getGroupList().observe(this, new Observer<List<SubjectGroup>>() {
            @Override
            public void onChanged(List<SubjectGroup> subjectGroups) {
                groupArrayList = new ArrayList<>();
                groupArrayList.addAll(subjectGroups);
                adapter = new GroupAdapter(groupArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void showDialog() {
        groupDialog = new Dialog(this);
        groupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


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
                String groupName = edt.getText().toString().trim();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Toast.makeText(GroupActivity.this,"You Subject name",Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Groups/"+userId);
                String randomKey = ref.push().getKey();
                SubjectGroup subject = new SubjectGroup(groupName);
                ref.child(randomKey).setValue(subject);
                groupDialog.dismiss();
            }
        });

    }
}