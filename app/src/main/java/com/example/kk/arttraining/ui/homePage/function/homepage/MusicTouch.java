package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.animation.AnimatorSet;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/11/25.
 * QQ邮箱:515849594@qq.com
 */
public class MusicTouch {

    public static void stopMusicAnimator(PlayAudioUtil playAudioUtil , AnimatorSet MusicArtSet,AnimationDrawable MusicAnim){
        if (playAudioUtil != null) {
            playAudioUtil.stop(1);
        }
        if (MusicArtSet != null) {
            MusicArtSet.end();
        }
        if (MusicAnim!=null){
            MusicAnim.stop();
            MusicAnim.selectDrawable(0);
        }
    }

    public static void stopMusicAnimation(PlayAudioUtil playAudioUtil ,AnimationDrawable MusicAnim){
        if (playAudioUtil != null) {
            playAudioUtil.stop(1);
        }
        if (MusicAnim!=null){
            MusicAnim.stop();
            MusicAnim.selectDrawable(0);
        }
    }
}
