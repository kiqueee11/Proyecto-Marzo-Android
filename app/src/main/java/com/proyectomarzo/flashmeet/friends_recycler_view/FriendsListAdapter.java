package com.proyectomarzo.flashmeet.friends_recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectomarzo.flashmeet.R;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {

    Context context;

    public FriendsListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FriendsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new  FriendsListAdapter.ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.friend_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
