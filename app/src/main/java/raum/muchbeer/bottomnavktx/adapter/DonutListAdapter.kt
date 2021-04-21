package raum.muchbeer.bottomnavktx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.bottomnavktx.R
import raum.muchbeer.bottomnavktx.databinding.DonutItemBinding
import raum.muchbeer.bottomnavktx.model.Donut

class DonutListAdapter(private var onEdit: (Donut) -> Unit,
                       private var onDelete: (Donut) -> Unit) :
    ListAdapter<Donut, DonutListAdapter.DonutVH>(DonutDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonutVH {
      val layoutInflater = LayoutInflater.from(parent.context)
     val binding =   DonutItemBinding.inflate(layoutInflater, parent, false)

        return DonutVH(binding, onEdit, onDelete)
    }

    override fun onBindViewHolder(holder: DonutVH, position: Int) {
        holder.bind(getItem(position))
    }

    class DonutVH(
        private val binding: DonutItemBinding,
        private var onEdit: (Donut) -> Unit,
        private var onDelete: (Donut) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var donutId: Long = -1
        private var nameView = binding.name
        private var description = binding.description
        private var thumbnail = binding.thumbnail
        private var rating = binding.rating
        private var donut: Donut? = null

        fun bind(donut: Donut) {
            donutId = donut.id
            nameView.text = donut.name
            description.text = donut.description
            rating.text = donut.rating.toString()
            thumbnail.setImageResource(R.drawable.donut_with_sprinkles)
            this.donut = donut
            binding.deleteButton.setOnClickListener {
                onDelete(donut)
            }
            binding.root.setOnClickListener {
                onEdit(donut)
            }
        }
    }



    companion object DonutDiffUtil : DiffUtil.ItemCallback<Donut>() {
        override fun areItemsTheSame(oldItem: Donut, newItem: Donut): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Donut, newItem: Donut): Boolean {
            return oldItem == newItem
        }

    }


}
