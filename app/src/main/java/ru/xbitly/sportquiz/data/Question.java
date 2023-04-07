package ru.xbitly.sportquiz.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private final String question;
    private final String[] answers;
    private final int correctAnswer;

    public Question(String question, String[] answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    protected Question(Parcel in) {
        question = in.readString();
        answers = in.createStringArray();
        correctAnswer = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringArray(answers);
        dest.writeInt(correctAnswer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
