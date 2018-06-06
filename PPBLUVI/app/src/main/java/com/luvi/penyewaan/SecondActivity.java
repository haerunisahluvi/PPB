package com.luvi.penyewaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tanggal,waktu,ruangan,nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        String nm = getIntent().getStringExtra("nama");
        String rua = getIntent().getStringExtra("alat");
        String tgl = getIntent().getStringExtra("tanggal");
        String wkt = getIntent().getStringExtra("waktu");

        nama = (TextView) findViewById(R.id.tvNama);
        ruangan = (TextView) findViewById(R.id.tvBilling);
        tanggal = (TextView) findViewById(R.id.tvTanggal);
        waktu = (TextView) findViewById(R.id.tvWaktu);

        nama.setText("Nama: "+nm);
        ruangan.setText("Alat: "+rua);
        tanggal.setText("Tanggal: "+tgl);
        waktu.setText("Waktu: "+wkt);

    }

}