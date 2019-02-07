package com.example.a60010743.movieshow.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a60010743.movieshow.R;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<String> trailerKeys;
    public TrailerAdapter(Context context, List<String> trailerKeys){
        this.context = context;
        this.trailerKeys = trailerKeys;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.trailer_layout, parent,false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).btnView.setText("Trailer-"+position+1);
        ((Item)holder).btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoPath = "https://www.youtube.com/watch?v="+trailerKeys.get(position);
                Uri uri = Uri.parse(videoPath);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerKeys.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        Button btnView;
        public Item(View itemView) {
            super(itemView);
            btnView = (Button) itemView.findViewById(R.id.trailer);
        }
    }
}
