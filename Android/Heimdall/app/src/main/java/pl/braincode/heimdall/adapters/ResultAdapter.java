package pl.braincode.heimdall.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.braincode.heimdall.R;
import pl.braincode.heimdall.models.ResultItem;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private ArrayList<ResultItem> resultItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView pictureView;
        public TextView titleView;
        public TextView priceView;
        public TextView buyNowView;
        public String siteUrl;

        public ViewHolder(View view) {
            super(view);
            pictureView = view.findViewById(R.id.picture);
            titleView = view.findViewById(R.id.title);
            priceView = view.findViewById(R.id.price);
            buyNowView = view.findViewById(R.id.buyNow);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(siteUrl));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public ResultAdapter(ArrayList<ResultItem> resultItems) {
        this.resultItems = resultItems;
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResultItem resultItem = resultItems.get(position);

        Picasso.get().load(resultItem.pictureUrl).into(holder.pictureView);
        holder.titleView.setText(resultItem.title);
        if(resultItem.buyNowPrice != 0) {
            holder.priceView.setText(resultItem.buyNowPrice+" zł");
            holder.buyNowView.setVisibility(View.VISIBLE);
        } else {
            holder.priceView.setText(resultItem.auctionPrice+" zł");
            holder.buyNowView.setVisibility(View.GONE);
        }
        holder.siteUrl = resultItem.siteUrl;
    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

}