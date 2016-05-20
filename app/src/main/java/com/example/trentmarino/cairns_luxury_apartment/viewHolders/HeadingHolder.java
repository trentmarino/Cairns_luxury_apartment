package com.example.trentmarino.cairns_luxury_apartment.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.R;

/**
 * Created by trentmarino on 19/05/16.
 */
public class HeadingHolder extends RecyclerView.ViewHolder {
    public TextView headingtext;
    public HeadingHolder(View itemView) {
        super(itemView);
        headingtext = (TextView) itemView.findViewById(R.id.heading);
    }

    public TextView getHeadingtext() {
        return headingtext;
    }

    public void setHeadingtext(TextView headingtext) {
        this.headingtext = headingtext;
    }
}
