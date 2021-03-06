package com.example.ankitnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextEmpno;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRootRef = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextEmail = (EditText)findViewById(R.id.edittext_mailid);
        mTextEmpno = (EditText)findViewById(R.id.edittext_empno);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mTextCnfPassword = (EditText)findViewById(R.id.edittext_cnf_password);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);
        db= new DatabaseHelper(this);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(LoginIntent);
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();
                String e_mail = mTextEmail.getText().toString().trim();
                String e = mTextEmpno.getText().toString().trim();
                long emp=Long.parseLong(e);

                if(pwd.equals(cnf_pwd)){

//                    Employee employee = new Employee(emp, e_mail, pwd, user);
//                    Log.i("asdf", employee.toString());
//                    mRootRef.child("User-Credentials").child(employee.getUserId()).setValue(employee.getEmail(), mCompletionListener);
//                    mRootRef.push();

                    long val = db.addUser(user,pwd,e_mail,emp);
                    if(val > 0) {
                        Toast.makeText(RegisterActivity.this,"You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    DatabaseReference.CompletionListener mCompletionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            if(error != null) {
                Log.e("asdf", error.getMessage());
                Toast.makeText(getApplicationContext(), "Error while reading/writing to DB", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
