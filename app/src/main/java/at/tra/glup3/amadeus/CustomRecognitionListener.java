package at.tra.glup3.amadeus;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomRecognitionListener implements RecognitionListener {

    private final String TAG = "VoiceListener";

    private Activity activity;

    public CustomRecognitionListener() {

    }

    public CustomRecognitionListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d(TAG, "On Ready for Speech");
        Toast.makeText(activity.getBaseContext(), "Start Recording", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "On Beginning of Speech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d(TAG, "On Rms Changed");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d(TAG, "On Buffer Received");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "On End of Speech");
    }

    @Override
    public void onError(int error) {
        Log.d(TAG, "Error: " + error);
        //TODO Do something on error
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        // Stop when no Speech Input exists
        if(data == null) {
            return;
        }

        TextView tv = activity.findViewById(R.id.anzeige_Text);
        tv.setText(data.get(0).toLowerCase());
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d(TAG, "On Partial Results");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d(TAG, "On Event: " + eventType);
    }
}
