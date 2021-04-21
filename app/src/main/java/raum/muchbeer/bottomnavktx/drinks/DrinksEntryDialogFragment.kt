package raum.muchbeer.bottomnavktx.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import raum.muchbeer.bottomnavktx.Notifier
import raum.muchbeer.bottomnavktx.R
import raum.muchbeer.bottomnavktx.databinding.DonutEntryDialogBinding
import raum.muchbeer.bottomnavktx.databinding.DrinkEntryDialogBinding
import raum.muchbeer.bottomnavktx.databinding.DrinksFragmentBinding
import raum.muchbeer.bottomnavktx.donut.DonutEntryDialogFragment
import raum.muchbeer.bottomnavktx.donut.DonutEntryDialogFragmentArgs
import raum.muchbeer.bottomnavktx.donut.DonutEntryVM
import raum.muchbeer.bottomnavktx.donut.DonutVMFactory
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.Drinks
import raum.muchbeer.bottomnavktx.model.database.BreakfastDatabase

class DrinksEntryDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_DRINKS,
        EXISTING_DRINK
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DrinkEntryDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val drinksDao = BreakfastDatabase.getDatabase(requireContext()).drinksDao()
        val entryViewModel : DrinksEntryVM by viewModels {
            DrinksVMFactory(drinksDao)

        }

        val binding = DrinkEntryDialogBinding.bind(view)
        var drink: Drinks? = null

        val args : DonutEntryDialogFragmentArgs by navArgs()

        val editingState =
            if (args.itemId > 0) {
                DrinksEntryDialogFragment.EditingState.EXISTING_DRINK
            } else {
                DrinksEntryDialogFragment.EditingState.NEW_DRINKS
            }

        // If we arrived here with an itemId of >= 0, then we are editing an existing item
        if (editingState == DrinksEntryDialogFragment.EditingState.EXISTING_DRINK) {
            // Request to edit an existing item, whose id was passed in as an argument.
            // Retrieve that item and populate the UI with its details
            entryViewModel.get(args.itemId).observe(viewLifecycleOwner) { drinkItem ->
                binding.apply {
                    name.setText(drinkItem.name)
                    description.setText(drinkItem.description)
                    ratingBar.rating = drinkItem.rating.toFloat()
                }
                drink = drinkItem
            }
        }

        // When the user clicks the Done button, use the data here to either update
        // an existing item or create a new one
        binding.doneButton.setOnClickListener {
            // Grab these now since the Fragment may go away before the setupNotification
            // lambda below is called
            val context = requireContext().applicationContext
            val navController = findNavController()

            entryViewModel.addData(
                drink?.id ?: 0,
                binding.name.text.toString(),
                binding.description.text.toString(),
                binding.ratingBar.rating.toInt()
            ) { actualId ->
                val arg = DonutEntryDialogFragmentArgs(actualId).toBundle()
                val pendingIntent = navController
                    .createDeepLink()
                    .setDestination(R.id.donutEntryDialogFragment)
                    .setArguments(arg)
                    .createPendingIntent()

                Notifier.postNotification(actualId, context, pendingIntent)
            }
            dismiss()
        }
        // User clicked the Cancel button; just exit the dialog without saving the data
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}