package com.example.multimatics.myshoppingmall;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Multimatics on 22/07/2016.
 */
public class CustomOnItemClickListener implements View.OnClickListener {
    private OnItemClickCallback onItemClickCallback;
    private int position;

    public CustomOnItemClickListener (int position, OnItemClickCallback onItemClickCallback){
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position );

    }
    public interface OnItemClickCallback{
        void onItemClicked (View view, int position);
    }


}
