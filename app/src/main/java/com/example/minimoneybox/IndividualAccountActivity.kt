package com.example.minimoneybox

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.minimoneybox.Constants.AUTH_TOKEN_KEY
import com.example.minimoneybox.Constants.FIXED_TOP_UP_AMOUNT
import com.example.minimoneybox.Constants.PRODUCT_RESPONSE_KEY
import com.example.minimoneybox.Request.TopUpRequest
import com.example.minimoneybox.api.MoneyBoxApiService
import com.example.minimoneybox.response.ProductResponse
import com.example.minimoneybox.response.TopUpResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.ResponseBody
import java.util.*

class IndividualAccountActivity : AppCompatActivity(){

    private lateinit var accountName : TextView
    private lateinit var planValueTitle : TextView
    private lateinit var planValue : TextView
    private lateinit var moneyBoxTitle : TextView
    private lateinit var moneyBoxValue : TextView
    private var account: ProductResponse? = null
    private lateinit var topUpButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_accounts)
        setupViews()
    }

    private fun setupViews() {

        val userAccountsIntentExtras : Bundle? = intent.extras;
        if (userAccountsIntentExtras?.containsKey(PRODUCT_RESPONSE_KEY)!!) {
            account = intent.getParcelableExtra(PRODUCT_RESPONSE_KEY)
            accountName = findViewById(R.id.individual_account_name)
            accountName.setText(account?.product?.friendlyName)

            planValueTitle = findViewById(R.id.individual_plan_value_title)
            planValueTitle.visibility = View.VISIBLE
            planValue = findViewById(R.id.individual_plan_value_value)
            planValue.setText(account?.planValue)

            moneyBoxTitle = findViewById(R.id.individual_money_box_title)
            moneyBoxTitle.visibility = View.VISIBLE
            moneyBoxValue = findViewById(R.id.individual_money_box_value)
            moneyBoxValue.setText(account?.moneyBox)


            topUpButton = findViewById(R.id.top_up_button)
            topUpButton.setOnClickListener(View.OnClickListener { v ->
                doTopUp()
            })
        }

    }

    private fun doTopUp() {
        val observable = MoneyBoxApiService.topUpApiCall().topUp(intent.getStringExtra(AUTH_TOKEN_KEY), TopUpRequest(FIXED_TOP_UP_AMOUNT, account?.id))
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ topUpResponse: TopUpResponse ->
                account?.moneyBox = topUpResponse?.moneyBox
                moneyBoxValue.setText(topUpResponse?.moneyBox)
                val msg = "Sent " + Currency.getInstance(Locale.UK).getCurrencyCode() + FIXED_TOP_UP_AMOUNT + " to account " + account?.product?.friendlyName
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }, { error ->
                val msg = "Failed to top up " + account?.product?.friendlyName + " with error: " + error.message
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            })
    }

}