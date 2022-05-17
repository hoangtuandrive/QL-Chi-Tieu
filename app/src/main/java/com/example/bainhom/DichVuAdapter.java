package com.example.bainhom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DichVuAdapter extends BaseAdapter {
    private MainActivity2 context;
    private int idLayout;
    private List<DichVu> list;

    public DichVuAdapter(MainActivity2 context, int idLayout, List<DichVu> list){

        this.context =context;
        this.idLayout = idLayout;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list.size() !=0 && !list.isEmpty())
        {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(list.size() !=0 && !list.isEmpty())
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(idLayout,parent,false);
        }

        final DichVu dva=list.get(position);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_12);
        TextView text1 = (TextView) convertView.findViewById(R.id.ten);

        image.setImageResource(list.get(position).getHinh());
        text1.setText(list.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.ChuyenDuLieu(dva);
            }
        });
        return convertView;

    }
}
