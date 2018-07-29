package wn.listviewdemoone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;

import wn.R;

public class ListViewNestedScrollViewOneActivity extends AppCompatActivity {
    private static final String TAG = ListViewNestedScrollViewOneActivity.class.getName();
    private FixedHeightListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_nested_scroll_view_one_main);

        mListView = (FixedHeightListView) findViewById(R.id.listview);
        mListView.setAdapter(new ListViewAdapter(getApplicationContext()));
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (absListView.getChildCount() > 0) {
                    Log.d(TAG, "first child top:" + absListView.getChildAt(0).getTop()
                            + ", absListView.getFirstVisiblePosition() == 0:" + (absListView.getFirstVisiblePosition() == 0));
                }

                if (absListView.getChildCount() > 0 && absListView.getFirstVisiblePosition() == 0
                        && absListView.getChildAt(0).getTop() == 0) {
                    NestedScrollView parent = (NestedScrollView) (absListView.getParent().getParent());
                //    parent.setScrollable(true);
                    parent.setDividing(true);
                }
            }
        });
    }

}
