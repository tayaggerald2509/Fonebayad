package estansaas.fonebayad.auth;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import estansaas.fonebayad.utils.Constants;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/5/2015.
 */
public class RestClient {

    private static RestApi REST_CLIENT;

    public static RestApi get() {
        setupRestClient();
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();


        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                                //.method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                return response;
            }
        });

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        REST_CLIENT = restAdapter.create(RestApi.class);
    }

    public static class EmptyCheckTypeAdapterFactory implements TypeAdapterFactory {

        @Override
        public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            // We filter out the EmptyCheckTypeAdapter as we need to check this for emptiness!
            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

            return new TypeAdapter<T>() {

                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                public T read(JsonReader in) throws IOException {
                    JsonElement jsonElement = elementAdapter.read(in);
                    return delegate.fromJsonTree(jsonElement);
                }
            }.nullSafe();
        }
    }

}
