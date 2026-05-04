package com.example.lab2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class InputFragment extends Fragment {

    private CalculatorViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Підключаємо спільну ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);

        // Знаходимо всі елементи інтерфейсу
        EditText etNum1 = view.findViewById(R.id.num1);
        EditText etNum2 = view.findViewById(R.id.num2);
        RadioGroup opGroup = view.findViewById(R.id.opGroup);
        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> {
            try {
                // Зчитуємо дані
                double n1 = Double.parseDouble(etNum1.getText().toString());
                double n2 = Double.parseDouble(etNum2.getText().toString());
                double res = 0;
                int selectedId = opGroup.getCheckedRadioButtonId();

                // Виконуємо операцію за варіантом 8
                if (selectedId == R.id.rbPlus) {
                    res = n1 + n2;
                } else if (selectedId == R.id.rbMinus) {
                    res = n1 - n2;
                } else if (selectedId == R.id.rbMult) {
                    res = n1 * n2;
                } else if (selectedId == R.id.rbDiv) {
                    if (n2 == 0) {
                        viewModel.setResult("Помилка: ділення на 0");
                        navigateToResult();
                        return;
                    }
                    res = n1 / n2;
                } else {
                    viewModel.setResult("Виберіть операцію");
                    navigateToResult();
                    return;
                }

                // Зберігаємо результат і переходимо
                viewModel.setResult("Результат: " + res);
                navigateToResult();

            } catch (NumberFormatException e) {
                viewModel.setResult("Введіть коректні числа");
                navigateToResult();
            }
        });
    }

    private void navigateToResult() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ResultFragment())
                .addToBackStack(null)
                .commit();
    }
}