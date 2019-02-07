package com.example.a60010743.movieshow.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a60010743.movieshow.R;
import com.example.a60010743.movieshow.model.ReviewContent;
import com.example.a60010743.movieshow.model.ReviewDetails;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ReviewContent> reviews;
    public ReviewAdapter( Context context, List<ReviewContent> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    public void setReviewsList(List<ReviewContent> reviewsContent){
        reviews.clear();
        reviews.addAll(reviewsContent);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.review_layout, parent, false);
        Review review = new Review(row);
        return review;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Review)holder).authorView.setText(reviews.get(position).getAuthor());
        ((Review)holder).contentView.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class Review extends RecyclerView.ViewHolder{
        TextView authorView;
        TextView contentView;
        public Review(View itemView) {
            super(itemView);
            authorView = (TextView) itemView.findViewById(R.id.rv_author);
            contentView = (TextView) itemView.findViewById(R.id.rv_content);
        }
    }
}
