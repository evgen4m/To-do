package com.esoft.devtodolist.activity.noteListActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.esoft.devtodolist.R
import com.esoft.devtodolist.activity.newNoteActivity.NewNoteActivity
import com.esoft.devtodolist.databinding.RecyclerViewItemBinding
import com.esoft.devtodolist.helpers.CREATE_NOTE
import com.esoft.devtodolist.helpers.DELETE_NOTE
import com.esoft.devtodolist.helpers.NO_SEARCH_NOTE
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.NoteRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(handler: Handler) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(),
    Filterable {

    private var handler: Handler = handler
    private lateinit var searchList: ArrayList<NoteModel>

    var listOfNote = ArrayList<NoteModel>()
        set(value) {
            field = value
            searchList = ArrayList(listOfNote)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listOfNote[position])
    }

    override fun getItemCount(): Int {
        if (listOfNote.size == 0) {
            val message = Message.obtain()
            message.what = DELETE_NOTE
            handler.sendMessage(message)
        }
        return listOfNote.size
    }


     class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RecyclerViewItemBinding.bind(view)
        var silentUpdate = false

        companion object {
            var noteModel = NoteModel()
        }


        fun bind(note: NoteModel) {

            noteModel = note

            binding.apply {
                noteHead.text = note.textHead
                textDetailInfoNote.text = note.text
                textDate.text = note.dataCalendar
                if (note.notifTime.equals("Время") || note.notifTime == null) {
                    binding.textNotification.text = "Без уведомления"
                    binding.imageNotification.setImageResource(R.drawable.ic_bell_disabled)
                } else {
                    binding.imageNotification.setImageResource(R.drawable.ic_notification)
                    binding.textNotification.text = note.notifTime
                }
                updateStrokeOut()

                silentUpdate = true
                checkNote.isChecked = note.done
                silentUpdate = false

                checkNote.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(!silentUpdate) {
                        note.done = isChecked
                        NoteRepository(application = Application()).update(noteModel = note)
                    }
                    updateStrokeOut()
                }


            }

            binding.btnSettingsNote.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, binding.btnSettingsNote)
                popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popupMenu.setForceShowIcon(true)
                }
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.deleteNote -> {
                            //App.getInstance().repositoryDao.delete(note)
                            NoteRepository(application = Application()).delete(noteModel = note)
                            true
                        }
                        R.id.editNote -> {
                            NewNoteActivity.start((itemView.context as Activity), note)
                            true
                        }
                        else -> false
                    }
                })

                popupMenu.show()
            }

            itemView.setOnClickListener {
                if (binding.textDetailInfoNote.visibility == View.GONE && note.text!!.isNotEmpty()) {
                    binding.textDetailInfoNote.visibility = View.VISIBLE
                } else {
                    binding.textDetailInfoNote.visibility = View.GONE
                }
            }
        }
        private fun updateStrokeOut() {
            if(noteModel.done) {
                binding.apply {
                    noteHead.paintFlags = noteHead.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }else {
                binding.apply {
                    noteHead.paintFlags =
                        noteHead.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return filterList
    }

    private val filterList: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<NoteModel> = ArrayList()
            if (constraint.isEmpty() || constraint == null) {
                filteredList.addAll(searchList)
            } else {
                val message = Message.obtain()
                message.what = NO_SEARCH_NOTE
                handler.sendMessage(message)
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in searchList) {
                    if (item.textHead?.lowercase(Locale.getDefault())!!.contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            listOfNote.clear()
            listOfNote.addAll(results.values as ArrayList<NoteModel>)
            val message = Message.obtain()
            message.what = CREATE_NOTE
            handler.sendMessage(message)
            notifyDataSetChanged()
        }
    }
}