package com.arley.amazonclone.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arley.amazonclone.R;

import org.w3c.dom.Text;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView tvPreco;
    public TextView tvNome;
    public TextView tvQuantidade;
    public ImageView ivImagem;
    public ImageButton ibAdicionar, ibDiminuir;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        tvPreco = (TextView) itemView.findViewById(R.id.item_product_tv_preco);
        tvNome = (TextView) itemView.findViewById(R.id.item_product_tv_nome);
        tvQuantidade = (TextView) itemView.findViewById(R.id.item_product_tv_quantidade);
        ivImagem = (ImageView) itemView.findViewById(R.id.item_product_iv_image);
        ibAdicionar = (ImageButton) itemView.findViewById(R.id.item_product_ib_adicionar);
        ibDiminuir = (ImageButton) itemView.findViewById(R.id.item_product_ib_diminuir);
    }
}
