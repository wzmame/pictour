package wzmame.pictour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import wzmame.pictour.R;

public class NewTour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tour);
        Button btnAddLocation = (Button) findViewById(R.id.btnAddLocation);
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewTour.this, NewLocation.class);
                startActivity(i);
            }
        });
    }

    public void onDoneAction(View view) {
        Toast.makeText(this, "Done button!", Toast.LENGTH_SHORT).show();

        EditText etName = (EditText) findViewById(R.id.etName);
        String name = etName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Tour name is required", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void onAddLocationAction(View view) {
        Toast.makeText(this, "Add LocationView button!", Toast.LENGTH_SHORT).show();
    }
}
