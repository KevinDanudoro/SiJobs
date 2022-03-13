package com.example.sijobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sijobs.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Jika user belum login akan dipindahkan ke halaman login
        checkUser()

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val jobFragment = JobFragment()
        val chatFragment = ChatFragment()
        val profileFragment = ProfileFragment()

        // to set homeFragment as main fragment
        setCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miSearch -> setCurrentFragment(searchFragment)
                R.id.miJob -> setCurrentFragment(jobFragment)
                R.id.miChat -> setCurrentFragment(chatFragment)
                R.id.miProfile -> setCurrentFragment(profileFragment)
            }
            true
        }
    }

    //function to set fragment
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_Fragmment, fragment)
            commit()
        }


    // Cek user sudah login atau belum
    private fun checkUser() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, GetStartedActivity::class.java))
            finish()
        }
    }
}