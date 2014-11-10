package scottso.assist911;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainMenuActivity extends SimKidsActivity implements View.OnClickListener{

    private Button mPractiseButton;
    private Button mVideosButton;
    private Button mSettingsButton;
    private Button mReportButton;

    final String[] videoArray = {"flame","smoke","passed","car","drowning","a","b"};

    public static int TIMES_OPENED;
    public static int CURRENT_TRY_SCORE;
    public static boolean IS_REMOVE_TEXT_PROMPT = false;
    public static boolean IS_REMOVE_AUDIO_PROMPT = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainmenu);

        mPractiseButton = (Button) this.findViewById(R.id.practice_button);
        mPractiseButton.setOnClickListener(this);

        mVideosButton = (Button) this.findViewById(R.id.videos_button);
        mVideosButton.setOnClickListener(this);

        mSettingsButton = (Button) this.findViewById(R.id.profile_button);
        mSettingsButton.setOnClickListener(this);

        mReportButton = (Button) this.findViewById(R.id.report_button);
        mReportButton.setOnClickListener(this);

        TIMES_OPENED = LoginActivity.PREF.getInt(LoginActivity.TIMES_OPENED, 0);
        IS_REMOVE_TEXT_PROMPT = LoginActivity.PREF.getBoolean(LoginActivity.REMOVE_TEXT_PROMPT, false);
        IS_REMOVE_AUDIO_PROMPT = LoginActivity.PREF.getBoolean(LoginActivity.REMOVE_AUDIO_PROMPT, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.practice_button:
                CURRENT_TRY_SCORE = 0;
                TIMES_OPENED++;
                LoginActivity.EDITOR.putInt(LoginActivity.TIMES_OPENED, TIMES_OPENED);
                LoginActivity.EDITOR.commit();

                System.out.println(LoginActivity.PREF.getInt(LoginActivity.TIMES_OPENED, 0));

                int index = new Random().nextInt(videoArray.length);
                VideosActivity.VIDEO_NAME = (videoArray[index]);

                if (VideosActivity.VIDEO_NAME.equals("flame"))  {
                    VideosActivity.EMERGENCY = true;
                    VideosActivity.FIRE = true;
                } else if (VideosActivity.VIDEO_NAME.equals("car")) {
                    VideosActivity.EMERGENCY = true;
                    VideosActivity.POLICE = true;
                } else if (VideosActivity.VIDEO_NAME.equals("smoke")) {
                    VideosActivity.EMERGENCY = true;
                    VideosActivity.FIRE = true;
                } else if (VideosActivity.VIDEO_NAME.equals("passed")) {
                    VideosActivity.EMERGENCY = true;
                    VideosActivity.AMBULANCE = true;
                } else if (VideosActivity.VIDEO_NAME.equals("drowning")) {
                    VideosActivity.EMERGENCY = true;
                    VideosActivity.AMBULANCE = true;
                } else if (VideosActivity.VIDEO_NAME.equals("a")) {
                    VideosActivity.EMERGENCY = false;
                } else if (VideosActivity.VIDEO_NAME.equals("b")) {
                    VideosActivity.EMERGENCY = false;
                }
                goToVideoPlayer();
                break;

            case R.id.videos_button:
                goToVideos();
                System.out.println(LoginActivity.PREF.getInt(LoginActivity.TIMES_OPENED, 0));
                break;
            case R.id.profile_button:
                goToSettings();
                break;
            case R.id.report_button:
                goToReport();
                break;
        }
    }

    public void goToVideos() {
        Intent video = new Intent(this, VideosActivity.class);
        startActivity(video);
    }

    public void goToSettings() {
        Intent settings = new Intent(this, ProfileActivity.class);
        startActivity(settings);
        finish();
    }

    public void goToReport() {
        Intent report = new Intent(this, ReportActivity.class);
        startActivity(report);
    }

    public void goToVideoPlayer() {
        Intent videoPlayer = new Intent(this, PracticePlayVideoActivity.class);
        startActivity(videoPlayer);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override public void onBackPressed(){ moveTaskToBack(true); }
}