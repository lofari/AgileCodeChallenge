package com.example.codechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.R
import com.example.codechallenge.model.PictureDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class PictureAdapter(val modelList: List<PictureDTO>, val clickListener: OnImageClickListener) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PictureViewHolder(
            layoutInflater.inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bindData(modelList[position], clickListener)
    }

    class PictureViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(model: PictureDTO, action: OnImageClickListener) {
            Picasso.get().load(model.image).into(view.item_image)
            view.setOnClickListener {
                action.onItemClick(model, adapterPosition)
            }
        }
    }
}

interface OnImageClickListener{
    fun onItemClick(item: PictureDTO, position: Int)
}
