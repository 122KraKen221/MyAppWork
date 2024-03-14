package com.example.myappwork;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;
public class CustomHelp extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Диалоговое окно").setMessage("Все поля должны быть заполнены.\n" +
                "Пароль должен состоять как миниум из 6 символов ").create();
    }
}
