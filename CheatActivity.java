package com.bignerdranch.android.geoquiz3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    boolean mAnswerIsTrue;
    boolean isCheated;
    TextView mTextAnswerView;
    private static final String KEY_CHEAT = "CHEAT";
    private static final String KEY_POVOROT_CHEAT = "POVOROT_CHEAT";
    private static final String KEY_SHOWN_ANSWER = "SHOWN_ANSWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            isCheated = savedInstanceState.getBoolean(KEY_POVOROT_CHEAT, false);
        }
        setAnswerShownResult(isCheated);
        mTextAnswerView = findViewById(R.id.text_answer_view);
        mAnswerIsTrue = getIntent().getBooleanExtra(KEY_CHEAT,false);
    }


    public void onShowAnswerButtonClick(View view) {
        if (mAnswerIsTrue) {
            mTextAnswerView.setText(R.string.true_button); }
        else {
            mTextAnswerView.setText(R.string.false_button); }
        isCheated = true;
        setAnswerShownResult(true);
    }

    private void setAnswerShownResult (boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(KEY_SHOWN_ANSWER,isAnswerShown);
        setResult(RESULT_OK,data);
    }

    public static boolean wasAnswerShown (Intent result){
        return result.getBooleanExtra(KEY_SHOWN_ANSWER,false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_POVOROT_CHEAT,isCheated);
    }
}
