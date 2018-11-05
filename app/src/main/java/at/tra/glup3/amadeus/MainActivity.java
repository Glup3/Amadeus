package at.tra.glup3.amadeus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SpeechRecognizer mySpeechRecognizer;
    private String recognitionLanguage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        recognitionLanguage = mySettings.getString("pref_recognition_language_key", "ja-JP");



        mySpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mySpeechRecognizer.setRecognitionListener(new CustomRecognitionListener(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 11130);
        }

    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageContextWrapper.wrap(context));
    }


    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, recognitionLanguage);

        if(intent.resolveActivity(getPackageManager()) != null) {
            mySpeechRecognizer.startListening(intent);
        }
        else {
            Toast.makeText(this, "Device Doesn't Support Speech Input", Toast.LENGTH_SHORT).show();
        }

    }

}
