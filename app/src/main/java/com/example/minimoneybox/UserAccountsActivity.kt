package com.example.minimoneybox

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.minimoneybox.Adaptors.AccountsListAdapter
import com.example.minimoneybox.Constants.AUTH_TOKEN_KEY
import com.example.minimoneybox.Constants.FULL_NAME_KEY
import com.example.minimoneybox.Constants.HELLO_CONSTANT
import com.example.minimoneybox.Constants.MONEYBOX_RESULT
import com.example.minimoneybox.Constants.PLAN_VALUE_KEY
import com.example.minimoneybox.response.ProductResponse
import org.w3c.dom.Text

// Displays the logged in user's investments scemes and various accounts.
class UserAccountsActivity : AppCompatActivity() {

    private lateinit var helloFullName : TextView
    private lateinit var planValueTitle : TextView
    private lateinit var planValueText : TextView
    private lateinit var accountsRecyclerAdapter: AccountsListAdapter
    private lateinit var accountsViewLayoutManager: RecyclerView.LayoutManager
    private var accountList: ArrayList<ProductResponse> = arrayListOf()
    private  lateinit var accountsRecyclerView : RecyclerView
    private var authToken : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)
        setupViews()
    }

    private fun setupViews() {
        helloFullName = findViewById(R.id.hello_full_name)
        var loginIntentExtras : Bundle? = intent.extras;
        if (loginIntentExtras?.containsKey(FULL_NAME_KEY)!!) {
            helloFullName.visibility = View.VISIBLE
            helloFullName.setText(HELLO_CONSTANT + loginIntentExtras.getString(FULL_NAME_KEY) + "!")
        }
        planValueTitle = findViewById(R.id.total_plan_value_title)
        planValueText = findViewById(R.id.total_plan_value_text)
        if (loginIntentExtras.containsKey(PLAN_VALUE_KEY)) {
            planValueTitle.visibility = View.VISIBLE
            planValueText.visibility = View.VISIBLE
            planValueText.text = loginIntentExtras.getString(PLAN_VALUE_KEY)
        }
        authToken = loginIntentExtras.getString(AUTH_TOKEN_KEY)
        accountList = loginIntentExtras.getParcelableArrayList(Constants.PRODUCT_RESPONSES_KEY)
        setupAccountsList();
    }

    private fun setupAccountsList() {
        accountsRecyclerView =  findViewById(R.id.user_accounts_list)
        accountsViewLayoutManager = LinearLayoutManager(this)
        accountsRecyclerAdapter = AccountsListAdapter(this, accountList, authToken)
        accountsRecyclerView.adapter = accountsRecyclerAdapter
        accountsRecyclerView.layoutManager = accountsViewLayoutManager
    }

    // Take the user back to the log in screen? No
    override fun onBackPressed() {
        // Do nothing
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        if (intent.getStringExtra(MONEYBOX_RESULT) != null) {

        }
    }
}