package com.yarnify.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yarnify.model.Needle;
import com.yarnify.repo.Repository;

import java.util.List;

public class NeedleViewModel extends AndroidViewModel {

    //access the Repository in the constructor of this viewModel
    private Repository repository;
    public NeedleViewModel(Application application){
        super(application);
        repository = Repository.getInstance(application.getApplicationContext());
    }

    //getNeedle, getNeedles, addNeedle, updateNeedle deleteNeedle
    public LiveData<Needle> getNeedle(long needleId) {
        return repository.getNeedle(needleId);
    }

    public LiveData<List<Needle>> getNeedles() {
        return repository.getNeedles();
    }

    public void addNeedle(Needle needle) {
        repository.addNeedle(needle);
    }

    public void updateNeedle(Needle needle) {
        repository.updateNeedle(needle);
    }

    public void deleteNeedle(Needle needle) {
        repository.deleteNeedle(needle);
    }

    public void deleteNeedle(long id) {
        repository.deleteNeedle(id);
    }

}
