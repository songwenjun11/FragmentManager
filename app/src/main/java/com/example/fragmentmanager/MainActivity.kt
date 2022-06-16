package com.example.fragmentmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.note.RegisterFragment
import com.model.fragmentmanager.tools.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val name = OneFragment::class.java.name
        Log.e("asdasda", name)
        findViewById<TextView>(R.id.tv).setOnClickListener {
            FragmentManager.startFragment(Intent(this, TwoFragment::class.java))
        }
    }
}