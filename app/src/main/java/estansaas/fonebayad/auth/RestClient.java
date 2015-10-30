package estansaas.fonebayad.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
    private static RestApi REST_CLIENT_OAUTH;


    public static RestApi get() {
        setupRestClient();
        return REST_CLIENT;
    }

    public static RestApi getOAuth() {
        setupRestOAuthClient();
        return REST_CLIENT_OAUTH;
    }

    private static void setupRestClient() {

        Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapterFactory(new EmptyCheckTypeAdapterFactory()).create();

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

    private static void setupRestOAuthClient() {

        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);


        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        REST_CLIENT_OAUTH = restAdapter.create(RestApi.class);
    }

    public static class EmptyCheckTypeAdapterFactory implements TypeAdapterFactory {

        @Override
        public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            // We filter out the EmptyCheckTypeAdapter as we need to check this for emptiness!
            if (EmptyCheckTypeAdapterFactory.class.isAssignableFrom(type.getRawType())) {
                final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
                final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
                return new EmptyCheckTypeAdapter<>(delegate, elementAdapter).nullSafe();
            }
            return null;
        }

        public class EmptyCheckTypeAdapter<T> extends TypeAdapter<T> {

            private final TypeAdapter<T> delegate;
            private final TypeAdapter<JsonElement> elementAdapter;

            public EmptyCheckTypeAdapter(final TypeAdapter<T> delegate,
                                         final TypeAdapter<JsonElement> elementAdapter) {
                this.delegate = delegate;
                this.elementAdapter = elementAdapter;
            }

            @Override
            public void write(final JsonWriter out, final T value) throws IOException {
                this.delegate.write(out, value);
            }

            @Override
            public T read(final JsonReader in) throws IOException {
                final JsonObject asJsonObject = elementAdapter.read(in).getAsJsonObject();
                if (asJsonObject.entrySet().isEmpty()) return null;
                return this.delegate.fromJsonTree(asJsonObject);
            }
        }

    }

}
