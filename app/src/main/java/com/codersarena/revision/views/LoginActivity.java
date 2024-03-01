package com.codersarena.revision.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codersarena.revision.R;
import com.codersarena.revision.databinding.ActivityLoginBinding;
import com.codersarena.revision.viewmodel.MyViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    MyViewModel myViewModel;
    EditText emailEt,passwordEt;
    Button btn,createBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Intent i = new Intent(LoginActivity.this, GroupActivity.class);
            startActivity(i);
        }
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setVModel(myViewModel);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        btn = findViewById(R.id.submitBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.signUpUser(emailEt.getText().toString().trim(),passwordEt.getText().toString().trim());
            }
        });
        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,CreateUser.class);
                startActivity(i);
            }
        });

    }
}