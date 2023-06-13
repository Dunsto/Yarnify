package com.yarnify.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yarnify.model.Pattern;
import com.yarnify.repo.Repository;

import java.util.List;

public class PatternViewModel  extends AndroidViewModel {

    //access the Repository in the constructor of this viewModel
    private Repository repository;
    public PatternViewModel(Application application){
        super(application);
        repository = Repository.getInstance(application.getApplicationContext());
    }

    //getPattern, getPatterns, addPattern, updatePattern, deletePattern

    public LiveData<Pattern> getPattern(long patternId) {
        return repository.getPattern(patternId);
    }

    public LiveData<List<Pattern>> getPatterns() {
        return repository.getPatterns();
    }

    public void addPattern(Pattern pattern) {
        repository.addPattern(pattern);
    }

    public void updatePattern(Pattern pattern) {
        repository.updatePattern(pattern);
    }

    public void deletePattern(Pattern pattern) {
        repository.deletePattern(pattern);
    }

    public void deletePattern(long id) {
        repository.deletePattern(id);
    }

    public void deleteAllPatterns() { repository.deleteAllPatterns(); }

    public LiveData<Integer> getPatternCountLiveData(String title, String creator) {
        return repository.getPatternCountLiveData(title, creator);
    }

    public long getPatternIdByTitleAndCreator(String title, String creator) {
        return repository.getPatternIdByTitleAndCreator(title, creator);
    }
}

