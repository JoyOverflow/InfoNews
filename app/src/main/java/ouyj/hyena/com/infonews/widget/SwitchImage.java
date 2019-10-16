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
import android.view.ViewGroup;
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
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView txtTitle;
    private ViewPagerAdapter adapter;
    private MyHandler handler;
    private List<View> viewList = new ArrayList<>();

    private String titles[];
    private List<ImageView> imgViewList = new ArrayList<>();
    private static final int LOOP_TIMES = 2000;
    private DisplayImageView displayImageView;


    private boolean moving = false;
    private final int whatID = 1;
    private int delayTime = 1000;

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


        //加载轮播图片栏的布局
        LayoutInflater.from(context).inflate(R.layout.switch_guide, this, true);
        viewPager = findViewById(R.id.guide_viewpager);
        dotsLayout = findViewById(R.id.guide_dots);
        txtTitle = findViewById(R.id.banner_title);

        //设置适配器
        adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new PageListener());
        
        
        handler = new MyHandler(this);
    }



    public void initPager(int[] images, String[] pics, String[] texts) {
        if (images.length != pics.length || images.length != texts.length)
            throw new RuntimeException("确保长度相等");

        this.titles = texts.clone();

        viewList.clear();
        imgViewList.clear();

        //初始化图像列表（设置标签）
        for (int i = 0; i < images.length; i++) {
            View v = initView(images[i], pics[i]);
            v.setTag(i);
            viewList.add(v);
        }
        initDots(viewList.size());


        //当lastItemToNextOrStop为true,才设置初始很大的位置,否则会初始化显示最后一张
        if (lastItemToNextOrStop)
            viewPager.setCurrentItem(viewList.size() * LOOP_TIMES / 2);

        if (images.length == 2) {
            //add one more time, size == 2 crash
            for (int i = 0; i < images.length; i++) {
                View holdView = initView(images[i], pics[i]);
                holdView.setTag(i);
                viewList.add(holdView);
            }
        }

        txtTitle.setText(this.titles[0]);
        adapter.notifyDataSetChanged();
    }
    private View initView(int res, String url) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_guide, null);

        //设置图像资源
        ImageView imgView = view.findViewById(R.id.img_view);
        imgView.setTag(url);
        imgView.setImageResource(res);

        imgViewList.add(imgView);
        return view;
    }
    
    /**
     * 加入轮播的点点
    */
    private void initDots(int count) {
        dotsLayout.removeAllViews();
        for (int j = 0; j < count; j++) {
            dotsLayout.addView(initDot());
        }
        dotsLayout.getChildAt(0).setSelected(true);
    }
    private View initDot() {
        return LayoutInflater.from(mContext).inflate(R.layout.layout_dot, null);
    }



    public void setAndLoadImage(DisplayImageView displayImageView) {
        this.displayImageView = displayImageView;
        if (viewList.size() == 0)
            throw new RuntimeException("需先调用initPager()");

        for (ImageView imageView : imgViewList) {
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
            delayTime = time;
            moving = flag;
            //防止一张图的时候切换
            if (viewList != null && viewList.size() > 1)
                handler.sendEmptyMessageDelayed(whatID, delayTime);
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
                if (msg.what == switchImage.whatID) {
                    switchImage.viewPager.setCurrentItem(
                            switchImage.viewPager.getCurrentItem() + 1,
                            true
                    );
                    sendEmptyMessageDelayed(switchImage.whatID, switchImage.delayTime);
                }
            }
        }
    }
    private class PageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        @Override
        public void onPageScrollStateChanged(int state) { }

        /**
         * 选中的当前页
         * @param position
         */
        @Override
        public void onPageSelected(int position) {

            //选中当前的点
            position = (position % dotsLayout.getChildCount());
            for (int i = 0; i < dotsLayout.getChildCount(); i++) {
                if (i == position)
                    dotsLayout.getChildAt(i).setSelected(true);
                else
                    dotsLayout.getChildAt(i).setSelected(false);
            }
            //设置当前的新闻标题
            txtTitle.setText(titles[position]);

            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(whatID, delayTime);
            }
        }
    }
    private class ViewPagerAdapter extends PagerAdapter {

        private List<View> data;
        public ViewPagerAdapter(List<View> data) {
            super();
            this.data = data;
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (data.get(position % data.size()).getParent() == null)
                container.addView(data.get(position % data.size()), 0);
            return data.get(position % data.size());
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }



        @Override
        public int getCount() {
            if (data.size() == 1)
                return 1;
            return lastItemToNextOrStop ? data.size() * LOOP_TIMES : data.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
