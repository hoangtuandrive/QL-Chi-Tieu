package com.example.bainhom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FrmDangNhap extends AppCompatActivity {
    private EditText emailedit, passedit;
    private Button btnLogin;
    private TextView txt2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dang_nhap);
        mAuth = FirebaseAuth.getInstance();

        emailedit =findViewById(R.id.Edt_Emaildk);
        passedit = findViewById(R.id.Edt_Passdk);
        txt2 = findViewById(R.id.Txt_Tao);
        btnLogin = findViewById(R.id.Btn_SignIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrmDangNhap.this, FrmDangKy.class));

            }
        });


    }
    private void login() {
        String email, pass;
        email = emailedit.getText().toString();
        pass = passedit.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Vui lòng nhập email!!!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Vui lòng nhập password!!!",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FrmDangNhap.this, FrnHome.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}