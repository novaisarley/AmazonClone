package com.arley.amazonclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.RegionIterator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arley.amazonclone.R;
import com.arley.amazonclone.model.Product;
import com.arley.amazonclone.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText edtSenha, edtEmail, edtFullname;
    Button btSignup;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setComponentsId();
        setComponentsListeners();
    }

    private void setComponentsListeners() {
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtFullname.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    createNewUser(nome, email, senha);
                }else {
                    Toast.makeText(RegisterActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void setComponentsId() {
        edtEmail = findViewById(R.id.activity_register_edt_email);
        edtSenha = findViewById(R.id.activity_register_edt_senha);
        edtFullname = findViewById(R.id.activity_register_edt_fullname);

        btSignup = findViewById(R.id.activity_register_bt_signup);

        progressBar = findViewById(R.id.activity_register_progressBar);
    }

    private void createNewUser(String nome, String email, String senha) {
        mAuth.createUserWithEmailAndPassword(email, senha).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful())
                        {
                            String uid = task.getResult().getUser().getUid();

                            DatabaseReference reference = database.getReference("usuarios");
                            Usuario usuario = new Usuario(nome, email, " "
                                    , uid
                                    , new ArrayList<Product>(), new ArrayList<Product>());

                            reference.child(uid).setValue(usuario);

                            Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}