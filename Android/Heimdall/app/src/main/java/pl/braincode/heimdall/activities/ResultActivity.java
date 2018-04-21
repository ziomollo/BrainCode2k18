package pl.braincode.heimdall.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ResultActivity extends AppCompatActivity implements SearchPhraseAdapter.OnItemClickListener {

    private static final String TAG = ResultActivity.class.getSimpleName();

    public static final String SEARCH_PHRASE_EXTRA = "SEARCH_PHRASE_EXTRA";

    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;
    private ImageView imageView;

    private RecyclerView searchPhraseRecyclerView;
    private SearchPhraseAdapter searchPhraseResultAdapter;
    private RecyclerView.LayoutManager searchPhraseLayoutManager;

    ArrayList<ResultItem> results;
    ArrayList<SearchPhrase> searchPhrases;

    BifrostAPI bifrostUserAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageButton);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        results = new ArrayList<>();

        bifrostUserAPI = ServiceGenerator.createService(BifrostAPI.class);
        searchPhrases = getIntent().getParcelableArrayListExtra(SEARCH_PHRASE_EXTRA);

        for(SearchPhrase phrase : searchPhrases) {
            Log.d(TAG, phrase.phrase);
        }

        editText.setText(searchPhrases.get(0).phrase);

        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        resultAdapter = new ResultAdapter(results);
        recyclerView.setAdapter(resultAdapter);

        Call<ArrayList<ResultItem>> call = bifrostUserAPI.getResults(searchPhrases.get(0));

        searchPhraseLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        searchPhraseRecyclerView = findViewById(R.id.searchPharseRecyclerView);
        searchPhraseResultAdapter = new SearchPhraseAdapter(searchPhrases, this);
        searchPhraseRecyclerView.setLayoutManager(searchPhraseLayoutManager);
        searchPhraseRecyclerView.setAdapter(searchPhraseResultAdapter);


        call.enqueue(new Callback<ArrayList<ResultItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ResultItem>> call, Response<ArrayList<ResultItem>> response) {
                Log.d(TAG, "ResultItem works");
                results.addAll(response.body());
                resultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ResultItem>> call, Throwable t) {
                Log.d(TAG, "Failure");
            }
        });
    }

    public void search(){
        SearchPhrase searchPhrase = new SearchPhrase(editText.getText().toString());
        Call<ArrayList<ResultItem>> call = bifrostUserAPI.getResults(searchPhrase);
        call.enqueue(new Callback<ArrayList<ResultItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ResultItem>> call, Response<ArrayList<ResultItem>> response) {
                Log.d(TAG, "ResultItem works");
                results.clear();
                results.addAll(response.body());
                resultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ResultItem>> call, Throwable t) {
                Log.d(TAG, "Failure");
            }
        });
    }

    @Override
    public void onSearchPhraseItemClick(int position) {
        editText.setText(searchPhrases.get(position).phrase);
        search();
    }
}
