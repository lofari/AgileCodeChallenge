package com.example.codechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.R
import com.example.codechallenge.model.PictureDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class ModelAdapter(val modelList: List<PictureDTO>, val clickListener: OnImageClickListener) :
    RecyclerView.Adapter<ModelAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ModelViewHolder(
            layoutInflater.inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindData(modelList[position], clickListener)
//        holder.view.setOnClickListener {
//            val intent = Intent(context, DetailActivity::class.java).apply {
//                putExtra("IMAGE_URL", modelList[position].id)
//            }
//            context.startActivity(intent)
//        }
    }

    class ModelViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
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
