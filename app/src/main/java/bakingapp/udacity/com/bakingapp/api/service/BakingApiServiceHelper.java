package bakingapp.udacity.com.bakingapp.api.service;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import bakingapp.udacity.com.bakingapp.R;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingApiServiceHelper {

    private static BakingApiService bakingApiService;

    public static synchronized BakingApiService getInstance(Context context) {
        if(bakingApiService == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(context.getString(R.string.api_base_uri))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            bakingApiService = retrofit.create(BakingApiService.class);
        }

        return bakingApiService;
    }
}
