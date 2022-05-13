package ru.maxmv.notes.presentation.notes_list

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import org.koin.androidx.viewmodel.ext.android.viewModel

import ru.maxmv.notes.R
import ru.maxmv.notes.data.Note
import ru.maxmv.notes.databinding.FragmentNotesListBinding
import ru.maxmv.notes.presentation.notes_list.adapter.NoteListAdapter

class NotesListFragment : Fragment() {

    //TODO: Сделать поиск заметок, добавить контент в кнопку info.

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
            binding.buttonInfo.visibility = View.GONE
            binding.buttonSearch.visibility = View.GONE
            binding.textViewTitle.visibility = View.GONE

            binding.searchField.root.visibility = View.VISIBLE

            binding.textViewEmpty.visibility = View.GONE

            binding.textViewNotFound.visibility = View.VISIBLE

            binding.recyclerView.visibility = View.GONE
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
        colors.add(getColor(requireContext(), R.color.first))
        colors.add(getColor(requireContext(), R.color.second))
        colors.add(getColor(requireContext(), R.color.third))
        colors.add(getColor(requireContext(), R.color.fourth))
        colors.add(getColor(requireContext(), R.color.fifth))
        colors.add(getColor(requireContext(), R.color.sixth))
    }
}