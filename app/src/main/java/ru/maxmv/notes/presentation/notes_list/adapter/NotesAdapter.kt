package ru.maxmv.notes.presentation.notes_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView

import ru.maxmv.notes.data.Note
import ru.maxmv.notes.databinding.ItemNoteBinding
import ru.maxmv.notes.presentation.notes_list.NoteDiffCallback

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
    private var items = mutableListOf<Note>()

    var onItemClick: ((Note) -> Unit)? = null

    fun setItems(items: List<Note>) {
        val diffResult = calculateDiff(NoteDiffCallback(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onViewRecycled(holder: NoteViewHolder) {
        holder.unbind()
    }

    override fun getItemCount() = items.size

    inner class NoteViewHolder(
        private var binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
            binding.apply {
                textViewTitle.text = note.title
                cardView.setCardBackgroundColor(note.color)
            }
        }

        fun unbind() {
            itemView.setOnClickListener(null)
        }
    }
}