package com.example.kk.arttraining.custom.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.List;


/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 * 说明：上下滚动文字
 */
public class TipView extends FrameLayout {

    /**
     * 动画间隔
     */
    private static final int ANIM_DELAYED_MILLIONS = 3 * 1000;
    /**
     * 动画持续时长
     */
    private static final int ANIM_DURATION = 1 * 1000;
    /**
     * 默认字体颜色
     */
    private static final String DEFAULT_TEXT_COLOR = "#2F4F4F";
    /**
     * 默认字体大小  dp
     */
    private static final int DEFAULT_TEXT_SIZE = 14;
    private Animation anim_out, anim_in;
    private TextView tv_tip_out, tv_tip_in;
    /**
     * 循环播放的消息
     */
    private static List<String> tipList;
    /**
     * 当前轮播到的消息索引
     */
    private int curTipIndex = 0;
    private long lastTimeMillis;
//    private Drawable head_boy, head_girl;

    public TipView(Context context) {
        this(context, null);
    }

    public TipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initTipFrame() {
//        head_boy = loadDrawable(R.mipmap.user_head_boy);
//        head_girl = loadDrawable(R.mipmap.user_head_girl);
        tv_tip_out = newTextView();
        tv_tip_in = newTextView();
        addView(tv_tip_in);
        addView(tv_tip_out);
    }

    /**
     * 设置要循环播放的信息
     *
     * @param tipList
     */
    public void setTipList(List<String> tipList) {
        this.tipList = tipList;
        initTipFrame();
        initAnimation();
        curTipIndex = 0;
        updateTip(tv_tip_out);
        updateTipAndPlayAnimation();
    }

    private void initAnimation() {
        anim_out = newAnimation(0, -1);
        anim_in = newAnimation(1, 3000);
        anim_in.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                updateTipAndPlayAnimationWithCheck();
            }
        });
    }

    private void updateTipAndPlayAnimation() {
        if (curTipIndex % 2 == 0) {
            updateTip(tv_tip_out);
            tv_tip_in.startAnimation(anim_out);
            tv_tip_out.startAnimation(anim_in);
            this.bringChildToFront(tv_tip_in);
        } else {
            updateTip(tv_tip_in);
            tv_tip_out.startAnimation(anim_out);
            tv_tip_in.startAnimation(anim_in);
            this.bringChildToFront(tv_tip_out);
        }
    }

    private void updateTipAndPlayAnimationWithCheck() {
        if (System.currentTimeMillis() - lastTimeMillis < 1000) {
            return;
        }
        lastTimeMillis = System.currentTimeMillis();
        updateTipAndPlayAnimation();
    }

    private void updateTip(TextView tipView) {
//        if (new Random().nextBoolean()) {
//            tipView.setCompoundDrawables(head_boy, null, null, null);
//        } else {
//            tipView.setCompoundDrawables(head_girl, null, null, null);
//        }
        String tip = getNextTip();
        if (!TextUtils.isEmpty(tip)) {
            tipView.setText(tip);
        }
    }

    /**
     * 获取下一条消息
     *
     * @return
     */
    private String getNextTip() {
        if (isListEmpty(tipList)) return null;
        return tipList.get(curTipIndex++ % tipList.size());
    }

    private TextView newTextView() {
        final TextView textView = new TextView(getContext());
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
        textView.setLayoutParams(lp);
        textView.setCompoundDrawablePadding(10);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setLines(2);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE);
        textView.setSingleLine(true);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity activity = (Activity) getContext();
//                Intent intent = new Intent(activity, HeadLinesContent.class);
//                intent.putExtra("headlines", textView.getText().toString());
//                activity.startActivity(intent);
            }
        });
        return textView;
    }

    private Animation newAnimation(float fromYValue, float toYValue) {
        Log.i("9999", "9999");
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        anim.setDuration(ANIM_DURATION);
        anim.setStartOffset(ANIM_DELAYED_MILLIONS);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }

    /**
     * 将资源图片转换为Drawable对象
     *
     * @param ResId
     * @return
     */
    private Drawable loadDrawable(int ResId) {
        Drawable drawable = getResources().getDrawable(ResId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() - 10, drawable.getMinimumHeight() - 10);
        return drawable;
    }

    /**
     * list是否为空
     *
     * @param list
     * @return true 如果是空
     */
    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

}
