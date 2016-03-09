package runsystem.net.global_hr;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import runsystem.net.global_hr.api.APICheckAudio;
import runsystem.net.global_hr.api.APICheckAudio.CheckAudioOnResult;
import runsystem.net.global_hr.api.APICheckAudioHeader;
import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.common.ExtAudioRecorder;
import runsystem.net.global_hr.common.LogoutService;
import runsystem.net.global_hr.network.NetworkUtils;

;


public class DialogRecorderActivity extends BaseActivity implements CheckAudioOnResult {
    public static final String LOG_TAG = "DialogRecorder";
    static final int DIALOG_RECORD_REQUEST = 1;  // The request code
    public static Activity activity = null;
    private static String mFileName = null;
    private ExtAudioRecorder extAudioRecorder = null;
    private MediaPlayer mPlayer = null;
    private Chronometer chronometer = null;
    boolean mStartRecording = false;
    boolean mStartPlaying = false;
    private String userId = "";
    private String index = "";
    private String jSonString = "";
    private String lessonNo = "";
    private long timeRecord = 0;
    private long timePlay = 0;
    private int modeLanguage = 0;

    private File file = null;
    private ImageButton btn_record, btn_play, btn_cancel, btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dialog_recorder);
        this.setFinishOnTouchOutside(false);
        activity = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        btn_record = (ImageButton) findViewById(R.id.recorder_btn_record);
        btn_play = (ImageButton) findViewById(R.id.recorder_btn_play);
        btn_cancel = (ImageButton) findViewById(R.id.recorder_btn_cancel);
        btn_ok = (ImageButton) findViewById(R.id.recorder_btn_ok);
        chronometer = (Chronometer) findViewById(R.id.recorder_btn_chrono);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                timeRecord = time;
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                cArg.setText(hh+":"+mm+":"+ss);
            }
        });

        btn_record.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_play.setClickable(false);
        btn_play.setEnabled(false);
        btn_ok.setEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.KEY_AUDIO_FILE;
        extAudioRecorder = ExtAudioRecorder.getInstanse(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getString("userId");
            index = extras.getString("index");
            jSonString = extras.getString("jSonString");
            lessonNo = extras.getString("lessonNo");
            modeLanguage = Integer.parseInt(extras.getString("modeLanguage"));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        extAudioRecorder.release();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.recorder_btn_record:
                processRecord();
                break;
            case R.id.recorder_btn_play:
                processPlay();
                break;
            case R.id.recorder_btn_cancel:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("message", "");
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
                break;
            case R.id.recorder_btn_ok:
                processOk();
                break;
        }
    }

    @Override
    public void onCheckAudioOnResult(APICheckAudioHeader header) {
        // TODO Auto-generated method stub
        // Hide loading
        closeLoading();
        Intent returnIntent = new Intent();
        if (header != null) {
            if (header.getResponse().getStatus().equalsIgnoreCase(Constant.STATUS_OK)
                    && header.getResult() != null) {
                String content = "";
                String message = header.getResponse().getMessage();
                switch (modeLanguage){
                    case Constant.MODE_LANG_KANA:
                        content = header.getResult().getKana();
                        break;
                    case Constant.MODE_LANG_KANJI:
                        content = header.getResult().getKanji();
                        break;
                    case Constant.MODE_LANG_ROMAJI:
                        content = header.getResult().getRomaji();
                        break;
                    default:
                        content = header.getResult().getKana();
                        break;
                }
                if (!content.equalsIgnoreCase("NULL")) {
                    returnIntent.putExtra("index", index);
                    returnIntent.putExtra("content", content);
                    returnIntent.putExtra("message", message);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    //showDialogInfo("Error", "Null data", false);
                    returnIntent.putExtra("message", message);
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            } else {
                //showDialogInfo("Error", "error analysis", false);
                String message = header.getResponse().getMessage();
                returnIntent.putExtra("message", message);
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        } else {
            showAlertInfo(this, " ", getString(R.string.mess_server_error), false);
        }

    }
    /*=================== METHOD ===================*/

    /**
     * processRecord
     */
    private void processRecord() {

        if (!mStartRecording) {
            btn_record.setImageResource(R.drawable.ic_stop);
            btn_play.setClickable(false);
            btn_play.setEnabled(false);
            btn_ok.setEnabled(false);
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            startRecording();
        } else {
            btn_record.setImageResource(R.drawable.ic_record);
            btn_play.setClickable(true);
            btn_play.setEnabled(true);
            btn_ok.setEnabled(true);
            chronometer.stop();
            timePlay = timeRecord;
            stopRecording();
        }
        mStartRecording = !mStartRecording;
    }

    /**
     * processPlay
     */
    private void processPlay() {
        if (!mStartPlaying) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            startPlaying();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    chronometer.stop();
                    mStartPlaying = !mStartPlaying;
                }
            }, timePlay);
        } else {
            stopPlaying();
        }
        mStartPlaying = !mStartPlaying;
    }

    /**
     * processOk
     */
    private void processOk() {
        file = new File(mFileName);
        if (file.exists() && index != "" && jSonString != "" && lessonNo != "") {
            startAPICheckAudio(lessonNo, jSonString, file, modeLanguage);
        }
    }

    /**
     * startRecording
     */
    private void startRecording() {

        extAudioRecorder.setOutputFile(mFileName);
        extAudioRecorder.prepare();
        extAudioRecorder.start();

    }

    /**
     * stopRecording
     */
    private void stopRecording() {
        extAudioRecorder.stop();
    }
    /**
     * startPlaying
     */
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    /**
     * stopPlaying
     */
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    /**
     * startAPICheckAudio
     * @param lessonNo
     * @param jSonString
     * @param file
     */
    private void startAPICheckAudio(String lessonNo, String jSonString, File file, int modeLanguage) {
        // Check network
        if (NetworkUtils.isNetworkConnected(this)) {
            showLoading(getText(R.string.mes_startup_uploading).toString());
            APICheckAudio api = new APICheckAudio();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());
            api.lessonId = lessonNo;
            api.txtQuestion = jSonString;
            api.modeLanguage = modeLanguage;
            api.fileName = getRegistrationId(this)+"_"+ currentDateandTime +".wav";
            //api.fileName = "Android_"+lessonNo+"_"+index+".wav";
            api.file = file;
            api.token = getRegistrationToken();
            api.startAPI(this);
            api.setCheckAudioOnResult(this);
        } else {
            // No connect internet
            showAlertInfo(this, " ",getString(R.string.mess_no_network), false);
        }
    }

    /**
     * startActivity
     *
     * @param activity
     * @param userId
     * @param index
     * @param jSonString
     * @param lessonNo
     */
    public static void startActivity(Activity activity, String userId, String index, String jSonString, String lessonNo, String modeLanguage) {
        Intent intent = new Intent(activity.getBaseContext(), DialogRecorderActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("index", index);
        intent.putExtra("jSonString", jSonString);
        intent.putExtra("lessonNo", lessonNo);
        intent.putExtra("modeLanguage", modeLanguage);
        activity.startActivityForResult(intent, DIALOG_RECORD_REQUEST);
    }
}
