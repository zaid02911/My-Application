package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText userNameET;
    private EditText emailET;
    private EditText pwdET;
    private Button regBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName =firstNameET.getText().toString();
                String lastName =lastNameET.getText().toString();
                String userName =userNameET.getText().toString();
                String email =emailET.getText().toString();
                String pwd =pwdET.getText().toString();

                if( validatePassword(pwd) && validateEmail(email) &&
                    validateUserName(userName) && validateName(firstName) &&
                        validateName(lastName)) {

                    databaseReference.child(userName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful() && task.getResult().exists()) {
                                Toast.makeText(RegisterActivity.this, "User Name Exists", Toast.LENGTH_LONG).show();
                            } else {
                                User user = new User(firstName, lastName, email, userName, pwd);
                                databaseReference.child(userName).setValue(user);
                                Intent data = new Intent();
                                data.putExtra("userName", userName);
                                data.putExtra("pwd", pwd);
                                setResult(RESULT_OK, data);
                                finish();
                            }

                        }
                    });
                }
            }
        });



    }

    public void init(){
        firstNameET=findViewById(R.id.first_name_r);
        lastNameET=findViewById(R.id.last_name_r);
        userNameET=findViewById(R.id.user_name_r);
        pwdET=findViewById(R.id.password_r);
        emailET=findViewById(R.id.email_r);
        regBtn = findViewById(R.id.btn_reg);
    }
    private boolean validateName(String name){
        return true;
    }
    private boolean validateUserName(String userName){
        return true;
    }
    private boolean validatePassword(String pwd){
        return true;
    }
    private boolean validateEmail(String email){
        return true;
    }
}