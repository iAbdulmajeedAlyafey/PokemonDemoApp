package com.example.demoapp.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.databinding.ItemPokemonBinding
import com.example.demoapp.domain.pokemon.model.Pokemon
import com.example.demoapp.util.layoutInflater
import com.example.demoapp.util.load
import com.example.demoapp.util.onClick

class PokemonSearchAdapter(
    private val clickListener: PokemonClickListener,
) : ListAdapter<Pokemon, PokemonSearchAdapter.PokemonViewHolder>(PokemonDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = PokemonViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), clickListener)
    }

    class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            pokemon: Pokemon,
            clickListener: PokemonClickListener
        ) = binding.apply {
            tvName.text = pokemon.name
            ivIcon.load(pokemon.image)
            btnSave.onClick { clickListener.onClickSavePokemon(pokemon) }
            root.onClick { clickListener.onClickPokemon(pokemon.id) }
        }

        companion object {
            fun from(parent: ViewGroup) = PokemonViewHolder(
                ItemPokemonBinding.inflate(parent.layoutInflater, parent, false)
            )
        }
    }

    private object PokemonDiffUtil : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(
            oldItem: Pokemon,
            newItem: Pokemon,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Pokemon,
            newItem: Pokemon,
        ) = oldItem == newItem
    }

    interface PokemonClickListener {
        fun onClickPokemon(pokemonId: String)
        fun onClickSavePokemon(pokemon: Pokemon)
    }
}
