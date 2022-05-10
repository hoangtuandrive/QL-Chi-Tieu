package com.example.bainhom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ListView lvContact;
    private DichVuAdapter adapter=null ;
    public static ArrayList<DichVu> list = new ArrayList<DichVu>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lvContact =(ListView) findViewById(R.id.lv_dichvu);
        list = new ArrayList<DichVu>();
        list.add(new DichVu("Food&Beveraga", R.drawable.rectangle_17));
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
}