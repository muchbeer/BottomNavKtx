package raum.muchbeer.bottomnavktx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.bottomnavktx.R
import raum.muchbeer.bottomnavktx.databinding.DrinkItemBinding
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.Drinks

class DrinkListAdapter(
    private var onEdit: (Drinks) -> Unit,
    private var onDelete: (Drinks) -> Unit
) : ListAdapter<Drinks, DrinkListAdapter.DrinkVH>(DrinkDiffUtil){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DrinkItemBinding.inflate(layoutInflater, parent, false)

        return DrinkVH(onEdit, onDelete, binding)
    }

    override fun onBindViewHolder(holder: DrinkVH, position: Int) {
        holder.bind(getItem(position))
    }



    class DrinkVH (
        private var onEdit: (Drinks) -> Unit,
        private var onDelete: (Drinks) -> Unit,
        private val binding : DrinkItemBinding
            ) : RecyclerView.ViewHolder(binding.root){

        private var drinkId: Long = -1
        private var nameView = binding.name
        private var description = binding.description
        private var thumbnail = binding.thumbnail
        private var rating = binding.rating
        private var drink: Drinks? = null

        fun bind(drinks: Drinks) {
            drinkId = drinks.id
            nameView.text = drinks.name
            description.text = drinks.description
            rating.text = drinks.rating.toString()
            thumbnail.setImageResource(R.drawable.donut_with_sprinkles)
            this.drink = drinks
            binding.deleteButton.setOnClickListener {
                onDelete(drinks)
            }
            binding.root.setOnClickListener {
                onEdit(drinks)
            }
        }
    }

  companion object DrinkDiffUtil : DiffUtil.ItemCallback<Drinks>() {
      override fun areItemsTheSame(oldItem: Drinks, newItem: Drinks): Boolean {
       return   oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Drinks, newItem: Drinks): Boolean {
          return oldItem == newItem
      }

  }
}