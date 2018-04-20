package pl.braincode.heimdall.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.braincode.heimdall.R;
import pl.braincode.heimdall.models.ResultItem;
import pl.braincode.heimdall.services.BifrostAPI;
import pl.braincode.heimdall.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  SurfaceHolder.Callback {
    private static final int MY_PERMISSIONS_REQUEST_CAM = 1995;
    private static final String TAG = MainActivity.class.getSimpleName();
    Camera camera;
    SurfaceView surfaceView;
    FloatingActionButton floatingActionButton;
    SurfaceHolder surfaceHolder;
    BifrostAPI bifrostUserAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surfaceView);
        floatingActionButton = findViewById(R.id.button);


        bifrostUserAPI = ServiceGenerator.createService(BifrostAPI.class);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null, null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                            RequestBody body = RequestBody.create(MediaType.parse("image/raw"), bytes);
                            Call<List<ResultItem>> call = bifrostUserAPI.sendImage(body);
                            call.enqueue(new Callback<List<ResultItem>>() {
                                @Override
                                public void onResponse(Call<List<ResultItem>> call, Response<List<ResultItem>> response) {
                                    int code = response.code();
                                    if (code == 200) {
                                        List<ResultItem> results = response.body();
                                        Log.d(TAG, "Did work: " + String.valueOf(code));
                                        Log.d(TAG, "Result[0] " + results.get(0).title);
                                    } else {
                                        Log.d(TAG, "Did not work: " + String.valueOf(code));
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ResultItem>> call, Throwable t) {
                                    Log.d(TAG, "Failure");
                                }
                            });
                        Intent intent = new Intent( getBaseContext() , ResultActivity.class);
                        startActivity(intent);
                        }
                });
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAM);
        } else {
            cameraInit();
        }
    }

    private void cameraInit(){
        camera = Camera.open();
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setCameraDisplayOrientation(this, 0, camera);
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {

        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();

        android.hardware.Camera.getCameraInfo(cameraId, info);

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAM: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraInit();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
