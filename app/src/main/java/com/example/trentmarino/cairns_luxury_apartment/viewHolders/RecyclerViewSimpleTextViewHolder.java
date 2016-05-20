package com.example.trentmarino.cairns_luxury_apartment.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.R;

/**
 * Created by trentmarino on 19/05/16.
 */
public class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {
    TextView Heading;

    public RecyclerViewSimpleTextViewHolder(View itemView) {
        super(itemView);
        Heading = (TextView) itemView.findViewById(R.id.simple);
    }

    public TextView getHeading() {
        return Heading;
    }
}
