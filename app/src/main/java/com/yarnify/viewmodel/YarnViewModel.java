/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/

package com.yarnify.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yarnify.model.Yarn;
import com.yarnify.repo.Repository;

import java.util.List;

public class YarnViewModel extends AndroidViewModel {

    //Access the Repository in the constructor of this viewModel
    private Repository repository;
    public YarnViewModel(Application application){
        super(application);
        repository = Repository.getInstance(application.getApplicationContext());
    }

    //getYarn, getYarns, addYarn, updateYarn, deleteYarn
    //TODO: getYarnsOrderBy(String orderChoice)
    public LiveData<Yarn> getYarn(long yarnId) {
        return repository.getYarn(yarnId);
    }

    public LiveData<List<Yarn>> getYarns() {
        return repository.getYarns();
    }

    public void addYarn(Yarn yarn) {
        repository.addYarn(yarn);
    }

    public void updateYarn(Yarn yarn) {
        repository.updateYarn(yarn);
    }

    public void deleteYarn(Yarn yarn) {
        repository.deleteYarn(yarn);
    }

    public void deleteYarn(long id) {
        repository.deleteYarn(id);
    }
}
