package ru.maxmv.notes.presentation.notes_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView

import ru.maxmv.notes.data.Note
import ru.maxmv.notes.databinding.ItemNoteBinding
import ru.maxmv.notes.presentation.notes_list.NoteDiffCallback

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    private var items = mutableListOf<Note>()

    private var copyItems = mutableSetOf<Note>()

    var onItemClick: ((Note) -> Unit)? = null

    var buttonDeleteClick: ((Note) -> Unit)? = null

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

    fun removeNote(note: Note) {
        val indexToDelete = items.indexOfFirst { it.id == note.id }
        if (indexToDelete != -1) {
            items.removeAt(indexToDelete)
            notifyItemRemoved(indexToDelete)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(queryText: String?) {
        copyItems.addAll(items)
        items.clear()
        if (queryText.isNullOrEmpty()) {
            items.addAll(copyItems)
        } else {
            for (note in copyItems) {
                if (note.title.contains(queryText, true) or note.text.contains(queryText, true)) {
                    items.add(note)
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    inner class NoteViewHolder(
        private var binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }

            itemView.setOnLongClickListener {
                binding.buttonDelete.visibility = View.VISIBLE
                return@setOnLongClickListener true
            }

            binding.apply {
                textViewTitle.text = note.title
                cardView.setCardBackgroundColor(note.color)
                buttonDelete.setOnClickListener {
                    buttonDeleteClick?.invoke(note)
                    binding.buttonDelete.visibility = View.GONE
                }
            }
        }

        fun unbind() {
            itemView.setOnClickListener(null)
        }
    }
}