package com.arley.amazonclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arley.amazonclone.R;
import com.arley.amazonclone.model.Product;
import com.arley.amazonclone.viewholder.MainProductViewHolder;
import com.arley.amazonclone.viewholder.ProductViewHolder;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.transition.Hold;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Product> options;
    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    FirebaseAuth mAuth;
    DatabaseReference dbReference;
    ImageView ivVoltar, ivCarrinho;
    private ProgressBar progressBar;
    private TextView tvQtdCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.activity_cart_rv);

        progressBar = findViewById(R.id.activity_cart_progressBar);
        tvQtdCarrinho = findViewById(R.id.activity_cart_tv_quantidade_cart);
        ivCarrinho = findViewById(R.id.activity_cart_iv_cart);
        ivVoltar = findViewById(R.id.activity_cart_iv_voltar);
        tvQtdCarrinho = findViewById(R.id.activity_cart_tv_quantidade_cart);

        progressBar.setVisibility(View.GONE);

        dbReference = FirebaseDatabase.getInstance().getReference("usuarios").child(mAuth.getUid()).child("carrinho");

        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(dbReference, Product.class).build();

        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
                Glide.with(CartActivity.this).load(model.getImagem_url()).into(holder.ivImagem);
                holder.tvNome.setText(model.getNome());
                holder.tvPreco.setText("R$" + model.getPreco());
                holder.tvQuantidade.setText(model.getQuantidade());

                holder.ibAdicionar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbReference.child(model.getCodigo()).child("quantidade").setValue();
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressBar.setVisibility(View.VISIBLE);

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new ProductViewHolder(view);
            }
        };

    }
}