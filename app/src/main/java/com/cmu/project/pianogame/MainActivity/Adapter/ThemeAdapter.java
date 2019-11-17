package com.cmu.project.pianogame.MainActivity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.project.pianogame.MainActivity.MainActivity;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.Database.Coin;
import com.cmu.project.pianogame.Database.ThemeList;
import com.cmu.project.pianogame.Options.Options;
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
import pl.droidsonroids.gif.GifImageView;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.MyViewHolder> {

    private Context mContext;
    private List<ThemeList> mData ;
    private String cur_theme;

    public ThemeAdapter(Context mContext, List<ThemeList> mData, String cur_theme) {
        this.mContext = mContext;
        this.mData = mData;
        this.cur_theme = cur_theme;
    }

    @NonNull
    @Override
    public ThemeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_theme, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThemeAdapter.MyViewHolder holder, final int position) {

        final ThemeList themeList = mData.get(position);
        holder.gif.setImageResource(Options.getResourceId(mContext, "drawable", themeList.getTag()));
        holder.gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.CreateImgDialog(mContext, themeList.getTag());
            }
        });
        holder.name.setText(themeList.getName());
        if (themeList.getPrice() == 0) holder.price.setText("Free");
        else holder.price.setText(themeList.getPrice()+" Coins");

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ThemeList").child(""+ themeList.getNumber()).child("id").child(Firebase.getCurrent().getUid());

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
                    ThemeList themeList1 = dataSnapshot.getValue(ThemeList.class);
                    if (themeList1.isBuy()) {
                        holder.btn.setImageResource(R.drawable.use);
                        holder.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MainActivity.setGifImageViewByTag(mContext, themeList.getTag());
                                Set.SetTheme(Firebase.getCurrent().getUid(), MainActivity.getResourceName());
                                holder.btn.setVisibility(View.INVISIBLE);
                                holder.img.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        holder.btn.setImageResource(R.drawable.buy);
                        holder.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Set.coins >= themeList.getPrice()) {
                                    Utils.CreateDialogSuccess(mContext, "Do you want to buy theme "+themeList.getName()+"?", ""+themeList.getPrice(), 20);
                                    Utils.getYes().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            MainActivity.setGifImageViewByTag(mContext, themeList.getTag());
                                            Set.SetTheme(Firebase.getCurrent().getUid(), MainActivity.getResourceName());
                                            Set.SetCoin(Firebase.getCurrent().getUid(), Set.coins - themeList.getPrice());
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

        if (cur_theme.equals(themeList.getTag())) {
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

        GifImageView gif;
        TextView name, price;
        ImageView btn;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            gif = itemView.findViewById(R.id.gif);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            btn = itemView.findViewById(R.id.btn);
            img = itemView.findViewById(R.id.img);
        }
    }

}
