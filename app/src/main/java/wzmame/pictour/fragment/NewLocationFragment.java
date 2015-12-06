package wzmame.pictour.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.SaveCallback;

import wzmame.pictour.R;
import wzmame.pictour.activity.NewLocation;
import wzmame.pictour.model.Location;

public class NewLocationFragment extends Fragment {
    private final static String TAG = "NewLocationFragment";

    private ParseImageView ivPreview;
    private EditText etName;
    private EditText etDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_location, container, false);

        etName = (EditText) v.findViewById(R.id.etName);
        etDescription = (EditText) v.findViewById(R.id.etDescription);
        setUpTakePictureButton(v);
        setUpDoneButton(v);
        setUpCancelButton(v);

        ivPreview = (ParseImageView) v.findViewById(R.id.ivPreview);
        ivPreview.setVisibility(View.INVISIBLE);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        NewLocation newLocationActivity = (NewLocation) getActivity();
        ParseFile pictureFile = newLocationActivity.getLocation().getPicture();

        if (pictureFile != null) {
            ivPreview.setParseFile(pictureFile);
            ivPreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    ivPreview.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void startCamera() {
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragmentContainer, cameraFragment);
        tx.addToBackStack("NewLocationFragment");
        tx.commit();
    }

    private void setUpTakePictureButton(View view) {
        Button btnTakePicture = (Button) view.findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                startCamera();
            }
        });
    }

    private void setUpDoneButton(View view) {
        Button btnDone = (Button) view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasRequiredFields = true;

                NewLocation newLocationActivity = (NewLocation) getActivity();
                Location location = newLocationActivity.getLocation();
                if (location.getLatitude() == null) {
                    /*
                     Up to this point we've been unable to grab gps location so we'll try one last time
                     before showing a message to the user to turn on the gps.

                     If the user is only just now turned the gps on, the startLocationUpdates call
                     in the NewLocation activity will not have done anything, so we need to call it
                     again here, just in case.
                    */

                    GoogleApiClient googleApiClient = newLocationActivity.getGoogleApiClient();
                    android.location.Location geoLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    if (geoLocation != null) {
                        location.setLatitude(geoLocation.getLatitude());
                        location.setLongitude(geoLocation.getLongitude());

                        newLocationActivity.startLocationUpdates();
                    } else {
                        Toast.makeText(getContext(), "Can't get location, please turn on gps", Toast.LENGTH_SHORT).show();
                        hasRequiredFields = false;
                    }
                }

                String name = etName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Please add a name to the location", Toast.LENGTH_SHORT).show();
                    hasRequiredFields = false;
                }

                if (location.getPicture() == null) {
                    Toast.makeText(getContext(), "Take a picture first!", Toast.LENGTH_SHORT).show();
                    hasRequiredFields = false;
                }

                if (!hasRequiredFields) {
                    return;
                }

                String description = etDescription.getText().toString();

                location.setName(name);
                location.setDescription(description);
                location.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(getActivity(), "Couldn't save image", Toast.LENGTH_LONG).show();
                            return;
                        }

                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }
                });
            }
        });
    }

    private void setUpCancelButton(View view) {
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });
    }
}
