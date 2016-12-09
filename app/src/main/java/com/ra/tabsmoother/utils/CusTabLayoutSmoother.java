package com.ra.tabsmoother.utils;

import android.graphics.Rect;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 实现ViewPager 左右翻页时，Cus tab Layout（自己的View，非官方TabLayout）也进行同步滑动
 * <br/>
 * <br/>
 * implement custom TabLayout to smooth scroll to show when ViewPager change page.
 */

public class CusTabLayoutSmoother {

    /**
     * 在ViewPager 左右翻页时调用此方法，进行llTypes同步
     * <br/>
     * Invoke this function to synchronize the llTypes
     * <br/>
     * <br/>
     * <b>用在ViewPager.OnPageChangeListener#onPageSelected 中</b>
     * <br/>
     * Use this function in ViewPager.OnPageChangeListener#onPageSelected
     *
     * @param position ViewPager page index<br/>ViewPager 当前页码
     * @param llTypes  custom tab Layout  <br/>自定义的TabLayout
     * @param hsvTypes HorizontalScrollView who wrapped the custom TabLayout <br/>自定义TabLayout
     *                 的HorizontalScrollView
     */
    public static void doPageSelect(int position, LinearLayout llTypes, HorizontalScrollView
            hsvTypes) {
        View childTarget = llTypes.getChildAt(position);
        Rect scrollBounds = new Rect();
        Rect lr = new Rect();
        childTarget.getLocalVisibleRect(lr);
        if (!childTarget.getLocalVisibleRect(scrollBounds) || lr.width() < childTarget.getWidth()) {
            int lengthFront = 0;
            int toScrollLength = 0;
            if (targetViewIsOnRight(hsvTypes, position, llTypes)) {
                for (int i = 0; i <= position; i++) {
                    View childView = llTypes.getChildAt(i);
                    lengthFront += childView.getWidth();
                }
                toScrollLength = lengthFront - hsvTypes.getWidth();
            } else {
                for (int i = 0; i < position; i++) {
                    View childView = llTypes.getChildAt(i);
                    toScrollLength += childView.getWidth();
                }
            }
            hsvTypes.smoothScrollTo(toScrollLength, 0);
        }
    }

    /**
     * 判断position位置的view在type条的右边还是左边。<br/>
     * return the View on position is on right or left
     *
     * @param hsvTypes HorizontalScrollView who wrapped the custom TabLayout <br/>自定义TabLayout
     *                 的HorizontalScrollView
     * @param llTypes  custom tab Layout  <br/>自定义的TabLayout<br/>
     * @param position 希望被露出的View 位置<br/> The position that the view need to be scroll to
     * @return true:在右边  on right   <br/><br/> false:在左边  on left
     */
    private static boolean targetViewIsOnRight(HorizontalScrollView hsvTypes, int position,
                                               LinearLayout llTypes) {
        int count = llTypes.getChildCount();
        Rect scrollBounds = new Rect();
        hsvTypes.getHitRect(scrollBounds);
        for (int i = 0; i < count; i++) {
            View child = llTypes.getChildAt(i);
            if (child.getLocalVisibleRect(scrollBounds)) {
                if (i < position) {
                    return true;
                }
            }
        }
        return false;
    }

}
