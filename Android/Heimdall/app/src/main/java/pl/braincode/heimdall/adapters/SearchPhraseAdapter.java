package pl.braincode.heimdall.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.braincode.heimdall.R;
import pl.braincode.heimdall.models.ResultItem;
import pl.braincode.heimdall.models.SearchPhrase;

public class SearchPhraseAdapter extends RecyclerView.Adapter<SearchPhraseAdapter.ViewHolder> {

    private ArrayList<SearchPhrase> searchPhrases;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.phrase);
        }
    }

    public SearchPhraseAdapter(ArrayList<SearchPhrase> searchPhrases) {
        this.searchPhrases = searchPhrases;
    }

    @Override
    public SearchPhraseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_phrase_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchPhrase searchPhrase = searchPhrases.get(position);

        holder.textView.setText(searchPhrase.phrase);
    }

    @Override
    public int getItemCount() {
        return searchPhrases.size();
    }

}