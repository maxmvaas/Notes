package ru.maxmv.notes.presentation.notes_list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.google.android.material.dialog.MaterialAlertDialogBuilder

import org.koin.androidx.viewmodel.ext.android.viewModel

import ru.maxmv.notes.R
import ru.maxmv.notes.data.Note
import ru.maxmv.notes.databinding.FragmentNotesListBinding
import ru.maxmv.notes.presentation.notes_list.adapter.NoteListAdapter

class NotesListFragment : Fragment() {
    private val viewModel by viewModel<NotesListViewModel>()

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val colors = mutableListOf<Int>()

    private val adapter = NoteListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadColors()

        binding.recyclerView.adapter = adapter

        viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
            var color = 0
            notes.forEach { note ->
                if (color == 6) {
                    color = 0
                }
                note.color = colors[color]
                ++color
            }
            notes?.let { showNotes(notes) }
        }

        binding.fabAdd.setOnClickListener {
            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteAddFragment()
            findNavController().navigate(action)
        }

        adapter.onItemClick = { note ->
            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteAddFragment(note)
            findNavController().navigate(action)
        }

        adapter.buttonDeleteClick = { note ->
            viewModel.deleteNote(note.id)
            adapter.removeNote(note)
        }

        binding.buttonInfo.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setView(R.layout.popup_info)
                .setBackground(ColorDrawable(Color.TRANSPARENT))
                .show()
        }

        binding.buttonSearch.setOnClickListener {
            setStateSearch()
        }

        binding.searchField.textViewInput.addTextChangedListener {
            binding.textViewNotFound.visibility = View.GONE
            adapter.filter(it.toString())
            if (adapter.itemCount == 0) {
                binding.textViewNotFound.visibility = View.VISIBLE
            }
            if (it.toString().isBlank()) {
                binding.searchField.root.setEndIconOnClickListener {
                    setDefaultState()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showNotes(noteList: List<Note>) {
        adapter.setItems(noteList)
        if (noteList.isEmpty()) {
            binding.textViewEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.textViewEmpty.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun loadColors() {
        colors.apply {
            add(getColor(requireContext(), R.color.first))
            add(getColor(requireContext(), R.color.second))
            add(getColor(requireContext(), R.color.third))
            add(getColor(requireContext(), R.color.fourth))
            add(getColor(requireContext(), R.color.fifth))
            add(getColor(requireContext(), R.color.sixth))
        }
    }

    private fun setDefaultState() {
        binding.apply {
            buttonInfo.visibility = View.VISIBLE
            buttonSearch.visibility = View.VISIBLE
            textViewTitle.visibility = View.VISIBLE
            searchField.root.visibility = View.GONE
        }
    }

    private fun setStateSearch() {
        binding.apply {
            buttonInfo.visibility = View.GONE
            buttonSearch.visibility = View.GONE
            textViewTitle.visibility = View.GONE
            textViewEmpty.visibility = View.GONE
            searchField.root.visibility = View.VISIBLE
            searchField.textViewInput.setText("")
        }
    }
}