package ru.maxmv.notes.presentation.note_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import org.koin.androidx.viewmodel.ext.android.viewModel

import ru.maxmv.notes.data.Note
import ru.maxmv.notes.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment() {

    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<NoteEditViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            if (binding.editTextTitle.text.isNotBlank()) {
                viewModel.addNote(
                    Note(
                        binding.editTextTitle.text.toString(),
                        binding.editTextContent.text.toString(),
                        0
                    )
                )
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}