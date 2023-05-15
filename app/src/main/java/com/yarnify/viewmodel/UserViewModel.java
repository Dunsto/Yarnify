package com.yarnify.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yarnify.model.User;
import com.yarnify.repo.Repository;

public class UserViewModel extends AndroidViewModel {

    //access the Repository in the constructor of this viewModel
    private Repository repository;
    public UserViewModel(Application application){
        super(application);
        repository = Repository.getInstance(application.getApplicationContext());
    }

    //add, get, update user
    public void addUser(User user) {
       repository.addUser(user);
    }

    public LiveData<User> getUser(long userId) {
        return repository.getUser(userId);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }
}
