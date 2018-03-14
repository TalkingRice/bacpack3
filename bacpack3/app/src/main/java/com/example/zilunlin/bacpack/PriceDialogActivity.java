package com.example.zilunlin.bacpack;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PriceDialogActivity extends DialogFragment implements View.OnClickListener{

    Button ok_btn, cancel_btn;
    ImageButton add_price_btn;
    Relay mRelay;
    EditText item_name, item_price;
    TextView preview;
    ArrayList mArray;
    StringBuilder mStringBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        mArray = new ArrayList();
        mStringBuilder = new StringBuilder();

        View view = inflater.inflate(R.layout.activity_price_dialog, null);
        add_price_btn = (ImageButton) view.findViewById(R.id.add_price_btn);
        ok_btn = (Button) view.findViewById(R.id.ok_btn);
        cancel_btn = (Button) view.findViewById(R.id.free_btn);

        cancel_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
        add_price_btn.setOnClickListener(this);

        item_name = (EditText) view.findViewById(R.id.item_name);
        item_price = (EditText) view.findViewById(R.id.item_price);
        preview = (TextView) view.findViewById(R.id.preview);

        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_price_btn :
                if (!item_name.getText().toString().isEmpty()|| !item_name.getText().toString().isEmpty()) {
                    mArray.add(item_name.getText().toString() + " - " + item_price.getText().toString()+ "\n");
                        for (Object value : mArray){
                            mStringBuilder.append(value.toString());
                        }
                    String priceList = mStringBuilder.toString();
                    preview.setText(priceList);
                    item_price.setText("");
                    item_name.setText("");
                    mArray.clear();
                } else {
                    Toast.makeText(getActivity(),"Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.free_btn:
                mRelay.onDialogMessage("free");
                dismiss();
                break;
            case R.id.ok_btn:
                mRelay.onDialogMessage(preview.getText().toString());
                dismiss();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRelay = (Relay) context;
    }

    public interface Relay{
        void onDialogMessage(String message);
    }
}
