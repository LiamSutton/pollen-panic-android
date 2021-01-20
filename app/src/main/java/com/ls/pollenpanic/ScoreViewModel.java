package com.ls.pollenpanic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
// used to store/retrieve information about leaderboard entries
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
