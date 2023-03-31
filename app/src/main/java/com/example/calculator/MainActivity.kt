package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    private  lateinit var expression : Expression


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    fun onClearclick(view: View) {
        binding.entryTv.text =""
        lastNumeric = false
    }
    fun onBkspaceclick(view: View) {
        binding.entryTv.text  = binding.entryTv.text.toString().dropLast(1)

    }
    fun onOptclick(view: View) {
        if(!stateError && lastNumeric){
            binding.entryTv.append((view as Button).text)
            lastNumeric = false
            lastDot = false
            onEquating()
        }
    }
    fun onDigitclick(view: View) {
        if(stateError){
            binding.entryTv.text = (view as Button).text
        }

        else{
            binding.entryTv.append((view as Button).text)
        }
        lastNumeric = true
        onEquating()

    }
    fun onAcclick(view: View) {
        binding.entryTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.resultTv.visibility = View.GONE
    }
    fun onEqualclick(view: View) {
        binding.entryTv.text = binding.resultTv.text.toString().drop(1)
    }
    fun onEquating(){
      if(lastNumeric && !stateError){
          var txt = binding.entryTv.text.toString()
          expression = ExpressionBuilder(txt).build()
      }
        try {
            var result = expression.evaluate()
            binding.resultTv.visibility = View.VISIBLE
            binding.resultTv.text = result.toString()
        }catch (ex : ArithmeticException){
            Log.e("Evaluation error", ex.toString())
            binding.resultTv.text ="Error occured"
            stateError = true
            lastNumeric = false
        }

    }

    fun onDotclick(view: View) {
        binding.entryTv.append((view as Button).text)
    }
}