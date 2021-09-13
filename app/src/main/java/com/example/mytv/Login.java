package com.example.mytv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView signup_text,forgotpwd;
    Button login_button;
    EditText Email_et2,pwd_et2;
    FirebaseAuth mAuth;
    String loginemail, loginpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup_text=findViewById(R.id.signUp_text);
        login_button=findViewById(R.id.login_button);
        forgotpwd=findViewById(R.id.forgotpwd);
        Email_et2=findViewById(R.id.Email_et2);
        pwd_et2=findViewById(R.id.pwd_et2);
        mAuth=FirebaseAuth.getInstance();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()){
                    return;
                }
                mAuth.signInWithEmailAndPassword(loginemail,loginpassword).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPwdPage.class);
                startActivity(intent);
            }
        });


        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SignupPage.class);
                startActivity(intent);
            }
        });
    }


    private boolean validateEmail()
    {
        loginemail=Email_et2.getText().toString().trim();
        if(TextUtils.isEmpty(loginemail)){
            Toast.makeText(Login.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if(!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()){
            Toast.makeText(Login.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    private boolean validatePassword() {
        loginpassword = pwd_et2.getText().toString().trim();

        if (TextUtils.isEmpty(loginpassword)) {
            Toast.makeText(Login.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }
}