package com.example.csu_kotlin

import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.util.Optional

class MainActivity : ComponentActivity(), View.OnClickListener {

    private lateinit var view: TextView
    private var firstNumber = 0;
    private var symbol = ""
    private val operationsMap = mapOf<Operations, String>(
        Operations.Plus to "+",
        Operations.Minus to "-",
        Operations.Multiply to "*",
        Operations.Divide to "/",
        Operations.Equals to "=",
        Operations.Clear to "Clear"
    )

    private val numberMap = mapOf<Numbers, String>(
        Numbers.Zero to "0",
        Numbers.One to "1",
        Numbers.Two to "2",
        Numbers.Three to "3",
        Numbers.Four to "4",
        Numbers.Five to "5",
        Numbers.Six to "6",
        Numbers.Seven to "7",
        Numbers.Eight to "8",
        Numbers.Nine to "9"
    )

    val viewElements = listOf(
        R.id.Zero,
        R.id.One,
        R.id.Two,
        R.id.Three,
        R.id.Four,
        R.id.Five,
        R.id.Six,
        R.id.Seven,
        R.id.Eight,
        R.id.Nine,
        R.id.Plus,
        R.id.Minus,
        R.id.Multiply,
        R.id.Divide,
        R.id.Equal,
        R.id.Clear
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttons = mutableListOf<Button>();
        this.view = findViewById<TextView>(R.id.view)

        viewElements.forEach {
            buttons.add(findViewById<Button>(it))
        }

        buttons.forEach {
            it.setOnClickListener(this)
        }
        view.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.Zero -> setViewText(number = Numbers.Zero)
            R.id.One -> setViewText(number = Numbers.One)
            R.id.Two -> setViewText(number = Numbers.Two)
            R.id.Three -> setViewText(number = Numbers.Three)
            R.id.Four -> setViewText(number = Numbers.Four)
            R.id.Five -> setViewText(number = Numbers.Five)
            R.id.Six -> setViewText(number = Numbers.Six)
            R.id.Seven -> setViewText(number = Numbers.Seven)
            R.id.Eight -> setViewText(number = Numbers.Eight)
            R.id.Nine -> setViewText(number = Numbers.Nine)

            R.id.Plus -> processOperation(operation = Operations.Plus)
            R.id.Minus -> processOperation(operation = Operations.Minus)
            R.id.Multiply -> processOperation(operation = Operations.Multiply)
            R.id.Divide -> processOperation(operation = Operations.Divide)
            R.id.Equal -> processOperation(operation = Operations.Equals)
            R.id.Clear -> processOperation(operation = Operations.Clear)
        }
    }

    private fun setViewText(number: Numbers) {
        view.text = view.text.toString() + numberMap.get(number)
    }

    private fun processOperation(operation: Operations) {
        var firstOperand = view.text.toString();
        if (firstOperand == null) {
            firstOperand = "0";
        }
        when (operation) {
            Operations.Plus -> setFirstOperand(firstOperand, Operations.Plus)
            Operations.Minus -> setFirstOperand(firstOperand, Operations.Minus)
            Operations.Multiply -> setFirstOperand(firstOperand, Operations.Multiply)
            Operations.Divide -> setFirstOperand(firstOperand, Operations.Divide)
            Operations.Delete -> deleteNumber()
            Operations.Equals -> {
                symbol = view.text.toString().dropLast(view.text.length - 1)
                getAnswer(view.text.toString().drop(1), findMap(operationsMap, symbol))
            }

            Operations.Clear -> clear()

            null -> return
        }
    }

    private fun clear() {
        view.text = ""
        firstNumber = 0
    }

    private fun getAnswer(number: String, operation: Operations) {
        when (operation) {
            Operations.Plus -> view.text = (firstNumber + number.toInt()).toString()
            Operations.Minus -> view.text = (firstNumber - number.toInt()).toString()
            Operations.Multiply -> view.text = (firstNumber * number.toInt()).toString()
            Operations.Divide -> view.text = (firstNumber / number.toInt()).toString()

            else -> {
                return
            }
        }
    }

    private fun setFirstOperand(number: String, operation: Operations) {
        firstNumber = number.toInt()
        view.text = operationsMap[operation]
    }

    private fun deleteNumber() {
        if (view.text.isEmpty()) {
            firstNumber = 0
            return
        }
        view.text = view.text.dropLast(1)
    }

    private fun findMap(hashMap: Map<Operations, String>, operationSymbol: String): Operations {
        return hashMap.filter { operationSymbol == it.value }.keys.first()
    }
}

enum class Numbers {
    Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine
}

enum class Operations {
    Plus, Minus, Multiply, Divide, Delete, Equals, Clear
}

