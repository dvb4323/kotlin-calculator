package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Các biến để lưu giá trị
    private var currentNumber: String = ""
    private var operator: String = ""
    private var firstNumber: Double = 0.0
    private var secondNumber: Double = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // TextView để hiển thị kết quả
        val answerTextView: TextView = findViewById(R.id.answer)

        // Các nút số
        val buttons = listOf(
            findViewById<Button>(R.id.num0),
            findViewById<Button>(R.id.num1),
            findViewById<Button>(R.id.num2),
            findViewById<Button>(R.id.num3),
            findViewById<Button>(R.id.num4),
            findViewById<Button>(R.id.num5),
            findViewById<Button>(R.id.num6),
            findViewById<Button>(R.id.num7),
            findViewById<Button>(R.id.num8),
            findViewById<Button>(R.id.num9)
        )

        // Các nút chức năng và toán tử
        val buttonClear = findViewById<Button>(R.id.C)
        val buttonClearEntry = findViewById<Button>(R.id.clear)
        val buttonBackspace = findViewById<Button>(R.id.BS)
        val buttonAdd = findViewById<Button>(R.id.actionAdd)
        val buttonSubtract = findViewById<Button>(R.id.actionMinus)
        val buttonMultiply = findViewById<Button>(R.id.actionMultiply)
        val buttonDivide = findViewById<Button>(R.id.actionDivide)
        val buttonEqual = findViewById<Button>(R.id.actionEqual)
        val buttonDot = findViewById<Button>(R.id.numDot)
        val buttonAddOrMinus = findViewById<Button>(R.id.addOrMinus)

        // Lắng nghe sự kiện bấm các nút số
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onNumberClick(index.toString(), answerTextView)
            }
        }

        // Nút Clear (C)
        buttonClear.setOnClickListener {
            currentNumber = ""
            firstNumber = 0.0
            secondNumber = 0.0
            operator = ""
            answerTextView.text = "0"
        }

        // Nút Clear Entry (CE)
        buttonClearEntry.setOnClickListener {
            currentNumber = ""
            answerTextView.text = "0"
        }

        // Nút Backspace (BS)
        buttonBackspace.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber = currentNumber.dropLast(1)
                answerTextView.text = currentNumber
            }
        }

        // Nút toán tử
        buttonAdd.setOnClickListener { onOperatorClick("+", answerTextView) }
        buttonSubtract.setOnClickListener { onOperatorClick("-", answerTextView) }
        buttonMultiply.setOnClickListener { onOperatorClick("*", answerTextView) }
        buttonDivide.setOnClickListener { onOperatorClick("/", answerTextView) }

        // Nút bằng
        buttonEqual.setOnClickListener {
            onEqualClick(answerTextView)
        }

        // Nút dấu chấm
        buttonDot.setOnClickListener {
            if (!currentNumber.contains(".")) {
                currentNumber += "."
                answerTextView.text = currentNumber
            }
        }

        // Nút đổi dấu (+/-)
        buttonAddOrMinus.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                if (currentNumber.startsWith("-")) {
                    currentNumber = currentNumber.drop(1)
                } else {
                    currentNumber = "-$currentNumber"
                }
                answerTextView.text = currentNumber
            }
        }
    }

    private fun onNumberClick(number: String, answerTextView: TextView) {
        if (isNewOperation) {
            currentNumber = number
            isNewOperation = false
        } else {
            currentNumber += number
        }
        answerTextView.text = currentNumber
    }

    private fun onOperatorClick(selectedOperator: String, answerTextView: TextView) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber.toDouble()
            currentNumber = ""
            operator = selectedOperator
            answerTextView.text = "0"
        }
    }

    private fun onEqualClick(answerTextView: TextView) {
        if (currentNumber.isNotEmpty()) {
            secondNumber = currentNumber.toDouble()
            val result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else "Error"
                else -> "Error"
            }

            answerTextView.text = result.toString()
            currentNumber = result.toString()
            isNewOperation = true
        }
    }
}