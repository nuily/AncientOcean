package nyc.c4q.huilin.ancientocean;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    private Button requestBtn;
    private List<People> peopleList;
    private Button postBtn;
    private Retrofit retrofit;
    private AncientOceanApi api;
    private TextView messageTV;
    private Button previousBtn;
    private Button brooklynBtn;
    private People recentlyAddedPerson;
    private Button getPersonOneBtn;
    private Button deletePersonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.185:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(AncientOceanApi.class);
        messageTV = (TextView) findViewById(R.id.messageTV);
        requestBtn = (Button) findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(this);
        postBtn = (Button) findViewById(R.id.postBtn);
        postBtn.setOnClickListener(this);
        previousBtn = (Button) findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(this);
        brooklynBtn = (Button) findViewById(R.id.brooklynBtn);
        brooklynBtn.setOnClickListener(this);
        getPersonOneBtn = (Button) findViewById(R.id.getPersonOneBtn);
        getPersonOneBtn.setOnClickListener(this);
        deletePersonBtn = (Button) findViewById(R.id.deletePersonBtn);
        deletePersonBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestBtn:
                makeGetRequest();
                break;
            case R.id.postBtn:
                makePostRequest();
                break;
            case R.id.previousBtn:
                getPreviouslyAddedPerson();
                break;
            case R.id.brooklynBtn:
                modifyCityToBrooklyn();
                break;
            case R.id.getPersonOneBtn:
                getIdOnePerson();
                break;
            case R.id.deletePersonBtn:
                deletePerson("1");
                break;

        }
    }

    private void deletePerson(String id) {
        Call<People> call = api.deletePerson(id);
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                try {
                    if (response.isSuccessful()) {
                        messageTV.setText("Person was deleted");
                    } else {
                        Log.d(TAG, "onResponse: Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }

    private void getIdOnePerson() {
        Call<People> call = api.getPersonWithIdOne();
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                try {
                    if (response.isSuccessful()) {
                        People personIdOne = response.body();
                        messageTV.setText(personIdOne.getName() + ", " + personIdOne.getId() + ", " + personIdOne.getFavoriteCity());
                    } else {
                        Log.d(TAG, "onResponse: Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }

    private void modifyCityToBrooklyn() {
        if (recentlyAddedPerson != null) {
            Call<People> call = api.updatePerson(recentlyAddedPerson.getId(), "Brooklyn");
            call.enqueue(new Callback<People>() {
                @Override
                public void onResponse(Call<People> call, Response<People> response) {
                    try {
                        if (response.isSuccessful()) {
                            response.body();
                            messageTV.setText(recentlyAddedPerson.getName() + "'s favorite city was updated/added");
                        } else {
                            messageTV.setText("You didn't retrieve a person yet!");
                            Log.d(TAG, "onResponse: Error" + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<People> call, Throwable t) {

                }
            });
        }
    }

    private void getPreviouslyAddedPerson() {
        Call<List<People>> call = api.getDirectoryData();
        call.enqueue(new Callback<List<People>>() {
            @Override
            public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<People> peopleList = response.body();
                        recentlyAddedPerson = peopleList.get(peopleList.size() - 1);
                        messageTV.setText(recentlyAddedPerson.getName() + " was recently added!");
                    } else {
                        Log.d(TAG, "onResponse: Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<People>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }

        });
    }

    private void makeGetRequest() {
        Call<List<People>> call = api.getDirectoryData();
        call.enqueue(new Callback<List<People>>() {
            @Override
            public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                try {
                    if (response.isSuccessful()) {
                        StringBuilder sb = new StringBuilder();
                        List<People> directory = response.body();
                        for (int i = 0; i < directory.size(); i++) {
                            sb.append(directory.get(i).getName() + ", " + directory.get(i).getId() + ", " + directory.get(i).getFavoriteCity() + "; ");
                        }
                        messageTV.setText(sb.toString());
                        Log.d(TAG, "onResponse: Success" + response.body().get(0).getName());
                    } else {
                        Log.d(TAG, "onResponse: Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<People>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }

        });
    }

    private void makePostRequest() {
        Call<People> call = api.addPerson("3", "Sean", "New York");
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                try {
                    if (response.isSuccessful()) {
                        messageTV.setText("Person has been added!");
                    } else {
                        messageTV.setText("Woops, not added! Check log statement");
                        Log.d(TAG, "onResponse: Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
