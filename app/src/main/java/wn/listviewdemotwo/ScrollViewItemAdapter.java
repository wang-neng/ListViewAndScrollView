package wn.listviewdemotwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import wn.R;

/**
 * Created by wn on 2018/7/12.
 */
public class ScrollViewItemAdapter extends BaseAdapter {

    private Context mContext;

    public ScrollViewItemAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 12;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_view_item, null);
        }
        return view;
    }

}
