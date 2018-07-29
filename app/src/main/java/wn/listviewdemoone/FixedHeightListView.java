package wn.listviewdemoone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * Created by wn on 17/8/5.
 */

public class FixedHeightListView extends ListView {

    public FixedHeightListView(Context context) {
        super(context);
    }

    public FixedHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(getWindowHeight() - getStatusBarHeight(),
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private int getStatusBarHeight() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    private int getWindowHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


}
