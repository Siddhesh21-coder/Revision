package com.codersarena.revision.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.codersarena.revision.model.Image;
import com.codersarena.revision.model.SubTopicM;
import com.codersarena.revision.model.SubjectGroup;
import com.codersarena.revision.model.Topic;
import com.codersarena.revision.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    Repository repository;

    Context context;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();

    }

    public void signUpUser(String email, String password){
        context = this.getApplication();

        repository.logEmailPassword(context,email,password);
    }

    public void createUser(String email,String password,String name)
    {
        context = this.getApplication();

        repository.createUser(context,email,password,name);
    }

    public MutableLiveData<List<SubjectGroup>> getGroupList() {
        return repository.getGroupMutableList();
    }

    public MutableLiveData<List<SubTopicM>> getSubTopicList(String group) {
        return repository.getSubTopicMutableList(group);
    }
    public MutableLiveData<List<Topic>> getTopicList(String subjectName) {
        return repository.getTopicList(subjectName);
    }

    public void saveInDb(String groupName, String name, String link, ArrayList<Image> stringUrl)
    {
        context = this.getApplication();
        repository.saveToDb(context,groupName,name,link,stringUrl);

    }
}
