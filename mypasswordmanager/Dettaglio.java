package com.example.mypasswordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class Dettaglio extends AppCompatActivity {

    public static String EXTRA_SERIA = "EXTRA_SERIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio);

        TextView nome = findViewById(R.id.nome_dett);
        TextView email = findViewById(R.id.email_dett);
        TextView password = findViewById(R.id.psw_dett);
        ImageView logo = findViewById(R.id.logo_dett);
        Button button = findViewById(R.id.login_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dest = "http://www.google.com/search?q=" + nome.getText() + "&btnI";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dest));
                startActivity(browserIntent);
            }
        });

        Intent intent = getIntent();
        Password psw = (Password) intent.getSerializableExtra(EXTRA_SERIA);

        nome.setText(psw.getNome());
        email.setText(psw.getEmail());
        password.setText(psw.getPassword());
        if (psw.getNome().toLowerCase(Locale.ROOT).contains("facebook")) {
            logo.setImageResource(R.drawable.f1);
        } else if (psw.getNome().toLowerCase(Locale.ROOT).contains("instagram")) {
            logo.setImageResource(R.drawable.instagram);
        } else if (psw.getNome().toLowerCase(Locale.ROOT).contains("amazon")) {
            logo.setImageResource(R.drawable.amazon);
        } else if (psw.getNome().toLowerCase(Locale.ROOT).contains("binance")) {
            logo.setImageResource(R.drawable.binance1);
        } else if (psw.getNome().toLowerCase(Locale.ROOT).contains("coinbase")) {
            logo.setImageResource(R.drawable.coinbase);
        } else if (psw.getNome().toLowerCase(Locale.ROOT).contains("twitter")) {
            logo.setImageResource(R.drawable.twitter);
        } else if (psw.getNome().toLowerCase(Locale.ROOT).contains("linkedin")) {
            logo.setImageResource(R.drawable.linkedin);
        } else if (psw.getNome().toLowerCase(Locale.ROOT).contains("zalando")) {
            logo.setImageResource(R.drawable.zalando);
        } else logo.setImageResource(R.drawable.download);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", password.getText());
                clipboard.setPrimaryClip(clip);
                Snackbar.make(v, "Password copiata!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}