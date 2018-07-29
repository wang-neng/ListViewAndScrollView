package wn.listviewdemoone;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by wn on 17/8/6.
 */

public class NestedScrollView extends ScrollView {

    private final String TAG = "NestedScrollView";

    private boolean dividing = false;

    private boolean isListViewFocus = false;

    private int nowY;

    public NestedScrollView(Context context) {
        super(context);
    }

    public NestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                nowY = (int) motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) motionEvent.getY();
                Log.d(TAG, "scrollview scrollY:" + getScrollY()
                        + ", nowY:" + nowY + ", y=" + y + ", dividing:" + dividing);
                if (dividing && nowY < y) { // 向下滑动 切换到scrollview
                    Log.d(TAG, "向下滑动，切换到scrollview");
                    isListViewFocus = false;
                    dividing = false;
                    return super.onInterceptTouchEvent(motionEvent);
                } else if (getScrollY() == 200 && nowY > y) { // 向上滑动 切换到listview
                    Log.d(TAG, "向上滑动，切换到listview");
                    isListViewFocus = true;
                    dividing = false;
                    return false;
                }
                dividing = false;

            default:
        }

        if (isListViewFocus) {   // 持续向上滑动 listview
            return false;
        } else {
            return super.onInterceptTouchEvent(motionEvent);
        }

    }

    public void setDividing(boolean dividing) {
        this.dividing = dividing;
    }
}
