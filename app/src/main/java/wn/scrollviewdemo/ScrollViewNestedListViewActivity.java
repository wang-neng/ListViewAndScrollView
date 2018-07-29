package wn.scrollviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import wn.R;

/**
 * Created by wn on 2018/7/14.
 */

public class ScrollViewNestedListViewActivity extends Activity implements View.OnClickListener{
    private static final String TAG = ScrollViewNestedListViewActivity.class.getName();
    private ListView listView ;
    private ScrollView scrollView ;
    private ListViewAdapter listViewAdapter ;
    private TextView textView1 ;
    private TextView textView2 ;
    private TextView textView3 ;
    private TextView textView4 ;
    private float nowY ;
    private boolean flag ;
    private String top = "TOP"  ;
    private String bottom = "BOTTOM";
    private String top_bottom = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_nested_list_view_main);
        listView = (ListView)findViewById(R.id.list_view);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        textView1 = (TextView) findViewById(R.id.text_View_1);
        textView2 = (TextView) findViewById(R.id.text_View_2);
        textView3 = (TextView) findViewById(R.id.text_View_3);
        textView4 = (TextView) findViewById(R.id.text_View_4);
        listViewAdapter = new ListViewAdapter(this);
        listView.setAdapter(listViewAdapter);
        setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"listView Item"+position+"---"+view.isFocused());
                Toast.makeText(ScrollViewNestedListViewActivity.this,"点击ListView Item---"+view.isFocused()+"---"+listView.isFocused()+"---"+textView2.isFocused(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        scrollView.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                Log.i(TAG,"----"+scrollState+"---");
                switch (scrollState) {

                    case SCROLL_STATE_IDLE:   //停止滑动   ----0
                        flag = isListViewReachBottomEdge(absListView);
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL: //正在滑动[滚动]   -----1
                        break;
                    case SCROLL_STATE_FLING:  //开始滑动[滚动]   ------2
                        break;

                }
            }
            @Override
            public void onScroll(AbsListView absListView,  int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //  firstVisibleItem表示在显示屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置（下标从0开始）
                //  visibleItemCount表示在显示屏幕可以见到的ListItem(部分显示的ListItem也算)总数
                //  totalItemCount表示ListView的ListItem总数
                top_bottom = "";
                Log.i(TAG,"[firstVisibleItem ,visibleItemCount,totalItemCount]"+firstVisibleItem+","+visibleItemCount+","+totalItemCount);
                flag = ((firstVisibleItem + visibleItemCount) == totalItemCount);
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = absListView.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        top_bottom = top;
                        Log.d(TAG, "##### 滚动到顶部 #####");
                    }
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = absListView.getChildAt(absListView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == absListView.getHeight()) {
                        top_bottom = bottom;
                        Log.d(TAG, "##### 滚动到底部 ######");
                    }
                }

            }
        });


//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (absListView.getChildCount() > 0) {
//                    Log.d("MainActivity", "first child top:" + absListView.getChildAt(0).getTop()
//                            + ", absListView.getFirstVisiblePosition() == 0:" + (absListView.getFirstVisiblePosition() == 0));
//                }
//
//                if (absListView.getChildCount() > 0 && absListView.getFirstVisiblePosition() == 0
//                        && absListView.getChildAt(0).getTop() == 0) {
//                    OuterScrollView parent = (OuterScrollView) (absListView.getParent().getParent());
//                    //    parent.setScrollable(true);
//                    parent.setDividing(true);
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        v.setFocusable(true);
        v.requestFocus();
        Log.i(TAG,"scrollView--textView"+v.getId()+"---");
        Toast.makeText(this,"点击TextView----"+v.isFocused()+"---"+listView.isFocused()+"---"+textView2.isFocused(),
                Toast.LENGTH_SHORT).show();
    }

    public boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }

    public boolean isListViewReachTopEdge(final ListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            final View topChildView = listView.getChildAt(0);
            result = topChildView.getTop() == 0;
        }
        return result;
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //默认情况下，ViewGroup都是被禁止拦截触摸事件(允许往下传递)，由子View自己处理
       //即requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nowY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,listView.isFocusable()+"----"+top_bottom+"----"+nowY+"-----"+ev.getY());


                if (listView.isFocused() && ((nowY-ev.getY()>0 && top_bottom==bottom) || ((nowY-ev.getY()<0) && top_bottom==top))){
                    //listView滑到顶部或底部，不禁止父ViewGroup[ScrollView]拦截，即父View可以拦截该事件
                    listView.requestDisallowInterceptTouchEvent(false);
                    break;
                }
                //禁止拦截触摸事件(允许往下传递)
                listView.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("up", "dispatch ");
                listView.requestDisallowInterceptTouchEvent(true);
            case MotionEvent.ACTION_CANCEL:
                //禁止拦截触摸事件
                listView.requestDisallowInterceptTouchEvent(true);
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
