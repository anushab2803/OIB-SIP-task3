package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private String currentInput = "";
    private double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);

        Button btn1 = findViewById(R.id.btn_1);
        Button btn2 = findViewById(R.id.btn_2);
        Button btn3 = findViewById(R.id.btn_3);
        Button btn4 = findViewById(R.id.btn_4);
        Button btn5 = findViewById(R.id.btn_5);
        Button btn6 = findViewById(R.id.btn_6);
        Button btn7 = findViewById(R.id.btn_7);
        Button btn8 = findViewById(R.id.btn_8);
        Button btn9 = findViewById(R.id.btn_9);
        Button btn0 = findViewById(R.id.btn_0);
        Button btnClear = findViewById(R.id.btn_clear);
        Button btnPlus = findViewById(R.id.btn_plus);
        Button btnMinus = findViewById(R.id.btn_minus);
        Button btnMultiply = findViewById(R.id.btn_multiply);
        Button btnDivide = findViewById(R.id.btn_divide);
        Button btnEquals = findViewById(R.id.btn_equals);

        btn1.setOnClickListener(view -> appendNumber("1"));
        btn2.setOnClickListener(view -> appendNumber("2"));
        btn3.setOnClickListener(view -> appendNumber("3"));
        btn4.setOnClickListener(view -> appendNumber("4"));
        btn5.setOnClickListener(view -> appendNumber("5"));
        btn6.setOnClickListener(view -> appendNumber("6"));
        btn7.setOnClickListener(view -> appendNumber("7"));
        btn8.setOnClickListener(view -> appendNumber("8"));
        btn9.setOnClickListener(view -> appendNumber("9"));
        btn0.setOnClickListener(view -> appendNumber("0"));

        btnPlus.setOnClickListener(view -> setOperator("+"));
        btnMinus.setOnClickListener(view -> setOperator("-"));
        btnMultiply.setOnClickListener(view -> setOperator("*"));
        btnDivide.setOnClickListener(view -> setOperator("/"));

        btnClear.setOnClickListener(view -> clearInput());

        btnEquals.setOnClickListener(view -> calculateResult());
    }

    private void appendNumber(String number) {
        currentInput += number;
        input.setText(currentInput);
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            currentInput += op;
            input.setText(currentInput);
        }
    }

    private void clearInput() {
        currentInput = "";
        input.setText(currentInput);
        result = 0;
    }

    private void calculateResult() {
        if (!currentInput.isEmpty()) {
            try {
                result = evaluateExpression(currentInput);
                currentInput = String.valueOf(result);
                input.setText(currentInput);
            } catch (ArithmeticException e) {
                input.setText("Error");
            } catch (Exception e) {
                input.setText("Invalid Input");
            }
        }
    }

    private double evaluateExpression(String expression) throws ArithmeticException {
        String[] tokens = expression.split("(?=[+\\-*/])|(?<=[+\\-*/])");
        double total = 0;
        double currentNum = Double.parseDouble(tokens[0]);
        String currentOperator = "";

        for (int i = 1; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("+") || token.equals("-")) {
                if (currentOperator.equals("+")) {
                    total += currentNum;
                } else if (currentOperator.equals("-")) {
                    total -= currentNum;
                } else {
                    total = currentNum;
                }
                currentOperator = token;
                currentNum = Double.parseDouble(tokens[i + 1]);
            } else if (token.equals("*") || token.equals("/")) {
                double nextNum = Double.parseDouble(tokens[i + 1]);
                if (token.equals("*")) {
                    currentNum *= nextNum;
                } else if (token.equals("/")) {
                    if (nextNum == 0) {
                        throw new ArithmeticException("Division by Zero");
                    }
                    currentNum /= nextNum;
                }
                i++;
            }
        }

        if (currentOperator.equals("+")) {
            total += currentNum;
        } else if (currentOperator.equals("-")) {
            total -= currentNum;
        } else {
            total = currentNum;
        }

        return total;
    }
}
