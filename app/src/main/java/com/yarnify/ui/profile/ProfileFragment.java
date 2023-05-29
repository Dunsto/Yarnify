package com.yarnify.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.yarnify.R;
import com.example.yarnify.databinding.FragmentProfileBinding;
import com.example.yarnify.databinding.FragmentProfileBinding;
import com.yarnify.NeedleListActivity;
import com.yarnify.SavedPatternsActivity;

public class ProfileFragment extends Fragment {

    public TextView text;
    private FragmentProfileBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button savedPatternsButton = root.findViewById(R.id.savedPatternsButton);
        Button displayNeedleList = root.findViewById(R.id.needleListButton);
        savedPatternsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the SavedPatternsActivity
                Intent intent = new Intent(getActivity(), SavedPatternsActivity.class);
                startActivity(intent);
            }
        });

        displayNeedleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the SavedPatternsActivity
                Intent intent = new Intent(getActivity(), NeedleListActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}