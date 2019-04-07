package company.co.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// https://lx5475.github.io/2017/03/30/android-retrofit/

public class RetorofitActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private TextView textView;

    private final String BASE_URL = "https://api.github.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("E : ", "onCreate in RetrofitActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        init();



        GitHub gitHub = retrofit.create(GitHub.class);
        Call<List<Contributor>> call = gitHub.contributors("square","retrofit");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributors = response.body();

                for(Contributor contributor : contributors){
                    Log.e("E : ", contributor.login);
                    textView.append("    " + contributor.login);
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Toast.makeText(RetorofitActivity.this, "failed for getting information", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void init(){

        textView = (TextView)findViewById(R.id.textView);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();


    }
}
