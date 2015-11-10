package estansaas.fonebayad.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import org.joda.time.DateTime;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.Response;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBillCategory;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelBillers;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.model.ModelStatus;
import estansaas.fonebayad.utils.Connection;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import estansaas.fonebayad.view.FormSelector;
import estansaas.fonebayad.view.FormView;
import nl.changer.polypicker.ImagePickerActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/19/2015.
 */
public class ActivityAddManualBill extends BaseActivity implements CalendarDatePickerDialogFragment.OnDateSetListener, View.OnFocusChangeListener, MaterialDialog.SingleButtonCallback {

    public static final String ACTIVITY_ADDBILL_VIEW = "ActivityAddManualBill";

    private static final String FRAG_TAG_DATE_PICKER = "fragment_due_date";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int FILE_CHOOSER_IMAGE_REQUEST_CODE = 200;
    private static final int SELECT_PHOTO = 1;

    private Uri fileUri; // file url to store image/video

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @Bind(R.id.ll_camera)
    public LinearLayout ll_camera;

    @Bind(R.id.ll_attach)
    public LinearLayout ll_attach;

    @Bind(R.id.txtBillCategory)
    public FormSelector<ModelBillCategory> categoryModelFormSelector;

    @Bind(R.id.txtBillerName)
    public FormSelector<ModelBillers> txtBillername;

    @Bind(R.id.txtAccount)
    public FormView txtAccount;

    @Bind(R.id.txtBillAmount)
    public FormView txtBillAmount;

    @Bind(R.id.txtStatus)
    public FormSelector<ModelStatus> txtStatus;

    @Bind(R.id.txtDuedate)
    public FormView txtDueDate;

    @Bind(R.id.btnCreate)
    public Button btnCreate;

    private List<ModelBillCategory> modelBillCategory;
    private List<ModelStatus> modelStatusList;
    private ModelStatus modelStatus;
    private ModelBillInformation billInfoModel;

    private String bill_amount = "0.00";
    private static String bill_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);
        ButterKnife.bind(this);

        billInfoModel = new ModelBillInformation();

        InitializeViews();

        modelBillCategory = new ArrayList<>();
        for (ModelBillCategory category : ModelBillCategory.getCategory()) {
            modelBillCategory.add(category);
        }

        categoryModelFormSelector.setItems(modelBillCategory);
        categoryModelFormSelector.setOnItemSelectedListener(new FormSelector.OnItemSelectedListener<ModelBillCategory>() {
            @Override
            public void onItemSelectedListener(ModelBillCategory item, int selectedIndex) {
                txtBillername.setText("");
                txtBillername.setItems(ModelBillers.getBillersByCategory(item.getCategory_id()));
                txtBillername.setText(ModelBillers.getBillersByCategory(item.getCategory_id()).get(0).getBiller_name());
                bill_id = ModelBillers.getBillersByCategory(item.getCategory_id()).get(0).getBiller_id();
                txtBillername.setOnItemSelectedListener(new FormSelector.OnItemSelectedListener<ModelBillers>() {
                    @Override
                    public void onItemSelectedListener(ModelBillers item, int selectedIndex) {
                        bill_id = item.getBiller_id();
                    }
                });
            }
        });

        modelStatus = new ModelStatus();
        modelStatusList = new ArrayList<>();
        for (String status : getResources().getStringArray(R.array.bill_status)) {
            modelStatusList.add(new ModelStatus(status));
        }

        txtStatus.setItems(modelStatusList);
        txtStatus.setOnItemSelectedListener(new FormSelector.OnItemSelectedListener<ModelStatus>() {
            @Override
            public void onItemSelectedListener(ModelStatus status, int selectedIndex) {
            }
        });

        txtBillAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6) {
                    bill_amount = v.getText().toString();
                    ConvertCurrency();
                }
                return false;
            }
        });

        //txtStatus.setError(null, Util.resizeDrawable(this, R.drawable.ic_keyboard_arrow_down_black_48dp));
        //txtBillername.setError(null, Util.resizeDrawable(this, R.drawable.ic_keyboard_arrow_down_black_48dp));
        //categoryModelFormSelector.setError(null, Util.resizeDrawable(this, R.drawable.ic_keyboard_arrow_down_black_48dp));

        txtAccount.setOnFocusChangeListener(this);
        txtDueDate.setOnFocusChangeListener(this);
        txtBillAmount.setOnFocusChangeListener(this);

    }

    private void InitializeViews() {
        categoryModelFormSelector.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtBillername.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtAccount.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtBillAmount.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtStatus.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtDueDate.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        btnCreate.setTypeface(Util.setTypeface(this, Util.ROBOTO));

        txtDueDate.setKeyListener(null);
    }

    private void ValidateForms() {
        if (ValidateString(categoryModelFormSelector)) {
            if (ValidateString(txtBillername)) {
                if (ValidateString(txtAccount)) {
                    if (ValidateString(txtBillAmount)) {
                        if (ValidateString(txtStatus)) {
                            if (ValidateString(txtDueDate)) {
                                ValiddateEntryBill();
                                return;
                            }
                        }
                    }
                }
            }
        }

        Util.ShowMessage(coordinatorLayout, "All fields are required!");
    }

    private boolean ValidateString(EditText editText) {

        if (editText.getText().toString() == null || editText.getText().toString().compareTo("") == 0) {
            editText.setError(null, Util.resizeDrawable(this, R.drawable.ic_action_ic_clear_white_48dp));
            return false;
        }
        editText.setError(null);
        return true;
    }

    private void ValiddateEntryBill() {
        if (Network.isConnected(this)) {
            Call<Response> responseCall = RestClient.get().validateBillEntry(bill_id, txtAccount.getText().toString(), "", ModelLogin.getUserInfo().getApp_id());
            responseCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                    Log.i("Bill Validate", response.code() + "");
                    if (response.isSuccess()) {
                        if (response.code() == 200) {
                            Log.i("response", response.body().getStatus());
                            if (response.body().getStatus().contains(Connection.STATUS_ACCEPTED)) {

                                billInfoModel.setBill_biller(bill_id);
                                billInfoModel.setBill_account_number(txtAccount.getText().toString());
                                billInfoModel.setBill_status(txtStatus.getText().toString());
                                billInfoModel.setBill_amount(txtBillAmount.getText().toString().replace("Php", "").replace(",", "").trim());
                                billInfoModel.setBill_due_date(txtDueDate.getText().toString());

                                Log.i("Bill Due Date", txtDueDate.getText().toString());

                                Intent intent = new Intent(ActivityAddManualBill.this, ActivityPaymentMethod.class);
                                intent.putExtra("PAYMENT_VIEW", ACTIVITY_ADDBILL_VIEW);
                                intent.putExtra("ModelBillInformation", billInfoModel);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                            } else {
                                Util.ShowNeutralDialog(ActivityAddManualBill.this, "fonebayad", "Sorry your bill is already added!", "OK", ActivityAddManualBill.this);
                            }
                        }
                        return;
                    }
                    Util.ShowDialog(ActivityAddManualBill.this, "fonebayad", "Unable to connect to server", "RETRY", "CANCEL", ActivityAddManualBill.this);
                }

                @Override
                public void onFailure(Throwable t) {
                    Util.ShowDialog(ActivityAddManualBill.this, "fonebayad", "Unable to connect to server", "RETRY", "CANCEL", ActivityAddManualBill.this);
                }
            });
        } else {
            Util.ShowNeutralDialog(this, "fonebayad", "Please connect to internet", "OK", this);
        }
    }

    @OnClick(R.id.btnCreate)
    public void CreateBill() {
        ValidateForms();
    }

    @OnClick(R.id.ll_camera)
    public void ShowCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = Util.getMediaFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    @OnClick(R.id.ll_attach)
    public void ShowFileChoose() {
        /*Intent intent = new Intent(this, ImagePickerActivity.class);
        Config config = new Config.Builder()
                .setTabBackgroundColor(R.color.app_color)    // set tab background color. Default white.
                .setTabSelectionIndicatorColor(R.color.app_color)
                .setCameraButtonColor(R.color.white)
                .setSelectionLimit(1)
                .build();
        ImagePickerActivity.setConfig(config);
        startActivityForResult(intent, FILE_CHOOSER_IMAGE_REQUEST_CODE);
        */
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private void ConvertCurrency() {
        txtBillAmount.setText("Php " + new DecimalFormat("#,##0.00").format(Double.valueOf(bill_amount.toString())));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()) {
            case R.id.txtAccount:
                txtAccount.setError(null);
                break;
            case R.id.txtDuedate:
                if (hasFocus) {
                    FragmentManager fm = getSupportFragmentManager();
                    DateTime now = DateTime.now();
                    CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = CalendarDatePickerDialogFragment.newInstance(this, now.getYear(), now.getMonthOfYear() - 1,
                            now.getDayOfMonth());
                    calendarDatePickerDialogFragment.show(fm, FRAG_TAG_DATE_PICKER);
                }
                break;
            case R.id.txtBillAmount:
                txtBillAmount.setError(null);
                bill_amount = txtBillAmount.getText().toString();
                if (!txtBillAmount.getText().toString().equals("") && !txtBillAmount.getText().equals(null)) {
                    if (hasFocus) {
                        txtBillAmount.setText(bill_amount.replace("Php", "").replace(",", "").trim());
                    } else {
                        ConvertCurrency();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                File file = new File(fileUri.getPath());
                billInfoModel.setBill_attachment(file.getName());
                billInfoModel.setFile_path(fileUri.getPath());
                Toast.makeText(ActivityAddManualBill.this, "Captured Image Attached as a file.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECT_PHOTO) {
            File file = new File(getPath(data));
            billInfoModel.setBill_attachment(file.getName());
            billInfoModel.setFile_path(file.getPath());
        } else {
            if (resultCode == RESULT_OK) {
                Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                if (parcelableUris == null) {
                    Toast.makeText(ActivityAddManualBill.this, "Please select a file or capture image.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

                if (uris != null) {
                    for (Uri uri : uris) {
                        Log.i("FILE", " uri: " + uri);
                        File file = new File(uri.getPath());
                        billInfoModel.setBill_attachment(file.getName());
                        billInfoModel.setFile_path(uri.getPath());
                    }
                }
            } else {
                Toast.makeText(ActivityAddManualBill.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getPath(Intent data) {
        String realPath = null;
        if (Build.VERSION.SDK_INT < 15) {
            realPath = Util.getRealPathFromURI_BelowAPI11(this, data.getData());
        } else if (Build.VERSION.SDK_INT < 19) {
            realPath = Util.getRealPathFromURI_API11to18(this, data.getData());
        } else {
            realPath = Util.getRealPathFromURI_API19(this, data.getData());

        }
        return realPath;
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int month, int day) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date newFormat = format.parse(day + "-" + (month + 1) + "-" + year);
            format = new SimpleDateFormat("dd-MMM-yyyy");
            txtDueDate.setText(format.format(newFormat));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtDueDate.setError(null);
        txtDueDate.clearFocus();
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }


    @OnClick(R.id.back)
    public void Back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        Util.startNextActivity(this, ActivityAddBill.class);
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                ValiddateEntryBill();
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
            default:
                break;
        }
    }
}
