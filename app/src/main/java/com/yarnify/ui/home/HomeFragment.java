package com.yarnify.ui.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yarnify.R;
import com.example.yarnify.databinding.FragmentHomeBinding;
import com.yarnify.API.Request;
import com.yarnify.API.ResponseModels.ResponsePatternList;
import com.yarnify.API.ResponseUtilities.ToPojo;
import com.yarnify.cardAdapter;
import com.yarnify.model.Pattern;
import com.yarnify.viewmodel.PatternViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Pattern> exampleList = new ArrayList<>(); //array of pattern objects
    //binding is an instance of the auto-generated FragmentHomeBinding class, which is used to bind the layout XML elements to their corresponding Java objects.
    private FragmentHomeBinding binding;

    private PatternViewModel patternViewModel;
    private ArrayList<Pattern> patternList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Alex Changes start here

        // I don't think this is an ideal way to allow Network stuff on the main thread
        // Just using it for testing purposes
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Request request =
                new Request("patterns.json?ids=1335913");

        Request request2 =
                new Request("patterns.json?ids=1337254");


        ToPojo toPojo =
                new ToPojo();



        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());

        ResponsePatternList ac2 = toPojo.fromJSONSimple(request2.getResponse());

        // I can make cleaner methods for these, but this is an example of how the classes
        // are deserialize and accessed
        String title1 = ac.getPatterns().getPatternAttributes().getTitle();
        String name1 = ac.getPatterns().getPatternAttributes().getPattern_author().getName();
        String craft1 = ac.getPatterns().getPatternAttributes().getCraft().getName();
        String photoURL1 = ac.getPatterns().getPatternAttributes().getPhotos().get(0).getMedium_url();


        // Inflate the layout for this fragment using the FragmentHomeBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //clear the exampleList before adding new pattern objects
        exampleList.clear();
        //hardcoded example pattern objects
        exampleList.add(new Pattern(R.drawable.ravelry_sample_photo, title1, name1, craft1, "https://www.ravelry.com/patterns/library/helia-bolero", 1500));


        // Alex changes end here


        exampleList.add(new Pattern(R.drawable.armillafirm_small2, "thin sweater", "Jack", "Crochet", "https://www.ravelry.com/patterns/library/helia-bolero", 1500));
        exampleList.add(new Pattern(R.drawable.beanies_medium2, "beanies", "Bob", "Crochet", "https://www.ravelry.com/patterns/library/helia-bolero", 1500));

        setUpRecyclerView(); //method initializes and sets the recyclerview, adapter, and layout manager


        return root; // Return the root view created by inflating the FragmentHomeBinding
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setUpRecyclerView() {
        RecyclerView mRecyclerView = binding.recyclerView; //initialize the RecyclerView from the binding object
        mRecyclerView.setHasFixedSize(true); //the size of the RecyclerView will remain constant and won't change as the contents of the adapter change
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()); //LinearLayoutManager lays out items in a RecyclerView in a vertical fashion
        RecyclerView.Adapter mAdapter = new cardAdapter(exampleList); //A cardAdapter is a custom adapter class that provides the data to the RecyclerView to display.
        //The exampleList is passed to the constructor and contains the list of pattern objects that will be displayed in the RecyclerView.
        mRecyclerView.setLayoutManager(mLayoutManager); //ensures that the RecyclerView knows how to lay out its items
        mRecyclerView.setAdapter(mAdapter); //sets adapter
    }
}