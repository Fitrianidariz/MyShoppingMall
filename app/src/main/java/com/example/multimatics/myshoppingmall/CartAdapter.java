package com.example.multimatics.myshoppingmall;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.multimatics.myshoppingmall.db.CartHelper;
import com.example.multimatics.myshoppingmall.db.CartItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Multimatics on 22/07/2016.
 */
public class CartAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<CartItem> list;
    private CartHelper mCartHelper;

    private String[] qtyOptions = new String [] {
            "1", "2", "3", "4", "5"
    };

    public CartAdapter(Activity activity) {
        this.activity = activity;
        mCartHelper = new CartHelper(activity);
    }

    public ArrayList<CartItem> getList() {
        return list;
    }

    public void setList(ArrayList<CartItem> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return getList().size();
    }

    @Override
    public Object getItem(int position) {
        return getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_row_cart, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CartItem item = (CartItem) getItem(i);
        holder.tvProductName.setText(item.getName());
        holder.tvProductPrice.setText(String.valueOf(item.getPrice()));
        Glide.with(activity)
                .load(item.getImage())
                .into(holder.imgItemProduct);
        int subTotal = getSubTotal(item.getQty(), item.getPrice());
        holder.tvProductSubTotal.setText("Sub Total" + String.valueOf(subTotal));

        holder.spnQty.setAdapter(new ArrayAdapter<String>(activity,
                android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1,
                qtyOptions));
        int selectedQtyPosition = 0;
        for (int j = 0; j < qtyOptions.length; j++) {
            if (Integer.parseInt(qtyOptions[j]) == item.getQty()) {
                selectedQtyPosition = j;
                break;
            }
        }
        holder.spnQty.setSelection(selectedQtyPosition);

        holder.imgDelete.setOnClickListener(new CustomOnItemClickListener(i,
                new CustomOnItemClickListener.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        mCartHelper.delete(getList().get(position).getId());
                        getList().remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(activity, "One item is successfully deleted", Toast.LENGTH_SHORT).show();

                        RefreshCartEvent event = new RefreshCartEvent();
                        event.setEventMessage("Delete item");

                        EventBus.getDefault().post(event);
                    }
                }));

        holder.spnQty.setOnItemSelectedListener(new CustomOnItemSelectedListener(i, holder.tvProductSubTotal, new CustomOnItemSelectedListener.OnItemSelectedCallback() {

            @Override
            public void onItemSelected(View view, TextView textView, int itemRowPosition, int itemArrayPosition) {
                int selectedQty = Integer.parseInt(qtyOptions[itemArrayPosition]);
                int productQty = getList().get(itemRowPosition).getQty();

                if (selectedQty != productQty) {
                    mCartHelper.update(Integer.parseInt(qtyOptions[itemArrayPosition]),
                            getList().get(itemRowPosition).getId());

                    int updatedSubTotal = getSubTotal(selectedQty,
                            (int) getList().get(itemRowPosition).getPrice());
                    textView.setText("Sub Total" + updatedSubTotal);

                    RefreshCartEvent event = new RefreshCartEvent();
                    event.setEventMessage("Update qty "+selectedQty+" on ProductId : "+getList().get(itemRowPosition).getPrice());

                    EventBus.getDefault().post(event);
                }
            }
        }));
        return view;
    }

    private int getSubTotal (int subTotalQty, double price){
        return subTotalQty * (int)price;
    }

    static class ViewHolder {
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.img_item_product)
        ImageView imgItemProduct;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.spn_qty)
        Spinner spnQty;
        @BindView(R.id.tv_product_sub_total)
        TextView tvProductSubTotal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
