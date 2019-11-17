package com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.MainActivity.Service.Settings;
import com.cmu.project.pianogame.Options.Utils;
import com.cmu.project.pianogame.R;

public class GadgetsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gadgets, container, false);

        ImageView btn1 = view.findViewById(R.id.btn1);
        ImageView btn2 = view.findViewById(R.id.btn2);
        ImageView btn3 = view.findViewById(R.id.btn3);
        ImageView btn4 = view.findViewById(R.id.btn4);

        ImageView btnl1 = view.findViewById(R.id.btnl1);
        ImageView btnl2 = view.findViewById(R.id.btnl2);
        ImageView btnl3 = view.findViewById(R.id.btnl3);

        BuyItem(btn1, 15, 1, true);
        BuyItem(btn2, 50, 5, true);
        BuyItem(btn3, 90, 10, true);
        BuyItem(btn4, 130, 15, true);

        BuyItem(btnl1, 50, 1, false);
        BuyItem(btnl2, 200, 5, false);
        BuyItem(btnl3, 350, 10, false);

        return view;
    }

    private void BuyItem(ImageView button, final int price, final int number, final boolean mode) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Set.coins >= price) {
                    if (mode) {
                        if (Set.energys + number <= Settings.max_energy) {
                            Utils.CreateDialogSuccess(getActivity(), "Do you want to buy " + number + " energy ", "" + price, 20);
                            yesIsClick(mode, price, number);
                        } else Utils.CreateDialogError(getActivity(), "You have Max Energy!!", 20);
                    } else {
                        Utils.CreateDialogSuccess(getActivity(), "Do you want to buy " + number + " love ", "" + price, 20);
                        yesIsClick(mode, price, number);
                    }
                } else Utils.CreateDialogError(getActivity(), "You have not enough Coins!!", 20);
            }
        });
    }

    private void yesIsClick(final boolean mode, final int price, final int number) {
        Utils.getYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set.SetCoin(Firebase.getCurrent().getUid(), Set.coins - price);
                if (mode) Set.SetEnergy(Firebase.getCurrent().getUid(), Set.energys + number);
                else Set.SetLove(Firebase.getCurrent().getUid(), Set.loves + number);
                Utils.getMyDialog().dismiss();
            }
        });
    }
}
