package com.example.trentmarino.cairns_luxury_apartment.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.R;

/**
 * Created by trentmarino on 19/05/16.
 */
public class paragraphViewHolder extends RecyclerView.ViewHolder{
    TextView parragraph;
    public paragraphViewHolder(View v) {
        super(v);
        parragraph = (TextView) v.findViewById(R.id.paragraph);
    }

    public TextView getParragraph() {
        return parragraph;
    }

    public void setParragraph(TextView parragraph) {
        this.parragraph = parragraph;
    }
}
