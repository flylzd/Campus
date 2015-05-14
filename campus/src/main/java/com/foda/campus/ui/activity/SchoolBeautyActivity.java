package com.foda.campus.ui.activity;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.foda.campus.R;

public class SchoolBeautyActivity extends BaseActivity {

//    // 声明并静态初始化图片id数组
//    private int[] imageIds = {R.drawable.bg_campus_1, R.drawable.bg_campus_2,
//            R.drawable.bg_campus_3, R.drawable.bg_campus_4, R.drawable.bg_campus_5,
//            R.drawable.bg_campus_6, R.drawable.bg_campus_7, R.drawable.bg_campus_8,
//            R.drawable.bg_campus_9, R.drawable.bg_campus_10, R.drawable.bg_campus_11,
//            R.drawable.bg_campus_12, R.drawable.bg_campus_13};

    // 声明并静态初始化图片id数组
    private int[] imageIds = {R.drawable.bg_campus_1, R.drawable.bg_campus_2,
            R.drawable.bg_campus_3, R.drawable.bg_campus_4, R.drawable.bg_campus_5,
            R.drawable.bg_campus_6, R.drawable.bg_campus_7};

    // 声明一个Gallery视图控件变量
    private Gallery gallery;

    // 声明一个ImageSwitcher视图控件变量
    private ImageSwitcher imageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_beauty);

        initView();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_school_about;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {

        // 获取视图控件对象
        gallery = (Gallery) findViewById(R.id.gallery);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

        // 设置动画效果
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));

        // 为imageSwitcher设置ViewFactory对象
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                // 初始化一个ImageView对象
                ImageView imageView = new ImageView(SchoolBeautyActivity.this);
                // 设置保持纵横比居中缩放图像
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                // 设置imageView的宽高
                imageView.setLayoutParams(new FrameLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                return imageView;
            }
        });

        //初始化一个MainGalleryAdapter对象
        MainGalleryAdapter adapter = new MainGalleryAdapter();

        //将适配器与gallery关联起来
        gallery.setAdapter(adapter);

        //初始选中中间的图片
        gallery.setSelection(imageIds.length / 2);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //在ImageSwitcher中显示选中的图片
                imageSwitcher.setImageResource(imageIds[position]);
            }
        });
    }


    /**
     * 定义Gallery的数据适配器MainGalleryAdapter
     */
    class MainGalleryAdapter extends BaseAdapter {

        /**
         * 获得数量
         */
        @Override
        public int getCount() {
            return imageIds.length;
        }

        /**
         * 获得当前选项
         */
        @Override
        public Object getItem(int position) {
            return getResources().getDrawable(imageIds[position]);
        }

        /**
         * 获得当前选项的id
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 获得当前选项的视图
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //初始化一个ImageView对象
            ImageView imageView = new ImageView(SchoolBeautyActivity.this);
            //设置缩放方式
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置ImageView的宽高
            imageView.setLayoutParams(new Gallery.LayoutParams(180, 135));
            //设置IamgeView显示的图片
            imageView.setImageResource(imageIds[position]);

            /**
             * 设置ImageView背景，这里背景使用的是android提供的一种背景风格
             * 在values/attr.xml文件中需要一下内容
             *  <declare-styleable name="Gallery">
             *      <attr name="android:galleryItemBackground" />
             *  </declare-styleable>
             */
            TypedArray typedArray = SchoolBeautyActivity.this
                    .obtainStyledAttributes(R.styleable.Gallery);
            int mGalleryItemBackground = typedArray.getResourceId(
                    R.styleable.Gallery_android_galleryItemBackground, 0);
            imageView.setBackgroundResource(mGalleryItemBackground);

            //返回ImageView对象
            return imageView;
        }
    }

}

