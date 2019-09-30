package com.jy.framework.pictureselector.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jy.framework.pictureselector.R;
import com.jy.framework.pictureselector.adapter.PhotoPagerAdapter;
import com.jy.framework.pictureselector.widget.ViewPagerFixed;

import java.util.ArrayList;

/**
 * Created by foamtrace on 2015/8/25.
 */
public class PhotoPreviewActivity extends AppCompatActivity implements PhotoPagerAdapter.PhotoViewClickListener {

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";

    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "preview_result";

    /** 预览请求状态码 */
    public static final int REQUEST_PREVIEW = 99;

    private ArrayList<String> paths;
    private ViewPagerFixed mViewPager;
    private PhotoPagerAdapter mPagerAdapter;
    private int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_preview);

        initViews();

        paths = new ArrayList<>();
        ArrayList<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        if(pathArr != null){
            paths.addAll(pathArr);
        }

        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);

        mPagerAdapter = new PhotoPagerAdapter(this, paths);
        mPagerAdapter.setPhotoViewClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews(){
        mViewPager = (ViewPagerFixed) findViewById(R.id.vp_photos);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolBar);
        TextView title=(TextView)findViewById(R.id.center);
        title.setText(getString(R.string.preview));
        setSupportActionBar(mToolbar);

        Button left=(Button)findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button right=(Button)findViewById(R.id.right);
        right.setTextColor(ContextCompat.getColor(this,R.color.morange));
        right.getPaint().setFakeBoldText(true);
        right.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
                .getDimensionPixelSize(R.dimen.action_text_size));
        right.setText(getString(R.string.delete));
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int index = mViewPager.getCurrentItem();
                final String deletedPath =  paths.get(index);
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.deleted_a_photo,
                        Snackbar.LENGTH_LONG);
                if(paths.size() <= 1){
                    // 最后一张照片弹出删除提示
                    // show confirm dialog
                    new AlertDialog.Builder(PhotoPreviewActivity.this)
                            .setTitle(R.string.confirm_to_delete)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    paths.remove(index);
                                    onBackPressed();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }else{
                    snackbar.show();
                    paths.remove(index);
                    mPagerAdapter.notifyDataSetChanged();
                }

                snackbar.setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (paths.size() > 0) {
                            paths.add(index, deletedPath);
                        } else {
                            paths.add(deletedPath);
                        }
                        mPagerAdapter.notifyDataSetChanged();
                        mViewPager.setCurrentItem(index, true);
                    }
                });
            }
        });
    }

    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        onBackPressed();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, paths);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }


}
