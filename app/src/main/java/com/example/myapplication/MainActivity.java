package com.example.myapplication;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText userNameET;
    private EditText pwdEt;
    private Button loginBtn;
    private TextView createAccountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
//        mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                }
//            }
//        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameET.getText().toString();
                String pwd = pwdEt.getText().toString();
                if(validateUserName(userName) && validatePassword(pwd)){
                    databaseReference.child(userName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if( task.isSuccessful() ){
                                User user = task.getResult().getValue(User.class);
                                if( user !=null && user.getPassword().equals(pwd)){
                                    Intent intent = new Intent(MainActivity.this,MathActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this,"Error Password",Toast.LENGTH_LONG).show();

                                }
                            }else{
                                Toast.makeText(MainActivity.this,"User Name Not Found",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }



            }
        });

        createAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent,67);
            }
        });

/*        databaseReference.child("a").setValue(5);
        databaseReference.child("b").setValue(61);
        databaseReference.child("c").setValue(8);
        databaseReference.child("d").child("o").setValue(89);
        databaseReference.child("aa").removeValue();
        databaseReference.child("points").child("p1").setValue(new Point(5,96));
        databaseReference.child("points").child("p2").setValue(new Point(-5,6));
        databaseReference.child("points").child("p3").setValue(new Point(5,-6));
        databaseReference.child("points").child("p4").setValue(new Point(45,16));
        databaseReference.child("email").setValue("weew.google.com");

        databaseReference.child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Point p = ds.getValue(Point.class);
                    Log.d("Points",p.toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/

    }

    private void init(){
        userNameET = findViewById(R.id.user_name);
        pwdEt = findViewById(R.id.password);
        loginBtn = findViewById(R.id.btn_login);
        createAccountTV = findViewById(R.id.create_account);
    }

    private boolean validateUserName(String userName){
        if( userName.isEmpty())
            return false;
        for(int i=0; i<userName.length(); i++ )
            if(userName.charAt(i) < 'a' || userName.charAt(i) > 'z')
                return false;
        return true;
    }
    private boolean validatePassword(String userName){
       return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String userName = data.getStringExtra("userName");
        String pwd = data.getStringExtra("pwd");
        userNameET.setText(userName);
        pwdEt.setText(pwd);
    }
}