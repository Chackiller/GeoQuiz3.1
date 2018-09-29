package com.bignerdranch.android.geoquiz3;

public class Questions {
    int Text;
    boolean Answer;

    public Questions(int text, boolean answer) {
        Text = text;
        Answer = answer;
    }

    public int getText() {
        return Text;
    }

    public void setText(int text) {
        Text = text;
    }

    public boolean isAnswer() {
        return Answer;
    }

    public void setAnswer(boolean answer) {
        Answer = answer;
    }
}
