package com.geek.myproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geek.myproject.R
import com.geek.myproject.data.entity.Note
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRVAdapter: RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes:List<Note> = listOf()
    set(value){
        field = value//field - notes , value - то что приходит в сет
        notifyDataSetChanged()//обновить изменения в рв
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            =ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])//берем холдер,у него вызываем метод бинд(написанный нами) и вставляем заметку по позиции
    }

    override fun getItemCount(): Int = notes.size



    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(note :Note)= with(itemView){//with перед всеми вызовами вставляем itemView,здесь itemView = this
            tv_title.text =note.title//java = TextView t = itemView.findViewById(R.id.tv_title)     .setText(note.title)
            tv_text.text = note.text
            setBackgroundColor(note.color)
            //this.setBackgroundColor(note.color)
            //itemView.setBackgroundColor(note.color)

        }

    }

}
