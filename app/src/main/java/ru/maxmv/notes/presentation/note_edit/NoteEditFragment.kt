package ru.maxmv.notes.presentation.note_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import org.koin.androidx.viewmodel.ext.android.viewModel

import ru.maxmv.notes.data.Note
import ru.maxmv.notes.data.db.NoteEntity
import ru.maxmv.notes.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment() {

    //TODO: Сделать кнопку предпросмотра доступной.

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
            binding.editTextTitle.setText(note.title)
            binding.editTextContent.setText(note.text)
            binding.buttonEdit.setOnClickListener {
                setStateAdd()
            }
            binding.buttonSave.setOnClickListener {
                if (binding.editTextTitle.text.isNotBlank()) {
                    val noteToAdd = NoteEntity(
                        note.id,
                        binding.editTextTitle.text.toString(),
                        binding.editTextContent.text.toString()
                    )
                    viewModel.updateNote(noteToAdd)
                }
            }
        } else {
            setStateAdd()
            binding.buttonSave.setOnClickListener {
                if (binding.editTextTitle.text.isNotBlank()) {
                    viewModel.addNote(
                        Note(
                            0,
                            binding.editTextTitle.text.toString(),
                            binding.editTextContent.text.toString(),
                            0
                        )
                    )
                }
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

    private fun setStateEdit() {
        binding.buttonSave.visibility = View.GONE
        binding.editTextContent.hint = ""
        binding.buttonPreview.visibility = View.GONE
        binding.buttonEdit.visibility = View.VISIBLE
        binding.editTextTitle.isEnabled = false
        binding.editTextContent.isEnabled = false
    }

    private fun setStateAdd() {
        binding.buttonSave.visibility = View.VISIBLE
        binding.buttonPreview.visibility = View.VISIBLE
        binding.buttonEdit.visibility = View.GONE
        binding.editTextTitle.isEnabled = true
        binding.editTextContent.isEnabled = true
    }
}