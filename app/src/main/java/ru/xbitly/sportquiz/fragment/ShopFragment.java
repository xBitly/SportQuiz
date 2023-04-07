package ru.xbitly.sportquiz.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ru.xbitly.sportquiz.R;
import ru.xbitly.sportquiz.data.Wallpaper;
import ru.xbitly.sportquiz.pager.WallpaperPagerAdapter;

public class ShopFragment extends Fragment {

    private final Context context;
    private final TextView points;
    private final ArrayList<Wallpaper> wallpapers;

    private ViewPager pager;
    private ImageButton buttonLeft;
    private ImageButton buttonRight;

    private WallpaperPagerAdapter adapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ShopFragment(Context context, ArrayList<Wallpaper> wallpapers, TextView points) {
        this.context = context;
        this.wallpapers = wallpapers;
        this.points = points;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        super.onViewCreated(view, savedInstanceState);

        pager = view.findViewById(R.id.pager);
        buttonLeft = view.findViewById(R.id.button_left);
        buttonRight = view.findViewById(R.id.button_right);

        adapter = new WallpaperPagerAdapter(getContext(), wallpapers, points);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                editor.putInt("wallpaper", position);
                editor.apply();
                if (position == 0) {
                    buttonLeft.setVisibility(View.INVISIBLE);
                } else {
                    buttonLeft.setVisibility(View.VISIBLE);
                }
                if (position == adapter.getCount() - 1) {
                    buttonRight.setVisibility(View.INVISIBLE);
                } else {
                    buttonRight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        buttonLeft.setOnClickListener(v -> {
            int currentItem = pager.getCurrentItem();
            if (currentItem > 0) {
                pager.setCurrentItem(currentItem - 1);
            }
        });

        buttonRight.setOnClickListener(v -> {
            int currentItem = pager.getCurrentItem();
            if (currentItem < adapter.getCount() - 1) {
                pager.setCurrentItem(currentItem + 1);
            }
        });

        pager.setCurrentItem(sharedPreferences.getInt("wallpaper", 0));
        if (sharedPreferences.getInt("wallpaper", 0) == 0) buttonLeft.setVisibility(View.INVISIBLE);
    }
}