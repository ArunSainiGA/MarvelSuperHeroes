package com.example.marvelsuperheros.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.example.domain.model.SuperHeroModel
import com.example.marvelsuperheros.R
import com.example.marvelsuperheros.ext.loadUrl

class ListAdapter constructor(val items: List<SuperHeroModel> = emptyList()): DragDropSwipeAdapter<SuperHeroModel, ListAdapter.ViewHolder>(items) {
    class ViewHolder(itemView: View): DragDropSwipeAdapter.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.name)
        val descriptionTV: TextView = itemView.findViewById(R.id.description)
        val profilePicture: ImageView = itemView.findViewById(R.id.profilePicture)
    }

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    override fun getViewToTouchToStartDraggingItem(item: SuperHeroModel, viewHolder: ViewHolder, position: Int): View? {
        return null
    }

    override fun onBindViewHolder(item: SuperHeroModel, viewHolder: ViewHolder, position: Int) {
        viewHolder.nameTV.text = item.name
        viewHolder.descriptionTV.text = item.description
        viewHolder.profilePicture.loadUrl(item.image, R.drawable.ic_placeholder_24)
    }

    override fun canBeDragged(item: SuperHeroModel, viewHolder: ViewHolder, position: Int): Boolean {
        return false
    }

    override fun canBeSwiped(item: SuperHeroModel, viewHolder: ViewHolder, position: Int): Boolean {
        return true
    }

    override fun canBeDroppedOver(item: SuperHeroModel, viewHolder: ViewHolder, position: Int): Boolean {
        return false
    }
}