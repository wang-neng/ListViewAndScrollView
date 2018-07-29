package wn.leftscrolldeletetwo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Scroller;

import com.nineoldandroids.view.ViewHelper;

/**
 * HorizontalScrollView extends FrameLayout
 * QQListView左滑删除，经典案例,高仿QQ左滑，动画效果，自定义
 * reference url :https://blog.csdn.net/qq_33172458/article/details/51408188
 * Created by wn on 2018/7/29.
 */

public class QQListView extends ListView {

    /**
     * 屏幕宽度
     */
    private int width;
    /**
     * 屏幕高度
     */
    private int height;
    /**
     * 系统默认最小的滑动距离，
     */
    private int slop;
    /**
     * 速度追踪者
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 当用户按下Item的时候记录的 前景TextView
     */
    private View target;
    /**
     * 上一次X坐标
     */
    private int lastX;
    /**
     * 上一次Y坐标
     */
    private int lastY;
    /**
     * 滑动的模式
     */
    private int scrollModel;
    /**
     * item 滑东模式
     */
    private final int ITEMSCROLLMODE = 1 ;
    /**
     * ListView滑动模式
     */
    private final int LISTVIEWSCROLLMODEL = 2 ;

    /**
     * 判断是否需要改变滑动模式
     */
    boolean isNeed = true;
    /**
     * 背景TextView宽度
     */
    private int backgroudViewWidht;
    /**
     * 表示背景TextView是否已经显示
     */
    boolean isShow = false;
    /**
     * 前景TextView
     */
    private View showView;
    /**
     * 背景TextView
     */
    private View backgroudView;

    public QQListView(Context context) {
        this(context, null);
    }

    public QQListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // 获取屏幕的宽高
    }

    public QQListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (isShow == true)  // 表示背景View（删除TextView）可见的时候，在触发down就把背景View 隐秘掉
                {
                    Log.i("Info ", "is show");
                    closeViewByAnimation(showView);
                }
                lastX = (int) ev.getX();  //获取当前X坐标（这里LastX是相对父控件）
                lastY = (int) ev.getY();
                getTargetView(ev, lastX, lastY);  //获取点击按下那个Item里面的前景TextView
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();   //获取触摸当前move事件下得X Y坐标
                int moveY = (int) ev.getY();
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                }
                mVelocityTracker.clear();  //清空一些数据
                mVelocityTracker.addMovement(ev);
                mVelocityTracker.computeCurrentVelocity(500);  //计算500ms 用户滑动速度，注意这里是有方向区别的
                if (Math.abs(moveX - ev.getX()) > Math.abs(moveY - ev.getY())) {   //判断是左滑还是上下滑动， 这里默认X>Y则为Item滑动，也就是左滑删除
                    scrollModel = ITEMSCROLLMODE;
                } else {
                    scrollModel = LISTVIEWSCROLLMODEL;
                }

                if (scrollModel != ITEMSCROLLMODE && isNeed == true) //判断是不是item滑动模式
                {
                    if (Math.abs(mVelocityTracker.getXVelocity()) <= 50) {
                        Log.i("Info", "速度太慢无法触发 X:" + mVelocityTracker.getXVelocity());
                        lastX = (int) ev.getX();
                        lastY = (int) ev.getY();  //把当前X Y赋值给lastX lastY
                        return super.onTouchEvent(ev);   // 这里触发listview上下滑动
                    }
                    Log.i("Info", "tag4");
                    if (Math.abs(moveX - lastX) > Math.abs(moveY - lastY)) {   //判断是左滑还是上下滑动， 这里默认X>Y则为Item滑动，也就是左滑删除
                        scrollModel = ITEMSCROLLMODE;
                        isNeed = false;
                    } else {
                        isNeed = false;
                    }

                }
                if (moveX - lastX > 0) {//大于零 说明已经往右滑
                    moveX = lastX;
                }

                if (scrollModel == LISTVIEWSCROLLMODEL || target == null) {
				 Log.i("Info", "scrollModel: "+ scrollModel);
                    return super.onTouchEvent(ev);  //不处理， 交个listview 做上下滑动处理
                } else {
                    int deltaX = moveX - lastX;
                    ViewHelper.setTranslationX(target, deltaX); //移动target（前景TextView）显示背景TextView(删除TextView)
                    return true;
                }
            case MotionEvent.ACTION_UP:
                try {
                        mVelocityTracker.clear();  //清除下数据
                        mVelocityTracker.recycle(); //回收下
                        if (isShow) {
                            Log.i("Info", "消费事件");
                            isShow = false;
                            stateRestore();  //恢复先原来状态
                            return true;    // 如果当前是显示状态， 把事件消费掉  结束整个事件分发
                        }
                        if (scrollModel == ITEMSCROLLMODE) {
    					Log.i("Info", "up return "+"true");
                            restoreOrSmoonth(ev, target);  //关闭前景TextView
                            stateRestore();
                            return true;
                        }
                        stateRestore();
                } catch (Exception e) {   //这里捕捉下异常
                    Log.e("Info", "up  事件 异常");
                }
                break;
        }

        return super.onTouchEvent(ev);


    }

    private void stateRestore(){
        isNeed = true ;
        isShow = false ;
        isRuning = false ;
        scrollModel = 0 ;
    }

    private View getTargetView(MotionEvent ev, int x, int y) {

        mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(ev);
        ViewGroup viewGroup = (ViewGroup) this.getChildAt(pointToPosition(x, y) - this.getFirstVisiblePosition()); //获取外层的FrameLayout
        if (viewGroup != null)  //这里需要盘空，有各种意外情况  （比如点了分割线 没有获取到）
        {

            View view = viewGroup.getChildAt(1);  //前景TextView
            target = view; //赋值给target 我们要对target进行一系列动画处理
            backgroudView = viewGroup.getChildAt(0);   //获取backgroudview，然后其宽度
            backgroudView.setEnabled(true);
            backgroudViewWidht = backgroudView.getWidth();
        }
        return null;
    }

    private void closeViewByAnimation(View showView_) {

        Message message=new Message();
        message.what=0x22;
        message.obj=showView_;
        from=-backgroudViewWidht;
        distance=backgroudViewWidht;
        handler.sendMessage(message);


    }


    boolean isRuning=false;
    private Scroller mScroller;

    private void restoreOrSmoonth(MotionEvent ev ,View v) {
        Log.i("Info", "v:"+v+"   isRuning:"+isRuning);
        if(v==null||isRuning==true){
            return;
        }
        isRuning=true;
        int endX=(int) ev.getX();
        int deltaX=endX-lastX;
        Log.i("Info", "触发动画");
        if(deltaX<0){//左滑
            if(Math.abs(deltaX)>backgroudViewWidht/2){
                int oppsiteDeltaX=backgroudViewWidht-Math.abs(deltaX);
                Log.i("Info", "x oppsi:"+oppsiteDeltaX+"  deltaX:"+deltaX);
                from=deltaX;
                distance=-oppsiteDeltaX;
                Message message=handler.obtainMessage();
                Log.i("Info", "target:"+v);
                message.what=0x22;
                message.obj=v;
                handler.sendMessage(message);
                showView=v;
                isShow=true;//显示View
                Log.i("Info", "isShow:"+isShow);
                isRuning=false;
            }
            else{//如果滑动大于bacgroud的一半
                int oppsiteDeltaX=backgroudViewWidht-Math.abs(deltaX);
                Log.i("Info", "s oppsi:"+oppsiteDeltaX+"  deltaX:"+deltaX);
                from=deltaX;
                distance=-deltaX;
                Message message=handler.obtainMessage();
                message.what=0x22;
                message.obj=v;
                handler.sendMessage(message);
                Log.i("Info", "isShow_:"+isShow);
                isShow=false;
                isRuning=false;

            }
        }

        isRuning=false;
    }

    float from;  //从哪里开始
    float distance;  //滑动到哪里
    float frame=30;   //帧数
    float duration=100;  //时长
    float count=0;   //用于计数
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x22) {
                if (count <= frame){   //小于帧数继续走
                    ++count;
                    View view = (View) msg.obj;
                    Message message = handler.obtainMessage();
                    message.what = 0x22;
                    message.obj = view;
                    ViewHelper.setTranslationX(view, from + distance / frame * count);  //view移动的距离
                    handler.sendMessageDelayed(message, (int) duration / 100); //继续发送消息直到移动结束
                } else {
                    count = 0;
                }
            }
        }
    };


}

