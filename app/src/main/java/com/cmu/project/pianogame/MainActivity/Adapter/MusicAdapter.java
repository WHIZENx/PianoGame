package com.cmu.project.pianogame.MainActivity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.Database.Coin;
import com.cmu.project.pianogame.Database.MusicList;
import com.cmu.project.pianogame.Options.Utils;
import com.cmu.project.pianogame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private Context mContext;
    private List<MusicList> mData ;
    private String cur_music;

    public MusicAdapter(Context mContext, List<MusicList> mData, String cur_music) {
        this.mContext = mContext;
        this.mData = mData;
        this.cur_music = cur_music;
    }

    @NonNull
    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_music, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicAdapter.MyViewHolder holder, final int position) {

        final MusicList musicList = mData.get(position);
        holder.name.setText(musicList.getName());
        if (musicList.getPrice() == 0) holder.price.setText("Free");
        else holder.price.setText(musicList.getPrice()+" Coins");

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MusicList").child(""+ musicList.getNumber()).child("id").child(Firebase.getCurrent().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    if (position == 0) hashMap.put("buy", true);
                    else hashMap.put("buy", false);
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                } else {
                    MusicList musicList1 = dataSnapshot.getValue(MusicList.class);
                    if (musicList1.isBuy()) {
                        holder.btn.setImageResource(R.drawable.use);
                        holder.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Set.SetMusic(Firebase.getCurrent().getUid(), musicList.getTag());
                                Set.SetNameMusic(Firebase.getCurrent().getUid(), musicList.getName());
                                holder.btn.setVisibility(View.INVISIBLE);
                                holder.img.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        holder.btn.setImageResource(R.drawable.buy);
                        holder.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Set.coins >= musicList.getPrice()) {
                                    Utils.CreateDialogSuccess(mContext, "Do you want to buy music "+musicList.getName()+"?", ""+musicList.getPrice(), 20);
                                    Utils.getYes().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Set.SetMusic(Firebase.getCurrent().getUid(), musicList.getTag());
                                            Set.SetNameMusic(Firebase.getCurrent().getUid(), musicList.getName());
                                            Set.SetCoin(Firebase.getCurrent().getUid(), Set.coins - musicList.getPrice());
                                            holder.btn.setVisibility(View.INVISIBLE);
                                            holder.img.setVisibility(View.VISIBLE);
                                            reference.child("buy").setValue(true);
                                            Utils.getMyDialog().dismiss();
                                        }
                                    });
                                } else Utils.CreateDialogError(mContext, "You have not enough Coins!!", 20);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        if (cur_music.equals(musicList.getTag())) {
            holder.btn.setVisibility(View.INVISIBLE);
            holder.img.setVisibility(View.VISIBLE);
        } else {
            holder.btn.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
        ImageView btn;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.namemusic);
            price = itemView.findViewById(R.id.pricemusic);
            btn = itemView.findViewById(R.id.btnmusic);
            img = itemView.findViewById(R.id.imgmusic);
        }
    }

}
