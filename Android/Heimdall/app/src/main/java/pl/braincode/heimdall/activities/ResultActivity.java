package pl.braincode.heimdall.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.braincode.heimdall.R;
import pl.braincode.heimdall.adapters.ResultAdapter;
import pl.braincode.heimdall.models.ResultItem;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ArrayList<ResultItem> results = new ArrayList<>();
        results.add(new ResultItem("Tytuł","https://www.google.pl/","https://media-cdn.tripadvisor.com/media/photo-s/0d/28/67/45/beef-supe.jpg",1));
        results.add(new ResultItem("Tytuł 2","https://www.google.pl/","https://media-cdn.tripadvisor.com/media/photo-s/0d/28/67/45/beef-supe.jpg",2));
        results.add(new ResultItem("Tytuł 3","https://www.google.pl/","https://media-cdn.tripadvisor.com/media/photo-s/0d/28/67/45/beef-supe.jpg",3.4));

        resultAdapter = new ResultAdapter(results);

        layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultAdapter);
    }
}
