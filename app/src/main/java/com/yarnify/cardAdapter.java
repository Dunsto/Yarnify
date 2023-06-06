package com.yarnify;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.yarnify.R;
import com.squareup.picasso.Picasso;
import com.yarnify.model.Pattern;

import java.util.ArrayList;

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.exampleViewHolder> {

    private ArrayList<Pattern> mExampleList; //local variable that will hold the passed in list of patternObjects

    public static class exampleViewHolder extends RecyclerView.ViewHolder { //contains three views  that will be displayed in the card. The constructor of this class sets the views
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public exampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);
        }
    }

    //defines the constructor for the adapter that takes an array of patternObjects and assigns it to the mExampleList variable.
    public cardAdapter(ArrayList<Pattern> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    //This method is called when a new ViewHolder object needs to be created for the RecyclerView. It inflates the pattern_card.xml layout file
    //and returns a new exampleViewHolder object based on the inflated view.
    public exampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pattern_card, parent, false);
        // Create a new ConstraintLayout.LayoutParams instance for the card view
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 8, 8, 8); // Set margins for the card view
        v.setLayoutParams(layoutParams); // Apply the LayoutParams to the card view
        return new exampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull exampleViewHolder holder, int position) { //This method is called to bind the data to a specific view holder
        Pattern currentItem = mExampleList.get(position);
        //sets the values of the ImageView and two TextView views in the exampleViewHolder object based on the patternObject at the position in the mExampleList array.
        Picasso.get().load(currentItem.getImageResource()).into(holder.mImageView);
        Picasso.get().load(currentItem.getImageResource()).into(holder.mImageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                // Get the loaded Bitmap from the ImageView
                Drawable imageDrawable = holder.mImageView.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) imageDrawable).getBitmap();
                // Create a RoundedBitmapDrawable from the Bitmap
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(holder.itemView.getContext().getResources(), bitmap);
                // Set the corner radius
                roundedDrawable.setCornerRadius(16f);
                // Apply the RoundedBitmapDrawable to the ImageView
                holder.mImageView.setImageDrawable(roundedDrawable);
            }

            @Override
            public void onError(Exception e) {
                Log.d("adapter", "image couldn't load");
            }
        });
        holder.mTextView1.setText(currentItem.getTitle());
        holder.mTextView2.setText("by " + currentItem.getCreator());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity and pass the clicked item as an extra
                Intent intent = new Intent(view.getContext(), PatternPageActivity.class);
                intent.putExtra("clicked_item", currentItem);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}