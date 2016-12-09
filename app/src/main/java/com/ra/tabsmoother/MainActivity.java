package com.ra.tabsmoother;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ra.tabsmoother.adapter.TimeChooserAdapter;
import com.ra.tabsmoother.bean.TestBean;
import com.ra.tabsmoother.utils.CusTabLayoutSmoother;
import com.ra.tabsmoother.utils.DensityUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView hsvType;
    private LinearLayout llType;
    private ViewPager vpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hsvType = (HorizontalScrollView) findViewById(R.id.hsv_type);
        llType = (LinearLayout) findViewById(R.id.ll_type);
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        ArrayList<TestBean> testBeenList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TestBean bean = new TestBean();
            bean.type = "type" + i;
            bean.content = "content" + i;
            testBeenList.add(bean);
        }
        initData(testBeenList);
    }

    private void initData(ArrayList<TestBean> testBeen) {
        initTypes(testBeen);
        initContents(testBeen);
    }

    private void initContents(ArrayList<TestBean> testBeen) {
        final ArrayList<TextView> viewList = initTimeChooserDate(testBeen);
        TimeChooserAdapter mAdapter = new TimeChooserAdapter(viewList);
        vpContent.setAdapter(mAdapter);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                CusTabLayoutSmoother.doPageSelect(position, llType, hsvType);//在此处使用 Use the
                // function here
                signShownType(position);
                signUnShownTypes(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        signShownType(0);
        vpContent.setCurrentItem(0);
        vpContent.setOffscreenPageLimit(1);
    }

    private void initTypes(ArrayList<TestBean> testBeen) {
        for (int i = 0; i < testBeen.size(); i++) {
            final TextView textView = new TextView(this);
            textView.setText(testBeen.get(i).type);
            textView.setFocusable(true);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setPadding(DensityUtil.dip2px(this, 5f), 0, DensityUtil.dip2px(this, 5f), 0);
            textView.setTextColor(this.getResources().getColor(R.color.text_white));
            textView.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
            textView.setBackgroundResource(R.drawable.focus_selector_type_bg);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vpContent.setCurrentItem(finalI);
                }
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(this,
                    50));
            llType.addView(textView, lp);
        }
    }

    private ArrayList<TextView> initTimeChooserDate(ArrayList<TestBean> testBeenList) {
        ArrayList<TextView> viewList = new ArrayList<>();
        for (int i = 0; i < testBeenList.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(testBeenList.get(i).content);
            textView.setBackgroundColor(this.getResources().getColor(R.color.content_bg));
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewPager.LayoutParams
                    .MATCH_PARENT, DensityUtil.dip2px(this, 300));
            textView.setLayoutParams(lp);
            textView.setTextSize(30);
            textView.setTextColor(this.getResources().getColor(R.color.text_white));
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.focus_selector_content_bg);
            textView.setFocusable(true);
            viewList.add(textView);
        }
        return viewList;
    }

    /**
     * 设置当前页的类别字为红色<br/>
     * Set the type on current page to turn red
     *
     * @param position 当前页位置<br/>current page index
     */
    private void signShownType(int position) {
        TextView textView = (TextView) llType.getChildAt(position);
        if (textView != null) {
            textView.setTextColor(this.getResources().getColor(R.color.selected_sign));
        }
    }

    /**
     * 设置非当前页的类别字为默认色<br/>
     * Set the type not on the current page to turn default
     *
     * @param position 当前页位置<br/>current page index
     */
    private void signUnShownTypes(int position) {
        for (int i = 0; i < llType.getChildCount(); i++) {
            TextView textView = (TextView) llType.getChildAt(i);
            if (textView != null) {
                if (position != i) {
                    textView.setTextColor(this.getResources().getColor(R.color.text_white));
                }
            }
        }
    }
}
