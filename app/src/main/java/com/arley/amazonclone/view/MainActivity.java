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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arley.amazonclone.R;
import com.arley.amazonclone.viewholder.MainProductViewHolder;
import com.arley.amazonclone.model.Product;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ImageView ivCart;
    ImageView ivConfig;

    RecyclerView productsRecyclerView;

    TextView tvQtdCart;
    ProgressBar progressBar;

    DatabaseReference dbReference;
    DatabaseReference dbCarrinhoReference;
    FirebaseAuth mAuth;
    FirebaseRecyclerOptions<Product> options;
    FirebaseRecyclerAdapter<Product, MainProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setComponentsId();
        setComponentsListeners();

        mAuth = FirebaseAuth.getInstance();

        dbCarrinhoReference = FirebaseDatabase.getInstance().getReference("usuarios").child(mAuth.getUid()).child("carrinho");

        dbCarrinhoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    tvQtdCart.setVisibility(View.VISIBLE);
                    tvQtdCart.setText(Integer.toString((int)snapshot.getChildrenCount()));
                }else {
                    tvQtdCart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        intent.putExtra("produto", model);
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

    }

    private void setComponentsId(){
        ivCart = findViewById(R.id.activity_main_iv_carrinho);
        ivConfig = findViewById(R.id.activity_main_iv_configuracoes);
        tvQtdCart = findViewById(R.id.activity_main_tv_quantidade_cart);
        productsRecyclerView = findViewById(R.id.activity_main_rv_product);
        progressBar = findViewById(R.id.activity_main_progressBar);
    }

    private void setComponentsListeners(){
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        ivConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }

}