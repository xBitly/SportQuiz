package ru.xbitly.sportquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import ru.xbitly.sportquiz.data.Question;
import ru.xbitly.sportquiz.data.Wallpaper;
import ru.xbitly.sportquiz.fragment.QuizFragment;
import ru.xbitly.sportquiz.R;
import ru.xbitly.sportquiz.fragment.ShopFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private long backPressedTime;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Wallpaper> wallpapers = new ArrayList<>();
    private ArrayList<Question> easyQuestions = new ArrayList<>();
    private ArrayList<Question> mediumQuestions = new ArrayList<>();
    private ArrayList<Question> hardQuestions = new ArrayList<>();

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
        easyQuestions.add(
                new Question(
                        "What is the highest score you can achieve with a single shot in basketball?",
                        new String[]{"2 points", "3 points", "4 points", "5 points"},
                        1
                )
        );
        easyQuestions.add(
                new Question(
                        "In what sport is the 'Birdie' used?",
                        new String[]{"Table Tennis", "Badminton", "Tennis", "Volleyball"},
                        1
                )
        );
        easyQuestions.add(
                new Question(
                        "Which of these sports uses a net, but not a ball?",
                        new String[]{"Table Tennis", "Badminton", "Soccer", "Basketball"},
                        1
                )
        );
        easyQuestions.add(
                new Question(
                        "In what country did the game of chess originate?",
                        new String[]{"Greece", "India", "China", "Egypt"},
                        1
                )
        );
        easyQuestions.add(
                new Question(
                        "What is the highest score you can achieve in gymnastics?",
                        new String[]{"10.0", "9.5", "9.9", "8.5"},
                        0
                )
        );
        easyQuestions.add(
                new Question(
                        "In what year was the first FIFA World Cup held?",
                        new String[]{"1930", "1920", "1950", "1940"},
                        0
                )
        );
        easyQuestions.add(
                new Question(
                        "Which of these sports is not an Olympic sport?",
                        new String[]{"Table Tennis", "Baseball", "Basketball", "Cricket"},
                        3
                )
        );
        easyQuestions.add(
                new Question(
                        "What is the distance of a marathon race in kilometers?",
                        new String[]{"26.2 km", "10 km", "50 km", "100 km"},
                        0
                )
        );
        easyQuestions.add(
                new Question(
                        "What is the national sport of Japan?",
                        new String[]{"Sumo Wrestling", "Judo", "Karate", "Kendo"},
                        0
                )
        );
        easyQuestions.add(
                new Question(
                        "In what year was the first modern Olympic Games held?",
                        new String[]{"1896", "1900", "1910", "1880"},
                        0
                )
        );
        mediumQuestions.add(
                new Question(
                        "Which country won the first World Cup?",
                        new String[]{"Brazil", "Uruguay", "Italy", "Argentina"},
                        1
                )
        );
        mediumQuestions.add(
                new Question(
                        "Which player has scored the most goals in World Cup history?",
                        new String[]{"Pele", "Miroslav Klose", "Diego Maradona", "Lionel Messi"},
                        1
                )
        );
        mediumQuestions.add(
                new Question(
                        "Which city hosted the 2000 Summer Olympics?",
                        new String[]{"Sydney", "Atlanta", "Athens", "Beijing"},
                        0
                )
        );
        mediumQuestions.add(
                new Question(
                        "What is the oldest tennis tournament in the world?",
                        new String[]{"US Open", "Australian Open", "French Open", "Wimbledon"},
                        3
                )
        );
        mediumQuestions.add(
                new Question(
                        "Which team won the first Super Bowl in 1967?",
                        new String[]{"New York Jets", "Green Bay Packers", "Dallas Cowboys", "Pittsburgh Steelers"},
                        1
                )
        );
        mediumQuestions.add(
                new Question(
                        "Which athlete has won the most Olympic medals of all time?",
                        new String[]{"Usain Bolt", "Michael Phelps", "Carl Lewis", "Jesse Owens"},
                        1
                )
        );

        mediumQuestions.add(
                new Question(
                        "Which team won the 2018 FIFA World Cup?",
                        new String[]{"Brazil", "Spain", "France", "Argentina"},
                        2
                )
        );
        mediumQuestions.add(
                new Question(
                        "Who is the all-time leading scorer in NBA history?",
                        new String[]{"Kareem Abdul-Jabbar", "LeBron James", "Kobe Bryant", "Michael Jordan"},
                        0
                )
        );
        mediumQuestions.add(
                new Question(
                        "Which country has won the most medals in the Summer Olympics?",
                        new String[]{"United States", "China", "Russia", "Australia"},
                        0
                )
        );
        mediumQuestions.add(
                new Question(
                        "Which team won the UEFA Champions League in 2019?",
                        new String[]{"Barcelona", "Liverpool", "Real Madrid", "Manchester City"},
                        1
                )
        );
        hardQuestions.add(
                new Question(
                        "In which year was the first World Cup held?",
                        new String[]{"1928", "1930", "1932", "1934"},
                        1
                )
        );
        hardQuestions.add(
                new Question(
                        "Which country has won the most Olympic gold medals in men's ice hockey?",
                        new String[]{"Canada", "Russia", "Sweden", "United States"},
                        0
                )
        );
        hardQuestions.add(
                new Question(
                        "Who is the only tennis player to win two calendar year Grand Slams?",
                        new String[]{"Rafael Nadal", "Novak Djokovic", "Roger Federer", "Rod Laver"},
                        3
                )
        );
        hardQuestions.add(
                new Question(
                        "Which African country won the 2019 Africa Cup of Nations?",
                        new String[]{"Algeria", "Nigeria", "Senegal", "Tunisia"},
                        0
                )
        );
        hardQuestions.add(
                new Question(
                        "Which team won the 2018 FIFA World Cup?",
                        new String[]{"Argentina", "Brazil", "France", "Germany"},
                        2
                )
        );
        hardQuestions.add(
                new Question(
                        "What is the highest possible break in snooker?",
                        new String[]{"140", "147", "150", "153"},
                        1
                )
        );
        hardQuestions.add(
                new Question(
                        "Which country has won the most Rugby World Cups?",
                        new String[]{"Australia", "England", "New Zealand", "South Africa"},
                        2
                )
        );
        hardQuestions.add(
                new Question(
                        "Who holds the record for the most home runs in a single MLB season?",
                        new String[]{"Babe Ruth", "Mark McGwire", "Barry Bonds", "Sammy Sosa"},
                        2
                )
        );
        hardQuestions.add(
                new Question(
                        "Which country won the first ever cricket World Cup in 1975?",
                        new String[]{"Australia", "England", "India", "West Indies"},
                        3
                )
        );
        hardQuestions.add(
                new Question(
                        "Who won the men's singles gold medal in tennis at the 2016 Rio Olympics?",
                        new String[]{"Andy Murray", "Novak Djokovic", "Rafael Nadal", "Roger Federer"},
                        0
                )
        );

        ShopFragment shopFragment = new ShopFragment(this, wallpapers, points);
        QuizFragment quizFragment = new QuizFragment(this, easyQuestions, mediumQuestions, hardQuestions);

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
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.click_again_to_exit), Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(sharedPreferences.getInt("fragment", R.id.quiz));
    }
}