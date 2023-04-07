package ru.xbitly.sportquiz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

import ru.xbitly.sportquiz.R;
import ru.xbitly.sportquiz.data.Question;
import ru.xbitly.sportquiz.recycler.ButtonRecyclerAdapter;

public class GameActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private ImageButton exit;
    private TextView timer;
    private TextView points;

    private RecyclerView recyclerView;
    private TextView question;

    private ArrayList<Question> questions;

    private CountDownTimer countDownTimer;

    private int correct = 0;
    private int incorrect = 0;

    private int position = 0;

    private int fine;
    private int bonus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questions = getIntent().getParcelableArrayListExtra("questions");
        String complexity = getIntent().getStringExtra("complexity");

        exit = findViewById(R.id.button_exit);
        timer = findViewById(R.id.text_timer);
        points = findViewById(R.id.text_points);
        recyclerView = findViewById(R.id.recycler);
        question = findViewById(R.id.text_question);

        fine = 0;
        bonus = 0;

        switch (complexity){
            case "easy":
                bonus = 1;
                break;
            case "medium":
                fine = 1;
                bonus = 2;
                break;
            case "hard":
                fine = 2;
                bonus = 3;
                break;
            default:
                break;
        }

        countDownTimer = new CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timer.setText(Long.toString(millisUntilFinished / 1000));
            }
            public void onFinish() {
                toFinish();
            }
        }.start();

        updatePoints();

        selectQuestion();

        exit.setOnClickListener(view -> createExitAlertDialog());
    }

    @Override
    public void onBackPressed() {
        createExitAlertDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    private void toFinish() {
        Intent intent = new Intent(GameActivity.this, FinishActivity.class);
        intent.putExtra("correct", correct);
        intent.putExtra("incorrect", incorrect);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    private void nextQuestion() {
        new Handler().postDelayed(() -> {
            updatePoints();
            if (position < questions.size() - 1) position++;
            else toFinish();
            selectQuestion();
        }, 1000);
    }

    private void selectQuestion() {
        question.setText(questions.get(position).getQuestion());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ButtonRecyclerAdapter buttonRecyclerAdapter = new ButtonRecyclerAdapter(
                this,
                questions.get(position).getAnswers(),
                questions.get(position).getCorrectAnswer(),
                fine,
                bonus
        );
        recyclerView.setAdapter(buttonRecyclerAdapter);
        buttonRecyclerAdapter.setOnButtonClickListener(result -> {
            if (result < 0) incorrect++;
            else correct++;
            nextQuestion();
        });

    }

    private void updatePoints() {
        String pointsText = getString(R.string.points) + ": " + sharedPreferences.getInt("points", 0);
        points.setText(pointsText);
    }

    private void createExitAlertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(dialog.getContext());
        View installView = inflater.inflate(R.layout.view_exit_dialog, null);
        dialog.setView(installView);

        Button buttonExit = installView.findViewById(R.id.button_exit);
        Button buttonCancel = installView.findViewById(R.id.button_cancel);

        AlertDialog alert = dialog.create();

        buttonCancel.setOnClickListener(view -> alert.dismiss());
        buttonExit.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        });

        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable back = getDrawable(R.drawable.bg_corner_white);
        InsetDrawable inset = new InsetDrawable(back, getResources().getDimensionPixelSize(R.dimen.item_vertical_margin));
        alert.getWindow().setBackgroundDrawable(inset);
        alert.show();
    }
}