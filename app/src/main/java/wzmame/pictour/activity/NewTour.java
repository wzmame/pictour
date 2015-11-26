package wzmame.pictour.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import wzmame.pictour.R;

public class NewTour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tour);
    }

    public void onDoneAction(View view) {
        Toast.makeText(this, "Done button!", Toast.LENGTH_SHORT).show();
    }

    public void onAddLocationAction(View view) {
        Toast.makeText(this, "Add Location button!", Toast.LENGTH_SHORT).show();
    }
}
