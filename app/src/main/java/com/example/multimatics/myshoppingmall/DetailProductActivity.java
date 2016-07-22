package com.example.multimatics.myshoppingmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.multimatics.myshoppingmall.db.CartHelper;
import com.example.multimatics.myshoppingmall.db.CartItem;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity
    implements View.OnClickListener{
    private TextView tvName, tvPrice;
    private Button btnAddToCart;
    private ImageView imgDetail;
    private Spinner spnSize;
    private TextView tvDesc;
    private ImageView imgThumbA, imgThumbB, imgThumbC,imgThumbD;
    private TextView tvCart, tvTitle;
    private ImageView imgCart;
    private Toolbar toolbar;

    private int currentImagePosition = 0;
    private Product selectedProduct;

    private CartHelper  mCartHelper;

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
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvCart = (TextView)findViewById(R.id.tv_cart);
        imgCart = (ImageView)findViewById(R.id.img_cart);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        imgDetail.setOnClickListener(this);

        btnAddToCart.setOnClickListener(this);

        imgThumbA = (ImageView)findViewById(R.id.img_thumb_a);
        imgThumbB = (ImageView)findViewById(R.id.img_thumb_b);
        imgThumbC = (ImageView)findViewById(R.id.img_thumb_c);
        imgThumbD = (ImageView)findViewById(R.id.img_thumb_d);

        imgThumbA.setOnClickListener(this);
        imgThumbB.setOnClickListener(this);
        imgThumbC.setOnClickListener(this);
        imgThumbD.setOnClickListener(this);

        setSupportActionBar(toolbar);
        tvTitle.setText("Detail Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedProduct = getIntent().getParcelableExtra("product");
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

        mCartHelper = new CartHelper(this);
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
                currentImagePosition = 0;
                break;
            case R.id.img_thumb_b:
                imageUrl = SampleData.thumb[1];
                currentImagePosition = 1;
                break;
            case R.id.img_thumb_c:
                imageUrl = SampleData.thumb[2];
                currentImagePosition = 2;
                break;
            case R.id.img_thumb_d:
                imageUrl = SampleData.thumb[3];
                currentImagePosition = 3;
                break;

            case R.id.img_detail:
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < SampleData.thumb.length; i++){
                    list.add(SampleData.thumb[i]);
                }
                Intent mIntent = new Intent(DetailProductActivity.this, DetailImageActivityJava.class);
                mIntent.putExtra("url_image",list);
                mIntent.putExtra("position",currentImagePosition);
                startActivity(mIntent);
                break;

            case R.id.btn_add_to_cart:
                if (mCartHelper.isItemAlreadyExist((int)selectedProduct.getId())){
                    Toast.makeText(DetailProductActivity.this,
                    "This product is already in cart", Toast.LENGTH_SHORT).show();
                }else {
                    mCartHelper.create((int)selectedProduct.getId(),
                            selectedProduct.getName(),
                            selectedProduct.getImageUrl(),
                            1,Double.parseDouble(selectedProduct.getPrice()));
                    Toast.makeText(DetailProductActivity.this,
                            "This product is successfully added",Toast.LENGTH_SHORT).show();

                    updateCartQty();
                }
                break;

            case R.id.img_cart:
                Intent intent = new Intent(DetailProductActivity.this, CartActivity.class);
                startActivity(intent);
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

    private  void updateCartQty(){
        ArrayList<CartItem> list = mCartHelper.getAll();
        tvCart.setVisibility(View.GONE);
        if (list != null){
            if (list.size() > 0){
                int cartQty = list.size();
                tvCart.setVisibility(View.VISIBLE);
                tvCart.setText(String.valueOf(cartQty));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartQty();
    }
}
