package com.example.apphkdn.ultil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.apphkdn.R;

public class ChoiceWayPayDialog extends DialogFragment {
    int position=0;

    public interface  ChoiceWayPayDialogListener{
        void onPotisitisionClicked(String[] list,int position);
    }

    ChoiceWayPayDialogListener choiceWayPayDialogListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            choiceWayPayDialogListener= (ChoiceWayPayDialogListener) context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString()+" ChoiceWayPayDialog must be implemented ");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] list=getActivity().getResources().getStringArray(R.array.WayPay);
        builder.setTitle("Chọn Phương thức thanh toán").setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                position=i;
            }
        }).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choiceWayPayDialogListener.onPotisitisionClicked(list,position);
            }
        });
        return builder.create();
    }
}
