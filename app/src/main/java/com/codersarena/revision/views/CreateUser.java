package com.codersarena.revision.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codersarena.revision.R;
import com.codersarena.revision.viewmodel.MyViewModel;

public class CreateUser extends AppCompatActivity {
    EditText emailEt,nameEt,passwordEt;
    Button submitBtn;
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        emailEt = findViewById(R.id.emailEt1);
        passwordEt = findViewById(R.id.passwordEt1);
        nameEt = findViewById(R.id.nameEt1);
        submitBtn = findViewById(R.id.submitBtn1);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.createUser(emailEt.getText().toString().trim(),passwordEt.getText().toString().trim(),nameEt.getText().toString().trim());
            }
        });
    }
}