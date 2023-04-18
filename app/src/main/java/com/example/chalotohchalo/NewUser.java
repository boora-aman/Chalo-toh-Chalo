package com.example.chalotohchalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewUser extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button signup_button;
    TextInputLayout newUser_fullName, newUser_Username, newUser_email, newUser_phoneN, newUser_password;
    TextView login_redirect;

    public class App extends Application {
        @Override
        public void onCreate() {
            super.onCreate();

            FirebaseApp.initializeApp(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this, "User Already Exists!", Toast.LENGTH_SHORT).show();
            Intent new_intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(new_intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        mAuth = FirebaseAuth.getInstance();
        newUser_fullName = findViewById(R.id.newUser_fullName);
        newUser_email = findViewById(R.id.newUser_email);
        //newUser_Username = findViewById(R.id.newUser_Username);
        newUser_password = findViewById(R.id.newUser_password);
        newUser_phoneN = findViewById(R.id.newUser_phoneN);

        signup_button = findViewById(R.id.signup_button);

        login_redirect = findViewById(R.id.login_redirect);
        login_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUser.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, username, email, phone, password;
                name = newUser_fullName.getEditText().getText().toString();
                username = newUser_Username.getEditText().getText().toString();
                email = newUser_email.getEditText().getText().toString();
                phone =newUser_phoneN.getEditText().getText().toString();
                password = newUser_password.getEditText().getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(NewUser.this, "Please Provide Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(NewUser.this, "Please Provide Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(NewUser.this, "Please Provide Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(NewUser.this, "Please Provide Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(NewUser.this, "Please Provide Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() != 10) {
                    Toast.makeText(NewUser.this, "Phone number should be 10 digits.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uid = user.getUid();

                                    User newUser = new User(name, username, email, phone, password);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference usersRef = database.getReference("users").child(user.getUid());

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).build();

                                    user.updateProfile(profileUpdates);

                                    usersRef.child(uid).setValue(newUser);

                                    Toast.makeText(NewUser.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(NewUser.this, LoginPage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(NewUser.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
