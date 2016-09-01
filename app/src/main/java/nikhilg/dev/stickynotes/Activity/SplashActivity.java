package nikhilg.dev.stickynotes.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import nikhilg.dev.stickynotes.R;

public class SplashActivity extends AppCompatActivity {

    private TextView appName, continueWithGoogleText, skipText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        appName = (TextView) findViewById(R.id.appName);
        continueWithGoogleText = (TextView) findViewById(R.id.continueWithGoogleText);
        skipText = (TextView) findViewById(R.id.skipText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.ttf");
        appName.setTypeface(typeface);
        Typeface typefaceRoboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        continueWithGoogleText.setTypeface(typeface);
        skipText.setTypeface(typeface);
    }
}
