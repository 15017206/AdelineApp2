package com.example.a15017206.adelineapp2;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchResultAdapter extends ArrayAdapter<SearchResult> {

    private ArrayList<SearchResult> searchResult;
    private Context context;
    private ImageView imageView;
    private TextView tvTitle, tvSubtitle, tvPrice, tvShipping;

    public SearchResultAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<SearchResult> objects) {
        super(context, resource, objects);
        searchResult = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //connect to row2.xml
        View rowView = inflater.inflate(R.layout.search_result_layout_row, parent, false);

        tvTitle = rowView.findViewById(R.id.tvTitle);
        imageView = rowView.findViewById(R.id.imageView);
        tvSubtitle = rowView.findViewById(R.id.tvSubtitle);
        tvPrice = rowView.findViewById(R.id.tvPrice);
        tvShipping = rowView.findViewById(R.id.tvShipping);

        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the food at the same index.
        SearchResult current_searchResult = searchResult.get(position);

        // Set the TextView to show the Year
        tvTitle.setText(current_searchResult.getTvTitle());
        tvShipping.setText(current_searchResult.getTvShipping());
        tvPrice.setText(current_searchResult.getTvPrice());
        tvSubtitle.setText(current_searchResult.getTvSubtitle());

        Picasso.with(context).load(current_searchResult.getImageView()).into(imageView);
//        imageView.setImageResource(current_searchResult.getImageView());
        // Set the image to star or nostar accordingly
//        if (currentModule.isProgramming()) {
//            iv.setImageResource(R.drawable.prog);
//        } else {
//            iv.setImageResource(R.drawable.nonprog);
//        }

        return rowView;
    }
}
