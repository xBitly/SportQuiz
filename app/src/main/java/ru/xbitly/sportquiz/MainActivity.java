package ru.xbitly.sportquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        TextView header = findViewById(R.id.text_header);
        TextView points = findViewById(R.id.text_points);

        String pointsText = getString(R.string.points) + ": " + sharedPreferences.getInt("points", 0);

        ShopFragment shopFragment = new ShopFragment(this);
        QuizFragment quizFragment = new QuizFragment(this);

        points.setText(pointsText);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.shop:
                    header.setText(getString(R.string.shop));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, shopFragment).commit();
                    editor.putInt("fragment", R.id.shop);
                    editor.apply();
                    return true;

                case R.id.quiz:
                    header.setText(getString(R.string.quiz));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, quizFragment).commit();
                    editor.putInt("fragment", R.id.quiz);
                    editor.apply();
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(sharedPreferences.getInt("fragment", R.id.quiz));
    }
}