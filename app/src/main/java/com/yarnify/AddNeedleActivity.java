package com.yarnify;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yarnify.databinding.ActivityAddNeedleBinding;

import com.example.yarnify.R;
import com.yarnify.model.Needle;
import com.yarnify.viewmodel.NeedleViewModel;

public class AddNeedleActivity extends AppCompatActivity {
    private Context context = this;
    private Button saveNeedleButton;
    private EditText lengthText;
    private NeedleViewModel needleViewModel;
    private Needle needle;
    private String craft, type, us;
    private double metric;
    private int length;
    private boolean isHook, metricUnits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_needle);
        needleViewModel = new ViewModelProvider(this).get(NeedleViewModel.class);
        setBackButton();

        //Type RadioButton
        //The options in the TypeSpinner are changed depending on which RadioButton is checked
        //TODO: remember the last type selected when the radio button goes back and forth
        //https://stackoverflow.com/questions/22943045/why-oncheckedchanged-for-radiobutton-doesnt-get-raised-in-android
        RadioGroup typeRadioGroup = (RadioGroup) findViewById(R.id.craftType);
        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Check which radio button was checked
                switch (checkedId) {
                    case R.id.knittingNeedle:
                        craft = "knitting";
                        isHook = false;
                        Log.i("Changed Craft:", craft);
                        setTypeSpinner(R.array.knitting_needle_type_choices);
                        if(metricUnits){
                            setSizeSpinner(R.array.needle_size_metric_choices);
                        } else {
                            setSizeSpinner(R.array.needle_size_US_knit_choices);
                        }
                        break;
                    case R.id.crochetHook:
                        craft = "crochet";
                        isHook = true;
                        Log.i("Changed Craft:", craft);
                        setTypeSpinner(R.array.crochet_hook_type_choices);
                        if(metricUnits){
                            setSizeSpinner(R.array.needle_size_metric_choices);
                        } else {
                            setSizeSpinner(R.array.needle_size_US_crochet_choices);
                        }
                        break;
                }
            }
        });

        //Type Spinner initially shows all options
        setTypeSpinner(R.array.all_needle_type_choices);

        //SizeUnit RadioButton
        //The size options in the size spinner update according to which radio button is selected
        //TODO: align the us and metric choices so if a user selects a metric option and changes to us, the equivalent option is already selected.
        //https://stackoverflow.com/questions/22943045/why-oncheckedchanged-for-radiobutton-doesnt-get-raised-in-android
        RadioGroup sizeUnitRadioGroup = (RadioGroup) findViewById(R.id.needleSizeUnit);
        sizeUnitRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Check which radio button was checked
                switch (checkedId) {
                    case R.id.metric:
                        metricUnits = true;
                        Log.i("Changed Units", "metric");
                        setSizeSpinner(R.array.needle_size_metric_choices);
                        break;
                    case R.id.us:
                        metricUnits = false;
                        Log.i("Changed Units", "us");
                        if(isHook){
                            setSizeSpinner(R.array.needle_size_US_crochet_choices);
                        } else {
                            setSizeSpinner(R.array.needle_size_US_knit_choices);
                        }
                        break;
                }
            }
        });

        setSizeSpinner(R.array.needle_size_metric_choices);

        lengthText = (EditText) findViewById(R.id.needleLength);

        //When the save button is clicked, create an instance of a Needle, save it in the
        //database, and return to the last activity.
        saveNeedleButton = findViewById(R.id.saveNeedleButton);
        saveNeedleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.geeksforgeeks.org/numberformatexception-in-java-with-examples/
                try {
                    length = Integer.parseInt(lengthText.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e("NumberFormatException", e.toString());
                }
                String brand = "TBD";
                String material = "TBD";
                needle = new Needle(type, craft, metric, isHook, us, length, brand,
                        material, 1);
                needleViewModel.addNeedle(needle);
                finish();
            }
        }
        );
    }

    /*
    * Sets and updates the Type Spinner to whatever array is appropriate
    * @params: int array ResourceID - the id of the array of strings for the dropdown
    */
    private void setTypeSpinner(int arrayResourceId) {
        //Spinner for type of needle
        Spinner typeSpinner = findViewById(R.id.needleTypeSpinner);
        int typeChoicesArrayId = arrayResourceId;
        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                typeChoicesArrayId, android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
     * Sets and updates the Type Spinner to whatever array is appropriate
     * @params: int array ResourceID - the id of the array of strings for the dropdown
     */
    private void setSizeSpinner(int arrayResourceId) {
        //Spinner for type of needle
        Spinner unitSpinner = findViewById(R.id.needleSizeSpinner);
        int sizeChoicesArrayId = arrayResourceId;
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                sizeChoicesArrayId, android.R.layout.simple_spinner_item);
        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(sizeSpinnerAdapter);
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(metricUnits){
                    Log.i("Metric Units are", (String) parent.getItemAtPosition(position));
                    String metricSelection = (String) parent.getItemAtPosition(position);
                    metric = Double.parseDouble(metricSelection);
                } else {
                    Log.i("US Units are", (String) parent.getItemAtPosition(position));
                    us = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onCraftSelected(View view) {
        // Is the button checked?
        boolean checked = ((RadioButton) view).isChecked();
        //Check which radio button was checked
        switch (view.getId()) {
            case R.id.knittingNeedle:
                if (checked){
                    craft = "knitting";
                    isHook = false;
                }
                break;
            case R.id.crochetHook:
                if (checked){
                    craft = "crochet";
                    isHook = true;
                }
                break;
        }
        Log.i("Craft:", craft);
    }

    public void onUnitsSelected(View view) {
        // Is the button checked?
        boolean checked = ((RadioButton) view).isChecked();
        //Check which radio button was checked
        switch (view.getId()) {
            case R.id.metric:
                if (checked){
                    metricUnits = true;
                    Log.i("Units", "metric");
                }
                break;
            case R.id.crochetHook:
                if (checked){
                    metricUnits = false;
                    Log.i("Units", "us");
                }
                break;
        }
    }
    public void setBackButton() {
        // Get the support action bar
        ActionBar actionBar = getSupportActionBar();
        // Set the title
        actionBar.setTitle("Needles and Hooks");
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