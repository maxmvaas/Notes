package ru.maxmv.notes.presentation.note_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.maxmv.notes.R

import ru.maxmv.notes.data.Note
import ru.maxmv.notes.data.db.NoteEntity
import ru.maxmv.notes.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment() {

    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!

    private val args: NoteEditFragmentArgs by navArgs()

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

        val note = args.noteArg

        if (note != null) {
            setStateEdit()
            binding.apply {
                editTextTitle.setText(note.title)
                editTextContent.setText(note.text)
                buttonEdit.setOnClickListener {
                    setStateAdd()
                }
                buttonSave.setOnClickListener {
                    if (editTextTitle.text.isNotBlank()) {
                        if (editTextTitle.text.toString() != args.noteArg?.title || editTextContent.text.toString() != args.noteArg?.text) {
                            val noteToAdd = NoteEntity(
                                note.id,
                                editTextTitle.text.toString(),
                                editTextContent.text.toString()
                            )
                            viewModel.updateNote(noteToAdd)
                        }
                    }
                }
            }
        } else {
            setStateAdd()
            binding.apply {
                buttonSave.setOnClickListener {
                    if (editTextTitle.text.isNotBlank()) {
                        viewModel.addNote(
                            Note(
                                0,
                                editTextTitle.text.toString(),
                                editTextContent.text.toString(),
                                0
                            )
                        )
                    }
                }
            }
        }

        binding.buttonBack.root.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setStateEdit() {
        binding.apply {
            buttonSave.visibility = View.GONE
            editTextContent.hint = ""
            buttonEdit.visibility = View.VISIBLE
            editTextTitle.isEnabled = false
            editTextContent.isEnabled = false
        }
    }

    private fun setStateAdd() {
        binding.apply {
            buttonSave.visibility = View.VISIBLE
            buttonEdit.visibility = View.GONE
            editTextTitle.isEnabled = true
            editTextContent.isEnabled = true
            if (editTextContent.text.isBlank()) {
                editTextContent.hint = getString(R.string.type_something)
            }
        }
    }
}