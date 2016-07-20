package com.example.multimatics.myshoppingmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailProductActivity extends AppCompatActivity {
    private TextView tvName, tvPrice;
    private Button btnAddToCart;
    private ImageView imgDetail;
    private Spinner spnSize;
    private TextView tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        tvName = (TextView)findViewById(R.id.tv_name);
        tvPrice = (TextView)findViewById(R.id.tv_price);
        btnAddToCart = (Button)findViewById(R.id.btn_add_to_cart);
        imgDetail = (ImageView)findViewById(R.id.img_detail);
        spnSize =(Spinner)findViewById(R.id.spn_size);
        tvDesc =(TextView)findViewById(R.id.tv_desc);

        getSupportActionBar().setTitle("Detail Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Product selectedProduct = getIntent().getParcelableExtra("Product");
        tvName.setText(selectedProduct.getName());
        tvPrice.setText(selectedProduct.getPrice());
        Glide.with(DetailProductActivity.this)
                .load(selectedProduct.getImageUrl())
                .into(imgDetail);

        String[] size = new String[]{
                "Rasa",
                "Strawberry",
                "Latte",
                "Milk"
        };
        ArrayAdapter<String >sizeAdapter = new ArrayAdapter<String>(DetailProductActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1, size);
        spnSize.setAdapter(sizeAdapter);

        String desc="Teh beraorma Jasmine blended dengan milk yang gurih dengan top puding yang lembut dan anda akan mendapatkan rasa yang sensasional ketika meminumnya";
                tvDesc.setText(desc);

    }
}
