/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/

package com.yarnify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.yarnify.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yarnify.model.Yarn;
import com.yarnify.viewmodel.YarnViewModel;

import java.util.ArrayList;
import java.util.List;

public class YarnListActivity extends AppCompatActivity {

    private ArrayList<Yarn> allYarns = new ArrayList<>();
    private YarnViewModel yarnViewModel;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yarn_list);
        setBackButton();

        //The FAB button brings the user to a new screen to enter a new yarn
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddYarnActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.yarnRecyclerView);

        yarnViewModel = new ViewModelProvider(this).get(YarnViewModel.class);
        yarnViewModel.getYarns().observe(this, yarns -> {
            if (yarns != null){
                allYarns.clear();
                allYarns.addAll(yarns);
            }
            /***************************************************************************************
             * Title: Impossible : No layout manager attached; Skipping layout
             * Author: Jai
             * Date: June 9, 2017
             * Code version: Java
             * Availability: https://stackoverflow.com/questions/37023992/impossible-no-layout-manager-attached-skipping-layout
             *
             ***************************************************************************************/
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new YarnAdapter(allYarns));
        });
    }

    private class YarnAdapter extends RecyclerView.Adapter<YarnHolder>{
        private final List<Yarn> yarnList;
        public YarnAdapter(List<Yarn> allYarns) { yarnList = allYarns; }

        @NonNull
        @Override
        public YarnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            return new YarnHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull YarnHolder holder, int position){
            Yarn yarn = yarnList.get(position);
            holder.bind(yarn, new AllYarnsLongClickListener());
            holder.itemView.setTag(R.string.id_tag, yarn.getId());
        }

        @Override
        public int getItemCount() { return allYarns.size(); }

        public class AllYarnsLongClickListener implements View.OnLongClickListener {

            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);

                popupMenu.inflate(R.menu.supply_long_click_menu);
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();

                    if (itemId == R.id.deleteItem) {
                        yarnViewModel.deleteYarn((long) v.getTag(R.string.id_tag));
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
                return false;
            }
        }
    }

    private static class YarnHolder extends RecyclerView.ViewHolder {
        private final TextView yarnNameText, yarnColorText, yarnDyeLotText,
                yarnWeightText, yarnQtyText;

        private YarnHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.from(parent.getContext())
                    .inflate(R.layout.yarn_list_item, parent, false));
            yarnNameText = itemView.findViewById(R.id.yarnItemName);
            yarnColorText = itemView.findViewById(R.id.yarnItemColor);
            yarnDyeLotText = itemView.findViewById(R.id.yarnItemDyeLot);
            yarnWeightText = itemView.findViewById(R.id.yarnItemWeight);
            yarnQtyText = itemView.findViewById(R.id.yarnItemQtyAvailable);
        }

        public void bind (Yarn yarn, YarnAdapter.AllYarnsLongClickListener listener){
            yarnNameText.setText(yarn.getName());
            yarnColorText.setText(yarn.getColorFamily());
            yarnDyeLotText.setText(yarn.getDyeLot());
            yarnWeightText.setText(yarn.getYarnWeight());

            //Available yarn, by length and weight show in the same field
            //Populate the field based on what data exists
            if(yarn.getTotalLength() != 0 && yarn.getTotalWeight() != 0){
                yarnQtyText.setText(yarn.getTotalLength() + " " + yarn.getLengthUnits()
                   + " - " + yarn.getTotalWeight() + " " + yarn.getWeightUnits());
            } else if(yarn.getTotalLength() != 0){
                yarnQtyText.setText(yarn.getTotalLength() + " " + yarn.getLengthUnits());
            } else {
                yarnQtyText.setText(yarn.getTotalWeight() + " " + yarn.getWeightUnits());
            }

            itemView.setOnLongClickListener(listener);
        }
    }
    public void setBackButton() {
        // Get the support action bar
        ActionBar actionBar = getSupportActionBar();
        // Set the title
        actionBar.setTitle("Yarns");
        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set a custom drawable for the up indicator to change its color
        Drawable upArrow = getResources().getDrawable(R.drawable.baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.light_gray), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
    }
}