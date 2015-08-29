package com.imooc.xpuzzle.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.xpuzzle.R;
import com.imooc.xpuzzle.adapter.GridItemsAdapter;
import com.imooc.xpuzzle.bean.ItemBean;
import com.imooc.xpuzzle.util.GameUtil;
import com.imooc.xpuzzle.util.ImagesUtil;
import com.imooc.xpuzzle.util.ScreenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 拼图逻辑主界面：面板显示
 *
 * @author xys
 */
public class PuzzleMain extends Activity implements OnClickListener {

    // 拼图完成时显示的最后一个图片
    public static Bitmap mLastBitmap;
    // 设置为N*N显示
    public static int TYPE = 2;
    // 步数显示
    public static int COUNT_INDEX = 0;
    // 计时显示
    public static int TIMER_INDEX = 0;
    /**
     * UI更新Handler
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 更新计时器
                    TIMER_INDEX++;
                    mTvTimer.setText("" + TIMER_INDEX);
                    break;
                default:
                    break;
            }
        }
    };
    // 选择的图片
    private Bitmap mPicSelected;
    // PuzzlePanel
    private GridView mGvPuzzleMainDetail;
    private int mResId;
    private String mPicPath;
    private ImageView mImageView;
    // Button
    private Button mBtnBack;
    private Button mBtnImage;
    private Button mBtnRestart;
    // 显示步数
    private TextView mTvPuzzleMainCounts;
    // 计时器
    private TextView mTvTimer;
    // 切图后的图片
    private List<Bitmap> mBitmapItemLists = new ArrayList<Bitmap>();
    // GridView适配器
    private GridItemsAdapter mAdapter;
    // Flag 是否已显示原图
    private boolean mIsShowImg;
    // 计时器类
    private Timer mTimer;
    /**
     * 计时器线程
     */
    private TimerTask mTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xpuzzle_puzzle_detail_main);
        // 获取选择的图片
        Bitmap picSelectedTemp;
        // 选择默认图片还是自定义图片
        mResId = getIntent().getExtras().getInt("picSelectedID");
        mPicPath = getIntent().getExtras().getString("mPicPath");
        if (mResId != 0) {
            picSelectedTemp = BitmapFactory.decodeResource(
                    getResources(), mResId);
        } else {
            picSelectedTemp = BitmapFactory.decodeFile(mPicPath);
        }
        TYPE = getIntent().getExtras().getInt("mType", 2);
        // 对图片处理
        handlerImage(picSelectedTemp);
        // 初始化Views
        initViews();
        // 生成游戏数据
        generateGame();
        // GridView点击事件
        mGvPuzzleMainDetail.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                // 判断是否可移动
                if (GameUtil.isMoveable(position)) {
                    // 交换点击Item与空格的位置
                    GameUtil.swapItems(
                            GameUtil.mItemBeans.get(position),
                            GameUtil.mBlankItemBean);
                    // 重新获取图片
                    recreateData();
                    // 通知GridView更改UI
                    mAdapter.notifyDataSetChanged();
                    // 更新步数
                    COUNT_INDEX++;
                    mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
                    // 判断是否成功
                    if (GameUtil.isSuccess()) {
                        // 将最后一张图显示完整
                        recreateData();
                        mBitmapItemLists.remove(TYPE * TYPE - 1);
                        mBitmapItemLists.add(mLastBitmap);
                        // 通知GridView更改UI
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(PuzzleMain.this, "拼图成功!",
                                Toast.LENGTH_LONG).show();
                        mGvPuzzleMainDetail.setEnabled(false);
                        mTimer.cancel();
                        mTimerTask.cancel();
                    }
                }
            }
        });
        // 返回按钮点击事件
        mBtnBack.setOnClickListener(this);
        // 显示原图按钮点击事件
        mBtnImage.setOnClickListener(this);
        // 重置按钮点击事件
        mBtnRestart.setOnClickListener(this);
    }

    /**
     * Button点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回按钮点击事件
            case R.id.btn_puzzle_main_back:
                PuzzleMain.this.finish();
                break;
            // 显示原图按钮点击事件
            case R.id.btn_puzzle_main_img:
                Animation animShow = AnimationUtils.loadAnimation(
                        PuzzleMain.this, R.anim.image_show_anim);
                Animation animHide = AnimationUtils.loadAnimation(
                        PuzzleMain.this, R.anim.image_hide_anim);
                if (mIsShowImg) {
                    mImageView.startAnimation(animHide);
                    mImageView.setVisibility(View.GONE);
                    mIsShowImg = false;
                } else {
                    mImageView.startAnimation(animShow);
                    mImageView.setVisibility(View.VISIBLE);
                    mIsShowImg = true;
                }
                break;
            // 重置按钮点击事件
            case R.id.btn_puzzle_main_restart:
                cleanConfig();
                generateGame();
                recreateData();
                // 通知GridView更改UI
                mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
                mAdapter.notifyDataSetChanged();
                mGvPuzzleMainDetail.setEnabled(true);
                break;
            default:
                break;
        }
    }

    /**
     * 生成游戏数据
     */
    private void generateGame() {
        // 切图 获取初始拼图数据 正常顺序
        new ImagesUtil().createInitBitmaps(
                TYPE, mPicSelected, PuzzleMain.this);
        // 生成随机数据
        GameUtil.getPuzzleGenerator();
        // 获取Bitmap集合
        for (ItemBean temp : GameUtil.mItemBeans) {
            mBitmapItemLists.add(temp.getBitmap());
        }
        // 数据适配器
        mAdapter = new GridItemsAdapter(this, mBitmapItemLists);
        mGvPuzzleMainDetail.setAdapter(mAdapter);
        // 启用计时器
        mTimer = new Timer(true);
        // 计时器线程
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        };
        // 每1000ms执行 延迟0s
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * 添加显示原图的View
     */
    private void addImgView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(
                R.id.rl_puzzle_main_main_layout);
        mImageView = new ImageView(PuzzleMain.this);
        mImageView.setImageBitmap(mPicSelected);
        int x = (int) (mPicSelected.getWidth() * 0.9F);
        int y = (int) (mPicSelected.getHeight() * 0.9F);
        LayoutParams params = new LayoutParams(x, y);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mImageView.setLayoutParams(params);
        relativeLayout.addView(mImageView);
        mImageView.setVisibility(View.GONE);
    }

    /**
     * 返回时调用
     */
    @Override
    protected void onStop() {
        super.onStop();
        // 清空相关参数设置
        cleanConfig();
        this.finish();
    }

    /**
     * 清空相关参数设置
     */
    private void cleanConfig() {
        // 清空相关参数设置
        GameUtil.mItemBeans.clear();
        // 停止计时器
        mTimer.cancel();
        mTimerTask.cancel();
        COUNT_INDEX = 0;
        TIMER_INDEX = 0;
        // 清除拍摄的照片
        if (mPicPath != null) {
            // 删除照片
            File file = new File(MainActivity.TEMP_IMAGE_PATH);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 重新获取图片
     */
    private void recreateData() {
        mBitmapItemLists.clear();
        for (ItemBean temp : GameUtil.mItemBeans) {
            mBitmapItemLists.add(temp.getBitmap());
        }
    }

    /**
     * 对图片处理 自适应大小
     *
     * @param bitmap bitmap
     */
    private void handlerImage(Bitmap bitmap) {
        // 将图片放大到固定尺寸
        int screenWidth = ScreenUtil.getScreenSize(this).widthPixels;
        int screenHeigt = ScreenUtil.getScreenSize(this).heightPixels;
        mPicSelected = new ImagesUtil().resizeBitmap(
                screenWidth * 0.8f, screenHeigt * 0.6f, bitmap);
    }

    /**
     * 初始化Views
     */
    private void initViews() {
        // Button
        mBtnBack = (Button) findViewById(R.id.btn_puzzle_main_back);
        mBtnImage = (Button) findViewById(R.id.btn_puzzle_main_img);
        mBtnRestart = (Button) findViewById(R.id.btn_puzzle_main_restart);
        // Flag 是否已显示原图
        mIsShowImg = false;
        // GridView
        mGvPuzzleMainDetail = (GridView) findViewById(
                R.id.gv_puzzle_main_detail);
        // 设置为N*N显示
        mGvPuzzleMainDetail.setNumColumns(TYPE);
        LayoutParams gridParams = new LayoutParams(
                mPicSelected.getWidth(),
                mPicSelected.getHeight());
        // 水平居中
        gridParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        // 其他格式属性
        gridParams.addRule(
                RelativeLayout.BELOW,
                R.id.ll_puzzle_main_spinner);
        // Grid显示
        mGvPuzzleMainDetail.setLayoutParams(gridParams);
        mGvPuzzleMainDetail.setHorizontalSpacing(0);
        mGvPuzzleMainDetail.setVerticalSpacing(0);
        // TV步数
        mTvPuzzleMainCounts = (TextView) findViewById(
                R.id.tv_puzzle_main_counts);
        mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
        // TV计时器
        mTvTimer = (TextView) findViewById(R.id.tv_puzzle_main_time);
        mTvTimer.setText("0秒");
        // 添加显示原图的View
        addImgView();
    }
}
