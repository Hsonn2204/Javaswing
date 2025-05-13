/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package detaithltm;
/**
 *

 */
public class CauHoi {
    private String cauhoi;
    private String[] tuychon;
    private String dapan;

    public String getDecauhoi() {
        return cauhoi;
    }

    public String[] getTuychon() {
        return tuychon;
    }

    public String getDapandung() {
        return dapan;
    }

    public CauHoi(String decauhoi, String[] tuychon, String dapandung) {
        this.cauhoi = decauhoi;
        this.tuychon = tuychon;
        this.dapan = dapandung;
    }

    
     public String getPhuongAnA() {
        return tuychon[0]; // Lấy phương án A từ mảng options
    }

    public String getPhuongAnB() {
        return tuychon[1]; // Lấy phương án B từ mảng options
    }

    public String getPhuongAnC() {
        return tuychon[2]; // Lấy phương án C từ mảng options
    }

    public String getPhuongAnD() {
        return tuychon[3]; // Lấy phương án D từ mảng options
    }


 
}
