package ouyj.hyena.com.infonews;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.shizhefei.fragment.LazyFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends LazyFragment {

    public static final String STRING_TABNAME = "intent_String_tabName";
    public static final String INT_TABPOSITION = "intent_int_position";

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_info);
    }
}
