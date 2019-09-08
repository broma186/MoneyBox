package com.example.minimoneybox.Adaptors

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
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


class AccountsListAdapter(private val mContext: Context, private var products: ArrayList<ProductResponse>) :
        RecyclerView.Adapter<AccountsListAdapter.AccountsViewHolder>() {

        private val TAG: String = "AccountsListAdapter"
        private val inflater: LayoutInflater = LayoutInflater.from(mContext)
        private var itemView: View? = null

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): AccountsListAdapter.AccountsViewHolder {
                // create a new view
                // val textView = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view, parent, false) as TextView
                // set the view's size, margins, paddings and layout parameters
                itemView = inflater.inflate(R.layout.layout_accounts_item, parent, false)
                return AccountsViewHolder(itemView!!)
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class AccountsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }



        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
                holder.itemView.account_name.text = products[position].product?.friendlyName
                holder.itemView.plan_value_value.text = products[position].planValue
                holder.itemView.money_box_value.text = products[position].moneyBox

                holder.itemView.setOnClickListener({View.OnClickListener {
                        val intent = Intent(mContext, IndividualAccountActivity::class.java)
                        intent.putExtra(PRODUCT_RESPONSE_KEY, products[position])
                        mContext.startActivity(intent)
                }})
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = products.size
}