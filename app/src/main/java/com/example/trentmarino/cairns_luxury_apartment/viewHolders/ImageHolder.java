package com.example.trentmarino.cairns_luxury_apartment.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.trentmarino.cairns_luxury_apartment.R;

/**
 * Created by trentmarino on 19/05/16.
 */
public class ImageHolder extends RecyclerView.ViewHolder {
    private ImageView ivExample;

    public ImageHolder(View v) {
        super(v);
        ivExample = (ImageView) v.findViewById(R.id.pageImage);
    }

    public ImageView getImageView() {
        return ivExample;
    }

    public void setImageView(ImageView ivExample) {
        this.ivExample = ivExample;
    }
}
