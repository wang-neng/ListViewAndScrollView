package wn.leftscrolldeletetwo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wn.R;

/**
 *
 * Created by wn on 2018/7/29.
 */

public class LeftScrollDeleteTwoActivity extends Activity
                                         implements AdapterView.OnItemClickListener,
                                                    View.OnClickListener,
                                                    SwipeItemView.OnSlideListener {

    private static final String TAG = LeftScrollDeleteTwoActivity.class.getName();
    // 自定义Lv
    private CustomSwipeListView mListView;
    // 自定义适配器
    private SlideAdapter adapter;
    // 内容列表
    private List<MessageItem> mMessageItems = new ArrayList<LeftScrollDeleteTwoActivity.MessageItem>();
    private SwipeItemView mLastSlideViewWithStatusOn;
    private static final int SUCCESS = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.left_scroll_delete_two_main);
        initView();
        loadData(SUCCESS);

    }

    private void loadData(final int what) {
        // 这里模拟从服务器获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = getData();
                handler.sendMessage(msg);
            }
        }).start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            List<MessageItem> result = (List<MessageItem>) msg.obj;
            switch (msg.what) {
                case SUCCESS:
                    //mListView.onRefreshComplete();
                    mMessageItems.clear();
                    mMessageItems.addAll(result);
                    break;
//                case ListViewCompat.LOAD:
//                    mListView.onLoadComplete();
//                    mMessageItems.addAll(result);
//                    break;
//                case 2:
//                    mListView.onLoadComplete();
//                    Toast.makeText(DeleteListViewMainActivity.this, "已加载全部！", Toast.LENGTH_SHORT).show();
//                    break;
            }
            //mListView.setResultSize(result.size());
            adapter.notifyDataSetChanged();
        }
    };

    public void initView() {
        mListView = (CustomSwipeListView) findViewById(R.id.custom_swipe_list_view);
        adapter = new SlideAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        //mListView.setOnRefreshListener(this);
        //mListView.setOnLoadListener(this);

    }


    // 测试数据
    public List<MessageItem> getData() {
        List<MessageItem> result = new ArrayList<MessageItem>();
        for (int i = 0; i < 10; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.iconRes = R.mipmap.delete_default_qq_avatar;
                item.title = "腾讯新闻";
                item.msg = "深圳西站增开两趟临客";
                item.time = "下午14:15";
            } else {
                item.iconRes = R.mipmap.delete_wechat_icon;
                item.title = "微信团队";
                item.msg = "欢迎你使用微信";
                item.time = "12:28";
            }
            result.add(item);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.holder:
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "onItemClick position=" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SwipeItemView) view;
        }
    }

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SwipeItemView slideView = (SwipeItemView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.item_listview_delete, null);

                slideView = new SwipeItemView(LeftScrollDeleteTwoActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(LeftScrollDeleteTwoActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mMessageItems.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(LeftScrollDeleteTwoActivity.this, "删除" + position+"个条", Toast.LENGTH_SHORT).show();
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                    Toast.makeText(LeftScrollDeleteTwoActivity.this, "编辑" + position+"个条", Toast.LENGTH_SHORT).show();
                }
            });

            return slideView;
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SwipeItemView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public TextView deleteHolder;
        public TextView edit;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (TextView) view.findViewById(R.id.delete);
            edit = (TextView) view.findViewById(R.id.edit);
        }
    }

}
