package com.geek.fragmentdz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogErrorFragment extends DialogFragment {
    private String cod;
    private TextView msg;
    private Button okBtn;
    public DialogErrorFragment(int cod) {
        this.cod = String.valueOf(cod);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_err_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setMsg();
        onBtnClickListener();
    }

    private void setMsg() {
        msg.setText((getString(R.string.connect_error) + cod));
    }

    private void onBtnClickListener() {
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initViews(View view) {
        msg = view.findViewById(R.id.err_msg);
        okBtn = view.findViewById(R.id.err_btn);
    }
}
