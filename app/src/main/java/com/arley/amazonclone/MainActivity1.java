package com.arley.amazonclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arley.amazonclone.Model.Constants;
import com.arley.amazonclone.Model.MainProductViewHolder;
import com.arley.amazonclone.Model.Product;
import com.arley.amazonclone.Model.FakeStoreServer;
import com.arley.amazonclone.Model.Product1;
import com.arley.amazonclone.Model.ProductRecyclerViewAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity1 extends AppCompatActivity {

    RecyclerView productsRecyclerView;
    Retrofit retrofit;
    FakeStoreServer fakeStoreServer;
    ProgressBar progressBar;

    ProductRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productsRecyclerView = findViewById(R.id.activity_main_rv_product);
        progressBar = findViewById(R.id.activity_main_progressBar);
        progressBar.setVisibility(View.GONE);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fakeStoreServer = retrofit.create(FakeStoreServer.class);

        Call<List<Product1>> call = fakeStoreServer.getProducts();

        call.enqueue(new Callback<List<Product1>>() {
            @Override
            public void onResponse(Call<List<Product1>> call, Response<List<Product1>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity1.this, "Code: "+ response.code() + response.message()
                            , Toast.LENGTH_LONG).show();

                    return;
                }

                List<Product1> productsList = response.body();

                if (!productsList.isEmpty()){
                    buildRecyclerView(productsList);

                }else {
                    Toast.makeText(MainActivity1.this, "Lista Vazia"
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product1>> call, Throwable t) {

            }
        });

        progressBar.setVisibility(View.VISIBLE);
    }

    private void buildRecyclerView(List<Product1> productsList) {
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity1.this));
        adapter = new ProductRecyclerViewAdapter(productsList, MainActivity1.this);

        adapter.setOnItemClickListener(new ProductRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onProductClick(int position) {
                Intent intent = new Intent(MainActivity1.this, ProdutoDetalheActivity.class);
                intent.putExtra("imagem", productsList.get(position).getImage());
                intent.putExtra("descricao", productsList.get(position).getDescription());
                intent.putExtra("preco", ""+productsList.get(position).getPrice());
                intent.putExtra("categoria", ""+productsList.get(position).getCategory());
                intent.putExtra("nome", ""+productsList.get(position).getTitle());

                startActivity(intent);
            }
        });

        productsRecyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }
}