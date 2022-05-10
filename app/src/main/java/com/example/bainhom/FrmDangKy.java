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

public class FrmDangKy extends AppCompatActivity {
    private EditText email,pass;
    private Button btnDK;
    private TextView txt;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dang_ky);
        mAuth=FirebaseAuth.getInstance();

        email = findViewById(R.id.Edt_Emaildk);
        pass= findViewById(R.id.Edt_Passdk);
        txt = findViewById(R.id.Txt_Tao);
        btnDK = findViewById(R.id.Btn_SignIn);

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrmDangKy.this, FrmDangNhap.class));

            }
        });

    }

    private void register() {
        String email1,pass1;
        email1 =email.getText().toString();
        pass1 = pass.getText().toString();

        if(TextUtils.isEmpty(email1)){
            Toast.makeText(this, "Vui lòng nhập email!!!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass1)){
            Toast.makeText(this, "Vui lòng nhập password!!!",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email1,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(FrmDangKy.this, FrmDangNhap.class);
                    startActivity(intent);
                    Toast.makeText(FrmDangKy.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                } else {
                    Toast.makeText(FrmDangKy.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}