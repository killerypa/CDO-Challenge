package me.relex.circleindicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class CircleIndicator extends LinearLayout {
    private static final int DEFAULT_INDICATOR_WIDTH = 5;
    private Animator mAnimatorIn;
    private Animator mAnimatorOut;
    private int mAnimatorResId = C0196R.animator.scale_with_alpha;
    private int mAnimatorReverseResId = 0;
    private Animator mImmediateAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private int mIndicatorBackgroundResId = C0196R.drawable.white_radius;
    private int mIndicatorHeight = -1;
    private int mIndicatorMargin = -1;
    private int mIndicatorUnselectedBackgroundResId = C0196R.drawable.white_radius;
    private int mIndicatorWidth = -1;
    private DataSetObserver mInternalDataSetObserver = new C01952();
    private final OnPageChangeListener mInternalPageChangeListener = new C02511();
    private int mLastPosition = -1;
    private ViewPager mViewpager;

    class C01952 extends DataSetObserver {
        C01952() {
        }

        public void onChanged() {
            super.onChanged();
            if (CircleIndicator.this.mViewpager != null) {
                int newCount = CircleIndicator.this.mViewpager.getAdapter().getCount();
                if (newCount != CircleIndicator.this.getChildCount()) {
                    if (CircleIndicator.this.mLastPosition < newCount) {
                        CircleIndicator.this.mLastPosition = CircleIndicator.this.mViewpager.getCurrentItem();
                    } else {
                        CircleIndicator.this.mLastPosition = -1;
                    }
                    CircleIndicator.this.createIndicators();
                }
            }
        }
    }

    private class ReverseInterpolator implements Interpolator {
        private ReverseInterpolator() {
        }

        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    class C02511 implements OnPageChangeListener {
        C02511() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            if (CircleIndicator.this.mViewpager.getAdapter() != null && CircleIndicator.this.mViewpager.getAdapter().getCount() > 0) {
                if (CircleIndicator.this.mAnimatorIn.isRunning()) {
                    CircleIndicator.this.mAnimatorIn.end();
                    CircleIndicator.this.mAnimatorIn.cancel();
                }
                if (CircleIndicator.this.mAnimatorOut.isRunning()) {
                    CircleIndicator.this.mAnimatorOut.end();
                    CircleIndicator.this.mAnimatorOut.cancel();
                }
                if (CircleIndicator.this.mLastPosition >= 0) {
                    View currentIndicator = CircleIndicator.this.getChildAt(CircleIndicator.this.mLastPosition);
                    if (currentIndicator != null) {
                        currentIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                        CircleIndicator.this.mAnimatorIn.setTarget(currentIndicator);
                        CircleIndicator.this.mAnimatorIn.start();
                    }
                }
                View selectedIndicator = CircleIndicator.this.getChildAt(position);
                if (selectedIndicator != null) {
                    selectedIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorBackgroundResId);
                    CircleIndicator.this.mAnimatorOut.setTarget(selectedIndicator);
                    CircleIndicator.this.mAnimatorOut.start();
                }
                CircleIndicator.this.mLastPosition = position;
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public CircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        int i = 1;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, C0196R.styleable.CircleIndicator);
            this.mIndicatorWidth = typedArray.getDimensionPixelSize(C0196R.styleable.CircleIndicator_ci_width, -1);
            this.mIndicatorHeight = typedArray.getDimensionPixelSize(C0196R.styleable.CircleIndicator_ci_height, -1);
            this.mIndicatorMargin = typedArray.getDimensionPixelSize(C0196R.styleable.CircleIndicator_ci_margin, -1);
            this.mAnimatorResId = typedArray.getResourceId(C0196R.styleable.CircleIndicator_ci_animator, C0196R.animator.scale_with_alpha);
            this.mAnimatorReverseResId = typedArray.getResourceId(C0196R.styleable.CircleIndicator_ci_animator_reverse, 0);
            this.mIndicatorBackgroundResId = typedArray.getResourceId(C0196R.styleable.CircleIndicator_ci_drawable, C0196R.drawable.white_radius);
            this.mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(C0196R.styleable.CircleIndicator_ci_drawable_unselected, this.mIndicatorBackgroundResId);
            if (typedArray.getInt(C0196R.styleable.CircleIndicator_ci_orientation, -1) != 1) {
                i = 0;
            }
            setOrientation(i);
            int gravity = typedArray.getInt(C0196R.styleable.CircleIndicator_ci_gravity, -1);
            if (gravity < 0) {
                gravity = 17;
            }
            setGravity(gravity);
            typedArray.recycle();
        }
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
        configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin, C0196R.animator.scale_with_alpha, 0, C0196R.drawable.white_radius, C0196R.drawable.white_radius);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin, @AnimatorRes int animatorId, @AnimatorRes int animatorReverseId, @DrawableRes int indicatorBackgroundId, @DrawableRes int indicatorUnselectedBackgroundId) {
        this.mIndicatorWidth = indicatorWidth;
        this.mIndicatorHeight = indicatorHeight;
        this.mIndicatorMargin = indicatorMargin;
        this.mAnimatorResId = animatorId;
        this.mAnimatorReverseResId = animatorReverseId;
        this.mIndicatorBackgroundResId = indicatorBackgroundId;
        this.mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;
        checkIndicatorConfig(getContext());
    }

    private void checkIndicatorConfig(Context context) {
        this.mIndicatorWidth = this.mIndicatorWidth < 0 ? dip2px(5.0f) : this.mIndicatorWidth;
        this.mIndicatorHeight = this.mIndicatorHeight < 0 ? dip2px(5.0f) : this.mIndicatorHeight;
        this.mIndicatorMargin = this.mIndicatorMargin < 0 ? dip2px(5.0f) : this.mIndicatorMargin;
        this.mAnimatorResId = this.mAnimatorResId == 0 ? C0196R.animator.scale_with_alpha : this.mAnimatorResId;
        this.mAnimatorOut = createAnimatorOut(context);
        this.mImmediateAnimatorOut = createAnimatorOut(context);
        this.mImmediateAnimatorOut.setDuration(0);
        this.mAnimatorIn = createAnimatorIn(context);
        this.mImmediateAnimatorIn = createAnimatorIn(context);
        this.mImmediateAnimatorIn.setDuration(0);
        this.mIndicatorBackgroundResId = this.mIndicatorBackgroundResId == 0 ? C0196R.drawable.white_radius : this.mIndicatorBackgroundResId;
        this.mIndicatorUnselectedBackgroundResId = this.mIndicatorUnselectedBackgroundResId == 0 ? this.mIndicatorBackgroundResId : this.mIndicatorUnselectedBackgroundResId;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
    }

    private Animator createAnimatorIn(Context context) {
        if (this.mAnimatorReverseResId != 0) {
            return AnimatorInflater.loadAnimator(context, this.mAnimatorReverseResId);
        }
        Animator animatorIn = AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
        animatorIn.setInterpolator(new ReverseInterpolator());
        return animatorIn;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewpager = viewPager;
        if (this.mViewpager != null && this.mViewpager.getAdapter() != null) {
            this.mLastPosition = -1;
            createIndicators();
            this.mViewpager.removeOnPageChangeListener(this.mInternalPageChangeListener);
            this.mViewpager.addOnPageChangeListener(this.mInternalPageChangeListener);
            this.mInternalPageChangeListener.onPageSelected(this.mViewpager.getCurrentItem());
        }
    }

    public DataSetObserver getDataSetObserver() {
        return this.mInternalDataSetObserver;
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (this.mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        this.mViewpager.removeOnPageChangeListener(onPageChangeListener);
        this.mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    private void createIndicators() {
        removeAllViews();
        int count = this.mViewpager.getAdapter().getCount();
        if (count > 0) {
            int currentItem = this.mViewpager.getCurrentItem();
            int orientation = getOrientation();
            for (int i = 0; i < count; i++) {
                if (currentItem == i) {
                    addIndicator(orientation, this.mIndicatorBackgroundResId, this.mImmediateAnimatorOut);
                } else {
                    addIndicator(orientation, this.mIndicatorUnselectedBackgroundResId, this.mImmediateAnimatorIn);
                }
            }
        }
    }

    private void addIndicator(int orientation, @DrawableRes int backgroundDrawableId, Animator animator) {
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }
        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, this.mIndicatorWidth, this.mIndicatorHeight);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
        if (orientation == 0) {
            lp.leftMargin = this.mIndicatorMargin;
            lp.rightMargin = this.mIndicatorMargin;
        } else {
            lp.topMargin = this.mIndicatorMargin;
            lp.bottomMargin = this.mIndicatorMargin;
        }
        Indicator.setLayoutParams(lp);
        animator.setTarget(Indicator);
        animator.start();
    }

    public int dip2px(float dpValue) {
        return (int) ((dpValue * getResources().getDisplayMetrics().density) + 0.5f);
    }
}
