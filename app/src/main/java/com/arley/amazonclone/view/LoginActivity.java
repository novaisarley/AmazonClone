package com.arley.amazonclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arley.amazonclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btSignin;
    TextView tvGoSignup;
    EditText edtSenha, edtEmail;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        setComponentsId();
        setComponentsListeners();
    }

    private void setComponentsListeners() {
        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if (!email.isEmpty() && !senha.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    signInUser(email, senha);
                }else {
                    Toast.makeText(LoginActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

        tvGoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void setComponentsId() {
        btSignin = findViewById(R.id.activity_login_bt_signin);
        tvGoSignup = findViewById(R.id.activity_login_tv_signup);
        edtEmail = findViewById(R.id.activity_login_edt_email);
        edtSenha = findViewById(R.id.activity_login_edt_senha);
        progressBar = findViewById(R.id.activity_login_progressBar);
    }

    private void signInUser(String email, String senha){

        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser = task.getResult().getUser();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");
                    databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressBar.setVisibility(View.GONE);
                            if (snapshot.exists()){
                                Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }else {
                                mAuth.signOut();
                                Toast.makeText(LoginActivity.this, "Invalid account!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            Toast.makeText(LoginActivity.this, "Logged in as " + firebaseUser.getEmail(), Toast.LENGTH_LONG ).show();
            finish();
        }
    }
}