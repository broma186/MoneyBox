package com.example.minimoneybox

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.minimoneybox.Constants.FULL_NAME_KEY
import com.example.minimoneybox.Constants.PLAN_VALUE_KEY
import org.w3c.dom.Text

class UserAccountsActivity : AppCompatActivity() {

    lateinit var helloFullName : TextView
    lateinit var planValueTitle : TextView
    lateinit var planValueText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)
        setTitle(getString(R.string.user_accounts_title))
        setupViews()
    }

    private fun setupViews() {
        helloFullName = findViewById(R.id.hello_full_name)
        var loginIntentExtras : Bundle? = intent.extras;
        if (loginIntentExtras?.containsKey(FULL_NAME_KEY)!!) {
            helloFullName.visibility = View.VISIBLE
            helloFullName.setText("Hello " + loginIntentExtras.getString(FULL_NAME_KEY) + "!")
        }
        planValueTitle = findViewById(R.id.total_plan_value_title)
        planValueText = findViewById(R.id.total_plan_value_text)
        if (loginIntentExtras.containsKey(PLAN_VALUE_KEY)) {
            planValueTitle.visibility = View.VISIBLE
            planValueText.visibility = View.VISIBLE
            planValueText.text = loginIntentExtras.getString(PLAN_VALUE_KEY)
        }
    }
    
}