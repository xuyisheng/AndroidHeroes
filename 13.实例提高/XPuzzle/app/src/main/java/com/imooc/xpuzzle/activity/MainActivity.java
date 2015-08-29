package com.imooc.xpuzzle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imooc.xpuzzle.R;
import com.imooc.xpuzzle.adapter.GridPicListAdapter;
import com.imooc.xpuzzle.util.ScreenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 程序主界面：显示默认图片列表、自选图片按钮
 *
 * @author xys
 */
public class MainActivity extends Activity implements OnClickListener {

    // 返回码：系统图库
    private static final int RESULT_IMAGE = 100;
    // 返回码：相机
    private static final int RESULT_CAMERA = 200;
    // IMAGE TYPE
    private static final String IMAGE_TYPE = "image/*";
    // Temp照片路径
    public static String TEMP_IMAGE_PATH;
    // GridView 显示图片
    private GridView mGvPicList;
    private List<Bitmap> mPicList;
    // 主页图片资源ID
    private int[] mResPicId;
    // 显示Type
    private TextView mTvPuzzleMainTypeSelected;
    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    private TextView mTvType2;
    private TextView mTvType3;
    private TextView mTvType4;
    // 游戏类型N*N
    private int mType = 2;
    // 本地图册、相机选择
    private String[] mCustomItems = new String[]{"本地图册", "相机拍照"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xpuzzle_main);

        TEMP_IMAGE_PATH =
                Environment.getExternalStorageDirectory().getPath() +
                        "/temp.png";
        mPicList = new ArrayList<Bitmap>();
        // 初始化Views
        initViews();
        // 数据适配器
        mGvPicList.setAdapter(new GridPicListAdapter(
                MainActivity.this, mPicList));
        // Item点击监听
        mGvPicList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                if (position == mResPicId.length - 1) {
                    // 选择本地图库 相机
                    showDialogCustom();
                } else {
                    // 选择默认图片
                    Intent intent = new Intent(
                            MainActivity.this,
                            PuzzleMain.class);
                    intent.putExtra("picSelectedID", mResPicId[position]);
                    intent.putExtra("mType", mType);
                    startActivity(intent);
                }
            }
        });

        /**
         * 显示难度Type
         */
        mTvPuzzleMainTypeSelected.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 弹出popup window
                        popupShow(v);
                    }
                });
    }

    // 显示选择系统图库 相机对话框
    private void showDialogCustom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setTitle("选择：");
        builder.setItems(mCustomItems,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (0 == which) {
                            // 本地图册
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK, null);
                            intent.setDataAndType(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    IMAGE_TYPE);
                            startActivityForResult(intent, RESULT_IMAGE);
                        } else if (1 == which) {
                            // 系统相机
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            Uri photoUri = Uri.fromFile(
                                    new File(TEMP_IMAGE_PATH));
                            intent.putExtra(
                                    MediaStore.EXTRA_OUTPUT,
                                    photoUri);
                            startActivityForResult(intent, RESULT_CAMERA);
                        }
                    }
                });
        builder.create().show();
    }

    /**
     * 调用图库相机回调方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_IMAGE && data != null) {
                // 相册
                Cursor cursor = this.getContentResolver().query(
                        data.getData(), null, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(
                        cursor.getColumnIndex("_data"));
                Intent intent = new Intent(
                        MainActivity.this,
                        PuzzleMain.class);
                intent.putExtra("picPath", imagePath);
                intent.putExtra("mType", mType);
                cursor.close();
                startActivity(intent);
            } else if (requestCode == RESULT_CAMERA) {
                // 相机
                Intent intent = new Intent(
                        MainActivity.this,
                        PuzzleMain.class);
                intent.putExtra("mPicPath", TEMP_IMAGE_PATH);
                intent.putExtra("mType", mType);
                startActivity(intent);
            }
        }
    }

    /**
     * 显示popup window
     *
     * @param view popup window
     */
    private void popupShow(View view) {
        int density = (int) ScreenUtil.getDeviceDensity(this);
        // 显示popup window
        mPopupWindow = new PopupWindow(mPopupView,
                200 * density, 50 * density);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 透明背景
        Drawable transpent = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(transpent);
        // 获取位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(
                view,
                Gravity.NO_GRAVITY,
                location[0] - 40 * density,
                location[1] + 30 * density);
    }

    /**
     * 初始化Views
     */
    private void initViews() {
        mGvPicList = (GridView) findViewById(
                R.id.gv_xpuzzle_main_pic_list);
        // 初始化Bitmap数据
        mResPicId = new int[]{
                R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
                R.drawable.pic7, R.drawable.pic8, R.drawable.pic9,
                R.drawable.pic10, R.drawable.pic11, R.drawable.pic12,
                R.drawable.pic13, R.drawable.pic14,
                R.drawable.pic15, R.mipmap.ic_launcher};
        Bitmap[] bitmaps = new Bitmap[mResPicId.length];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(
                    getResources(), mResPicId[i]);
            mPicList.add(bitmaps[i]);
        }
        // 显示type
        mTvPuzzleMainTypeSelected = (TextView) findViewById(
                R.id.tv_puzzle_main_type_selected);
        mLayoutInflater = (LayoutInflater) getSystemService(
                LAYOUT_INFLATER_SERVICE);
        // mType view
        mPopupView = mLayoutInflater.inflate(
                R.layout.xpuzzle_main_type_selected, null);
        mTvType2 = (TextView) mPopupView.findViewById(R.id.tv_main_type_2);
        mTvType3 = (TextView) mPopupView.findViewById(R.id.tv_main_type_3);
        mTvType4 = (TextView) mPopupView.findViewById(R.id.tv_main_type_4);
        // 监听事件
        mTvType2.setOnClickListener(this);
        mTvType3.setOnClickListener(this);
        mTvType4.setOnClickListener(this);
    }

    /**
     * popup window item点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Type
            case R.id.tv_main_type_2:
                mType = 2;
                mTvPuzzleMainTypeSelected.setText("2 X 2");
                break;
            case R.id.tv_main_type_3:
                mType = 3;
                mTvPuzzleMainTypeSelected.setText("3 X 3");
                break;
            case R.id.tv_main_type_4:
                mType = 4;
                mTvPuzzleMainTypeSelected.setText("4 X 4");
                break;
            default:
                break;
        }
        mPopupWindow.dismiss();
    }
}
