package raum.muchbeer.bottomnavktx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import raum.muchbeer.bottomnavktx.databinding.FragmentSelectionBinding

class SelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSelectionBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = FragmentSelectionBinding.bind(view)

        binding.button.setOnClickListener {
            val coffeeEnabled = binding.checkBox.isSelected

            it.findNavController().navigate(
                SelectionFragmentDirections.actionSelectionFragment2ToDonutFragment()
            )

        }
    }
}