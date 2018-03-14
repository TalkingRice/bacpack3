package com.example.zilunlin.bacpack;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;

/**
 * Created by Zilun Lin on 2/16/2018.
 */

public class VacancyDialogFragment extends DialogFragment {
    private NumberPicker.OnValueChangeListener mOnValueChangeListener;
    boolean isUnlimited = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final NumberPicker mNumberPicker = new NumberPicker(getActivity());

        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(200);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("How many vacancies are there?");
        builder.setMessage("Indicate the number:" );

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mOnValueChangeListener.onValueChange(mNumberPicker,
                        mNumberPicker.getValue(), mNumberPicker.getValue());
            }
        });

        builder.setNegativeButton("Unlimited", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isUnlimited = true;
            }
        });
        builder.setView(mNumberPicker);
        return  builder.create();
    }

    public NumberPicker.OnValueChangeListener getOnValueChangeListener() {
        return mOnValueChangeListener;
    }

    public void setOnValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.mOnValueChangeListener = valueChangeListener;
    }
}
