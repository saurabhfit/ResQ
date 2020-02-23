package com.example.resq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText name, age, address, blood, relative1, relative2, relative3;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        register = (Button)findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.name);
        age = (EditText)findViewById(R.id.age);
        address = (EditText)findViewById(R.id.address);
        blood = (EditText)findViewById(R.id.blood);
        relative1 = (EditText)findViewById(R.id.relative1);
        relative2 = (EditText)findViewById(R.id.relative2);
        relative3 = (EditText)findViewById(R.id.relative3);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDetails();
            }
        });
    }

    private void submitDetails(){

        if(name.getText().toString().equals("")
                || age.getText().toString().equals("")
                || address.getText().toString().equals("")
                || blood.getText().toString().equals("")
                || relative1.getText().toString().equals("")
                || relative2.getText().toString().equals("")
                || relative3.getText().toString().equals("")){
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }else{
            CustomerDetails customer = new CustomerDetails(name.getText().toString(), age.getText().toString(), address.getText().toString(),
                                                            blood.getText().toString(), relative1.getText().toString(),
                                                            relative2.getText().toString(), relative3.getText().toString());

            String customerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Map<String, Object> map = new HashMap<>();
            map.put(customerID, customer);

            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference();
            current_user_db.child("Users").child("Customers").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SignUpActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    finish();
                }
            });
        }
    }
}
