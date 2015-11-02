package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.Response;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.FilePath;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/27/2015.
 */
public class ActivityAddBill extends BaseActivity implements MaterialDialog.SingleButtonCallback {

    private final int SELECT_PHOTO = 1;

    @Bind(R.id.ll_addManually)
    public LinearLayout ll_addManually;

    @Bind(R.id.ll_photo)
    public LinearLayout ll_photo;

    @Bind(R.id.ll_gallery)
    public LinearLayout ll_gallery;

    private static Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_addbill);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_addManually)
    public void ShowAdd() {
        finish();
        Util.startNextActivity(this, ActivityAddManualBill.class);
    }

    @OnClick(R.id.ll_photo)
    public void ShowPhoto() {
        finish();
        Util.startNextActivity(this, ActivityPhoto.class);
    }

    @OnClick(R.id.ll_gallery)
    public void ShowGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }


    @OnClick(R.id.back)
    public void Back() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    Util.ShowDialog(ActivityAddBill.this, "Image Upload", "Are you sure you want to upload this photo", "OK", "CANCEL", ActivityAddBill.this);
                }
                break;
            default:
                break;
        }

        return;
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                if (Network.isConnected(this)) {

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
                                    uploadFile(dialogInterface);
                                }
                            }).show();

                } else {
                    materialDialog.dismiss();
                    Util.ShowNeutralDialog(this, "fonebaayd", "No Internet Connection", "OK", this);
                }
                return;
            default:
                materialDialog.dismiss();
                break;
        }
    }

    private void uploadFile(final DialogInterface dialogInterface) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), FilePath.getPath(getApplicationContext(), imageUri));
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), ModelLogin.getUserInfo().getApp_id());
        RequestBody device = RequestBody.create(MediaType.parse("text/plain"), Util.getDeviceType());
        RequestBody device_guid = RequestBody.create(MediaType.parse("text/plain"), Util.getGUID(this));

        Call<Response> responseCall = RestClient.get().upload(fileBody, createdBy, device_guid, device);
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {

                Log.i("ACTIVITY PAYMENT METHOD", response.message());

                if (response.isSuccess()) {
                    if (response.body().getMessage().toLowerCase().equals("success")) {
                        Intent intent = new Intent(ActivityAddBill.this, ActivityPhoto.class);
                        intent.putExtra("photo", FilePath.getPath(getApplicationContext(), imageUri));
                        finish();
                        startActivity(intent);
                    }
                } else {
                    Util.ShowNeutralDialog(ActivityAddBill.this, "fonebayad", "failed to connect to server!", "OK", ActivityAddBill.this);
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
}
