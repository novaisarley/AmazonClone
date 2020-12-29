package com.arley.amazonclone.model;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.arley.amazonclone.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    public List<Product> productList;
    private OnItemClickListener mListener;
    private Context context;

    public interface OnItemClickListener {
        void onProductClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductRecyclerViewAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantidade;
        private ImageView ivImage;
        ImageButton ibAdicionar, ibDiminuir;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.item_product_iv_image);
            tvName = itemView.findViewById(R.id.item_product_tv_nome);
            tvPrice = itemView.findViewById(R.id.item_product_tv_preco);
            tvQuantidade = itemView.findViewById(R.id.activity_main_tv_quantidade_cart);
            ibAdicionar = itemView.findViewById(R.id.item_product_ib_adicionar);
            ibDiminuir = itemView.findViewById(R.id.item_product_ib_diminuir);

            ibDiminuir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onProductClick(position);
                        }
                    }
                }
            });

            ibAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onProductClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(productList.get(position).getNome());
        holder.tvQuantidade.setText(productList.get(position).getQuantidade());
        holder.tvPrice.setText("R$" + Float.toString(productList.get(position).getPreco() * productList.get(position).getQuantidade()));
        Glide.with(context).load(productList.get(position).getImagem_url()).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
