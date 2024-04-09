package com.example.mypasswordmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Password> lista;
    cardViewAdapter adapter;
    MyDatabase db;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onRestart() {
        super.onRestart();
        lista.clear();
        lista.addAll(db.passwordDAO().getPassword());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_activity);
        db = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "my_database").allowMainThreadQueries().build();

        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SwitchCompat nascondi = findViewById(R.id.nascondi);
        nascondi.setChecked(sharedPreferences.getBoolean("nascosto", true));
        nascondi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences
                        .edit()
                        .putBoolean("nascosto", isChecked)
                        .apply();
                adapter.notifyDataSetChanged();
            }
        });

        swipeRefreshLayout = findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aggiornaRecyclerView();
            }
        });

        lista = new ArrayList<Password>();
        lista.addAll(db.passwordDAO().getPassword());

        adapter = new cardViewAdapter(this, lista);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(viewHolder.itemView.getContext());
                alertBuilder.setTitle("ELIMINA");
                alertBuilder.setMessage("Vuoi eliminare definitivamente l'elemento?");
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                    }
                });
                alertBuilder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.passwordDAO().delete(adapter.getPasswordAt(viewHolder.getAdapterPosition()));
                        aggiornaRecyclerView();
                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Aggiunta.class);
                startActivity(intent);
            }
        });
    }

    private void aggiornaRecyclerView() {
        adapter.clear();
        adapter.addAll((ArrayList<Password>) db.passwordDAO().getPassword());
        swipeRefreshLayout.setRefreshing(false);
    }
}