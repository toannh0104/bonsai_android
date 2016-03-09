package runsystem.net.global_hr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Date;

import runsystem.net.global_hr.common.Constant;
import runsystem.net.global_hr.common.LogoutService;
import runsystem.net.global_hr.network.NetworkUtils;


public class TopActivity extends BaseActivity {
    public static Activity activity = null;
    private LogoutService service = null;
    private Button btn_nihon_basic, btn_nihon_advance,
            btn_security_basic, btn_security_advance,
            btn_compliance, btn_security, btn_harassment,
            btn_moral, btn_governance, btn_lab_provisions,
            btn_travel, btn_jp_life, btn_email_sns, btn_video;
    private ImageButton btnLogout;
    private String learningJpBasic = "";
    private String learningSafetyBasic = "";
    private String learningJpAdvance = "";
    private String learningSafetyAdvance = "";

    private String learningCompliance = "";
    private String learningSecurity = "";
    private String learningHarassment = "";
    private String learningMoral = "";

    private String learningGovernance = "";
    private String learningLaborProvisions = "";
    private String learningVoyage = "";
    private String learningJpLife = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_top);
        super.onCreate(savedInstanceState);
        activity = this;
    }

    @Override
    protected void initView() {
        super.initView();
        btnLogout = (ImageButton) findViewById(R.id.btn_logout);
        btn_nihon_basic = (Button)findViewById(R.id.top_btn_nihon_basic);
        btn_nihon_advance = (Button) findViewById(R.id.top_btn_nihon_advance);
        btn_security_basic = (Button) findViewById(R.id.top_btn_security_basic);
        btn_security_advance = (Button) findViewById(R.id.top_btn_security_advance);

        btn_compliance =(Button) findViewById(R.id.top_btn_compliance);
        btn_security = (Button) findViewById(R.id.top_btn_security);
        btn_harassment = (Button) findViewById(R.id.top_btn_harassment);
        btn_moral = (Button) findViewById(R.id.top_btn_moral);
        btn_governance = (Button) findViewById(R.id.top_btn_governance);

        btn_lab_provisions = (Button) findViewById(R.id.top_btn_lab_provisions);
        btn_travel = (Button) findViewById(R.id.top_btn_travel);
        btn_jp_life = (Button) findViewById(R.id.top_btn_travel);

        btn_email_sns = (Button) findViewById(R.id.top_btn_email_sns);
        btn_video = (Button) findViewById(R.id.top_btn_video);

        btnLogout.setOnClickListener(this);
        btn_nihon_basic.setOnClickListener(this);
        btn_nihon_advance.setOnClickListener(this);
        btn_security_basic.setOnClickListener(this);
        btn_security_advance.setOnClickListener(this);

        btn_compliance.setOnClickListener(this);
        btn_security.setOnClickListener(this);
        btn_harassment.setOnClickListener(this);
        btn_moral.setOnClickListener(this);

        btn_governance.setOnClickListener(this);
        btn_lab_provisions.setOnClickListener(this);
        btn_travel.setOnClickListener(this);
        btn_jp_life.setOnClickListener(this);

        btn_email_sns.setOnClickListener(this);
        btn_video.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            learningJpBasic = extras.getString("learningJpBasic").trim();
            learningSafetyBasic = extras.getString("learningSafetyBasic").trim();
            learningJpAdvance = extras.getString("learningJpAdvance").trim();
            learningSafetyAdvance = extras.getString("learningSafetyAdvance").trim();

            learningCompliance = extras.getString("learningCompliance").trim();
            learningSecurity = extras.getString("learningSecurity").trim();
            learningHarassment = extras.getString("learningHarassment").trim();
            learningMoral = extras.getString("learningMoral").trim();

            learningGovernance = extras.getString("learningGovernance").trim();
            learningLaborProvisions = extras.getString("learningLaborProvisions").trim();
            learningVoyage = extras.getString("learningVoyage").trim();
            learningJpLife = extras.getString("learningJpLife").trim();

            // learningJpBasic
            if (learningJpBasic.equals("") || learningJpBasic.equalsIgnoreCase("NULL")){
                // Disable
                btn_nihon_basic.setClickable(false);
            } else {
                // Enable
                btn_nihon_basic.setClickable(true);
            }

            // learningSafetyBasic
            if (learningSafetyBasic.equals("") || learningSafetyBasic.equalsIgnoreCase("NULL")){
                // Disable
                btn_security_basic.setClickable(false);
            } else {
                // Enable
                btn_security_basic.setClickable(true);
            }

            // learningJpAdvance
            if (learningJpAdvance.equals("") || learningJpAdvance.equalsIgnoreCase("NULL")){
                // Disable
                btn_nihon_advance.setClickable(false);
            } else {
                // Enable
                btn_nihon_advance.setClickable(true);
            }

            // learningSafetyAdvance
            if (learningSafetyAdvance.equals("") || learningSafetyAdvance.equalsIgnoreCase("NULL")){
                // Disable
                btn_security_advance.setClickable(false);
            } else {
                // Enable
                btn_security_advance.setClickable(true);
            }

            // learningCompliance
            if (learningCompliance.equals("") || learningCompliance.equalsIgnoreCase("NULL")){
                // Disable
                btn_compliance.setClickable(false);
            } else {
                // Enable
                btn_compliance.setClickable(true);
            }

            // learningSecurity
            if (learningSecurity.equals("") || learningSecurity.equalsIgnoreCase("NULL")){
                // Disable
                btn_security.setClickable(false);
            } else {
                // Enable
                btn_security.setClickable(true);
            }

            // learningHarassment
            if (learningHarassment.equals("") || learningHarassment.equalsIgnoreCase("NULL")){
                // Disable
                btn_harassment.setClickable(false);
            } else {
                // Enable
                btn_harassment.setClickable(true);
            }

            // learningMoral
            if (learningMoral.equals("") || learningMoral.equalsIgnoreCase("NULL")){
                // Disable
                btn_moral.setClickable(false);
            } else {
                // Enable
                btn_moral.setClickable(true);
            }

            // learningGovernance
            if (learningGovernance.equals("") || learningGovernance.equalsIgnoreCase("NULL")){
                // Disable
                btn_governance.setClickable(false);
            } else {
                // Enable
                btn_governance.setClickable(true);
            }

            // learningLaborProvisions
            if (learningLaborProvisions.equals("") || learningLaborProvisions.equalsIgnoreCase("NULL")){
                // Disable
                btn_lab_provisions.setClickable(false);
            } else {
                // Enable
                btn_lab_provisions.setClickable(true);
            }

            // learningVoyage
            if (learningVoyage.equals("") || learningVoyage.equalsIgnoreCase("NULL")){
                // Disable
                btn_travel.setClickable(false);
            } else {
                // Enable
                btn_travel.setClickable(true);
            }

            // learningJpLife
            if (learningJpLife.equals("") || learningJpLife.equalsIgnoreCase("NULL")){
                // Disable
                btn_jp_life.setClickable(false);
            } else {
                // Enable
                btn_jp_life.setClickable(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (service == null)
            service = new LogoutService(this, this);
        service.getTimer().cancel();
        service.getTimer().start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if( ev.getAction() == MotionEvent.ACTION_UP){
            if (service != null){
                service.getTimer().cancel();
                service.getTimer().start();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (service != null){
            service.getTimer().cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (isFinishing()) return;
        tryFinish();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_logout:
                processLogout(this);
                break;
            case R.id.top_btn_nihon_basic:
                LearnActivity.startActivity(this, learningJpBasic);
                break;
            case R.id.top_btn_security_basic:
                LearnActivity.startActivity(this, learningSafetyBasic);
                break;
            case R.id.top_btn_nihon_advance:
                LearnActivity.startActivity(this, learningJpAdvance);
                break;
            case R.id.top_btn_security_advance:
                LearnActivity.startActivity(this, learningSafetyAdvance);
                break;
            case R.id.top_btn_compliance:
                LearnActivity.startActivity(this, learningCompliance);
                break;
            case R.id.top_btn_security:
                LearnActivity.startActivity(this, learningSecurity);
                break;
            case R.id.top_btn_harassment:
                LearnActivity.startActivity(this, learningHarassment);
                break;
            case R.id.top_btn_moral:
                LearnActivity.startActivity(this, learningMoral);
                break;
            case R.id.top_btn_governance:
                LearnActivity.startActivity(this, learningGovernance);
                break;
            case R.id.top_btn_lab_provisions:
                LearnActivity.startActivity(this, learningLaborProvisions);
                break;
            case R.id.top_btn_travel:
                LearnActivity.startActivity(this, learningVoyage);
                break;
            case R.id.top_btn_jp_life:
                LearnActivity.startActivity(this, learningJpLife);
                break;
            case R.id.top_btn_email_sns:
                processEmail();
                break;
            case R.id.top_btn_video:
                processVideo();
                break;
        }
    }

    /**
     * processVideos
     */
    private void processVideo(){
        showDialogSkypeCall();
    }

    /**
     * processEmail
     */
    private void processEmail(){
        // Check gmail install
        if (!isClientInstalled(this, Constant.PACKAGE_NAME_GMAIL)){
            goToMarket(this, Constant.PACKAGE_NAME_GMAIL);
            return;
        }
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        //sendIntent.setData(Uri.parse("thanhluan77it@gmail.com"));
        sendIntent.setClassName( Constant.PACKAGE_NAME_GMAIL, "com.google.android.gm.ComposeActivityGmail");
        //sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
        //sendIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
        //sendIntent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)");
        startActivity(sendIntent);
    }

    /*=================== METHOD ===================*/
    /**
     * startActivity
     *
     * @param activity
     * @param userId
     * @param index
     * @param jSonString
     * @param lessonNo
     */
    public static void startActivity(Activity activity, String learningJpBasic, String learningSafetyBasic, String learningJpAdvance, String learningSafetyAdvance,
                                     String learningCompliance, String learningSecurity, String learningHarassment, String learningMoral,
                                     String learningGovernance, String learningLaborProvisions, String learningVoyage, String learningJpLife) {
        Intent intent = new Intent(activity.getBaseContext(), TopActivity.class);
        intent.putExtra("learningJpBasic", learningJpBasic);
        intent.putExtra("learningSafetyBasic", learningSafetyBasic);
        intent.putExtra("learningJpAdvance", learningJpAdvance);
        intent.putExtra("learningSafetyAdvance", learningSafetyAdvance);

        intent.putExtra("learningCompliance", learningCompliance);
        intent.putExtra("learningSecurity", learningSecurity);
        intent.putExtra("learningHarassment", learningHarassment);
        intent.putExtra("learningMoral", learningMoral);

        intent.putExtra("learningGovernance", learningGovernance);
        intent.putExtra("learningLaborProvisions", learningLaborProvisions);
        intent.putExtra("learningVoyage", learningVoyage);
        intent.putExtra("learningJpLife", learningJpLife);
        activity.startActivity(intent);
    }
}
