package com.example.bainhom;

import java.util.HashMap;
import java.util.Map;

public class Vi {
    private double ViTien;
    public Vi(double viTien) {
        ViTien = viTien;
    }

    public double getViTien() {
        return ViTien;
    }

    public void setViTien(double viTien) {
        ViTien = viTien;
    }
    public Vi(){

    }
    public Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("viTien",ViTien);
        return  result;
    }

}
