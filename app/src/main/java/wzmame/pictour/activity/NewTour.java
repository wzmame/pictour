package wzmame.pictour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import wzmame.pictour.R;

public class NewTour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tour);
    }

    public void onDoneAction(View view) {
        EditText etName = (EditText) findViewById(R.id.etName);
        String name = etName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Tour name is required", Toast.LENGTH_LONG).show();
            return;
        }

        // TODO (https://github.com/wzmame/pictour/issues/20) Store the new tour in the backend.

        finish();
    }

    public void onAddLocationAction(View view) {
        Intent addLocationIntent = new Intent(this, NewLocation.class);
        startActivity(addLocationIntent);
    }
}
