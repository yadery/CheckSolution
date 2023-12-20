package com.example.checkthesolution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import com.example.checkthesolution.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private var timeTotal = 0.0
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun resetTimer()
    {
        binding.StopwatchChronometr.stop()
        var time =  (SystemClock.elapsedRealtime() - binding.StopwatchChronometr.base).toDouble() / 1000

        timeTotal += time;
        if (time < binding.MinTimeView.text.toString().toDouble()){
            binding.MinTimeView.text = time.toString();
        }
        else if (binding.MinTimeView.text.toString().toDouble() == 0.0){
            binding.MinTimeView.text = time.toString();
        }
        if (time > binding.MaxTimeView.text.toString().toDouble()){
            binding.MaxTimeView.text = time.toString();
        }
        var avgTime = ((timeTotal / binding.TotalNumberView.text.toString().toInt()) * 100).roundToInt().toDouble() / 100
        binding.AvgTimeView.text = avgTime.toString()
    }
    private fun startTimer()
    {
        binding.StopwatchChronometr.base = SystemClock.elapsedRealtime();
        binding.StopwatchChronometr.start()
    }
    class Equation(val equationText: String, val equationCorrectness: Boolean)
    class ResultOfEquation(val firstNumber: Int, val secondNumber: Int, val result: Double)
    fun resultGet(operand: Char): ResultOfEquation{
        var result = 0.00;
        val firstRng = (10..99).random();
        val secondRng = (10..99).random();
        when (operand){
            '/' -> result = ((firstRng.toDouble() / secondRng) * 100).roundToInt().toDouble() / 100;
            '*' -> result = (firstRng * secondRng).toDouble();
            '+' -> result = (firstRng + secondRng).toDouble();
            '-' -> result = (firstRng - secondRng).toDouble();
        }
        return ResultOfEquation(firstRng, secondRng, result)
    }
    fun equationCreation(): Equation{
        val operands = arrayOf('*','/','-','+');
        val operand = operands.random();
        val listOfResults = listOf(resultGet(operand), resultGet(operand));
        val result = listOfResults.random().result;
        val equtnR = listOfResults[0]
        var equation = "${equtnR.firstNumber} ${operand} ${equtnR.secondNumber} = ${result}";
        var correctness = result == listOfResults[0].result
        return Equation(equation, correctness)
    }


    private var equation = Equation("",false);
    fun onClickStart(view: View) {
        startTimer()
        equation = equationCreation();
        binding.EquationView.setBackgroundColor(Color.WHITE);
        binding.EquationView.text = equation.equationText;
        binding.RightButton.isEnabled = true;
        binding.WrongButton.isEnabled = true;
        binding.StartButton.isEnabled = false;
    }
    fun onClickRight(view: View) {
        var total = binding.TotalNumberView.text.toString().toInt();
        total++;
        binding.TotalNumberView.text = total.toString();
        var loses = binding.WrongExampleView.text.toString().toInt();
        var wins = binding.CorrectExampleView.text.toString().toInt();
        if (equation.equationCorrectness){
            wins++;
            binding.CorrectExampleView.text = wins.toString();
            binding.EquationView.setBackgroundColor(Color.GREEN);
        }
        else{
            loses++;
            binding.WrongExampleView.text = loses.toString();
            binding.EquationView.setBackgroundColor(Color.RED);
        }
        var percentage = ((wins.toDouble() / total) * 100).roundToInt().toDouble();
        binding.txtViewPercentage.text = percentage.toString() + "%";
        binding.StartButton.isEnabled = true;
        binding.RightButton.isEnabled = false;
        binding.WrongButton.isEnabled = false;

        resetTimer();
    }
    fun onClickWrong(view: View) {
        var total = binding.TotalNumberView.text.toString().toInt();
        total++;
        binding.TotalNumberView.text = total.toString();
        var loses = binding.WrongExampleView.text.toString().toInt();
        var wins = binding.CorrectExampleView.text.toString().toInt();
        if (!equation.equationCorrectness){
            wins++;
            binding.CorrectExampleView.text = wins.toString();
            binding.EquationView.setBackgroundColor(Color.GREEN);
        }
        else{
            loses++;
            binding.WrongExampleView.text = loses.toString();
            binding.EquationView.setBackgroundColor(Color.RED);
        }
        var percentage = ((wins.toDouble() / total) * 100).roundToInt().toDouble();
        binding.txtViewPercentage.text = percentage.toString() + "%";
        binding.StartButton.isEnabled = true;
        binding.RightButton.isEnabled = false;
        binding.WrongButton.isEnabled = false;

        resetTimer();
    }
    fun textMsg(s:String,c: Context){
        Toast.makeText(c,s, Toast.LENGTH_SHORT).show()
    }
}