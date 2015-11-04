package estansaas.fonebayad.auth;

import com.squareup.okhttp.RequestBody;

import estansaas.fonebayad.auth.Responses.AccessToken;
import estansaas.fonebayad.auth.Responses.Response;
import estansaas.fonebayad.auth.Responses.ResponseBankAccount;
import estansaas.fonebayad.auth.Responses.ResponseBillStatement;
import estansaas.fonebayad.auth.Responses.ResponseForexRate;
import estansaas.fonebayad.auth.Responses.ResponseLogin;
import estansaas.fonebayad.auth.Responses.ResponseNotification;
import estansaas.fonebayad.auth.Responses.ResponseOffer;
import estansaas.fonebayad.auth.Responses.ResponseSyncData;
import estansaas.fonebayad.auth.Responses.ResponseTransaction;
import estansaas.fonebayad.auth.Responses.ResponseUserSophisticate;
import estansaas.fonebayad.model.ModelRegistration;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by gerald.tayag on 10/5/2015.
 */
public interface RestApi {

    @GET("/fonebayad-web/public/api/loginuser")
    Call<ResponseLogin> fonebayadLogin(@Query("user_pin") String user_pin, @Query("device_guid") String device_guid);

    @POST("/fonebayad-web/public/mobileRegistration")
    Call<Response> mobileRegistration(@Body ModelRegistration registrationAuthModel);

    @POST("/fonebayad-web/public/checkIfEmailIsRegistered")
    Call<Response> checkPersonalEmail(@Query("email") String email);

    @POST("/fonebayad-web/public/checkIfFonebayadEmailIsAvailable")
    Call<Response> checkFonebayadEmail(@Query("ezibills_email") String email);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/syncrecord")
    Call<ResponseBillStatement> SyncDashboardRecord(@Field("userid") String userid, @Field("device_guid") String deviceguid);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/syncdata")
    Call<ResponseSyncData> SyncData(@Field("last_update") String last_update);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/getAllActiveBankAccounts")
    Call<ResponseBankAccount> getAllActiveBankAccounts(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/validateBillEntry")
    Call<Response> validateBillEntry(@Field("bill_biller") String bill_biller, @Field("bill_accountnumber") String bill_accountnumber, @Field("bill_transactionnumber") String bill_transactionnumber, @Field("bill_userid") String bill_userid);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/createBillStatement")
    Call<Response> createBillStatement(@Field("bill_biller") String bill_biller, @Field("bill_account_number") String bill_account_number, @Field("bill_transaction_number") String bill_transaction_number, @Field("bill_currency") String bill_currency, @Field("bill_amount") String bill_amount, @Field("bill_status") String bill_status, @Field("bill_attachment") String bill_attachment, @Field("bill_due_date") String bill_due_date, @Field("bill_schedule_of_payment") String bill_schedule_of_payment, @Field("bill_user_id") String bill_user_id, @Field("bill_payment_method") String bill_payment_method, @Field("bill_type") String bill_type, @Field("bill_user_entity") String bill_user_entity);

    @Multipart
    @POST("/fonebayad-web/public/upload")
    Call<Response> upload(@Part("uploadedImage\"; filename=\"pp.png\" ") RequestBody image, @Part("createdby") RequestBody createdBy, @Part("device_guid") RequestBody device_guid, @Part("device") RequestBody device);

    @GET("/fonebayad-web/public/api/getremotedata")
    Call<Response> getremotedata(@Query("user_pin") String user_pin, @Query("device_guid") String device_guid);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/checkUserSophisticate")
    Call<ResponseUserSophisticate> checkUserSophisticate(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/getNotifications")
    Call<ResponseNotification> getNotification(@Field("userID") String user_id);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/getOffers")
    Call<ResponseOffer> getOffers(@Field("userID") String user_id);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/getForexRate")
    Call<ResponseForexRate> getForexRate(@Field("base_currency") String base_currency, @Field("transfer_currency") String transfer_currency);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/paybillsMobile")
    Call<Response> paybillsMobile(@Field("statementID") String statementID, @Field("userID") String userID, @Field("status") String status, @Field("paymentAmount") String paymentAmount, @Field("newBalance") String newBalance, @Field("bankId") String bankId, @Field("transactionAmount") String transactionAmount, @Field("transactionLine") String transactionLine);

    @FormUrlEncoded
    @POST("/fonebayad-web/public/getUserTransactionHistory")
    Call<ResponseTransaction> getUserTransactionHistory(@Field("userid") String userid);

    @POST("/token")
    Call<AccessToken> getAccessToken(@Query("username") String username, @Query("password") String password);

}
