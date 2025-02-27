package com.proyectomarzo.flashmeet.friends_recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.proyectomarzo.flashmeet.R;
import com.proyectomarzo.flashmeet.datos_mock.Conversations;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {

    private static final String TAG = "FriendsListAdapter";
    private List<Conversations> friends;

    Context context;

    public FriendsListAdapter(Context context, List<Conversations> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return  new  FriendsListAdapter.ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.friend_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListAdapter.ViewHolder holder, int position) {

        holder.profileImageView = holder.itemView.findViewById(R.id.chat_profile_image);
        holder.nameTextView= holder.itemView.findViewById(R.id.chat_name);
        holder.lastMessageTextView= holder.itemView.findViewById(R.id.chat_last_message);
        holder.nameTextView.setText(friends.get(position).getRemitentName());
        holder.lastMessageTextView.setText(friends.get(position).getMessagesList().get(0).getMessageContent());
        Glide.with(holder.itemView.getContext()).load(friends.get(position).getRemitentImageUrl()).circleCrop().into(holder.profileImageView);
    }



    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView lastMessageTextView;
        public ImageView profileImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void updateList(List<Conversations> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.friends, newList));
        this.friends = newList;
        diffResult.dispatchUpdatesTo(this);
    }

    public static class MyDiffCallback extends DiffUtil.Callback {
        private List<Conversations> oldList;
        private List<Conversations> newList;

        public MyDiffCallback(List<Conversations> oldList, List<Conversations> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}
