package ru.maxmv.notes.presentation.notes_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.maxmv.notes.data.Note
import ru.maxmv.notes.databinding.ItemNoteBinding

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
    private val items = mutableListOf<Note>()

    var onItemClick: ((Note) -> Unit)? = null

    fun setItems(items: List<Note>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onViewRecycled(holder: NoteViewHolder) {
        holder.unbind()
    }

    override fun getItemCount() = items.size

    inner class NoteViewHolder(
        private var binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(Note: Note) {
            itemView.setOnClickListener {
                onItemClick?.invoke(Note)
            }
            binding.apply {
                textViewTitle.text = Note.title
            }
        }

        fun unbind() {
            itemView.setOnClickListener(null)
        }
    }
}