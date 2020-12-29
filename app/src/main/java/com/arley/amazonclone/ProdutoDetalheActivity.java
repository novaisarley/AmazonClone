package com.arley.amazonclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProdutoDetalheActivity extends AppCompatActivity {

    TextView tvPreco, tvDescricao, tvNome, tvCategoria;
    ImageView ivImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        tvPreco = findViewById(R.id.activity_produto_detalhe_tv_preco);
        tvDescricao = findViewById(R.id.activity_produto_detalhe_tv_descricao);
        tvNome = findViewById(R.id.activity_produto_detalhe_tv_name);
        tvCategoria = findViewById(R.id.activity_produto_detalhe_tv_category);
        ivImagem = findViewById(R.id.activity_produto_detalhe_iv_image);

        String imagem = getIntent().getStringExtra("imagem");
        String descricao = getIntent().getStringExtra("descricao");
        String preco = getIntent().getStringExtra("preco");
        String nome = getIntent().getStringExtra("nome");
        String categoria = getIntent().getStringExtra("categoria");

        tvPreco.setText("R$" + preco);
        tvDescricao.setText(descricao);
        tvNome.setText(nome);
        tvCategoria.setText(categoria);
        Glide.with(ProdutoDetalheActivity.this).load(imagem).into(ivImagem);

    }
}