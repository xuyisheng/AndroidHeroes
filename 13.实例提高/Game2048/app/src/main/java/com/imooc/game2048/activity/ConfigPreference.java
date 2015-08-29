package com.imooc.game2048.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.imooc.game2048.R;
import com.imooc.game2048.config.Config;

public class ConfigPreference extends Activity implements OnClickListener {

    private Button mBtnGameLines;

    private Button mBtnGoal;

    private Button mBtnBack;

    private Button mBtnDone;

    private String[] mGameLinesList;

    private String[] mGameGoalList;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_preference);
        initView();
    }

    private void initView() {
        mBtnGameLines = (Button) findViewById(R.id.btn_gamelines);
        mBtnGoal = (Button) findViewById(R.id.btn_goal);
        mBtnBack = (Button) findViewById(R.id.btn_back);
        mBtnDone = (Button) findViewById(R.id.btn_done);
        mBtnGameLines.setText("" + Config.mSp.getInt(Config.KEY_GAME_LINES, 4));
        mBtnGoal.setText("" + Config.mSp.getInt(Config.KEY_GAME_GOAL, 2048));
        mBtnGameLines.setOnClickListener(this);
        mBtnGoal.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mBtnDone.setOnClickListener(this);
        mGameLinesList = new String[]{"4", "5", "6"};
        mGameGoalList = new String[]{"1024", "2048", "4096"};
    }

    private void saveConfig() {
        Editor editor = Config.mSp.edit();
        editor.putInt(Config.KEY_GAME_LINES,
                Integer.parseInt(mBtnGameLines.getText().toString()));
        editor.putInt(Config.KEY_GAME_GOAL,
                Integer.parseInt(mBtnGoal.getText().toString()));
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gamelines:
                mBuilder = new AlertDialog.Builder(this);
                mBuilder.setTitle("choose the lines of the game");
                mBuilder.setItems(mGameLinesList,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mBtnGameLines.setText(mGameLinesList[which]);
                            }
                        });
                mBuilder.create().show();
                break;
            case R.id.btn_goal:
                mBuilder = new AlertDialog.Builder(this);
                mBuilder.setTitle("choose the goal of the game");
                mBuilder.setItems(mGameGoalList,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mBtnGoal.setText(mGameGoalList[which]);
                            }
                        });
                mBuilder.create().show();
                break;
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_done:
                saveConfig();
                setResult(RESULT_OK);
                this.finish();
                break;
            default:
                break;
        }
    }
}
