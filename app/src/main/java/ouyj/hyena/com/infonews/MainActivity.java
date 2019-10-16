package ouyj.hyena.com.infonews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

        //查找视图引用
        SViewPager viewPager = findViewById(R.id.view_pager);
        Indicator indicator = findViewById(R.id.fixed_indicator);
        //默认=1（表示左右相连的1个页面和当前页面会被缓存住，比如切换到左边页面则是不会重新创建的）
        //设置=4（一次性加载所有的TabsFragment，适配器内会执行5次getViewForTab或getFragmentForPage）
        viewPager.setOffscreenPageLimit(4);
        //将viewPager和indicator联合使用（设置适配器）
        IndicatorViewPager ivPager = new IndicatorViewPager(indicator, viewPager);
        ivPager.setAdapter(new IvAdapter(getSupportFragmentManager()));


        //查找工具栏并设置为动作栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.index_name);
        setSupportActionBar(toolbar);
    }
    /**
     * 活动选项菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            Log.d(TAG, "getCount！");
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
            Log.d(TAG, "getViewForTab！");

            //获取每一个选项卡视图
            if (view == null) {
                //调用View静态inflate方法，获取View对象
                view = inflater.inflate(R.layout.tab_main, viewGroup, false);
            }
            //设置文本和图片
            TextView txtTitle = view.findViewById(R.id.tv_tab_content);
            txtTitle.setText(tabNames[i]);
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
            Log.d(TAG, "getFragmentForPage！");

            Fragment tab= new TabsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabsFragment.STRING_TABNAME, tabNames[i]);
            bundle.putInt(TabsFragment.INT_TABINDEX, i);
            tab.setArguments(bundle);
            return tab;
        }
    }
}
