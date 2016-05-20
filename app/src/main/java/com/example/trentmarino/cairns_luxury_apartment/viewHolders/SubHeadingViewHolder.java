package com.example.trentmarino.cairns_luxury_apartment.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.R;

/**
 * Created by trentmarino on 19/05/16.
 */
public class SubHeadingViewHolder extends RecyclerView.ViewHolder{
    TextView SubHeading;
    public SubHeadingViewHolder(View v) {
        super(v);
        SubHeading = (TextView) v.findViewById(R.id.subHead);
    }

    public TextView getSubHeading() {
        return SubHeading;
    }

    public void setSubHeading(TextView subHeading) {
        SubHeading = subHeading;
    }
}
