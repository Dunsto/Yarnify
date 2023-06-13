/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/

package com.yarnify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.yarnify.R;
import com.yarnify.model.Yarn;
import com.yarnify.viewmodel.YarnViewModel;

public class AddYarnActivity extends AppCompatActivity {
    private Context context = this;
    private YarnViewModel yarnViewModel;
    private Button saveYarnButton;
    private EditText nameText, totalLengthText, totalWeightText, dyeLotText;
    private String name, weight, lengthUnits, weightUnits, colorFamily, dyeLot;
    private int totalLength, totalWeight;

    private Yarn yarn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_yarn);
        yarnViewModel = new ViewModelProvider(this).get(YarnViewModel.class);
        setBackButton();

        //Access and assign UI elements
        //EditText elements
        nameText = findViewById(R.id.yarnName);
        totalLengthText = findViewById(R.id.totalLength);
        totalWeightText = findViewById(R.id.totalWeight);
        dyeLotText = findViewById(R.id.dyeLot);

        //Spinner elements
        setSpinner(R.id.yarnWeightSpinner, R.array.yarn_weight_options);
        setSpinner(R.id.colorFamilySpinner, R.array.color_family_options);

        //RadioGroup elements
        RadioGroup lengthUnitsRadioGroup = (RadioGroup) findViewById(R.id.lengthUnits);
        RadioGroup weightUnitsRadioGroup = (RadioGroup) findViewById(R.id.weightUnits);

        //Button elements
        saveYarnButton = findViewById(R.id.saveYarnButton);

        lengthUnitsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Check which radio button was checked
                switch(checkedId){
                    case R.id.yards:
                        lengthUnits = "yards";
                        break;
                    case R.id.meters:
                        lengthUnits = "meters";
                        break;
                }
            }
        });

        weightUnitsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Check which radio button was checked
                switch(checkedId){
                    case R.id.grams:
                        weightUnits = "grams";
                        break;
                    case R.id.ounces:
                        weightUnits = "ounces";
                        break;
                }
            }
        });

        saveYarnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                try {
                    totalLength = Integer.parseInt(totalLengthText.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e("NumberFormatException", e.toString());
                }
                try {
                    totalWeight = Integer.parseInt(totalWeightText.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e("NumberFormatException", e.toString());
                }
                dyeLot = dyeLotText.getText().toString();

                yarn = new Yarn(name, weight, lengthUnits, totalLength, weightUnits, totalWeight,
                        colorFamily, "n/a", dyeLot);

                yarnViewModel.addYarn(yarn);
                finish();
            }
        });
    }

    /*
     * Sets and updates the Type Spinner to whatever array is appropriate
     * @params: int spinnerResourceID - the id of which spinner to update
     * @params: int arrayResourceID - the id of the array of strings for the dropdown
     */
    private void setSpinner(int spinnerResourceId, int arrayResourceId) {
        Spinner spinner = findViewById(spinnerResourceId);
        int choicesArrayId = arrayResourceId;
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                choicesArrayId, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Depending on the choice made in which spinner, update the appropriate attribute
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerResourceId == R.id.yarnWeightSpinner){
                    weight = (String) parent.getItemAtPosition(position);
                } else if (spinnerResourceId == R.id.colorFamilySpinner) {
                    colorFamily = (String) parent.getItemAtPosition(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
}