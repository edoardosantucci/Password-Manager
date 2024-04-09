package com.example.mypasswordmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


public class Aggiunta extends AppCompatActivity {
    MyDatabase db;
    cardViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta);

        db = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "my_database")
                .allowMainThreadQueries().build();

        EditText dominio = findViewById(R.id.dominio_agg);
        EditText email = findViewById(R.id.email_agg);
        EditText password = findViewById(R.id.password_agg);
        Button bottone = findViewById(R.id.button);
        adapter = new cardViewAdapter(this, db.passwordDAO().getPassword());
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = dominio.getText().toString();
                String mail = email.getText().toString();
                String psw = password.getText().toString();
                Password password1 = new Password(nome, mail, psw);
                db.passwordDAO().insertAll(password1);
                adapter.notifyDataSetChanged();
                finish();
            }
        });
    }
}