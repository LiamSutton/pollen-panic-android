package com.ls.pollenpanic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameOverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameOverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameOverFragment() {
        // Required empty public constructor
    }
    NavController navController;
    ScoreViewModel scoreViewModel;
    ScoreModel scoreModel;
    TextView scoreTv;
    EditText userNameEt;
    Button submitScoreBtn;
    Button playAgainBtn;
    Button mainMenuBtn;
    boolean scoreSubmitted = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        scoreTv = (TextView)view.findViewById(R.id.score_tv);
        userNameEt = (EditText)view.findViewById(R.id.user_name_et);
        submitScoreBtn = (Button)view.findViewById(R.id.submit_score_button);
        playAgainBtn = (Button)view.findViewById(R.id.play_again_btn);
        mainMenuBtn = (Button)view.findViewById(R.id.main_menu_btn);
        scoreViewModel = new ViewModelProvider(requireActivity()).get(ScoreViewModel.class);
        scoreModel = scoreViewModel.getScoreModel().getValue();
        scoreViewModel.getScoreModel().observe(getViewLifecycleOwner(), new Observer<ScoreModel>() {
            @Override
            public void onChanged(ScoreModel scoreModel) {
                scoreTv.setText("Your Score: " + scoreModel.getScore());
            }
        });

        submitScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userNameEt.getText().toString().trim();
                scoreModel.setUserName(name);
                DBHelper db = new DBHelper(getContext());

                db.addNewScore(scoreModel.getUserName(), scoreModel.getScore());

                scoreSubmitted = true;
                submitScoreBtn.setEnabled(false);
            }
        });

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_gameOverFragment_to_gameFragment);
            }
        });

        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_gameOverFragment_to_mainMenuFragment);
            }
        });

        userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && !scoreSubmitted) {
                    submitScoreBtn.setEnabled(true);
                }
            }
        });

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameOverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameOverFragment newInstance(String param1, String param2) {
        GameOverFragment fragment = new GameOverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_over, container, false);
    }
}