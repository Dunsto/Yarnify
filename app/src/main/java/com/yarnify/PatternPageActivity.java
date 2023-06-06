package com.yarnify;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.yarnify.R;
import com.squareup.picasso.Picasso;
import com.yarnify.model.Pattern;
import com.yarnify.repo.Repository;
import com.yarnify.viewmodel.PatternViewModel;

public class PatternPageActivity extends AppCompatActivity {

    //this activity is the display page for a pattern when it is clicked on

    public ImageView image;
    public TextView text1, text2, text3, text4, text5;
    public Button saveButton;
    public boolean isSaved;

    private PatternViewModel patternViewModel;
    private static final String TAG = "PatternPageActivity";
    long patternID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_page);
        setBackButton();

        //Access the database via the ViewModel
        patternViewModel = new ViewModelProvider(this).get(PatternViewModel.class);

        //sets local variables of the patterns data to the activity_pattern_page.xml layout's views
        image = findViewById(R.id.patternImage);
        text1 = findViewById(R.id.patternName);
        text2 = findViewById(R.id.patternCreator);
        saveButton = findViewById(R.id.saveButton);
        text3 = findViewById(R.id.patternCraft);
        text4= findViewById(R.id.patternURL);
        text5 = findViewById(R.id.patternTotalYardage);

        Intent intent = getIntent(); //grabs intent from parent
        Pattern pat = intent.getParcelableExtra("clicked_item"); //grabs parceled patternObject that was clicked on to use in this class

        //sets patternObject's data to the pattern page's layout views
        Picasso.get().load(pat.getImageResource()).into(image);
        //image.setImageResource(pat.getImageResource());
        text1.setText(pat.getTitle());
        text2.setText("by " + pat.getCreator());
        text3.setText("Craft: " + pat.getCraft());
        text4.setText("Pattern's URL: " + pat.getURL());
        text5.setText("Yardage: " + pat.getMinYardage() + " - " + pat.getMaxYardage() + " yards");

        //observes whether this pattern has been saved or not and updates the saved button's text as well as the isSaved boolean
        LiveData<Integer> patternCountLiveData = patternViewModel.getPatternCountLiveData(pat.getTitle(), pat.getCreator());
        patternCountLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                isSaved = count > 0;
                // Use the patternExists boolean value as needed
                if (isSaved) {
                    saveButton.setText("Saved");
                } else {
                    saveButton.setText("Save");
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSaved) {
                    //ID of saved pattern with matching title and creator columns
                    patternID = patternViewModel.getPatternIdByTitleAndCreator(pat.getTitle(), pat.getCreator());
                    saveButton.setText("Save");
                    Log.d(TAG, "pattern has been deleted");
                    patternViewModel.deletePattern(patternID);
                } else {
                    saveButton.setText("Saved");
                    Log.d(TAG, "pattern has been saved");
                    patternViewModel.addPattern(pat);
                }
            }
        });
    }

    public void setBackButton() {
        // Get the support action bar
        ActionBar actionBar = getSupportActionBar();
        // Set the title
        actionBar.setTitle("");
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


}