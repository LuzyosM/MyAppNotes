package com.luzdeilym.myappnotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var note: List<Note>, context: Context) :RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val TarefaTextView: TextView = itemView.findViewById(R.id.TarefaTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = note.size


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = note[position]
        holder.TarefaTextView.text = note.title
        holder.descriptionTextView.text = note.description
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)

            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Tarefa deletada com sucesso", Toast.LENGTH_SHORT).show()
        }




    }
    fun refreshData(newNotes: List<Note>) {
        note = newNotes
        notifyDataSetChanged()

    }

}