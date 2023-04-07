package ru.xbitly.sportquiz.pager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager.widget.PagerAdapter;

import java.io.IOException;
import java.util.ArrayList;

import ru.xbitly.sportquiz.R;
import ru.xbitly.sportquiz.data.Wallpaper;

public class WallpaperPagerAdapter extends PagerAdapter {

    private final Context context;
    private final TextView points;
    private final ArrayList<Wallpaper> wallpapers;
    private final LayoutInflater inflater;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public WallpaperPagerAdapter(Context context, ArrayList<Wallpaper> wallpapers, TextView points) {
        this.context = context;
        this.wallpapers = wallpapers;
        this.points = points;
        inflater = LayoutInflater.from(context);

        sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_wallpaper, container, false);

        ImageView imageView = view.findViewById(R.id.image_view);
        Button button = view.findViewById(R.id.button_buy);

        Wallpaper wallpaper = wallpapers.get(position);

        imageView.setImageResource(wallpaper.getDrawableId());
        if (wallpaper.isPurchased()){
            button.setText(context.getString(R.string.install));
        } else {
            String buttonText = wallpaper.getPrice() + " " + context.getString(R.string.points);
            button.setText(buttonText);
        }

        button.setOnClickListener(v -> {
            int price = wallpaper.getPrice();
            int currentPoints = sharedPreferences.getInt("points", 0);
            if (wallpaper.isPurchased()){
                createInstallAlertDialog(wallpaper);
            } else {
                if (currentPoints >= price) {
                    createBuyAlertDialog(wallpaper, button, price, currentPoints);
                    wallpapers.set(position, wallpaper);
                } else {
                    Toast.makeText(context, R.string.not_enough_points, Toast.LENGTH_SHORT).show();
                }
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return wallpapers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createInstallAlertDialog(Wallpaper wallpaper){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(dialog.getContext());
        View installView = inflater.inflate(R.layout.view_install_dialog, null);
        dialog.setView(installView);

        Button buttonInstall = installView.findViewById(R.id.button_install);
        Button buttonCancel = installView.findViewById(R.id.button_cancel);

        AlertDialog alert = dialog.create();

        buttonCancel.setOnClickListener(view -> alert.dismiss());
        buttonInstall.setOnClickListener(view -> {
            try {
                setWallpaper(context, drawableToBitmap(context.getDrawable(wallpaper.getDrawableId())));
            } catch (IOException e) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
            alert.dismiss();
            Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show();
        });

        Drawable back = context.getDrawable(R.drawable.bg_corner_white);
        InsetDrawable inset = new InsetDrawable(back, context.getResources().getDimensionPixelSize(R.dimen.item_vertical_margin));
        alert.getWindow().setBackgroundDrawable(inset);
        alert.show();
    }

    private void createBuyAlertDialog(Wallpaper wallpaper, Button button, int price, int currentPoints){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(dialog.getContext());
        View bayView = inflater.inflate(R.layout.view_buy_dialog, null);
        dialog.setView(bayView);

        Button buttonBuyAndInstall = bayView.findViewById(R.id.button_buy_and_install);
        Button buttonBuy = bayView.findViewById(R.id.button_buy);
        Button buttonCancel = bayView.findViewById(R.id.button_cancel);

        AlertDialog alert = dialog.create();

        buttonCancel.setOnClickListener(view -> alert.dismiss());
        buttonBuy.setOnClickListener(view -> {
            buyWallpaper(wallpaper, button, price, currentPoints);
            alert.dismiss();
        });
        buttonBuyAndInstall.setOnClickListener(view -> {
            buyWallpaper(wallpaper, button, price, currentPoints);
            try {
                setWallpaper(context, drawableToBitmap(AppCompatResources.getDrawable(context, wallpaper.getDrawableId())));
            } catch (IOException e) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            }
            alert.dismiss();
        });

        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable back = context.getDrawable(R.drawable.bg_corner_white);
        InsetDrawable inset = new InsetDrawable(back, context.getResources().getDimensionPixelSize(R.dimen.item_vertical_margin));
        alert.getWindow().setBackgroundDrawable(inset);
        alert.show();
    }

    public static void setWallpaper(Context context, Bitmap bitmap) throws IOException {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        wallpaperManager.setBitmap(bitmap);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void buyWallpaper(Wallpaper wallpaper, Button button, int price, int currentPoints) {
        editor.putInt("points", currentPoints - price);
        editor.putString("purchased", sharedPreferences.getString("purchased", "") + "|" + wallpaper.getDrawableId());
        editor.apply();
        wallpaper.setPurchased(true);
        button.setText(context.getString(R.string.install));
        updatePoints();
        Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show();
    }
    private void updatePoints() {
        String pointsText = context.getString(R.string.points) + ": " + sharedPreferences.getInt("points", 0);
        points.setText(pointsText);
    }
}