package ru.xbitly.sportquiz.recycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.xbitly.sportquiz.R;

public class ButtonRecyclerAdapter extends RecyclerView.Adapter<ButtonRecyclerViewHolder> {
    private final Context context;
    private final String[] answers;
    private final int correctAnswer;
    private final int fine;
    private final int bonus;
    private boolean flag = false;
    private OnButtonClickListener onButtonClickListener;

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public ButtonRecyclerAdapter(Context context, String[] answers, int correctAnswer, int fine, int bonus) {
        this.context = context;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.fine = fine;
        this.bonus = bonus;
        sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public ButtonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
        return new ButtonRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonRecyclerViewHolder holder, int position) {
        String buttonText = answers[position];
        holder.bindData(buttonText);

        holder.getButton().setOnClickListener(view -> {
            if (position == correctAnswer && !flag) {
                addPoints();
                onButtonClickListener.onButtonClick(+1);
                holder.getButton().setBackgroundResource(R.drawable.bg_corner_light_green_small);
                flag = true;
            } else if(!flag){
                deductPoints();
                onButtonClickListener.onButtonClick(-1);
                holder.getButton().setBackgroundResource(R.drawable.bg_corner_light_red_small);
                flag = true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.length;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    private void addPoints() {
        editor.putInt("points", sharedPreferences.getInt("points", 0) + bonus);
        editor.apply();
    }

    private void deductPoints() {
        editor.putInt("points", sharedPreferences.getInt("points", 0) - fine);
        editor.apply();
    }
}

