package android_labs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText input1, input2;
    private RadioGroup operations;
    private TextView textResult;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізація елементів (виправлено синтаксис R.id)
        input1 = findViewById(R.id.inputNumber1);
        input2 = findViewById(R.id.inputNumber2);
        operations = findViewById(R.id.radioGroupOperations);
        textResult = findViewById(R.id.textResult);
        btnOk = findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });
    }

    private void calculateResult() {
        String s1 = input1.getText().toString();
        String s2 = input2.getText().toString();
        int selectedId = operations.getCheckedRadioButtonId();

        // Перевірка на заповнення всіх даних
        if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2) || selectedId == -1) {
            Toast.makeText(this, "Будь ласка, введіть всі дані та оберіть операцію", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(s1);
            double num2 = Double.parseDouble(s2);
            double result = 0;

            // Визначення обраної операції
            if (selectedId == R.id.radioAdd) {
                result = num1 + num2;
            } else if (selectedId == R.id.radioSub) {
                result = num1 - num2;
            } else if (selectedId == R.id.radioMult) {
                result = num1 * num2;
            } else if (selectedId == R.id.radioDiv) {
                if (num2 == 0) {
                    textResult.setText("Помилка: ділення на нуль");
                    return;
                }
                result = num1 / num2;
            }

            // Виведення результату
            textResult.setText("Результат: " + result);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Некоректний формат чисел", Toast.LENGTH_SHORT).show();
        }
    }
}