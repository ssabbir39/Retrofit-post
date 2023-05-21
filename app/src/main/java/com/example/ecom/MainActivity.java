package com.example.ecom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, MainActivity.this);

        recyclerView.setAdapter(productAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        addProduct();
    }

    private void addProduct() {
        ProductService productService = retrofit.create(ProductService.class);

        Product product = new Product();
        product.setTitle("test product");
        product.setPrice(13.5);
        product.setDescription("lorem ipsum set");
        product.setImage("https://i.pravatar.cc");
        product.setCategory("electronic");

        Call<Product> call = productService.addProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product addedProduct = response.body();
                    productList.add(addedProduct);
                    productAdapter.notifyDataSetChanged();
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                // Handle network or other errors
            }
        });
    }


}
