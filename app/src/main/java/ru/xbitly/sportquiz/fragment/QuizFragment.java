package ru.xbitly.sportquiz.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import ru.xbitly.sportquiz.R;
import ru.xbitly.sportquiz.activity.GameActivity;
import ru.xbitly.sportquiz.data.Question;

public class QuizFragment extends Fragment {

    private final Context context;
    private final ArrayList<Question> easyQuestions;
    private final ArrayList<Question> mediumQuestions;
    private final ArrayList<Question> hardQuestions;

    private Button buttonEasy;
    private Button buttonMedium;
    private Button buttonHard;

    public QuizFragment(
            Context context,
            ArrayList<Question> easyQuestions,
            ArrayList<Question> mediumQuestions,
            ArrayList<Question> hardQuestions
    ) {
        this.context = context;
        this.easyQuestions = easyQuestions;
        this.mediumQuestions = mediumQuestions;
        this.hardQuestions = hardQuestions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonEasy = view.findViewById(R.id.button_easy);
        buttonMedium = view.findViewById(R.id.button_medium);
        buttonHard = view.findViewById(R.id.button_hard);

        buttonEasy.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameActivity.class);
            intent.putParcelableArrayListExtra("questions", easyQuestions);
            intent.putExtra("complexity", "easy");
            startActivity(intent);
            requireActivity().overridePendingTransition(0,0);
            requireActivity().finish();
        });

        buttonMedium.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameActivity.class);
            intent.putParcelableArrayListExtra("questions", mediumQuestions);
            intent.putExtra("complexity", "medium");
            startActivity(intent);
            requireActivity().overridePendingTransition(0,0);
            requireActivity().finish();
        });

        buttonHard.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameActivity.class);
            intent.putParcelableArrayListExtra("questions", hardQuestions);
            intent.putExtra("complexity", "hard");
            startActivity(intent);
            requireActivity().overridePendingTransition(0,0);
            requireActivity().finish();
        });
    }
}