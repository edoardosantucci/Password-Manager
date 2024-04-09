package com.example.mypasswordmanager;

import static com.example.mypasswordmanager.Dettaglio.EXTRA_SERIA;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cardViewAdapter extends RecyclerView.Adapter<cardViewAdapter.ViewHolder> {
    private ArrayList<Password> lista;
    private AppCompatActivity activity;

    public cardViewAdapter(AppCompatActivity activity, List<Password> passwords) {
        this.lista = (ArrayList<Password>) passwords;
        this.activity = activity;
    }

    public void addAll(ArrayList<Password> password) {
        this.lista = password;
        notifyDataSetChanged();

    }

    public void addPassword(Password password) {
        this.lista.add(password);
        notifyDataSetChanged();
    }

    public void removePassword(int position) {
        this.lista.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        this.lista.clear();
        notifyDataSetChanged();
    }

    public Password getPasswordAt(int position) {
        return lista.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder card, int position) {
        card.nome.setText(lista.get(position).getNome());
        card.email.setText(lista.get(position).getEmail());
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("nascosto", true)) {
            card.password.setText("*******");
        } else {
            card.password.setText(lista.get(position).getPassword());

        }

        if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("facebook")) {
            card.logo.setImageResource(R.drawable.f1);
        } else if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("instagram")) {
            card.logo.setImageResource(R.drawable.instagram);
        } else if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("amazon")) {
            card.logo.setImageResource(R.drawable.amazon);
        } else if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("binance")) {
            card.logo.setImageResource(R.drawable.binance1);
        } else if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("coinbase")) {
            card.logo.setImageResource(R.drawable.coinbase);
        } else if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("twitter")) {
            card.logo.setImageResource(R.drawable.twitter);
        } else if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("linkedin")) {
            card.logo.setImageResource(R.drawable.linkedin);
        } else if (lista.get(position).getNome().toLowerCase(Locale.ROOT).contains("zalando")) {
            card.logo.setImageResource(R.drawable.zalando);
        } else card.logo.setImageResource(R.drawable.download);

        Password psw = lista.get(position);

        card.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), Dettaglio.class);
                intent.putExtra(EXTRA_SERIA, psw);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        TextView email;
        TextView password;
        ImageView logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            email = itemView.findViewById(R.id.email);
            password = itemView.findViewById(R.id.password);
            logo = itemView.findViewById(R.id.imageView);

        }
    }
}
