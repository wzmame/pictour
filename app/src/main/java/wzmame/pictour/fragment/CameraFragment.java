package wzmame.pictour.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import wzmame.pictour.R;
import wzmame.pictour.activity.NewLocation;
import wzmame.pictour.model.Location;

public class CameraFragment extends Fragment {
    public static final String TAG = "CameraFragment";

    private static final String PICTURE_FILENAME = "location-picture.jpg";
    private static final int IMAGE_SAVE_QUALITY = 100;
    private static final int SCALED_PICTURE_WIDTH = 400;

    private Camera camera;
    private ParseFile pictureFile;
    private ImageButton btnTakePicture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        btnTakePicture = (ImageButton) view.findViewById(R.id.btnTakePicture);

        if (camera == null) {
            try {
                camera = Camera.open();
                btnTakePicture.setEnabled(true);
            } catch (Exception e) {
                Log.e(TAG, "Couldn't get camera: " + e.toString());
                btnTakePicture.setEnabled(false);
                Toast.makeText(getActivity(), "No camera detected", Toast.LENGTH_LONG).show();
            }
        }

        btnTakePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (camera == null) {
                    return;
                }

                Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() { }
                };

                Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        saveScaledPicture(data);
                    }
                };

                camera.takePicture(shutterCallback, null, jpegPictureCallback);
            }
        });

        SurfaceView svCameraPreview = (SurfaceView) view.findViewById(R.id.svCameraPreview);
        SurfaceHolder holder = svCameraPreview.getHolder();
        holder.addCallback(new Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (camera == null) {
                        return;
                    }

                    camera.setDisplayOrientation(90);
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Error setting up camera preview");
                    Toast.makeText(getActivity(), "Unable to get camera preview", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { }
        });

        return view;
    }

    private void saveScaledPicture(byte[] data) {
        Bitmap locationPic = BitmapFactory.decodeByteArray(data, 0, data.length);
        double heightWidthRatio = (double) locationPic.getHeight() / locationPic.getWidth();

        int newWidthPx = SCALED_PICTURE_WIDTH;
        int newHeightPx = (int) (newWidthPx * heightWidthRatio);

        Bitmap scaledLocationPic = Bitmap.createScaledBitmap(locationPic, newWidthPx, newHeightPx, false);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap rotatedScaledLocationPic = Bitmap.createBitmap(
                scaledLocationPic,
                0,
                0,
                scaledLocationPic.getWidth(),
                scaledLocationPic.getHeight(),
                matrix,
                true);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        rotatedScaledLocationPic.compress(Bitmap.CompressFormat.JPEG, IMAGE_SAVE_QUALITY, outStream);

        byte[] scaledData = outStream.toByteArray();

        pictureFile = new ParseFile(PICTURE_FILENAME, scaledData);
        pictureFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error saving scaled picture: " + e.toString());
                    Toast.makeText(getActivity(), "Couldn't save picture", Toast.LENGTH_LONG).show();
                    return;
                }

                addPictureToLocationAndReturn(pictureFile);
            }
        });
    }

    private void addPictureToLocationAndReturn(ParseFile pictureFile) {
        NewLocation newLocationActivity = (NewLocation) getActivity();
        Location location = newLocationActivity.getLocation();
        location.setPicture(pictureFile);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack("NewLocationFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (camera == null) {
            try {
                camera = Camera.open();
                btnTakePicture.setEnabled(true);
            } catch (Exception e) {
                Log.e(TAG, "No camera detected on resume: " + e.toString());
                btnTakePicture.setEnabled(false);
                Toast.makeText(getActivity(), "No camera detected", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }

        super.onPause();
    }
}
