package com.example.lab2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalculatorViewModel viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);

        TextView tvResult = view.findViewById(R.id.tvResult);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Спостерігаємо за результатом у ViewModel
        viewModel.getResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                tvResult.setText(result);
            }
        });

        // Кнопка Cancel повертає назад і очищає дані за завданням
        btnCancel.setOnClickListener(v -> {
            viewModel.clear(); // Метод clear ми додали у ViewModel
            getParentFragmentManager().popBackStack();
        });
    }
}