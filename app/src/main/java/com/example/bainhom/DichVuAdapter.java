package com.example.bainhom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DichVuAdapter extends ArrayAdapter<DichVu> {
    private Activity activity;
    private int idLayout;
    private ArrayList<DichVu> list;

    public DichVuAdapter(Activity activity, int idLayout,
                         ArrayList<DichVu> list){
        super(activity, idLayout, list);
        this.activity =activity;
        this.idLayout = idLayout;
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(idLayout, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.image_12);
        TextView text1 = (TextView) convertView.findViewById(R.id.ten);

        image.setImageResource(list.get(position).getHinh());
        text1.setText(list.get(position).getName());

        return convertView;

    }
}
