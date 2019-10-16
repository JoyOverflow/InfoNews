package ouyj.hyena.com.infonews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ouyj.hyena.com.infonews.R;

/**
 * 图片轮播类
 */
public class SwitchImage extends LinearLayout {

    private Context mContext;
    private boolean lastItemToNextOrStop = false;
    private ViewPager mPager;
    private LinearLayout mDotsLayout;
    private TextView mTextView;
    private ViewPagerAdapter ViewPagerAdapter;
    private MyHandler handler;
    private List<View> viewList = new ArrayList<>();

    private String texts[];
    private List<ImageView> imageViewList = new ArrayList<>();
    private static final int LOOP_TIMES = 2000;
    private DisplayImageView displayImageView;


    private boolean moving = false;
    private final int MOVING = 1;
    private int timeGap = 1000;

    public SwitchImage(Context context) {
        super(context,null);
    }
    public SwitchImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SwitchImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.page, defStyleAttr, 0);
        lastItemToNextOrStop = tArray.getBoolean(R.styleable.page_is_last_item_to_next_or_stop, true);
        tArray.recycle();
        LayoutInflater.from(context).inflate(R.layout.switch_guide, this, true);


        mPager = findViewById(R.id.guide_viewpager);
        mDotsLayout = findViewById(R.id.guide_dots);
        mTextView = findViewById(R.id.tv_banner_title_text);
        ViewPagerAdapter = new ViewPagerAdapter(viewList);
        mPager.setAdapter(ViewPagerAdapter);
        mPager.addOnPageChangeListener(new PageListener());
        handler = new MyHandler(this);
    }



    public void initPager(int[] images, String[] urls, String[] texts) {
        if (images.length != urls.length || images.length != texts.length)
            throw new RuntimeException("确保长度相等");

        this.texts = texts.clone();

        viewList.clear();
        imageViewList.clear();

        //设置标签
        for (int i = 0; i < images.length; i++) {
            View holdView = initView(images[i], urls[i]);
            holdView.setTag(i);
            viewList.add(holdView);
        }
        initDots(viewList.size());


        //当lastItemToNextOrStop为true,才设置初始很大的位置,否则会初始化显示最后一张
        if (lastItemToNextOrStop)
            mPager.setCurrentItem(viewList.size() * LOOP_TIMES / 2);

        if (images.length == 2) {
            //add one more time, size == 2 crash
            for (int i = 0; i < images.length; i++) {
                View holdView = initView(images[i], urls[i]);
                holdView.setTag(i);
                viewList.add(holdView);
            }
        }

        mTextView.setText(this.texts[0]);

        ViewPagerAdapter.notifyDataSetChanged();
    }
    private View initView(int res, String url) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_guide, null);

        //设置图像资源
        ImageView imageView = view.findViewById(R.id.iguide_img);
        imageView.setTag(url);
        imageView.setImageResource(res);

        imageViewList.add(imageView);
        return view;
    }
    private void initDots(int count) {
        mDotsLayout.removeAllViews();
        for (int j = 0; j < count; j++) {
            mDotsLayout.addView(initDot());
        }
        mDotsLayout.getChildAt(0).setSelected(true);
    }
    private View initDot() {
        return LayoutInflater.from(mContext).inflate(R.layout.layout_dot, null);
    }



    public void setAndLoadImage(DisplayImageView displayImageView) {
        this.displayImageView = displayImageView;
        if (viewList.size() == 0)
            throw new RuntimeException("需先调用initPager()");

        for (ImageView imageView : imageViewList) {
            displayImageView.displayImageFromURL(imageView, (String) imageView.getTag());
        }
    }

    /**
     * 设置是否移动和时间间隔
     * @param flag
     * @param time
     */
    public void setMove(boolean flag, int time) {
        if (flag) {
            timeGap = time;
            moving = flag;
            //防止一张图的时候切换
            if (viewList != null && viewList.size() > 1)
                handler.sendEmptyMessageDelayed(MOVING, timeGap);
        } else {
            moving = flag;
            handler.removeCallbacksAndMessages(null);
        }
    }








    public interface DisplayImageView {
        void displayImageFromURL(ImageView view, String url);
    }
    private static class MyHandler extends Handler {
        WeakReference<SwitchImage> mReference;
        MyHandler(SwitchImage sImage) {
            mReference = new WeakReference<>(sImage);
        }

        public void handleMessage(Message msg) {
            SwitchImage switchImage = mReference.get();
            if (switchImage == null)
                return;
            if (switchImage.moving && switchImage.viewList.size() > 0) {
                if (msg.what == switchImage.MOVING) {
                    switchImage.mPager.setCurrentItem(
                            switchImage.mPager.getCurrentItem() + 1,
                            true
                    );
                    sendEmptyMessageDelayed(switchImage.MOVING, switchImage.timeGap);
                }
            }
        }
    }
    private class PageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    private class ViewPagerAdapter extends PagerAdapter {

        private List<View> data;
        public ViewPagerAdapter(List<View> data) {
            super();
            this.data = data;
        }

        @Override
        public int getCount() {
            return 0;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
