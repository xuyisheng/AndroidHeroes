package com.imooc.game2048.config;

import android.app.Application;
import android.content.SharedPreferences;

public class Config extends Application {

    /**
     * SP对象
     */
    public static SharedPreferences mSp;

    /**
     * Game Goal
     */
    public static int mGameGoal;

    /**
     * GameView行列数
     */
    public static int mGameLines;

    /**
     * Item宽高
     */
    public static int mItemSize;

    /**
     * 记录分数
     */
    public static int SCROE = 0;

    public static String SP_HIGH_SCROE = "SP_HIGHSCROE";

    public static String KEY_HIGH_SCROE = "KEY_HighScore";

    public static String KEY_GAME_LINES = "KEY_GAMELINES";

    public static String KEY_GAME_GOAL = "KEY_GameGoal";

    @Override
    public void onCreate() {
        super.onCreate();
        mSp = getSharedPreferences(SP_HIGH_SCROE, 0);
        mGameLines = mSp.getInt(KEY_GAME_LINES, 4);
        mGameGoal = mSp.getInt(KEY_GAME_GOAL, 2048);
        mItemSize = 0;
    }
}
