package raum.muchbeer.bottomnavktx.donut

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import raum.muchbeer.bottomnavktx.adapter.DonutListAdapter
import raum.muchbeer.bottomnavktx.databinding.DonutFragmentBinding
import raum.muchbeer.bottomnavktx.model.database.BreakfastDatabase

class DonutFragment : Fragment() {

    private lateinit var donutListViewModel: DonutVM

    private val adapter = DonutListAdapter(
        onEdit = { donut ->
            findNavController().navigate(
                DonutFragmentDirections.actionDonutFragmentToDonutEntryDialogFragment(donut.id)
            )
        },
        onDelete = { donut ->
            NotificationManagerCompat.from(requireContext()).cancel(donut.id.toInt())
            donutListViewModel.delete(donut)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DonutFragmentBinding.bind(view)
        val donutDao = BreakfastDatabase.getDatabase(requireContext()).donutDao()

        val donutViewModel : DonutVM by viewModels {
            DonutVMFactory(donutDao)
        }

        donutViewModel.donuts.observe(viewLifecycleOwner)  {donuts ->
            adapter.submitList(donuts)
        }
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener { fabView ->
            fabView.findNavController().navigate(
                DonutFragmentDirections.actionDonutFragmentToDonutEntryDialogFragment()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DonutFragmentBinding.inflate(inflater, container, false).root
    }
}