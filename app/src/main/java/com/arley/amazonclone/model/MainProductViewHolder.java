package com.arley.amazonclone.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arley.amazonclone.R;

public class MainProductViewHolder extends RecyclerView.ViewHolder {

    public TextView tvPreco;
    public TextView tvNome;
    public ImageView ivImagem;

    public MainProductViewHolder(@NonNull View itemView) {
        super(itemView);

        tvPreco = (TextView) itemView.findViewById(R.id.item_main_product_tv_preco);
        tvNome = (TextView) itemView.findViewById(R.id.item_main_product_tv_nome);
        ivImagem = (ImageView) itemView.findViewById(R.id.activity_produto_detalhe_iv_image);
    }
}
