package com.example.firebase2.adapter;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase2.MainActivity;
import com.example.firebase2.R;
import com.example.firebase2.activity_teman_edit;
import com.example.firebase2.database.Teman;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class AdapterLihatBarang extends
            RecyclerView.Adapter<AdapterLihatBarang.ViewHolder> {
        private DatabaseReference database;
        private ArrayList<Teman> daftarTeman;
        private Context context;

        public AdapterLihatBarang(ArrayList<Teman> daftarTeman, Context context){
            this.daftarTeman = daftarTeman;
            this.context = context;

        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNama;
            ViewHolder(View v) {
                super(v);
                tvNama = (TextView) v.findViewById(R.id.tv_nama);
                database = FirebaseDatabase.getInstance().getReference();
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout., parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String kode, nama, telpon;
            kode = daftarTeman.get(position).getKode();
            nama = daftarTeman.get(position).getNama();
            telpon = daftarTeman.get(position).getTelpon();
            holder.tvNama.setText(nama);

            holder.tvNama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu pm = new PopupMenu(view.getContext(), view);
                    pm.inflate(R.menu.popupmenu);

                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.edit:
                                    Bundle bundle = new Bundle();
                                    bundle.putString("kunci 1", kode);
                                    bundle.putString("kunci 2", nama);
                                    bundle.putString("kunci 3", telpon);

                                    Intent i = new Intent(view.getContext(), activity_teman_edit.class);
                                    i.putExtras(bundle);
                                    view.getContext().startActivity(i);

                                    break;
                                case R.id.hapus:
                                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
                                    alertDlg.setTitle("Yakin "+nama+" akan dihapus ?");
                                    alertDlg.setMessage("Tekan Ya untuk menghapus")
                                            .setCancelable(false)
                                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    DeleteData(kode);
                                                    Toast.makeText(view.getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(view.getContext(), MainActivity.class);
                                                    view.getContext().startActivity(i);
                                                }
                                            })
                                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog aDlg = alertDlg.create();
                                    aDlg.show();
                                    break;
                            }
                            return true;
                        }
                    });
                    pm.show();
                    return;
                }
            });


        }
        @Override
        public int getItemCount() {
            return daftarTeman.size();
        }

        public void DeleteData(String kd){
            if (database !=null){
                database.child("Teman").child(kd).removeValue();
            }

        }
    }
}

