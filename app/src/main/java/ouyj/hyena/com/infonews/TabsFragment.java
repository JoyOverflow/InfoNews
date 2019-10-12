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

    private int tabIndex;
    private String tabName;

    private final int barWidth = 42;
    private final int textPadding = 20;
    private LayoutInflater inflate;

    /**
     * 构造方法
     */
    public TabsFragment() {
        Log.d(MainActivity.TAG, "TabsFragment！");
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_tabs);
        Log.d(MainActivity.TAG, "onCreateViewLazy！");

        Bundle bundle = getArguments();
        tabName = bundle.getString(STRING_TABNAME);
        tabIndex = bundle.getInt(INT_TABINDEX);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.scroll_indicator);


        ColorBar colorBar = new ColorBar(getApplicationContext(), Color.RED, 5);
        colorBar.setWidth(ScreenUtil.dp2px(getActivity(), barWidth));
        indicator.setScrollBar(colorBar);



        Resources res = getResources();
        int selectColor = res.getColor(R.color.tab_top_text_2);
        int unSelectColor = res.getColor(R.color.tab_top_text_1);
        float unSelectSize = 14;
        float selectSize = unSelectSize * 1f;
        indicator.setOnTransitionListener(
                new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize)
        );


        viewPager.setOffscreenPageLimit(4);
        IndicatorViewPager ivPager = new IndicatorViewPager(indicator, viewPager);
        ivPager.setAdapter(new IVAdapter(getChildFragmentManager()));

        inflate = LayoutInflater.from(getApplicationContext());
    }

    private class IVAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public IVAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return URLs.tabName.length;
        }
        @Override
        public View getViewForTab(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflate.inflate(R.layout.tab_top, viewGroup, false);
            }
            TextView textView = (TextView) view;
            textView.setText(URLs.tabName[i]);
            textView.setPadding(
                    ScreenUtil.dp2px(getActivity(), textPadding),
                    0,
                    ScreenUtil.dp2px(getActivity(), textPadding),
                    0
            );
            return view;
        }
        @Override
        public Fragment getFragmentForPage(int i) {
            InfoFragment fragment = new InfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString(InfoFragment.INTENT_STRING_TABNAME, URLs.tabName[i]);
            bundle.putInt(InfoFragment.INTENT_INT_POSITION, i);
            fragment.setArguments(bundle);
            return fragment;
        }
    }




}
