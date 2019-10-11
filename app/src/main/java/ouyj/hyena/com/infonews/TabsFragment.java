package ouyj.hyena.com.infonews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabsFragment extends Fragment {

    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";

    private int index;
    private String tabName;

    /**
     * 构造方法
     */
    public TabsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
        View view= inflater.inflate(R.layout.fragment_tabs, container, false);
        return view;
        */

        Bundle bundle = getArguments();
        index = bundle.getInt(INTENT_INT_INDEX);
        tabName = bundle.getString(INTENT_STRING_TABNAME);

        TextView textView = new TextView(getActivity());
        textView.setText(tabName);
        return textView;
    }



}
