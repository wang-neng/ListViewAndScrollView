package wn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import wn.leftscrolladdrefreshdemo.DeleteListViewMainActivity;
import wn.leftscrolldeleteone.LeftScrollDeleteOneActivity;
import wn.leftscrolldeletetwo.LeftScrollDeleteTwoActivity;
import wn.listviewdemoone.ListViewNestedScrollViewOneActivity;
import wn.listviewdemotwo.ListViewNestedScrollViewTwoActivity;
import wn.scrollviewdemo.ScrollViewNestedListViewActivity;

/**
 * profile:人物简介
 * Created by wn on 2018/7/29.
 */

public class MainActivity extends Activity  implements View.OnClickListener{

    private Button list_buttonOne ,scroll_buttonOne,list_buttonTwo,left_scroll_buttonOne,
                   left_scroll_buttonTwo,left_scroll_delete_add_refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_buttonOne = (Button)findViewById(R.id.list_nested_scroll_btn);
        scroll_buttonOne = (Button)findViewById(R.id.scroll_nested_list_btn);
        list_buttonTwo = (Button)findViewById(R.id.list_nested_scroll_btn_two);
        left_scroll_buttonOne = (Button)findViewById(R.id.left_scroll_delete_one_btn);
        left_scroll_buttonTwo = (Button)findViewById(R.id.left_scroll_delete_two_btn);
        left_scroll_delete_add_refresh = (Button)findViewById(R.id.left_scroll_delete_add_refresh_btn);
        list_buttonOne.setOnClickListener(this);
        list_buttonTwo.setOnClickListener(this);
        scroll_buttonOne.setOnClickListener(this);
        left_scroll_buttonOne.setOnClickListener(this);
        left_scroll_buttonTwo.setOnClickListener(this);
        left_scroll_delete_add_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.list_nested_scroll_btn:
                Intent intent_1 = new Intent();
                intent_1.setClass(MainActivity.this, ListViewNestedScrollViewOneActivity.class);
                startActivity(intent_1);
                break;
            case R.id.scroll_nested_list_btn:
                Intent intent_2 = new Intent();
                intent_2.setClass(MainActivity.this, ScrollViewNestedListViewActivity.class);
                startActivity(intent_2);
                break;
            case R.id.list_nested_scroll_btn_two:
                Intent intent_3 = new Intent();
                intent_3.setClass(MainActivity.this, ListViewNestedScrollViewTwoActivity.class);
                startActivity(intent_3);
                break;
            case R.id.left_scroll_delete_one_btn:
                Intent intent_4 = new Intent();
                intent_4.setClass(MainActivity.this, LeftScrollDeleteOneActivity.class);
                startActivity(intent_4);
                break;
            case R.id.left_scroll_delete_two_btn:
                Intent intent_5 = new Intent();
                intent_5.setClass(MainActivity.this, LeftScrollDeleteTwoActivity.class);
                startActivity(intent_5);
                break;
            case R.id.left_scroll_delete_add_refresh_btn:
                Intent intent_6 = new Intent();
                intent_6.setClass(MainActivity.this, DeleteListViewMainActivity.class);
                startActivity(intent_6);
                break;
            default:
                break;
        }
    }
}
