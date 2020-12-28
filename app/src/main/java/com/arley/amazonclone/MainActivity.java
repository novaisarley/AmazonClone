package com.arley.amazonclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arley.amazonclone.Model.MainProductViewHolder;
import com.arley.amazonclone.Model.Product;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    RecyclerView productsRecyclerView;
    DatabaseReference dbReference;
    FirebaseRecyclerOptions<Product> options;
    FirebaseRecyclerAdapter<Product, MainProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productsRecyclerView = findViewById(R.id.activity_main_rv_product);

        dbReference = FirebaseDatabase.getInstance().getReference("produtos");

        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(dbReference, Product.class).build();

        adapter = new FirebaseRecyclerAdapter<Product, MainProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MainProductViewHolder holder, int position, @NonNull Product model) {
                Glide.with(MainActivity.this).load(model.getImagem_url()).into(holder.ivImagem);
                holder.tvNome.setText(model.getNome());
                holder.tvPreco.setText("R$" + Float.toString(model.getPreco()));
            }

            @NonNull
            @Override
            public MainProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_product, parent, false);

                return new MainProductViewHolder(view);
            }
        };

        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.startListening();
        productsRecyclerView.setAdapter(adapter);

//
    }
}