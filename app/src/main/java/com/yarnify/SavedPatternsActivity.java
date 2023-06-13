package com.yarnify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yarnify.R;
import com.yarnify.model.Pattern;
import com.yarnify.viewmodel.PatternViewModel;

import java.util.ArrayList;

public class SavedPatternsActivity extends AppCompatActivity {

    ArrayList<Pattern> savedPatternExampleList = new ArrayList<>(); //array of pattern objects
    private PatternViewModel patternViewModel;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_patterns);
        setBackButton();

        // observes the live data and gets all saved patterns to display.... I think?
        patternViewModel = new ViewModelProvider(this).get(PatternViewModel.class);
        patternViewModel.getPatterns().observe(this, patterns -> {
            if (patterns != null) {
                savedPatternExampleList.clear();
                savedPatternExampleList.addAll(patterns);
                Log.d("saved patterns amount", String.valueOf(savedPatternExampleList.size()));
                if (savedPatternExampleList.isEmpty()) {
                    Log.d("Search Frag", "No search results!");
                    Toast.makeText(getApplicationContext(), "You don't have any saved patterns yet!", Toast.LENGTH_SHORT).show();
                }
                setUpRecyclerView(context); //method initializes and sets the recyclerview, adapter, and layout manager
            }
        });

    }

    public void setUpRecyclerView(Context context) {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView2); //initialize the RecyclerView from the binding object
        mRecyclerView.setHasFixedSize(true); //the size of the RecyclerView will remain constant and won't change as the contents of the adapter change
        // Use StaggeredGridLayoutManager instead of LinearLayoutManager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new cardAdapter(savedPatternExampleList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setBackButton() {
        // Get the support action bar
        ActionBar actionBar = getSupportActionBar();
        // Set the title
        actionBar.setTitle("Saved Patterns");
        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set a custom drawable for the up indicator to change its color
        Drawable upArrow = getResources().getDrawable(R.drawable.baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.light_gray), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public void setUpRecyclerView(Context context) {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView2); //initialize the RecyclerView from the binding object
        mRecyclerView.setHasFixedSize(true); //the size of the RecyclerView will remain constant and won't change as the contents of the adapter change
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context); //LinearLayoutManager lays out items in a RecyclerView in a vertical fashion
        RecyclerView.Adapter mAdapter = new cardAdapter(savedPatternExampleList); //A cardAdapter is a custom adapter class that provides the data to the RecyclerView to display.
        //The exampleList is passed to the constructor and contains the list of pattern objects that will be displayed in the RecyclerView.
        mRecyclerView.setLayoutManager(mLayoutManager); //ensures that the RecyclerView knows how to lay out its items
        mRecyclerView.setAdapter(mAdapter); //sets adapter
    }*/
}