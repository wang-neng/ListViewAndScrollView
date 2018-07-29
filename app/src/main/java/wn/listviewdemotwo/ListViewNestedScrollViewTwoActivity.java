package wn.listviewdemotwo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ListView;

import wn.R;

/**
 * Created by wn on 2018/7/29.
 */

public class ListViewNestedScrollViewTwoActivity extends Activity {

    private ListView mListView;
    private ScrollViewItemAdapter ScrollViewItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_nested_scroll_view_two_main);
        mListView = (ListView) findViewById(R.id.list_view);
        ScrollViewItemAdapter = new ScrollViewItemAdapter(getBaseContext());
        mListView.setAdapter(ScrollViewItemAdapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mListView.requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
