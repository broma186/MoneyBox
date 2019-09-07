package com.example.minimoneybox.Adaptors

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.minimoneybox.R
import com.example.minimoneybox.response.ProductResponse


class AccountsListAdapter(private val mContext: Context, private var products: ArrayList<ProductResponse>) :
        RecyclerView.Adapter<AccountsListAdapter.AccountsViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class AccountsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        }


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): AccountsListAdapter.AccountsViewHolder {
            // create a new view
           // val textView = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view, parent, false) as TextView
            // set the view's size, margins, paddings and layout parameters
            return AccountsViewHolder(parent)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = products.size


}