package com.example.owen.weathergo.modules.listener;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.tr4android.support.extension.widget.FloatingActionMenu;

/**
 * Created by owen on 2017/6/18.
 */

public class FloatingActionsMenuBehavior extends FloatingActionMenu.Behavior {
    /*@Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionMenu child
            , View directTargetChild, View target, int nestedScrollAxes) {
        // 确保是竖直判断的滚动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll
                (coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }*/

    public FloatingActionsMenuBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionMenu child, View directTargetChild, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionMenu child
            , View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed
                , dxUnconsumed, dyUnconsumed);
        /*if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
//            child.hide();
            child.setVisibility(View.INVISIBLE);
            //上拉消失，下拉出现
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.setVisibility(View.VISIBLE);
        }*/
    }
}
