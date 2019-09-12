package com.example.minimoneybox.Adaptors

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.JsonToken
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.minimoneybox.Constants.PRODUCT_RESPONSES_KEY
import com.example.minimoneybox.Constants.PRODUCT_RESPONSE_KEY
import com.example.minimoneybox.IndividualAccountActivity
import com.example.minimoneybox.R
import com.example.minimoneybox.response.ProductResponse
import kotlinx.android.synthetic.main.layout_accounts_item.view.*
import android.widget.Toast
import android.util.Log
import com.example.minimoneybox.Constants.AUTH_TOKEN_KEY
import com.example.minimoneybox.Constants.PASS_BACK_MONEYBOX_RESULT


/*
The adapter used for the user accounts screen's recyclerView.
 */
class AccountsListAdapter(private val mContext: AppCompatActivity, private val products: ArrayList<ProductResponse>, private val authToken: String?) :
        RecyclerView.Adapter<AccountsListAdapter.AccountsViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(mContext)
        private var itemView: View? = null


        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): AccountsListAdapter.AccountsViewHolder {
                itemView = inflater.inflate(com.example.minimoneybox.R.layout.layout_accounts_item, parent, false)
                return AccountsViewHolder(itemView!!)
        }

        class AccountsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

        override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
                holder.itemView.account_name.text = products[position].product?.friendlyName
                holder.itemView.plan_value_value.text = products[position].planValue
                holder.itemView.money_box_value.text = products[position].moneyBox


                holder.itemView.setOnClickListener(View.OnClickListener { v ->
                        takeUserToIndividualAccount(position)
                })
        }

        // Takes user to the account selected on individual accounts screen.
        fun takeUserToIndividualAccount(position: Int) {
                val intent = Intent(mContext, IndividualAccountActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtra(PRODUCT_RESPONSE_KEY, products[position])
                intent.putExtra(AUTH_TOKEN_KEY, authToken)
                mContext.startActivityForResult(intent, PASS_BACK_MONEYBOX_RESULT)
        }

        override fun getItemCount() = products.size
}