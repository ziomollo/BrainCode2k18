package pl.braincode.heimdall.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.braincode.heimdall.R;
import pl.braincode.heimdall.adapters.ResultAdapter;
import pl.braincode.heimdall.adapters.SearchPhraseAdapter;
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

    private RecyclerView searchPhraseRecyclerView;
    private SearchPhraseAdapter searchPhraseResultAdapter;
    private RecyclerView.LayoutManager searchPhraseLayoutManager;

    ArrayList<ResultItem> results;

    BifrostAPI bifrostUserAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getIntent().getStringExtra(SEARCH_PHRASE_EXTRA);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        results = new ArrayList<>();

        bifrostUserAPI = ServiceGenerator.createService(BifrostAPI.class);

        String phrase = getIntent().getStringExtra(SEARCH_PHRASE_EXTRA);
        SearchPhrase searchPhrase = new SearchPhrase(phrase);
        Call<ArrayList<ResultItem>> call = bifrostUserAPI.getResults(searchPhrase);

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

        ArrayList<SearchPhrase> searchPhrases = new ArrayList<>();
        searchPhrases.add(new SearchPhrase("test"));
        searchPhrases.add(new SearchPhrase("test2"));
        searchPhrases.add(new SearchPhrase("test3"));
        searchPhrases.add(new SearchPhrase("test4"));

        searchPhraseLayoutManager = new LinearLayoutManager(this);

        searchPhraseRecyclerView = findViewById(R.id.recyclerView);
        searchPhraseResultAdapter = new SearchPhraseAdapter(searchPhrases);
        searchPhraseRecyclerView.setLayoutManager(searchPhraseLayoutManager);
        searchPhraseRecyclerView.setAdapter(searchPhraseResultAdapter);

    }
}
