package ru.xbitly.sportquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import ru.xbitly.sportquiz.R;

public class FinishActivity extends AppCompatActivity {

    private Button buttonHome;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        int correct = getIntent().getIntExtra("correct", 0);
        int incorrect = getIntent().getIntExtra("incorrect", 0);

        buttonHome = findViewById(R.id.button_home);
        textResult = findViewById(R.id.text_result);

        String result = correct + "/" + incorrect;
        SpannableString spannableString = new SpannableString(result);

        int correctIndex = String.valueOf(correct).length();
        int incorrectIndex = result.length();

        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.green)), 0, correctIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.light_grey)), correctIndex, correctIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.red)), correctIndex + 1, incorrectIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textResult.setText(spannableString);

        buttonHome.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}