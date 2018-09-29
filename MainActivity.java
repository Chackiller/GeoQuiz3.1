package com.bignerdranch.android.geoquiz3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int mCurrentIndex;
    int mResult;
    int mResultFalse;
    int mCheatedIndex;
    boolean mCanPressButton = false;
    boolean mWasCheated;
    TextView mTextView;
    TextView mTextView2;
    Button mNextButton;
    Button mTrueButton;
    Button mFalseButton;
    Button mCheatButton;
    private static final String KEY_POVOROT = "POVOROT";
    private static final String KEY_POVOROT_CHEAT = "POVOROT_CHEAT";
    private static final String KEY_RESULT = "RESULT";
    private static final String KEY_RESULT_FALSE = "RESULT_FALSE";
    private static final String KEY_CHEAT = "CHEAT";
    private static final String KEY_CHEATED_INDEX = "CHEATED_INDEX";
    private static final String KEY_CAN_PRESS_BUTTON = "CAN_PRESS_BUTTON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_POVOROT);
            mCanPressButton = savedInstanceState.getBoolean(KEY_CAN_PRESS_BUTTON);
            mResult = savedInstanceState.getInt(KEY_RESULT);
            mResultFalse = savedInstanceState.getInt(KEY_RESULT_FALSE);
            mCheatedIndex = savedInstanceState.getInt(KEY_CHEATED_INDEX);
            mWasCheated = savedInstanceState.getBoolean(KEY_POVOROT_CHEAT,false);
        }
        mTextView = findViewById(R.id.question_text_view);
        mNextButton = findViewById(R.id.next_button);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mCheatButton = findViewById(R.id.cheat_button);
        mTextView2 = findViewById(R.id.textView2);
        setCanPressButton(mCanPressButton);
        updateQuestion();
    }

    Questions questionBank[] = new Questions[]{
            new Questions(R.string.first_question, true),
            new Questions(R.string.second_Question, true),
            new Questions(R.string.third_question, false),
            new Questions(R.string.n4th_question, true),
            new Questions(R.string.n5th_question, true),
            new Questions(R.string.n6th_question, false),
            new Questions(R.string.n7th_question, false),
            new Questions(R.string.n8th_question, false),
            new Questions(R.string.n9th_question, true),
            new Questions(R.string.n10th_question, true),
    };

    private void updateQuestion () {
        int question = questionBank[mCurrentIndex].getText();
        mTextView.setText(question);
    }
    private void checkAnswer (boolean isTrue) {
       boolean answer = questionBank[mCurrentIndex].isAnswer();
       int text;
       if(mWasCheated) {
           text = R.string.cheat_pressed;
           mCheatedIndex++;
           mWasCheated = false;
       } else {
           if (answer == isTrue) {
               text = R.string.correct_answer;
               mResult++;
           } else {
               text = R.string.incorrect_answer;
               mResultFalse++;
           }
       }

       if(questionBank.length == (mResult+mResultFalse+mCheatedIndex)){
            mTextView2.setText("Ваш результат: " + mResult + " правильних відповідей з " + questionBank.length + ". Ви змахлювали " + mCheatedIndex + " раз");
            mResult = 0;
            mResultFalse = 0;
       }
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();
    }

    private void setCanPressButton (boolean canPressButton){
        if (!canPressButton){
            mNextButton.setEnabled(false);
            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);
            mCheatButton.setEnabled(true);
        }
        else {
            mNextButton.setEnabled(true);
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
            mCheatButton.setEnabled(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POVOROT, mCurrentIndex);
        outState.putInt(KEY_RESULT,mResult);
        outState.putInt(KEY_RESULT_FALSE,mResultFalse);
        outState.putBoolean(KEY_CAN_PRESS_BUTTON, mCanPressButton);
        outState.putBoolean(KEY_POVOROT_CHEAT,mWasCheated);
        outState.putInt(KEY_CHEATED_INDEX,mCheatedIndex);
    }

    public void onFalseButtonClick(View view) {
        checkAnswer(false);
        mCanPressButton = true;
        setCanPressButton(true);
    }

    public void onTrueButtonClick(View view) {
        checkAnswer(true);
        mCanPressButton = true;
        setCanPressButton(true);
    }

    public void onNextButtonClick(View view) {
        mCurrentIndex = (mCurrentIndex + 1) % questionBank.length;
        mCanPressButton = false;
        setCanPressButton(false);
        updateQuestion();
    }

    public void onCheatButtonClick(View view) {
        boolean answerIsTrue = questionBank[mCurrentIndex].isAnswer();
        Intent intent = new Intent(MainActivity.this, CheatActivity.class);
        intent.putExtra(KEY_CHEAT,answerIsTrue);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {
            return; }
        if(requestCode == 1) {
            if (data == null)
                return;
        }
        mWasCheated = CheatActivity.wasAnswerShown(data);
    }
}
