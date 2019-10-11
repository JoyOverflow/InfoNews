package ouyj.hyena.com.infonews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        SViewPager viewPager = findViewById(R.id.view_pager);
        Indicator indicator = findViewById(R.id.fixed_indicator);

        //将viewPager和indicator联合使用（设置适配器）
        IndicatorViewPager ivPager = new IndicatorViewPager(indicator, viewPager);
        ivPager.setAdapter(new IvAdapter(getSupportFragmentManager()));

        //查找工具栏并设置为动作栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.index_name);
        setSupportActionBar(toolbar);
    }

    /**
     * 自定义适配器类
     */
    private class IvAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        //布局填充器（将Layout转为View）
        private LayoutInflater inflater;

        //选项卡的名称和图标
        private String[] tabNames = {"新闻", "阅读", "视听", "发现", "我的"};
        private int[] tabIcons = {
                R.drawable.main1_selector,
                R.drawable.main2_selector,
                R.drawable.main3_selector,
                R.drawable.main4_selector,
                R.drawable.main5_selector
        };
        /**
         * 构造方法
         * @param fragmentManager
         */
        public IvAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }
        /**
         * 得到选项卡数
         * @return
         */
        @Override
        public int getCount() {
            return tabNames.length;
        }

        /**
         * 获取到一个选项卡
         * @param i
         * @param view
         * @param viewGroup
         * @return
         */
        @Override
        public View getViewForTab(int i, View view, ViewGroup viewGroup) {
            //获取每一个选项卡视图
            if (view == null) {
                //调用View静态inflate方法，获取View对象
                view = inflater.inflate(R.layout.tab_main, viewGroup, false);
            }
            //设置文本和图片
            TextView textView = view.findViewById(R.id.tv_tab_content);
            textView.setText(tabNames[i]);
            ImageView image =  view.findViewById(R.id.iv_tab_img);
            image.setImageResource(tabIcons[i]);
            return view;
        }

        /**
         * 获取每一个页面（返回片段）
         * @param i
         * @return
         */
        @Override
        public Fragment getFragmentForPage(int i) {
            Fragment first= new TabsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabsFragment.INTENT_STRING_TABNAME, tabNames[i]);
            bundle.putInt(TabsFragment.INTENT_INT_INDEX, i);
            first.setArguments(bundle);
            return first;
        }
    }
}
