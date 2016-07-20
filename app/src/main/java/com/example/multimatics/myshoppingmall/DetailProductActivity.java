package com.example.multimatics.myshoppingmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailProductActivity extends AppCompatActivity
    implements View.OnClickListener{
    private TextView tvName, tvPrice;
    private Button btnAddToCart;
    private ImageView imgDetail;
    private Spinner spnSize;
    private TextView tvDesc;
    private ImageView imgThumbA, imgThumbB, imgThumbC,imgThumbD;

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

        imgThumbA = (ImageView)findViewById(R.id.img_thumb_a);
        imgThumbB = (ImageView)findViewById(R.id.img_thumb_b);
        imgThumbC = (ImageView)findViewById(R.id.img_thumb_c);
        imgThumbD = (ImageView)findViewById(R.id.img_thumb_d);

        imgThumbA.setOnClickListener(this);
        imgThumbB.setOnClickListener(this);
        imgThumbC.setOnClickListener(this);
        imgThumbD.setOnClickListener(this);

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

        Glide.with(DetailProductActivity.this).load(SampleData.thumb[0]).into(imgThumbA);
        Glide.with(DetailProductActivity.this).load(SampleData.thumb[1]).into(imgThumbB);
        Glide.with(DetailProductActivity.this).load(SampleData.thumb[2]).into(imgThumbC);
        Glide.with(DetailProductActivity.this).load(SampleData.thumb[3]).into(imgThumbD);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        String imageUrl = null;
        switch (view.getId()){
            case R.id.img_thumb_a:
                imageUrl = SampleData.thumb[0];
                break;
            case R.id.img_thumb_b:
                imageUrl = SampleData.thumb[1];
                break;
            case R.id.img_thumb_c:
                imageUrl = SampleData.thumb[2];
                break;
            case R.id.img_thumb_d:
                imageUrl = SampleData.thumb[3];
                break;
            default:
                imageUrl = null;
                break;
        }
        if (imageUrl !=null){
            Glide.with(DetailProductActivity.this)
                    .load(imageUrl)
                    .into(imgDetail);
        }
    }
}
