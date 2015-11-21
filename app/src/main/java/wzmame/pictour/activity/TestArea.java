package wzmame.pictour.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import wzmame.pictour.ParseConfig;
import wzmame.pictour.R;

public class TestArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_area);
    }

    public void onTestToastClick(View view) {
        Toast.makeText(this, "A toasty test", Toast.LENGTH_SHORT).show();
    }

    public void onTestParseClick(View view) {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ParseConfig.APPLICATION_ID, ParseConfig.CLIENT_ID);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
