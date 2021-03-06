package com.telefonica.hunting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import me.relex.circleindicator.CircleIndicator;

public class PageTutorial extends Activity {
    private CircleIndicator indicator;
    private ViewPager mPager;

    class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        private int[] mImages = new int[]{C0194R.drawable.img_01, C0194R.drawable.img_02, C0194R.drawable.img_03};
        LayoutInflater mLayoutInflater;
        private String[] mText = new String[]{"Hack me!", "Code as a Ninja", "Be our next RockStar"};
        private String[] mTitle = new String[]{"Get the APK from your device. Look deep in the source code and solve the code challenge inside", "Solve properly the suggested code kata and show us that you are the Android ninja we are looking for", "If you are a coding RockStar and you like Rock & Roll this is definitely your place!"};

        CustomPagerAdapter(Context context) {
            this.mContext = context;
            this.mLayoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        }

        public int getCount() {
            return this.mImages.length;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = this.mLayoutInflater.inflate(C0194R.layout.page_tutorial_fragment, container, false);
            ((ImageView) itemView.findViewById(C0194R.id.fragment_help_tutorial_imageview)).setImageResource(this.mImages[position]);
            ((TextView) itemView.findViewById(C0194R.id.fragment_help_tutorial_text)).setText(this.mTitle[position]);
            ((TextView) itemView.findViewById(C0194R.id.fragment_help_tutorial_subtitle_text)).setText(this.mText[position]);
            container.addView(itemView);
            return itemView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0194R.layout.activity_page_tutorial);
        retrieveViews();
        setPageAdapter();
    }

    private void retrieveViews() {
        this.indicator = (CircleIndicator) findViewById(C0194R.id.indicator);
        this.mPager = (ViewPager) findViewById(C0194R.id.pager);
    }

    private void setPageAdapter() {
        this.mPager.setAdapter(new CustomPagerAdapter(this));
        this.indicator.setViewPager(this.mPager);
    }
}
