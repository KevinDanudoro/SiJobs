package com.example.sijobs

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.sijobs.databinding.ActivityNewProfileBinding
import java.util.*

class NewProfileActivity : AppCompatActivity() {

    lateinit var binding :ActivityNewProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etDateofBirth.setOnClickListener { datePicker() }
    }

    fun datePicker(){
        //Calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
            //set to edittext
            binding.etDateofBirth.setText("$mDay/$mMonth/$mYear")
        },year,month, day)
        dpd.show();
    }
}