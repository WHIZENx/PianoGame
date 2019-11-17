package com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.Database.ColorBox;
import com.cmu.project.pianogame.MainActivity.Service.Settings;
import com.cmu.project.pianogame.Options.Utils;
import com.cmu.project.pianogame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

public class BoxFragment extends Fragment {

    private LinearLayout linearLayout, linearLayout2;
    private String color, colors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_box, container, false);

        ColorPickerView colorPickerView = view.findViewById(R.id.colorPickerView);
        linearLayout = view.findViewById(R.id.selectcolor);
        linearLayout2 = view.findViewById(R.id.curcolor);
        ImageView btn_change = view.findViewById(R.id.btn_change);

        TextView coin_box = view.findViewById(R.id.coin_box);
        coin_box.setText(Settings.price_box+" COINS");

        BrightnessSlideBar brightnessSlideBar = view.findViewById(R.id.brightnessSlide);
        colorPickerView.attachBrightnessSlider(brightnessSlideBar);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Colors").child(Firebase.getCurrent().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ColorBox colorBox = dataSnapshot.getValue(ColorBox.class);
                colors = colorBox.getColorbox();
                linearLayout2.setBackgroundColor(Color.parseColor(colors));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, boolean fromUser) {
                linearLayout.setBackgroundColor(color);
            }
        });

        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                linearLayout.setBackgroundColor(envelope.getColor());
                color = envelope.getHexCode();
            }
        });

        Set.getCoin(Firebase.getCurrent().getUid());
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set.getCoin(Firebase.getCurrent().getUid());
                if (Set.coins >= Settings.price_box) {
                    Utils.CreateDialogSuccess(getActivity(), "Do you want to change color?", ""+Settings.price_box, 20);
                    Utils.getYes().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Set.SetCoin(Firebase.getCurrent().getUid(), Set.coins - Settings.price_box);
                            Set.SetColor(Firebase.getCurrent().getUid(), "#" + color);
                            Utils.getMyDialog().dismiss();
                        }
                    });
                } else Utils.CreateDialogError(getActivity(), "You have not enough Coins!!", 20);
            }
        });

        return view;
    }
}
