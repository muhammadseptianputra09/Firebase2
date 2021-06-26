package com.example.firebase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase2.database.Teman;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_teman_edit extends AppCompatActivity {

    private DatabaseReference database;
    private TextView kodeText;
    EditText edNama, edTelpon;
    Button editBtn;
    String kode, nama, telpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman_edit);

        kodeText = findViewById(R.id.txtKode);
        edNama = findViewById(R.id.editNama);
        edTelpon = findViewById(R.id.editTelpon);
        editBtn = findViewById(R.id.btnEdit);

        database = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = this.getIntent().getExtras();
        kode = bundle.getString("kunci 1");
        nama = bundle.getString("kunci 2");
        telpon = bundle.getString("kunci 3");

        kodeText.setText("key : "+kode);
        edNama.setText(nama);
        edTelpon.setText(telpon);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTeman(new Teman(edNama.getText().toString(), edTelpon.getText().toString()));

            }
        });

    }

    public void editTeman(Teman tmn){
        database.child("Teman")
                .child(kode)
                .setValue(tmn)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_teman_edit.this, "Data Berhasil di Update", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(activity_teman_edit.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                });
    }
}