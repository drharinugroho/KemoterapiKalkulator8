package com.kemoterapi.android.kalkulatorkemoterapi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;
import androidx.appcompat.app.AppCompatActivity;

public class EMACO extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emaco);

    }

    /**
     * Fungsi tombol hitung
     */

    public void klikHitung (View view) {

        //mengubah input usia ke variable finalUsiaPasien
        EditText usia = (EditText) findViewById(R.id.usia);
        int usiaPasien = Integer.parseInt(usia.getText().toString());

        //mengubah input berat badan ke variable finalBeratBadan
        EditText berat = (EditText) findViewById(R.id.beratBadan);
        double beratBadan = Double.parseDouble(berat.getText().toString());

        //mengubah input tinggi badan ke variable finalTinggiBadan
        EditText tinggi = (EditText) findViewById(R.id.tinggiBadan);
        double tinggiBadan = Double.parseDouble(tinggi.getText().toString());

        //mengubah input serum Kreatinin ke variable finalSerumKreatinin
        EditText kadarSK = (EditText) findViewById(R.id.serumKreatinin);
        double serumKreatinin = Double.parseDouble(kadarSK.getText().toString());


        //menghitung IMT
        double IMT = hitungIMT(beratBadan, tinggiBadan);
        double isiIMTbulatFinal = pembulatanDuaDesimal(IMT);

        //menampilkan IMT
        TextView viewIMT = (TextView) findViewById(R.id.IndeksMassaTubuh);
        viewIMT.setText((double) isiIMTbulatFinal + " kg/m2");

        //Hitung LPT
        double LPT = hitungLPT(beratBadan, tinggiBadan);
        double luasPermukaanTubuhBulatFinal = pembulatanDuaDesimal(LPT);

        //menampilkan Luas Permukaan Tubuh
        TextView viewLPT = (TextView) findViewById(R.id.LuasPermukaanTubuh);
        viewLPT.setText((double) luasPermukaanTubuhBulatFinal + " m2");

        //Hitung GFR
        double GFR = hitungGFR(usiaPasien, beratBadan, serumKreatinin);
        double GFRBulatFinal = pembulatanDuaDesimal(GFR);

        //menampilkan GFR
        TextView viewGFR = (TextView) findViewById(R.id.GFR_Normal);
        viewGFR.setText((double) GFRBulatFinal + " mL/min");

        //Hitung GFR Obese
        double GFRobese = hitungGFRobese(usiaPasien, beratBadan, tinggiBadan, serumKreatinin);
        double GFRObeseBulatFinal = pembulatanDuaDesimal(GFRobese);

        //menampilkan GFR Obese
        TextView viewGFRobese = (TextView) findViewById(R.id.GFR_Obese);
        viewGFRobese.setText((double) GFRObeseBulatFinal + " mL/min");


        //menghitung dosis Etoposide = 100 mg/m2
        double dosisEtoposide = LPT * 100;

        //menampilkan kadar Etoposide
        TextView kadarEtoposide = (TextView) findViewById(R.id.etoposide);
        kadarEtoposide.setText((int)dosisEtoposide + " mg");

        //menghitung dosis Mtx IM = 100 mg/m2
        double dosisMtxIM = LPT * 100;

        //menampilkan dosis Mtx Ld
        TextView kadarMtxIM = (TextView) findViewById(R.id.mtxIM);
        kadarMtxIM.setText((int) dosisMtxIM + " mg");

        //menghitung dosis Mtx IV = 200 mg/m2
        double dosisMtxIV = LPT * 200;

        //menampilkan dosis Mtx IV
        TextView kadarMtxIV = (TextView) findViewById(R.id.mtxIV);
        kadarMtxIV.setText((int) dosisMtxIV + " mg");

        //dosis Leucovorin fix 15 mg
        //dosis Dactinomycin fix 0,5 mg

        //menghitung dosis Cyclophosphamide = LPT x 600
        double dosisCyclophophamide = LPT * 600;

        //menampilkan kadar Cyclophosphamide
        TextView kadarCyclophosphamide = (TextView) findViewById(R.id.cyclophosphamide);
        kadarCyclophosphamide.setText((int) dosisCyclophophamide + " mg");

        //menghitung dosis Vincristine = 1 x LPT
        double dosisVincristine = luasPermukaanTubuhBulatFinal;

        //menampilkan kadar Vincristine
        TextView kadarVincristine = (TextView) findViewById(R.id.vincristin);
        kadarVincristine.setText((double) dosisVincristine + " mg");


    }

    /**
     * Fungsi tombol reset
     */

    public void klikReset (View view) {

        //mengubah input usia ke variable finalUsiaPasien
        EditText usia = (EditText) findViewById(R.id.usia);
        usia.setText(null);

        //mengubah input berat badan ke variable finalBeratBadan
        EditText berat = (EditText) findViewById(R.id.beratBadan);
        berat.setText(null);

        //mengubah input tinggi badan ke variable finalTinggiBadan
        EditText tinggi = (EditText) findViewById(R.id.tinggiBadan);
        tinggi.setText(null);

        //mengubah input serum Kreatinin ke variable finalSerumKreatinin
        EditText kadarSK = (EditText) findViewById(R.id.serumKreatinin);
        kadarSK.setText(null);

    }


    //Hitung LPT
    //LPT = akar kuadrat dari ((BB x TB)/3600)
    static double hitungLPT(double beratBadan, double tinggiBadan) {
        double LPT = Math.sqrt((beratBadan * tinggiBadan) / 3600);
        return LPT;

    }

    //Hitung GFR
    //GFR = ((140-Umur) x BeratBadan x 0.85) / (72 x SK)
    static double hitungGFR(double umur, double beratBadan, double serumKreatinin) {
        double GFR = ((140 - umur) * beratBadan * 0.85) / (72 * serumKreatinin);
        return GFR;
    }

    //Hitung GFR Obese
    //GFR Obese = ((146-Umur) x BeratBadan x 0.287) + (9.74 * TB * TB) / (60 x SK)
    static double hitungGFRobese(double umur, double beratBadan, double tinggiBadan, double serumKreatinin) {
        double GFRobese = ((146 - umur) * ((beratBadan * 0.287) + (((tinggiBadan / 100) * (tinggiBadan / 100)) * 9.74))) / (60 * serumKreatinin);
        return GFRobese;
    }

    //menghitung IMT
    //IMT = BB / ((TB/100)x(TB/100))
    static double hitungIMT(double beratBadan, double tinggiBadan) {
        double IMT = beratBadan / ((tinggiBadan / 100) * (tinggiBadan / 100));
        return IMT;
    }

    //pembulatan ke 2 desimal
    public static double pembulatanDuaDesimal(double nilai) {
        double pembulatan1 = Math.round(nilai * 100);
        double pembulatan2 = pembulatan1 / 100;
        return pembulatan2;
    }

}