package ru.xbitly.sportquiz.recycler;

import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import ru.xbitly.sportquiz.R;

public class ButtonRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final Button button;

    public ButtonRecyclerViewHolder(View itemView) {
        super(itemView);
        button = itemView.findViewById(R.id.button);
    }

    public void bindData(String buttonText) {
        button.setText(buttonText);
    }

    public Button getButton() {
        return button;
    }
}

