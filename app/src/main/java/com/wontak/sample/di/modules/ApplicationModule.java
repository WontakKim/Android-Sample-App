package com.wontak.sample.di.modules;

import android.app.Application;
import android.content.Context;

import com.wontak.sample.R;
import com.wontak.sample.data.network.GithubApiService;
import com.wontak.sample.data.network.GithubCallAdapterFactory;
import com.wontak.sample.data.repositories.GithubDataRepository;
import com.wontak.sample.domain.repositories.GithubRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        return client;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Application application, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(application.getResources().getString(R.string.endpoint))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(GithubCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public GithubApiService provideGithubApiService(Retrofit retrofit) {
        return retrofit.create(GithubApiService.class);
    }

    @Provides
    @Singleton
    public GithubRepository provideGithubRepository(GithubDataRepository githubDataRepository) {
        return githubDataRepository;
    }
}
