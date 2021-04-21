package raum.muchbeer.bottomnavktx.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import raum.muchbeer.bottomnavktx.adapter.DrinkListAdapter
import raum.muchbeer.bottomnavktx.databinding.DrinksFragmentBinding
import raum.muchbeer.bottomnavktx.model.database.BreakfastDatabase

class DrinksFragment : Fragment() {

    private lateinit var drinksListViewModel: DrinksVM

    private val adapter = DrinkListAdapter(
        onEdit = { drink ->
            findNavController().navigate(
                DrinksFragmentDirections.actionDrinksFragmentToDrinksEntryDialogFragment(drink.id)
            )
        },
        onDelete = { drink ->
            NotificationManagerCompat.from(requireContext()).cancel(drink.id.toInt())
            drinksListViewModel.delete(drink)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DrinksFragmentBinding.bind(view)
        val drinksDao = BreakfastDatabase.getDatabase(requireContext()).drinksDao()

        val drinkViewModel : DrinksVM by viewModels {
            DrinksVMFactory(drinksDao)
        }

        drinkViewModel.drinks.observe(viewLifecycleOwner)  {drinks ->
            adapter.submitList(drinks)
        }
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener { fabView ->
            fabView.findNavController().navigate(
                DrinksFragmentDirections.actionDrinksFragmentToDrinksEntryDialogFragment()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DrinksFragmentBinding.inflate(inflater, container, false).root
    }
}