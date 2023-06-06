package com.yarnify;

import static android.view.View.INVISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yarnify.R;
import com.yarnify.model.Needle;
import com.yarnify.viewmodel.NeedleViewModel;

import java.util.ArrayList;
import java.util.List;

public class NeedleListActivity extends AppCompatActivity {

    private ArrayList<Needle> allNeedles = new ArrayList<>();
    private NeedleViewModel needleViewModel;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needle_list);
        setBackButton();

        //The FAB button brings the user to a new screen to enter another tool
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Enter New Needle or Hook", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
                Intent intent = new Intent(context, AddNeedleActivity.class);
                startActivity(intent);
            }
        });

        //Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.needleRecyclerView);

        //Populate the recyclerView from the database
        needleViewModel = new ViewModelProvider(this).get(NeedleViewModel.class);
        needleViewModel.getNeedles().observe(this, needles -> {
            if (needles != null){
                allNeedles.clear();
                allNeedles.addAll(needles);
                recyclerView.setAdapter(new NeedleAdapter(allNeedles));
                //https://stackoverflow.com/questions/37023992/impossible-no-layout-manager-attached-skipping-layout
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        });

    }

    //The NeedleAdapter class assists with populating the RecyclerView
    private class NeedleAdapter extends RecyclerView.Adapter<NeedleHolder>{

        private final List<Needle> needleList;

        public NeedleAdapter(List<Needle> allNeedles){
            needleList = allNeedles;
        }

        @NonNull
        @Override
        public NeedleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            return new NeedleHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NeedleHolder holder, int position){
            Needle needle = needleList.get(position);
            holder.bind(needle, new AllNeedlesLongClickListener());
            holder.itemView.setTag(R.string.id_tag, needle.getId());
            //holder.itemView.setTag("id", needle.getId());
        }

        @Override
        public int getItemCount() {
            return allNeedles.size();
        }

        public class AllNeedlesLongClickListener implements View.OnLongClickListener{

            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, v);

                popupMenu.inflate(R.menu.supply_long_click_menu);

                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();

                    if (itemId == R.id.deleteItem) {

                        needleViewModel.deleteNeedle((long) v.getTag(R.string.id_tag));
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
                return false;
            }
        }
    }

    private static class NeedleHolder extends RecyclerView.ViewHolder {
        private final TextView craftText, sizeText, lengthText, typeText, qtyText;

        //This method identifies what UI elements will "hold" the Needle data
        public NeedleHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.from(parent.getContext()).inflate(R.layout.needle_list_item, parent, false));
            craftText = itemView.findViewById(R.id.needleItemCraft);
            sizeText = itemView.findViewById(R.id.needleItemSize);
            lengthText = itemView.findViewById(R.id.needleItemLength);
            typeText = itemView.findViewById(R.id.needleItemType);
            qtyText = itemView.findViewById(R.id.needleItemQtyText);
        }

        //This method takes the info stored in the Needle and binds it view elements
        public void bind (Needle needle, NeedleAdapter.AllNeedlesLongClickListener listener) {
            //Craft depends on knit vs. crochet
            if(needle.getCraft().equals("knitting")){
                craftText.setText("Knitting Needle");
            } else {
                craftText.setText("Crochet Hook");
            }
            //Size depends on metric vs US
            if(needle.getMetric() > 0){
                sizeText.setText(needle.getMetric() + " mm");
            } else {
                sizeText.setText("US " + needle.getUs());
            }
            if(needle.getLength() <= 0){
                lengthText.setVisibility(INVISIBLE);
            }
            lengthText.setText(needle.getLength()+ "\"");
            typeText.setText(needle.getType());
            qtyText.setText("Qty: " + needle.getQty());

            itemView.setOnLongClickListener(listener);
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