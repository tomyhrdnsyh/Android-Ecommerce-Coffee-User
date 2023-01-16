package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        val btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_logout, null)
            val myDialog = Dialog(this)
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            val btn_acc_logout = dialogBinding.findViewById<Button>(R.id.btn_accept_logout)
            btn_acc_logout.setOnClickListener{
                startActivity(Intent(this@AccountActivity, LoginActivity::class.java))
            }

            val btn_cancel_logout = dialogBinding.findViewById<Button>(R.id.btn_cancel_logout)
            btn_cancel_logout.setOnClickListener{
                startActivity(Intent(this@AccountActivity, MainActivity::class.java))
            }
        }
    }
}