package com.example.demoapp.ui.pokemon.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.databinding.ItemPokemonFavoriteBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.util.layoutInflater
import com.example.demoapp.util.load
import com.example.demoapp.util.onClick

class PokemonFavoritesAdapter(
    private val clickListener: PokemonClickListener,
) : ListAdapter<Pokemon, PokemonFavoritesAdapter.PokemonViewHolder>(Pokemon.diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = PokemonViewHolder.from(parent)

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class PokemonViewHolder(private val binding: ItemPokemonFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            pokemon: Pokemon,
            clickListener: PokemonClickListener
        ) = binding.apply {
            tvName.text = pokemon.name
            ivIcon.load(pokemon.image)
            root.onClick { clickListener.onClickPokemon(pokemon.id) }
            root.setOnLongClickListener {
                clickListener.onLongClickPokemon(pokemon); true
            }
        }

        companion object {
            fun from(parent: ViewGroup) = PokemonViewHolder(
                ItemPokemonFavoriteBinding.inflate(parent.layoutInflater, parent, false)
            )
        }
    }

    interface PokemonClickListener {
        fun onLongClickPokemon(pokemon: Pokemon)
        fun onClickPokemon(pokemonId: String)
    }
}
