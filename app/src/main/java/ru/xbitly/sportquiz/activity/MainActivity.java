package ru.xbitly.sportquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import ru.xbitly.sportquiz.data.Question;
import ru.xbitly.sportquiz.data.Wallpaper;
import ru.xbitly.sportquiz.fragment.QuizFragment;
import ru.xbitly.sportquiz.R;
import ru.xbitly.sportquiz.fragment.ShopFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Wallpaper> wallpapers = new ArrayList<>();

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
        wallpapers.add(
                new Wallpaper(
                        10,
                        sharedPreferences.getString("purchased", "").contains(Integer.toString(R.drawable.img_wallpaper_1)),
                        R.drawable.img_wallpaper_1
                )
        );
        wallpapers.add(
                new Wallpaper(
                        20,
                        sharedPreferences.getString("purchased", "").contains(Integer.toString(R.drawable.img_wallpaper_2)),
                        R.drawable.img_wallpaper_2
                )
        );
        wallpapers.add(
                new Wallpaper(
                        30,
                        sharedPreferences.getString("purchased", "").contains(Integer.toString(R.drawable.img_wallpaper_3)),
                        R.drawable.img_wallpaper_3
                )
        );
        wallpapers.add(
                new Wallpaper(
                        40,
                        sharedPreferences.getString("purchased", "").contains(Integer.toString(R.drawable.img_wallpaper_4)),
                        R.drawable.img_wallpaper_4
                )
        );
        wallpapers.add(
                new Wallpaper(
                        50,
                        sharedPreferences.getString("purchased", "").contains(Integer.toString(R.drawable.img_wallpaper_5)),
                        R.drawable.img_wallpaper_5
                )
        );

        ShopFragment shopFragment = new ShopFragment(this, wallpapers, points);
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

    //TODO: onBackPressed()

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(sharedPreferences.getInt("fragment", R.id.quiz));
    }
}