package com.arley.amazonclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arley.amazonclone.R;
import com.arley.amazonclone.model.MainProductViewHolder;
import com.arley.amazonclone.model.Product;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView productsRecyclerView;

    ProgressBar progressBar;

    DatabaseReference dbReference;
    FirebaseRecyclerOptions<Product> options;
    FirebaseRecyclerAdapter<Product, MainProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productsRecyclerView = findViewById(R.id.activity_main_rv_product);
        progressBar = findViewById(R.id.activity_main_progressBar);
        progressBar.setVisibility(View.GONE);

        dbReference = FirebaseDatabase.getInstance().getReference("produtos");

        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(dbReference, Product.class).build();

        adapter = new FirebaseRecyclerAdapter<Product, MainProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MainProductViewHolder holder, int position, @NonNull Product model) {
                Glide.with(MainActivity.this).load(model.getImagem_url()).into(holder.ivImagem);
                holder.tvNome.setText(model.getNome());
                holder.tvPreco.setText("R$" + Float.toString(model.getPreco()));

                holder.ivImagem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ProdutoDetalheActivity.class);
                        intent.putExtra("imagem", model.getImagem_url());
                        intent.putExtra("nome", model.getNome());
                        intent.putExtra("descricao", model.getDescricao());
                        intent.putExtra("preco", ""+model.getPreco());
                        intent.putExtra("categoria", model.getEmpresa());
                        startActivity(intent);
                    }
                });

                progressBar.setVisibility(View.GONE);

            }

            @NonNull
            @Override
            public MainProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                progressBar.setVisibility(View.VISIBLE);

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_product, parent, false);
                return new MainProductViewHolder(view);
            }
        };

        progressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.startListening();
        productsRecyclerView.setAdapter(adapter);



//
    }
}