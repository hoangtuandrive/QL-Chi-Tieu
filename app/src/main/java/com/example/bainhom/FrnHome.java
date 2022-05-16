package com.example.bainhom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FrnHome extends AppCompatActivity {
    Button btnAdd,btnEdit,btnLog;
    private List<DV> listdv;
    private ListView lvwdv;
    private  DVAdapter adapter;
    private DV dv;
    private Vi v;
    private TextView txtViTien;
    private SQLiteDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frn_home);
        Intent intentLog=new Intent(this,
                FrmDangNhap.class);
        Intent intentAdd=new Intent(this,MainActivity.class);
        FindId();
        dv=new DV();
        v=new Vi();
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentLog);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentAdd);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double tien= Double.parseDouble(txtViTien.getText().toString());

                    opendialogVi(v);

            }
        });

        listdv=new ArrayList<>();
        lvwdv=(ListView)findViewById(R.id.listItem_gd);
        //  listdv.add(new Dv(100,"$ 350"));
        //listdv.add(new Dv(840,"$ 950"));
        getdataBase(dv);

        adapter=new DVAdapter(R.layout.itemgd,FrnHome.this,listdv);
        lvwdv.setAdapter(adapter);
        GetData();

        //Khởi tạo sql
        db = new SQLiteDBHandler(getApplicationContext());
    }


    private void getdataBase(DV dv) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String  pathObject= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRefup = database.getReference("DanhSach/"+pathObject);

        /*myRefup.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listdv!=null){
                    listdv.clear();
                }
                for(DataSnapshot data : snapshot.getChildren()){
                        Dv dv=data.getValue(Dv.class);
                        listdv.add(dv);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        myRefup.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DV dv=snapshot.getValue(DV.class);
                if(dv !=null){
                    listdv.add(dv);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DV dv=snapshot.getValue(DV.class);
                if(dv ==null||listdv==null||listdv.isEmpty()){
                    return;
                }
                for (int i=0;i<listdv.size();i++){
                    if(dv.getId()==listdv.get(i).getId()){
                        listdv.set(i,dv);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                DV dv=snapshot.getValue(DV.class);
                if(dv ==null||listdv==null||listdv.isEmpty()){
                    return;
                }
                for (int i=0;i<listdv.size();i++){
                    if(dv.getId()==listdv.get(i).getId()){
                        listdv.remove(listdv.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void opendialog(DV dv){
        final Dialog dialog =new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialogsua);
        Window window=dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText updateDv=dialog.findViewById(R.id.Edt_DichVudia);
        EditText updateTien=dialog.findViewById(R.id.Edt_Tiendia);
        Button btnHuy=dialog.findViewById(R.id.Btn_Huydia);
        Button btnUpdate=dialog.findViewById(R.id.Btn_Suadia);

        updateDv.setText(dv.getDichvu());
        updateTien.setText(Double.toString(dv.getTien()));
        double Tiencu= dv.getTien();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRefup = database.getReference("DanhSach");
                String  pathObject= FirebaseAuth.getInstance().getCurrentUser().getUid();
                String newDv=updateDv.getText().toString().trim();
                Double  newTien=Double.parseDouble(updateTien.getText().toString());
                dv.setDichvu(newDv);
                dv.setTien(newTien);

                myRefup.child(pathObject).child(String.valueOf(dv.getId())).updateChildren(dv.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        double TienCanSua=Tiencu-newTien;
                        if(TienCanSua>0){
                            XoaTienTrongViKhiEditList(TienCanSua);
                            Toast.makeText(FrnHome.this, "Duong", Toast.LENGTH_SHORT).show();
                        }
                        else if(TienCanSua<0){
                            XoaTienTrongViKhiEditList(TienCanSua);
                            Toast.makeText(FrnHome.this, "Am", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(FrnHome.this, "Update Thanh Cong"+dv.getTien(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        dialog.show();
    }
    public void opendialogVi(Vi v){
        final Dialog dialog =new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogeditvi);
        Window window=dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);


        EditText updateTienVi=dialog.findViewById(R.id.Edt_Tientrongvi);
        Button btnHuy=dialog.findViewById(R.id.Btn_Huydia);
        Button btnUpdate=dialog.findViewById(R.id.Btn_Suadia);


        updateTienVi.setText(Double.toString(v.getViTien()));

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRefup = database.getReference("DanhSachVi");
                String  pathObject= FirebaseAuth.getInstance().getCurrentUser().getUid();

                Double  newTien=Double.parseDouble(updateTienVi.getText().toString());
                v.setViTien(newTien);
                myRefup.child(pathObject).updateChildren(v.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(FrnHome.this, "Update Thanh Cong", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        dialog.show();
    }

    public  void GetData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefupa = database.getReference("DanhSachVi");
        String pathObject = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRefupa.child(pathObject).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Vi value = dataSnapshot.getValue(Vi.class);
                txtViTien.setText(Double.toString(value.getViTien()));
                // Toast.makeText(FrmTien.this, ""+value.getViTien(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
    public void XoaTienTrongViKhiEditList(double NewTien){

        double TienVi = Double.parseDouble(txtViTien.getText().toString());
        double  TienSua=TienVi+NewTien;
        v.setViTien(TienSua);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefup = database.getReference("DanhSachVi");
        String  pathObject= FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRefup.child(pathObject).updateChildren(v.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(FrnHome.this, "Update Thanh Cong", Toast.LENGTH_SHORT).show();
                GetData();
            }
        });

    }
    public void Xoa(DV dv){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefXoa = database.getReference("DanhSach");
        String  pathObject= FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRefXoa.child(pathObject).child(String.valueOf(dv.getId())).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                XoaTienTrongViKhiEditList(dv.getTien());
                Toast.makeText(FrnHome.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                //Delete SQL
                db.deleteDV(dv.getId());
            }
        });

    }
    public  void FindId(){
        btnAdd=findViewById(R.id.Btn_AddSe);
        btnEdit=findViewById(R.id.Btn_EditBa);
        btnLog=findViewById(R.id.Btn_LogOut);
        txtViTien=findViewById(R.id.Txt_ViTien);
    }
}