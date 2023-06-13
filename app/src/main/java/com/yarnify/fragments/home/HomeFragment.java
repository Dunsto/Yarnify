package com.yarnify.fragments.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.yarnify.databinding.FragmentHomeBinding;
import com.yarnify.API.ResponseUtilities.UrlToPattern;
import com.yarnify.cardAdapter;
import com.yarnify.model.Pattern;

import org.json.JSONException;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    //binding is an instance of the auto-generated FragmentHomeBinding class, which is used to bind the layout XML elements to their corresponding Java objects.
    private FragmentHomeBinding binding;

    ArrayList<Pattern> exampleList = new ArrayList<>(); //array of pattern objects

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment using the FragmentHomeBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        exampleList.clear(); //clear the exampleList before adding new pattern objects
        // I don't think this is an ideal way to allow Network stuff on the main thread. Just using it for testing purposes
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        UrlToPattern urlToPattern = new UrlToPattern();
        try {
            //featured page with 100 patterns I guess?
            exampleList = urlToPattern.UrlToPatternList("patterns/search.json?page_size=100");
            //exampleList = urlToPattern.UrlToPatternList("patterns/search.json?query=knit");
            // Use the patterns list as needed
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle the exception or show an error message
        }


        //hardcoded example pattern objects
        /*exampleList.add(new Pattern("https://images4-f.ravelrycache.com/uploads/nawatramani/926341990/Making_Waves_4_small2.jpg", "Making Waves", "Nita Awatramani", "Knitting", "https://www.ravelry.com/patterns/library/making-waves-31", 0, 0));
        exampleList.add(new Pattern("https://images4-f.ravelrycache.com/uploads/Finnceburk/926533726/IMG_9809_medium.jpg", "Dragon Derek", "Finn Burke", "Crochet", "https://www.ravelry.com/patterns/library/dragon-derek", 383, 0));
        exampleList.add(new Pattern("https://images4-f.ravelrycache.com/uploads/mongaknit/926461293/20230513_1431102_small2.png", "DDuDDu", "mongaknit kang", "\n" +
                "Knitting", "https://www.ravelry.com/patterns/library/dduddu", 0, 0));*/


        setUpRecyclerView(); //method initializes and sets the recyclerview, adapter, and layout manager
        return root; // Return the root view created by inflating the FragmentHomeBinding
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setUpRecyclerView() {
        RecyclerView mRecyclerView = binding.recyclerView;
        mRecyclerView.setHasFixedSize(true);

        // Use StaggeredGridLayoutManager instead of LinearLayoutManager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new cardAdapter(exampleList);
        mRecyclerView.setAdapter(mAdapter);
    }
}