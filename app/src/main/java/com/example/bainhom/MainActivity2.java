package com.example.bainhom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class MainActivity2 extends AppCompatActivity {
    private ListView lvContact;
    private DichVuAdapter adapter=null ;
    public static List<DichVu> list ;
    private ImageView imgve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgve=findViewById(R.id.imgVe);
        imgve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        lvContact =(ListView) findViewById(R.id.lv_dichvu);
        list = new ArrayList<DichVu>();
        list.add(new DichVu("Food&Beverage", R.drawable.rectangle_17));
        list.add(new DichVu("Shopping", R.drawable.rectangle_17__1_));
        list.add(new DichVu("Entertainment", R.drawable.rectangle_17__2_));
        list.add(new DichVu("Office Items", R.drawable.rectangle_17__3_));
        list.add(new DichVu("Electricity Bill", R.drawable.rectangle_17__4_));
        list.add(new DichVu("Phone Bill", R.drawable.rectangle_17__5_));
        list.add(new DichVu("Water Bill", R.drawable.rectangle_17__6_));
        list.add(new DichVu("Gas Bill", R.drawable.rectangle_17__7_));

        adapter  = new DichVuAdapter(this, R.layout.list_item1, list);
        lvContact.setAdapter(adapter);

    }
    public void ChuyenDuLieu(DichVu dva){
        Intent intent=new Intent(MainActivity2.this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("image", dva.getHinh());
        bundle.putString("name", dva.getName());


        intent.putExtras(bundle);

       // intent.putExtra("DichVu",dva);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}