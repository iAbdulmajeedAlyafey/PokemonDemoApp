package com.example.demoapp.domain.pokemon.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: String,
    val name: String,
    val artist: String,
    val healthPoints: Int,
    val image: String,
    var isFavorite: Boolean,
) : Parcelable {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(
                oldItem: Pokemon,
                newItem: Pokemon,
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Pokemon,
                newItem: Pokemon,
            ) = oldItem == newItem
        }
    }
}