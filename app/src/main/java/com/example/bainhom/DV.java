package com.example.bainhom;

import java.util.HashMap;
import java.util.Map;

public class DV {
    private String id;
    private double tien;
    private String dichvu;
    private String ghichu;
    private String ngay;
    private String thanhtoan;
    private int Anh;

     public DV(double tien, String dichvu, String ghichu, String ngay, String thanhtoan, int anh) {
         this.tien = tien;
         this.dichvu = dichvu;
         this.ghichu = ghichu;
         this.ngay = ngay;
         this.thanhtoan = thanhtoan;
         this.Anh = anh;
    }


    public int getAnh() {
        return Anh;
    }

    public void setAnh(int anh) {
        Anh = anh;
    }



    public DV(double tien, String dichvu, String ghichu, String ngay, String thanhtoan) {
        this.tien = tien;
        this.dichvu = dichvu;
        this.ghichu = ghichu;
        this.ngay = ngay;
        this.thanhtoan = thanhtoan;
    }
    public DV(){

    }
    public DV( String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTien() {
        return tien;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    public String getDichvu() {
        return dichvu;
    }

    public void setDichvu(String dichvu) {
        this.dichvu = dichvu;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(String thanhtoan) {
        this.thanhtoan = thanhtoan;
    }
    public   Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("ghichu",ghichu);
        result.put("tien",tien);
        return  result;
    }


}
