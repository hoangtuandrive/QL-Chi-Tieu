package com.example.bainhom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class DVAdapter extends BaseAdapter {
    private int Layout;
    private FrnHome context;
    private List<DV> listdv;
    private ImageButton btnxoa,btnsua;
    public DVAdapter(int layout, FrnHome context, List<DV> listdv) {
        Layout = layout;
        this.context = context;
        this.listdv = listdv;
    }
    @Override
    public int getCount() {
        if(listdv.size() !=0 && !listdv.isEmpty())
        {
            return listdv.size();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(listdv.size() !=0 && !listdv.isEmpty())
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(Layout,parent,false);
        }
        final DV dv=listdv.get(position);
        TextView txtTien=(TextView)convertView.findViewById(R.id.Txt_Tien);
        TextView txtGD=(TextView)convertView.findViewById(R.id.Txt_Tengd);
        String Tien =Double.toString(dv.getTien());
        TextView txtGC=(TextView)convertView.findViewById(R.id.Txt__GhiChu);
        ImageView anh=(ImageView)convertView.findViewById(R.id.Img_anhGd);

        txtTien.setText(Tien);
        txtGD.setText(dv.getDichvu());
        txtGC.setText(dv.getGhichu());
        anh.setImageResource(dv.getAnh());
        btnxoa=(ImageButton) convertView.findViewById(R.id.Img_Xoa);
        btnsua=(ImageButton) convertView.findViewById(R.id.Img_Sua);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.opendialog(dv);
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            context.Xoa(dv);
            }
        });
        return convertView;
    }
}
