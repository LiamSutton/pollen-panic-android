package com.ls.pollenpanic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {
    private MutableLiveData<ScoreModel> liveScoreModel;

    public ScoreViewModel() {
        liveScoreModel = new MutableLiveData<ScoreModel>();
        liveScoreModel.setValue(new ScoreModel());
    }

    LiveData<ScoreModel> getScoreModel() {
        return liveScoreModel;
    }
}
