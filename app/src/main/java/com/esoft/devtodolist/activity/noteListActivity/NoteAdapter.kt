package com.esoft.devtodolist.activity.noteListActivity

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
import com.esoft.devtodolist.base.CREATE_NOTE
import com.esoft.devtodolist.base.DELETE_NOTE
import com.esoft.devtodolist.base.NO_SEARCH_NOTE
import com.esoft.devtodolist.databinding.RecyclerViewItemBinding
import com.esoft.devtodolist.model.NoteModel
import com.esoft.devtodolist.model.NoteRepository
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

    /*private var sortedList: SortedList<NoteModel> =
    SortedList(NoteModel::class.java, object : SortedList.Callback<NoteModel>() {
        override fun compare(o1: NoteModel, o2: NoteModel): Int {
            if (!o2.done && o1.done) {
                return 1
            }
            return if (o2.done && !o1.done) {
                -1
            } else (o2.timestamp - o1.timestamp).toInt()
        }

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count)
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areItemsTheSame(item1: NoteModel, item2: NoteModel): Boolean {
            return item1.id == item2.id
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }
    })*/


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

    /*fun setItems(notes: List<NoteModel?>?) {
        listOfNote.replaceAll(notes!!)
    }*/


    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RecyclerViewItemBinding.bind(view)
        val note = NoteModel()
        var silentUpdate = false

        fun bind(note: NoteModel) {

            binding.apply {
                noteHead.text = note.textHead
                textDetailInfoNote.text = note.text
                textDate.text = note.dataCalendar
                if (note.notifTime.equals("Время") || note.notifTime == null) {
                    binding.viewNotification.visibility = View.GONE
                } else {
                    binding.viewNotification.visibility = View.VISIBLE
                    binding.textNotification.text = note.notifTime
                }

                checkNote.isChecked = note.done
                noteHead.paint.isStrikeThruText = note.done

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