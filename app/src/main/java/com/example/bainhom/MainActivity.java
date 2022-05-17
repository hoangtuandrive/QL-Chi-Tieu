package com.example.bainhom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView txtSave;
    EditText edtTien,edtDv,edtGhiChu,edtNgay,edtThanhToan,edtMail;
    ImageView imgout,imgchon;
    private Vi v;
    private double TienVi;
    private static final int REQUEST_CODE_EDIT =100;
    private SQLiteDBHandler db;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference mData ;
    Button btnCam, btnTV ;
    ImageView imgHinh;
    FrmDangNhap frmDangNhap;
    int REQUEST_CODE_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PHẦN TÔNG LÀM   ---------------------------------------------------------------------
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bainhom-baf47.appspot.com/");
        mData = FirebaseDatabase.getInstance().getReference();

        btnCam =findViewById(R.id.btnCam);
        edtMail = findViewById(R.id.Edt_Emaildk);
        btnTV = findViewById(R.id.btnGa);
        imgHinh = findViewById(R.id.imageView);
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        btnTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_IMAGE);
            }
        });

        //--------------


        TienVi=0;
        FiniID();
        //Khởi tạo sql
        db = new SQLiteDBHandler(getApplicationContext());


        GetData();
        v=new Vi();
        Intent intentout=new Intent(this,FrnHome.class);

        imgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentout);

            }
        });
        getIntent();
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              DV dv1= ThemDv();
//PHẦN TÔNG LÀM ----------------------------------------------
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child(edtGhiChu.getText().toString().trim()+".png");
                imgHinh.setDrawingCacheEnabled(true);
                imgHinh.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgHinh.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(MainActivity.this, "Lỗi lưu hình ảnh!!!", Toast.LENGTH_SHORT).show();
                    }
                });
                mountainsRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl = uri;
                                Log.d("AAAA", downloadUrl + "");

                                //tạo node trên database
                                HinhAnh hinhAnh = new HinhAnh(String.valueOf(downloadUrl), edtGhiChu.getText().toString());
                                String pathObject = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                mData.child("HinhAnh").child(pathObject).child(edtNgay.getText().toString()).setValue(hinhAnh, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {
                                            Toast.makeText(MainActivity.this, "Lưu hình ảnh thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Lỗi lưu hình ảnh", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
                    }
                });
                //Insert SQL
                db.addDV(dv1);
                Toast.makeText(MainActivity.this, "Thêm record thành công", Toast.LENGTH_SHORT).show();
                //------------------------------------------------
            }
        });


        imgchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGd=new Intent(MainActivity.this,MainActivity2.class);
                startActivityForResult(intentGd,REQUEST_CODE_EDIT);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_EDIT ){

            int idImage = data.getIntExtra("image",0);
            String name = data.getStringExtra("name");

            imgchon.setImageResource(idImage);
            edtDv.setText(name);
        }
        else {

        }


        super.onActivityResult(requestCode, resultCode, data);
        //Phần Tông làm ------------------------------------------------------------------------------
        if(requestCode==0){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");//image bitmap file
            Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap,680,500,false);
            imgHinh.setImageBitmap(resizeBitmap);
        }
        else if(requestCode ==REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap,680,500,false);
                imgHinh.setImageBitmap(resizeBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //--------------------------------------------------------------------------
    }



    public void FiniID(){
        txtSave=findViewById(R.id.Txt_Save);
        edtTien=findViewById(R.id.Edt_Tien);
        edtDv=(EditText) findViewById(R.id.Edt_Gd);
        edtGhiChu=findViewById(R.id.Edt_GhiChu);
        edtNgay=findViewById(R.id.Edt_Ngay);
        edtThanhToan=findViewById(R.id.Edt_ThanhToan);
        imgout=findViewById(R.id.Img_Out);
        imgchon=(ImageView) findViewById(R.id.ImgChon);
    }
    private DV ThemDv(){
        double tien=Double.parseDouble(edtTien.getText().toString());
        String dichvu=edtDv.getText().toString();
        String ghichu=edtGhiChu.getText().toString();
        String ngay=edtNgay.getText().toString();
        String thanhtoan=edtThanhToan.getText().toString();
        DV dv=new DV(tien,dichvu,ghichu,ngay,thanhtoan);
        return dv;
    }

    private void AddDv(DV dv){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DanhSach");
        String  pathObject= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String id= myRef.push().getKey();
        dv.setId(id);
        myRef.child(pathObject).child(String.valueOf(dv.getId())).setValue(dv, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                SuaTienTrongViKhiEditList(Double.parseDouble(edtTien.getText().toString()));
                Toast.makeText(MainActivity.this, "Luu thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void SuaTienTrongViKhiEditList(double NewTien) {

        double TienSua = TienVi - NewTien;
        v.setViTien(TienSua);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefup = database.getReference("DanhSachVi");
        String pathObject = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRefup.child(pathObject).updateChildren(v.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
               // Toast.makeText(MainActivity.this, "Update Thanh Cong:Vi:"+TienVi+"Sua:"+TienSua, Toast.LENGTH_SHORT).show();

            }
        });
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
                TienVi= value.getViTien();
                // Toast.makeText(MainActivity.this, ""+TienVi, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}