package wn.scrollviewdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by wn on 2018/7/14.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;

    public ListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 40;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText("listView text:" + position);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                100);
        textView.setLayoutParams(params);
        return textView;
    }
}
