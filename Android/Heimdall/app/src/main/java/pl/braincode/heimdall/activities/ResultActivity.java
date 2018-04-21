package pl.braincode.heimdall.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.braincode.heimdall.R;
import pl.braincode.heimdall.adapters.ResultAdapter;
import pl.braincode.heimdall.models.ResultItem;
import pl.braincode.heimdall.models.SearchPhrase;
import pl.braincode.heimdall.services.BifrostAPI;
import pl.braincode.heimdall.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();

    public static final String SEARCH_PHRASE_EXTRA = "SEARCH_PHRASE_EXTRA";
    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;

    ArrayList<ResultItem> results;

    BifrostAPI bifrostUserAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getIntent().getStringExtra(SEARCH_PHRASE_EXTRA);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        editText = findViewById(R.id.editText);

        results = new ArrayList<>();
        bifrostUserAPI = ServiceGenerator.createService(BifrostAPI.class);
        ArrayList<SearchPhrase> phrases = getIntent().getParcelableArrayListExtra(SEARCH_PHRASE_EXTRA);

        for(SearchPhrase phrase : phrases) {
            Log.d(TAG, phrase.phrase);
        }

        editText.setText(phrases.get(0).phrase);

        Call<ArrayList<ResultItem>> call = bifrostUserAPI.getResults(phrases.get(0));
        resultAdapter = new ResultAdapter(results);


        call.enqueue(new Callback<ArrayList<ResultItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ResultItem>> call, Response<ArrayList<ResultItem>> response) {
                results.addAll(response.body());
                resultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ResultItem>> call, Throwable t) {
                Log.d(TAG, "Failure");
            }
        });


        layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultAdapter);
    }
}
