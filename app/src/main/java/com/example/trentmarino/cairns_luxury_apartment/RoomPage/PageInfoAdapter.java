package com.example.trentmarino.cairns_luxury_apartment.RoomPage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trentmarino.cairns_luxury_apartment.R;
import com.example.trentmarino.cairns_luxury_apartment.pageContent.PageContent;
import com.example.trentmarino.cairns_luxury_apartment.viewHolders.HeadingHolder;
import com.example.trentmarino.cairns_luxury_apartment.viewHolders.ImageHolder;
import com.example.trentmarino.cairns_luxury_apartment.viewHolders.RecyclerViewSimpleTextViewHolder;
import com.example.trentmarino.cairns_luxury_apartment.viewHolders.SubHeadingViewHolder;
import com.example.trentmarino.cairns_luxury_apartment.viewHolders.paragraphViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by trentmarino on 19/05/16.
 */
public class PageInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<PageContent> pageContent;
    private ArrayList<String> contentType, content, displayOrder;
    private final int HEAD = 1, SUB = 2, PARA = 3, IMAGE = 4;
    private ArrayList<String> currentOrder;


    public PageInfoAdapter(ArrayList<PageContent> content, ArrayList<String> contentType, ArrayList<String> pageContent, ArrayList<String> pageOrder) {
        this.pageContent = content;
        this.contentType = contentType;
        this.content = pageContent;
        this.displayOrder = pageOrder;
        currentOrder = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case HEAD:
                View v1 = inflater.inflate(R.layout.content_type_heading, viewGroup, false);
                viewHolder = new HeadingHolder(v1);
                break;
            case SUB:
                View v2 = inflater.inflate(R.layout.content_type_subheading, viewGroup, false);
                viewHolder = new SubHeadingViewHolder(v2);
                break;
            case PARA:
                View v3 = inflater.inflate(R.layout.content_type_paragraph, viewGroup, false);
                viewHolder = new paragraphViewHolder(v3);
                break;
            case IMAGE:
                View v4 = inflater.inflate(R.layout.content_type_image, viewGroup, false);
                viewHolder = new ImageHolder(v4);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.i("types", HEAD + " " + SUB + " " + PARA);
            switch (viewHolder.getItemViewType()) {
                case HEAD:
                    HeadingHolder vh1 = (HeadingHolder) viewHolder;
                    configureViewHolder1(vh1, Integer.parseInt(displayOrder.get(position)));
                    Log.i("order", " " + displayOrder.get(position));
                    break;
                case SUB:
                    SubHeadingViewHolder vh2 = (SubHeadingViewHolder) viewHolder;
                    configureViewHolder2(vh2, Integer.parseInt(displayOrder.get(position)));
                    Log.i("order", " " + displayOrder.get(position));
                    break;
                case PARA:
                    paragraphViewHolder vh3 = (paragraphViewHolder) viewHolder;
                    configureViewHolder3(vh3, Integer.parseInt(displayOrder.get(position)));
                    Log.i("order", " " + displayOrder.get(position));

                    break;
                case IMAGE:
                    ImageHolder vh4 = (ImageHolder) viewHolder;
                    configureViewHolder4(vh4, Integer.parseInt(displayOrder.get(position)));
                    Log.i("order", " " + displayOrder.get(position));
                    break;
                default:
                    RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                    configureDefaultViewHolder(vh, Integer.parseInt(displayOrder.get(position)));
                    Log.i("order", " " + displayOrder.get(position));
                    break;
            }

    }

    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        vh.getHeading().setText((CharSequence) pageContent.get(position));
    }

    private void configureViewHolder1(HeadingHolder vh1, int position) {
        for (int i = 0; i < displayOrder.size(); i++) {
            PageContent user = (PageContent) pageContent.get(position);
            if (contentType.get(position).equals("1")) {
                if (user != null) {
                    vh1.getHeadingtext().setText(content.get(position));
                }
            }
        }
    }

    private void configureViewHolder2(SubHeadingViewHolder vh1, int position) {
        for (int i = 0; i < displayOrder.size(); i++) {
            PageContent user = (PageContent) pageContent.get(position);
            if (contentType.get(position).equals("2")) {
                if (user != null) {
                    vh1.getSubHeading().setText(content.get(position));

                }
            }
        }
    }

    private void configureViewHolder3(paragraphViewHolder vh1, int position) {
        for (int i = 0; i < displayOrder.size(); i++) {
            PageContent user = (PageContent) pageContent.get(position);
            if (contentType.get(position).equals("3")) {
                if (user != null) {
                    vh1.getParragraph().setText(content.get(position));
                }
            }
        }
    }

    private void configureViewHolder4(ImageHolder vh1, int position) {
        for (int i = 0; i < displayOrder.size(); i++) {
            PageContent user = (PageContent) pageContent.get(position);
            if (contentType.get(position).equals("4")) {
                if (user != null) {
                    ImageLoader.getInstance().displayImage(content.get(position), vh1.getImageView());
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return pageContent.size();
    }

    @Override
    public int getItemViewType(int position) {
            Log.i("display order", displayOrder.get(position));
            if (contentType.get(Integer.parseInt(displayOrder.get(position))).equals("1")) {
                return HEAD;
            } else if (contentType.get(Integer.parseInt(displayOrder.get(position))).equals("2")) {
                return SUB;
            } else if (contentType.get(Integer.parseInt(displayOrder.get(position))).equals("3")) {
                return PARA;
            } else if (contentType.get(Integer.parseInt(displayOrder.get(position))).equals("4")) {
                return IMAGE;
            }
        return -1;
    }


}



