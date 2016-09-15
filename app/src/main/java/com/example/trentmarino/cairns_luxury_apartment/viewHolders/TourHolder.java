package com.example.trentmarino.cairns_luxury_apartment.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.R;

/**
 * Created by trentmarino on 19/05/16.
 */
public class TourHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView title, url;

    public TourHolder(View v) {
        super(v);
        image = (ImageView) v.findViewById(R.id.image);
        title = (TextView) v.findViewById(R.id.title);
        url = (TextView) v.findViewById(R.id.url);
    }


    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getUrl() {
        return url;
    }

    public void setUrl(TextView url) {
        this.url = url;
    }
}
