package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class InputFragment extends Fragment {

    private CalculatorViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Інфлейтимо макет, який ми редагували (з двома кнопками)
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ініціалізація ViewModel для передачі даних між фрагментами
        viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);

        // Зв'язуємо елементи інтерфейсу з кодом
        EditText etNum1 = view.findViewById(R.id.num1);
        EditText etNum2 = view.findViewById(R.id.num2);
        RadioGroup opGroup = view.findViewById(R.id.opGroup);
        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnOpen = view.findViewById(R.id.btnOpen);

        // Обробка натискання кнопки OK (Розрахунок + Збереження)
        btnOk.setOnClickListener(v -> {
            try {
                String s1 = etNum1.getText().toString();
                String s2 = etNum2.getText().toString();

                if (s1.isEmpty() || s2.isEmpty()) {
                    Toast.makeText(requireContext(), "Заповніть усі поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                double n1 = Double.parseDouble(s1);
                double n2 = Double.parseDouble(s2);
                double res = 0;
                String opSymbol = "";
                int selectedId = opGroup.getCheckedRadioButtonId();

                // Логіка калькулятора (Варіант 8)
                if (selectedId == R.id.rbPlus) { res = n1 + n2; opSymbol = "+"; }
                else if (selectedId == R.id.rbMinus) { res = n1 - n2; opSymbol = "-"; }
                else if (selectedId == R.id.rbMult) { res = n1 * n2; opSymbol = "*"; }
                else if (selectedId == R.id.rbDiv) {
                    if (n2 == 0) {
                        Toast.makeText(requireContext(), "Ділення на нуль!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    res = n1 / n2; opSymbol = "/";
                } else {
                    Toast.makeText(requireContext(), "Виберіть операцію", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Формуємо рядок для бази даних
                String fullExpression = n1 + " " + opSymbol + " " + n2 + " = " + res;

                // Оновлюємо ViewModel для ResultFragment
                viewModel.setResult("Результат: " + res);

                // --- РОБОТА З БАЗОЮ ДАНИХ (ROOM) ---
                CalculationResult record = new CalculationResult();
                record.expression = fullExpression;

                // Вставка запису в БД
                AppDatabase.getInstance(requireContext()).resultDao().insert(record);

                // Візуальне підтвердження збереження (вимога ЛР3)
                Toast.makeText(requireContext(), "Дані успішно збережено!", Toast.LENGTH_SHORT).show();

                // Перехід до екрана результату (Фрагмент)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ResultFragment())
                        .addToBackStack(null)
                        .commit();

            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Помилка формату чисел", Toast.LENGTH_SHORT).show();
            }
        });

        // Обробка натискання кнопки "Відкрити історію" (Перехід до нової Activity)
        btnOpen.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HistoryActivity.class);
            startActivity(intent);
        });
    }
}