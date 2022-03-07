package com.example.sijobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Fitur Pindah halaman ke ActivityNewProfile
        button.setOnClickListener {
            // ubah editable text ke string
            val username = usernameInput.text.toString()
            val password = passInput.text.toString()

            // Buat intent untuk mengarahkan activity ini ke newProfileActivity
            Intent(this, ProfileActivity::class.java).also{
                // Pasangan antara key dan value untuk mengirimkan data pada editable text ke activity lain
                it.putExtra("USERNAME", username)
                it.putExtra("PASSWORD", password)

                startActivity(it) // Pindah ke halaman yg diatur Intent
            }
        }
        // Akhir fitur pindah halaman ke ActivityNewProfile



    }
}