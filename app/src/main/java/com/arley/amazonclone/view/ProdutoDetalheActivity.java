package com.arley.amazonclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arley.amazonclone.R;
import com.arley.amazonclone.model.Product;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDetalheActivity extends AppCompatActivity {

    TextView tvPreco, tvDescricao, tvNome, tvCategoria, tvQtdCart;
    ImageView ivImagem, ivVoltar, ivCarrinho;
    Button btAdicionarCarrinho;
    DatabaseReference databaseReference;
    DatabaseReference dbCarrinhoReference;
    FirebaseAuth mAuth;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        product = (Product) getIntent().getExtras().getParcelable("produto");
        product.setQuantidade(1);

        mAuth = FirebaseAuth.getInstance();

        setComponentsId();
        setComponentsListeners();

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

        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios")
                .child(mAuth.getUid()).child("carrinho");


        String imagem = product.getImagem_url();
        String descricao = product.getDescricao();
        String preco = "" + product.getPreco();
        String nome = product.getNome();
        String categoria = product.getEmpresa();

        tvPreco.setText("R$" + preco);
        tvDescricao.setText(descricao);
        tvNome.setText(nome);
        tvCategoria.setText(categoria);
        Glide.with(ProdutoDetalheActivity.this).load(imagem).into(ivImagem);

    }

    private void setComponentsId(){
        tvPreco = findViewById(R.id.activity_produto_detalhe_tv_preco);
        tvDescricao = findViewById(R.id.activity_produto_detalhe_tv_descricao);
        tvNome = findViewById(R.id.activity_produto_detalhe_tv_name);
        tvCategoria = findViewById(R.id.activity_produto_detalhe_tv_category);
        ivImagem = findViewById(R.id.activity_produto_detalhe_iv_image);
        ivCarrinho = findViewById(R.id.activity_produto_detalhe_iv_carrinho);
        ivVoltar = findViewById(R.id.activity_produto_detalhe_iv_voltar);
        btAdicionarCarrinho = findViewById(R.id.activity_produto_detalhe_bt_adicionar_carrinho);
        tvQtdCart = findViewById(R.id.activity_produto_detalhe_tv_quantidade_cart);
    }

    private void setComponentsListeners(){
        btAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(product.getCodigo()).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(ProdutoDetalheActivity.this, CartActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error while adding to the cart", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProdutoDetalheActivity.this, CartActivity.class));
            }
        });
    }
}