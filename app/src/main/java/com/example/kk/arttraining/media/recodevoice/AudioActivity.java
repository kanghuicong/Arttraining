package com.example.kk.arttraining.media.recodevoice;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.discover.view.PostingMain;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.presenter.AudioPresenter;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.AudioFileFunc;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/31 20:51
 * 说明:选择音频
 */
public class AudioActivity extends BaseActivity implements IAudioActivity {


    @InjectView(R.id.valuation_audio_start_recode)
    ImageView valuationAudioStartRecode;
    @InjectView(R.id.valuation_audio_choselocal_recode)
    ImageView valuationAudioChoselocalRecode;
    @InjectView(R.id.tv_minutes)
    TextView tvMinutes;
    @InjectView(R.id.tv_seconds)
    TextView tvSeconds;
    @InjectView(R.id.recode_ok)
    TextView recode_ok;
    @InjectView(R.id.iv_title_back)
    ImageView recode_back;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.icon_recode_bg)
    ImageView iconRecodeBg;

    private int minutes;
    private int seconds;
    private AudioPresenter audioPresenter;
    private int flag = 0;
    private AudioInfoBean audioInfoBean;
    public static int CHOSE_LOCAL_AUDIO = 001;
    private MediaPlayer mMediaPlayer;
    private String file_path;
    private Animation hyperspaceJumpAnimation;
    //判断从哪里进来的
    private String from;
    private String duration;
    Timer timer = new Timer();
    int MaxTime = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_audio_activity);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        from = intent.getStringExtra("fromIntent");
        init();
        UIUtil.showLog("AudioActivity11--->from", from + "");
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                AudioActivity.this, R.anim.loading_animation);


    }

    @Override
    public void init() {
        audioPresenter = new AudioPresenter(this);
    }

    @OnClick({R.id.valuation_audio_start_recode, R.id.valuation_audio_choselocal_recode, R.id.iv_title_back, R.id.recode_ok})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.valuation_audio_start_recode:
                valuationAudioChoselocalRecode.setVisibility(View.GONE);
                switch (flag) {
                    case 0:
                        iconRecodeBg.startAnimation(hyperspaceJumpAnimation);
                        valuationAudioStartRecode.setImageResource(R.mipmap.stop_recode);
                        UIUtil.showLog("AudioActivity22--->from", from + "");
                        timer.schedule(task, 0, 1000);
                        if (from.equals("postingMain")) {
                            audioPresenter.startArmRecode();
                            MaxTime=2;
                        } else if (from.equals("production")) {
                            audioPresenter.startWavRecode();
                            MaxTime=5;
                        }
                        flag = 1;
                        break;
                    case 1:
                        valuationAudioStartRecode.setImageResource(R.mipmap.stop_recode);
                        iconRecodeBg.clearAnimation();
                        int length = minutes * 60 + seconds;
                        duration = minutes * 60 + seconds + "";
                        minutes = 0;
                        seconds = 0;
                        timer.cancel();
                        if (from.equals("postingMain")) {
                            audioPresenter.stopArmRecode();
                            recode_ok.setVisibility(View.VISIBLE);
                        } else if (from.equals("production")) {
                            audioPresenter.stopWavRecode();
                            recode_ok.setVisibility(View.VISIBLE);
                        }
                        flag = 2;
                        break;
                    case 2:
                        break;
                }
                break;
            case R.id.valuation_audio_choselocal_recode:

                Intent intent = new Intent(this, MediaActivity.class);
                intent.putExtra("media_type", "music");
                startActivityForResult(intent, CHOSE_LOCAL_AUDIO);

                break;

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.recode_ok:
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("media_info", audioInfoBean);
                bundle.putString("type", "music");
                intent1.putExtras(bundle);
                if (from.equals("postingMain")) {
                    setResult(PostingMain.POST_MAIN_AUDIO_CODE, intent1);
                } else if (from.equals("production")) {
                    setResult(ValuationMain.CHOSE_PRODUCTION, intent1);
                }

                finish();
                break;

        }
    }


    //录音完成
    @Override
    public void RecordOK(AudioInfoBean audioInfoBean) {

        audioInfoBean.setAudio_length(duration);
        audioInfoBean.setMedia_type("music");
        this.audioInfoBean = audioInfoBean;
        play();
    }


    //从本地文件选取文件
    @Override
    public void choseLocalRecord() {

    }


    private void play() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource((new FileInputStream(new File(audioInfoBean.getAudio_path())).getFD()));
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            //播放音频监听
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    UIUtil.showLog("播放完成---》", "true");
                    iconRecodeBg.clearAnimation();
                    minutes = 0;
                    seconds = 0;
//                    tvMinutes.setText("00:");
//                    tvSeconds.setText("00");
                }
            });
            iconRecodeBg.startAnimation(hyperspaceJumpAnimation);

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CHOSE_LOCAL_AUDIO) {
            Uri uri = data.getData();

            file_path = FileUtil.getFilePath(AudioActivity.this, uri);
            UIUtil.showLog("uri", uri + "");
            UIUtil.showLog("file_path", file_path + "");
            long file_size = AudioFileFunc.getFileSize(file_path);
            UIUtil.showLog("file_size", file_size + "");
            AudioInfoBean audioInfoBean = new AudioInfoBean();
            audioInfoBean.setAudio_path(file_path);
            audioInfoBean.setAudio_size(file_size);
            RecordOK(audioInfoBean);

        } else if (requestCode == CHOSE_LOCAL_AUDIO) {
            try {
                file_path = data.getStringExtra("file_path");
                duration = data.getStringExtra("duration");
                long file_size = AudioFileFunc.getFileSize(file_path);
                audioInfoBean = new AudioInfoBean();
                audioInfoBean.setAudio_path(file_path);
                audioInfoBean.setAudio_size(file_size);
                audioInfoBean.setMedia_type("music");
                recode_ok.setVisibility(View.VISIBLE);
                RecordOK(audioInfoBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer != null) mMediaPlayer.stop();
    }



    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    if (seconds == 60) {
                        minutes++;
                        seconds = 0;
                    }
                    tvMinutes.setText("0" + minutes + ":");
                    if (seconds < 10) {
                        tvSeconds.setText("0" + seconds + "");
                    } else {
                        tvSeconds.setText(seconds + "");
                    }
                    if (minutes * 60 + seconds >= MaxTime * 60) {
                        timer.cancel();
                        if (from.equals("postingMain")) {
                            audioPresenter.stopArmRecode();
                        } else if (from.equals("production")) {
                            audioPresenter.stopWavRecode();
                        }

                    }

            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            seconds++;
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };


}
