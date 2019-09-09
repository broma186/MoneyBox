package com.example.minimoneybox

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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

class IndividualAccountActivity : AppCompatActivity(){

    private lateinit var accountName : TextView
    private lateinit var planValueTitle : TextView
    private lateinit var planValue : TextView
    private lateinit var moneyBoxTitle : TextView
    private lateinit var moneyBoxValue : TextView
    private var account: ProductResponse? = null
    private var authToken : String? = null
    private lateinit var topUpButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_accounts)
        setupViews()
    }

    companion object {
        val TAG = "IndividualAccount"
    }

    private fun setupViews() {

        val userAccountsIntentExtras : Bundle? = intent.extras;
        if (userAccountsIntentExtras?.containsKey(PRODUCT_RESPONSE_KEY)!!) {
            account = intent.getParcelableExtra(PRODUCT_RESPONSE_KEY)
            authToken = intent.getStringExtra(AUTH_TOKEN_KEY)
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


            topUpButton = findViewById(R.id.top_up_button)
            topUpButton.setOnClickListener(View.OnClickListener { v ->
                doTopUp()
            })
        }

    }

    private fun doTopUp() {
        val observable = MoneyBoxApiService.topUpApiCall().topUp(authToken, TopUpRequest(FIXED_TOP_UP_AMOUNT, account?.product?.id))
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ topUpResponse: TopUpResponse? ->
                Log.d(TAG,"top up success, first product id is: " + topUpResponse?.text)
            }, { error ->
                Log.d(TAG,"top up failure: " + error?.message)
            })
    }

}