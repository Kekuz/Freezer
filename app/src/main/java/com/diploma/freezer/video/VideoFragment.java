package com.diploma.freezer.video;

import static com.diploma.freezer.MainActivity.currentVideos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.diploma.freezer.R;

import java.util.ArrayList;

public class VideoFragment extends Fragment {
    GridView gridView;
    public SearchView searchView;
    public VideoItemAdapter videosItemAdapter;
    private ArrayList<VideoItem> searchFilteredList;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_video, container, false);


        gridView = inflatedView.findViewById(R.id.videoGridView);

        searchView = inflatedView.findViewById(R.id.video_search_view);
        searchView.setVisibility(View.VISIBLE);

        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter(newText);
                return true;
            }
        });


        videosItemAdapter = new VideoItemAdapter(inflatedView.getContext(), currentVideos.getVideoItems());
        gridView.setAdapter(videosItemAdapter);

        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            ArrayList<VideoItem> list;

            if (searchFilteredList == null){
                list = currentVideos.getVideoItems();
            }else{
                list = searchFilteredList;
            }

            Intent intent = new Intent(inflatedView.getContext(), VideoActivity.class);
            Log.i("GridViewInfo: ", "position " + i + " "+ list.get(i));

            intent.putExtra("caption", list.get(i).getCaption());
            intent.putExtra("description", list.get(i).getDescription());

            startActivity(intent);
        });


        return inflatedView;
    }
    private void searchFilter(String newText) {
        searchFilteredList = new ArrayList<>();
        for(VideoItem item : currentVideos.getVideoItems()){
            if(item.getCaption().toLowerCase().contains(newText.toLowerCase())){
                searchFilteredList.add(item);
            }
        }
        videosItemAdapter.filterList(searchFilteredList);
    }
}
