package com.example.minimoneybox

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.minimoneybox.Constants.PRODUCT_RESPONSE_KEY
import com.example.minimoneybox.response.ProductResponse

class IndividualAccountActivity : AppCompatActivity(){

    private lateinit var accountName : TextView
    private lateinit var planValueTitle : TextView
    private lateinit var planValue : TextView
    private lateinit var moneyBoxTitle : TextView
    private lateinit var moneyBoxValue : TextView
    private var account: ProductResponse? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)
        setupViews()
    }

    private fun setupViews() {

        val userAccountsIntentExtras : Bundle? = intent.extras;
        if (userAccountsIntentExtras?.containsKey(PRODUCT_RESPONSE_KEY)!!) {
            account = intent.getParcelableExtra(PRODUCT_RESPONSE_KEY)

            accountName = findViewById(R.id.individual_accounts_title)
            accountName.setText(account?.product?.friendlyName)

            planValueTitle = findViewById(R.id.individual_plan_value_title)
            planValueTitle.visibility = View.VISIBLE
            planValue = findViewById(R.id.individual_plan_value_value)
            planValue.setText(account?.planValue)

            moneyBoxTitle = findViewById(R.id.individual_money_box_title)
            moneyBoxTitle.visibility = View.VISIBLE
            moneyBoxValue = findViewById(R.id.individual_money_box_value)
            moneyBoxValue.setText(account?.moneyBox)
        }

    }

}