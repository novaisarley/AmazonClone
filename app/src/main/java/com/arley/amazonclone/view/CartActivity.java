package com.arley.amazonclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arley.amazonclone.R;
import com.arley.amazonclone.model.Product;
import com.arley.amazonclone.viewholder.MainProductViewHolder;
import com.arley.amazonclone.viewholder.ProductViewHolder;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.Hold;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Product> options;
    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    FirebaseAuth mAuth;
    DatabaseReference dbReference;
    ImageView ivVoltar;
    Button btComprar;

    private ProgressBar progressBar;
    private DatabaseReference dbCarrinhoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.activity_cart_rv);

        progressBar = findViewById(R.id.activity_cart_progressBar);

        ivVoltar = findViewById(R.id.activity_cart_iv_voltar);
        btComprar = findViewById(R.id.activity_cart_bt_comprar);

        dbReference = FirebaseDatabase.getInstance().getReference("usuarios").child(mAuth.getUid()).child("carrinho");

        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbCarrinhoReference = FirebaseDatabase.getInstance().getReference("usuarios").child(mAuth.getUid()).child("carrinho");

                dbCarrinhoReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            dbReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CartActivity.this, "Your products are being packed and will soon be at your home", Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {
                                        Toast.makeText(CartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        progressBar.setVisibility(View.GONE);


        buildRecyclerView();

    }

    private void buildRecyclerView() {
        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(dbReference, Product.class).build();

        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
                Glide.with(CartActivity.this).load(model.getImagem_url()).into(holder.ivImagem);
                holder.tvNome.setText(model.getNome());
                holder.tvPreco.setText("R$" + Float.toString(model.getPreco() * model.getQuantidade()));
                holder.tvQuantidade.setText(Integer.toString(model.getQuantidade()));

                if (adapter.getItemCount() == 0) {
                    Toast.makeText(CartActivity.this, "Your cart is empty : (", Toast.LENGTH_SHORT).show();
                }

                DatabaseReference qtdCarrinhoReference = FirebaseDatabase.getInstance().getReference("usuarios")
                        .child(mAuth.getUid()).child("carrinho").child(model.getCodigo()).child("quantidade");

                qtdCarrinhoReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            holder.tvQuantidade.setText(Integer.toString(snapshot.getValue(Integer.class)));
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                holder.ibAdicionar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);

                        qtdCarrinhoReference.setValue(model.getQuantidade() + 1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CartActivity.this, "Something went wrong, it was not possible to update your product info", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    }
                });

                holder.ibDiminuir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);

                        int qtd = Integer.parseInt(holder.tvQuantidade.getText().toString());

                        if (qtd == 1) {
                            holder.tvQuantidade.setText("0");
                            dbReference.child(model.getCodigo()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else if (qtd > 1) {
                            dbReference.child(model.getCodigo()).child("quantidade").setValue(qtd - 1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(CartActivity.this, "Something went wrong, it was not possible to update your product info", Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                });

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }

            @Override
            public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

                progressBar.setVisibility(View.GONE);

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressBar.setVisibility(View.VISIBLE);

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new ProductViewHolder(view);
            }
        };

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}