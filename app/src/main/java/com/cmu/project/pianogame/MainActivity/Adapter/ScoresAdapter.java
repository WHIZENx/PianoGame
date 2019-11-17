package com.cmu.project.pianogame.MainActivity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cmu.project.pianogame.Database.Score;
import com.cmu.project.pianogame.Database.Users;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.MyViewHolder> {

    private Context mContext;
    private List<Score> mData ;

    public ScoresAdapter(Context mContext, List<Score> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_score, parent, false);
        return new MyViewHolder(view);
    }

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Score score = mData.get(position);

        if (holder.getItemViewType() == HEADER) {
            holder.rank.setImageResource(R.drawable.gold);
            holder.rank.setVisibility(View.VISIBLE);
        } else {
            if (position == 1) {
                holder.rank.setImageResource(R.drawable.silver);
                holder.rank.setVisibility(View.VISIBLE);
            }
            if (position == 2) {
                holder.rank.setImageResource(R.drawable.copper);
                holder.rank.setVisibility(View.VISIBLE);
            }
        }

        if (position % 2 == 0) holder.relativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.score_1));
        else holder.relativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.score_2));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(score.getId());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);

                if(Options.isValidContextForGlide(mContext)) Glide.with(mContext).load(users.getImageURL()).into(holder.profile_img);
                holder.username.setText(users.getUsername());
                holder.showscore.setText(""+score.getMaxscore());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView showscore;
        TextView username;
        CircleImageView profile_img;
        RelativeLayout relativeLayout;
        ImageView rank;

        public MyViewHolder(View itemView) {
            super(itemView);

            showscore = itemView.findViewById(R.id.showscore);
            username = itemView.findViewById(R.id.username);
            profile_img = itemView.findViewById(R.id.profile_img);
            relativeLayout = itemView.findViewById(R.id.rev_score);
            rank = itemView.findViewById(R.id.rank);
        }
    }
}
