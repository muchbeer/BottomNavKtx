package raum.muchbeer.bottomnavktx.donut

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
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.database.BreakfastDatabase

class DonutEntryDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_DONUT,
        EXISTING_DONUT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DonutEntryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val donutDao = BreakfastDatabase.getDatabase(requireContext()).donutDao()
        val entryViewModel : DonutEntryVM by viewModels {
            DonutVMFactory(donutDao)
        }

        val binding = DonutEntryDialogBinding.bind(view)
        var donut: Donut? = null

        val args : DonutEntryDialogFragmentArgs by navArgs()

        val editingState =
            if (args.itemId > 0) {
                EditingState.EXISTING_DONUT
            } else {
                EditingState.NEW_DONUT
            }

        // If we arrived here with an itemId of >= 0, then we are editing an existing item
        if (editingState == EditingState.EXISTING_DONUT) {
            // Request to edit an existing item, whose id was passed in as an argument.
            // Retrieve that item and populate the UI with its details
            entryViewModel.get(args.itemId).observe(viewLifecycleOwner) { donutItem ->
                binding.apply {
                    name.setText(donutItem.name)
                    description.setText(donutItem.description)
                    ratingBar.rating = donutItem.rating.toFloat()
                }
                donut = donutItem
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
                donut?.id ?: 0,
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