package com.diploma.freezer.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.freezer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoItemAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<VideoItem> videoItems;
    LayoutInflater layoutInflater;

    public VideoItemAdapter(Context context, ArrayList<VideoItem> videoItems) {
        this.context = context;
        this.videoItems = videoItems;
    }

    @Override
    public int getCount() {
        return videoItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void filterList(ArrayList<VideoItem> filteredList){
        videoItems = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = layoutInflater.inflate(R.layout.grid_item_recipes, null);
        }
        ImageView gridImage = view.findViewById(R.id.gridImage);
        TextView gridCaption = view.findViewById(R.id.gridCaption);

        try{
            Picasso.get().load("https://img.youtube.com/vi/" + videoItems.get(i).getDescription() + "/0.jpg").into(gridImage);
        }
        catch (Exception e){
            gridImage.setImageResource(R.drawable.ic_null_fastfood_24);
        }
        gridCaption.setText(videoItems.get(i).getCaption());

        return view;
    }
}
