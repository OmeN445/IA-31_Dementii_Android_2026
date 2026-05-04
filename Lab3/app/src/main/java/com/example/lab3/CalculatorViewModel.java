package com.example.lab3;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {
    // Тут будемо зберігати результат (текст)
    private final MutableLiveData<String> resultData = new MutableLiveData<>();

    public void setResult(String text) {
        resultData.setValue(text);
    }

    public MutableLiveData<String> getResult() {
        return resultData;
    }

    public void clear() {
        resultData.setValue(null);
    }
}