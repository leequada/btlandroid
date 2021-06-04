package com.example.bigproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bigproject.ListVideoFromSearch;
import com.example.bigproject.R;

public class Search_fragment extends Fragment {
    View view;
    EditText editText;
    Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment,container,false);
        editText = view.findViewById(R.id.txtsearch);
        button = view.findViewById(R.id.btnS);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListVideoFromSearch.class);
                intent.putExtra("Contentsearch",editText.getText().toString());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
