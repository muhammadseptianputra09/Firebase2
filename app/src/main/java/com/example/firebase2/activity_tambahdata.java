package com.example.firebase2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase2.database.Teman;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_tambahdata extends AppCompatActivity {

    private DatabaseReference database;
    private Button btnTambah;
    private EditText edtNama;
    private EditText edtTelpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahdata);

        edtNama = findViewById(R.id.edNama);
        edtTelpon = findViewById(R.id.edTelpon);
        btnTambah = findViewById(R.id.Bttambah);

        database = FirebaseDatabase.getInstance().getReference();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(edtNama.getText().toString().isEmpty()) && !(edtTelpon.getText().toString().isEmpty()))
                    tambahDt(new Teman(edtNama.getText().toString(), edtTelpon.getText().toString()));
                else
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtNama.getWindowToken(),0);
            }
        });
    }
    public void tambahDt(Teman tmn ){

        database.child("Teman").push().setValue(tmn).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                edtNama.setText("");
                edtTelpon.setText("");
                Toast.makeText(getApplicationContext(),"Data Berhasil",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, activity_tambahdata.class);
    }
}