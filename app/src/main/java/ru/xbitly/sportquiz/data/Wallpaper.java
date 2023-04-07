package ru.xbitly.sportquiz.data;

public class Wallpaper {
    private final int price;
    private boolean isPurchased;
    private final int drawableId;

    public Wallpaper(int price, boolean isPurchased, int drawableId) {
        this.price = price;
        this.isPurchased = isPurchased;
        this.drawableId = drawableId;
    }

    public int getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setPurchased(boolean isPurchased){
        this.isPurchased = isPurchased;
    }
}
