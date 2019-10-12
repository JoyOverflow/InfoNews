package ouyj.hyena.com.infonews;


import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import ouyj.hyena.com.infonews.utils.ScreenUtil;
import ouyj.hyena.com.infonews.utils.URLs;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabsFragment extends LazyFragment {

    public static final String STRING_TABNAME = "string_tabname";
    public static final String INT_TABINDEX = "int_tabindex";


    private final int barWidth = 42;
    private final int textPadding = 20;
    //private LayoutInflater inflate;

    /**
     * 构造方法
     */
    public TabsFragment() {
        Log.d(MainActivity.TAG, "TabsFragment！");
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        //加载片段布局
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_tabs);
        Log.d(MainActivity.TAG, "onCreateViewLazy！");

        //获取传递数据
        Bundle bundle = getArguments();
        String tabName = bundle.getString(STRING_TABNAME);
        int tabIndex = bundle.getInt(INT_TABINDEX);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);

        //设置滚动选项指示器
        ColorBar colorBar = new ColorBar(getApplicationContext(), Color.RED, 5);
        colorBar.setWidth(ScreenUtil.dp2px(getActivity(), barWidth));
        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.scroll_indicator);
        indicator.setScrollBar(colorBar);

        //设置滚动时颜色的改变
        Resources res = getResources();
        int selectColor = res.getColor(R.color.tab_top_text_2);
        int unSelectColor = res.getColor(R.color.tab_top_text_1);
        float unSelectSize = 14;
        float selectSize = unSelectSize * 1f;
        indicator.setOnTransitionListener(
                new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize)
        );

        //将viewPager和indicator关联（设置适配器）
        IndicatorViewPager tabPager = new IndicatorViewPager(indicator, viewPager);
        tabPager.setAdapter(new TabAdapter(getChildFragmentManager()));

        //inflate = LayoutInflater.from(getApplicationContext());
    }

    private class TabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private LayoutInflater inflate;

        /**
         * 构造方法
         * @param manager
         */
        public TabAdapter(FragmentManager manager) {
            super(manager);
            inflate = LayoutInflater.from(getApplicationContext());
        }

        /**
         * 得到选项卡数
         * @return
         */
        @Override
        public int getCount() {
            return URLs.tabName.length;
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
            if (view == null) {
                //选项卡内选项的布局
                view = inflate.inflate(R.layout.tab_top, viewGroup, false);
            }
            //获取文本视图引用设置文本和边距
            TextView txtTitle = (TextView) view;
            txtTitle.setText(URLs.tabName[i]);
            txtTitle.setPadding(
                    ScreenUtil.dp2px(getActivity(), textPadding),
                    0,
                    ScreenUtil.dp2px(getActivity(), textPadding),
                    0
            );
            return view;
        }
        /**
         * 获取每一个页面（返回特定片段）
         * @param i
         * @return
         */
        @Override
        public Fragment getFragmentForPage(int i) {
            InfoFragment info = new InfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString(InfoFragment.STRING_TABNAME, URLs.tabName[i]);
            bundle.putInt(InfoFragment.INT_TABPOSITION, i);
            info.setArguments(bundle);
            return info;
        }
    }




}
