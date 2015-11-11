package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.Response;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/29/2015.
 */
public class ActivityPhoto extends BaseActivity implements SurfaceHolder.Callback {


    @Bind(R.id.rl_bill_added)
    public RelativeLayout rl_bill_added;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.surface_camera)
    public SurfaceView surface_camera;

    @Bind(R.id.btn_capture)
    public ImageView btn_capture;

    @Bind(R.id.img_captured)
    public ImageView img_captured;

    @Bind(R.id.btnRetake)
    public Button btnRetake;

    @Bind(R.id.btnUpload)
    public Button btnUpload;

    private SurfaceHolder prSurfaceHolder;
    private Camera prCamera;

    private static String fileName;
    private static Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        prSurfaceHolder = surface_camera.getHolder();
        prSurfaceHolder.addCallback(this);
        prSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        int isVisible = getIntent().getStringExtra("photo") == null ? View.INVISIBLE : View.VISIBLE;
        int isButtonVisible = getIntent().getStringExtra("photo") == null ? View.VISIBLE : View.INVISIBLE;

        rl_bill_added.setVisibility(isVisible);
        img_captured.setVisibility(isVisible);

        btn_capture.setVisibility(isButtonVisible);
        surface_camera.setVisibility(isButtonVisible);

        if (getIntent().getStringExtra("photo") != null) {
            try {
                Bitmap myBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("photo"));
                ExifInterface exif = new ExifInterface(getIntent().getStringExtra("photo"));
                int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int rotationInDegrees = exifToDegrees(rotation);
                int deg = rotationInDegrees;
                Matrix matrix = new Matrix();
                if (rotation != 0f) {
                    matrix.preRotate(rotationInDegrees);
                    myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
                }

                img_captured.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, Util.getScreenWidth(ActivityPhoto.this), Util.getScreenHeight(ActivityPhoto.this), false));
                YoYo.with(Techniques.Landing).duration(1200).playOn(findViewById(R.id.img_stamp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    @Override
    public void surfaceChanged(SurfaceHolder _holder, int _format, int _width, int _height) {
        Camera.Parameters lParam = prCamera.getParameters();
        lParam.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        prCamera.setParameters(lParam);
        try {
            prCamera.setPreviewDisplay(_holder);
            prCamera.setDisplayOrientation(90);
            prCamera.startPreview();
        } catch (IOException _le) {
            _le.printStackTrace();
        }
    }

    @OnClick(R.id.btn_capture)
    public void CaptureImage() {
        prCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                btn_capture.setVisibility(View.INVISIBLE);
                btnRetake.setVisibility(View.VISIBLE);
                btnUpload.setVisibility(View.VISIBLE);

                fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString() + ".jpg";

                File pictureFile = Util.getOutputMediaFile();
                if (pictureFile == null) {
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();

                    Display display = getWindowManager().getDefaultDisplay();
                    int rotation = 0;
                    switch (display.getRotation()) {
                        case Surface.ROTATION_0: // This is display orientation
                            rotation = 90;
                            break;
                        case Surface.ROTATION_90:
                            rotation = 0;
                            break;
                        case Surface.ROTATION_180:
                            rotation = 270;
                            break;
                        case Surface.ROTATION_270:
                            rotation = 180;
                            break;
                    }

                    bitmap = toBitmap(data);
                    bitmap = rotate(bitmap, rotation);
                    img_captured.setImageBitmap(Bitmap.createScaledBitmap(bitmap, Util.getScreenWidth(ActivityPhoto.this), Util.getScreenHeight(ActivityPhoto.this), false));
                    img_captured.setVisibility(View.VISIBLE);
                    surface_camera.setVisibility(View.INVISIBLE);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void ToUpload(Bitmap bitmap) {
        if (bitmap != null) {
            File file = Util.getOutputMediaFile();
            if (!file.isDirectory()) {
                file.mkdir();
            }
            file = new File(Util.getOutputMediaFile() + fileName);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public Bitmap toBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public Bitmap rotate(Bitmap in, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(in, 0, 0, in.getWidth(), in.getHeight(), matrix, true);
    }

    @OnClick(R.id.btnRetake)
    public void ReTakePicture() {
        btn_capture.setVisibility(View.VISIBLE);
        btnRetake.setVisibility(View.INVISIBLE);
        btnUpload.setVisibility(View.INVISIBLE);

        img_captured.setVisibility(View.INVISIBLE);
        surface_camera.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btnUpload)
    public void UploadPicture() {

        btn_capture.setVisibility(View.INVISIBLE);
        btnRetake.setVisibility(View.INVISIBLE);
        btnUpload.setVisibility(View.INVISIBLE);

        new MaterialDialog.Builder(this)
                .content(R.string.action_wait)
                .contentGravity(GravityEnum.CENTER)
                .theme(Theme.DARK)
                .widgetColor(Color.WHITE)
                .progressIndeterminateStyle(false)
                .progress(true, 0)
                .cancelable(false)
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        ToUpload(bitmap);
                        uploadFile(dialogInterface);
                    }
                }).show();
    }

    private void uploadFile(final DialogInterface dialogInterface) {

        File file = new File(Util.getOutputMediaFile() + fileName);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file.getPath());
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), ModelLogin.getUserInfo().getApp_id());
        RequestBody device = RequestBody.create(MediaType.parse("text/plain"), Util.getDeviceType());
        RequestBody device_guid = RequestBody.create(MediaType.parse("text/plain"), Util.getGUID(this));

        Call<Response> responseCall = RestClient.get().upload(fileBody, createdBy, device_guid, device);
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {

                Log.i("ACTIVITY PAYMENT METHOD", response.message());

                if (response.isSuccess()) {
                    if (response.code() == 200) {
                        rl_bill_added.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.Landing).duration(1200).playOn(findViewById(R.id.img_stamp));
                    }
                } else {
                    //Util.ShowMessage(coordinatorLayout, "Process Failed please try again");
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                //.ShowMessage(coordinatorLayout, "Failed to connect to server!");
            }
        });
    }

    @OnClick(R.id.ll_dashboard)
    public void GotoDashboard() {
        finish();
        Util.startNextActivity(this, ActivityDashboard.class);
    }

    @OnClick(R.id.ll_bill)
    public void GotoAddBill() {
        finish();
        Util.startNextActivity(this, ActivityAddBill.class);
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }

    @OnClick(R.id.back)
    public void Back() {
        GotoAddBill();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prCamera = Camera.open();
        if (prCamera == null) {
            Toast.makeText(this.getApplicationContext(), "Camera is not available!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        prCamera.stopPreview();
        prCamera.release();
        prCamera = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
            finish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        GotoAddBill();
    }
}