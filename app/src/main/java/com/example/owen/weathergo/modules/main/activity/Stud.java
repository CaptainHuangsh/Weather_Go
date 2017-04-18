package com.example.owen.weathergo.modules.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.stud.GitHubClient;
import com.example.owen.weathergo.stud.GitHubRepo;
import com.example.owen.weathergo.stud.GitHubRepoAdapter;

import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by owen on 2017/4/9.
 */

public class Stud extends Activity {


    @BindView(R.id.stud)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud);
        getR();

    }
    public void getR(){
        String API_BASE_URL = "https://api.github.com/";

//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit = builder.build();

        GitHubClient client =  retrofit.create(GitHubClient.class);








// Create a very simple REST adapter which points the GitHub API endpoint.
//        GitHubClient client =  retrofit.create(GitHubClient.class);

// Fetch a list of the Github repositories.
        Call<List<GitHubRepo>> call =
                client.reposForUser("fs-opensource");

// Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                // The network call was a success and we got a response
                // TODO: use the repository list and display it

                List<GitHubRepo> repos = response.body();
                listView.setAdapter(new GitHubRepoAdapter(Stud.this,repos));
                /*for(int i=0;i<response.body().size();i++){
                    Log.i("huangh","is here");
                }*/

                Log.i("huangh",response.body().size()+"");

            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

    }
    public void getR2(){

    }

}
