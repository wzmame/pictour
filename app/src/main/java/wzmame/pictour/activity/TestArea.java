package wzmame.pictour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseObject;

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
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }

    public void onNewLocationClick(View view) {
        Intent newLocationIntent = new Intent(this, NewLocation.class);
        startActivity(newLocationIntent);
    }
}
