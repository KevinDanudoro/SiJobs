package com.example.sijobs

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sijobs.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.birthInput.setOnClickListener{
            datePicker()
        }
    }

    private fun datePicker(){
        //Calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { datePicker, mYear, mMonth, mDay ->
            //set to edittext
            binding.birthInput.setText("$mDay/$mMonth/$mYear")
        },year,month, day)
        dpd.show()
    }
}