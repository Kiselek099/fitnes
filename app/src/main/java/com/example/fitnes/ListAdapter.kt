package com.example.fitnes

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter (context: Context, exercises:MutableList<Exercise>):
    ArrayAdapter<Exercise>(context,R.layout.list_item,exercises){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view=convertView
        val exercise=getItem(position)
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        }
        val gifIV=view?.findViewById<ImageView>(R.id.gifIV)
        val exerciseTV=view?.findViewById<TextView>(R.id.exerciseTV)
        gifIV?.setImageResource(exercise?.gifImage!!)
        exerciseTV?.text=exercise?.name
        return view!!
    }
}