package com.luvi.penyewaan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.luvi.penyewaan.LoginActivity.TAG_ID;
import static com.luvi.penyewaan.LoginActivity.TAG_USERNAME;

public class MainActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TextView nama, tvTanggal, tvWaktu;
    EditText ename;
    Button btnTanggal, btnWaktu, btnPesan;
    SimpleDateFormat dateFormat;
    String[] ruangan;
    int index;

    FloatingActionButton  fab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        nama = (TextView)findViewById(R.id.tvnama);
        ename = (EditText) findViewById(R.id.txtnama);
        tvTanggal = (TextView) findViewById(R.id.tvTanggal);
        tvWaktu = (TextView) findViewById(R.id.tvWaktu);
        btnTanggal = (Button) findViewById(R.id.btnTanggal);
        btnWaktu = (Button) findViewById(R.id.btnWaktu);
        btnPesan = (Button) findViewById(R.id.btnPesan);
        Spinner s1 = (Spinner) findViewById(R.id.spinner);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_logout);

        //logout
        final SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID,null);
                editor.putString(TAG_USERNAME,null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });


        ruangan = getResources().getStringArray(R.array.Alat);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ruangan);

        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        btnWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTanggal = tvTanggal.getText().toString();
                String strWaktu = tvWaktu.getText().toString();
                String strNama = ename.getText().toString();
                Intent pesan = new Intent(MainActivity.this,SecondActivity.class);
                if (strTanggal.matches("Tanggal")||strWaktu.matches("Waktu")){
                    Toast.makeText(MainActivity.this,"Harap Diisi Semua",Toast.LENGTH_SHORT).show();
                }else{
                    pesan.putExtra("nama",strNama);
                    pesan.putExtra("alat",ruangan[index]);
                    pesan.putExtra("tanggal",strTanggal);
                    pesan.putExtra("waktu",strWaktu);
                    startActivity(pesan);
                }
            }
        });
    }

    public void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                tvTanggal.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void showTimeDialog(){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvWaktu.setText(hourOfDay+":"+minute);
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
}

