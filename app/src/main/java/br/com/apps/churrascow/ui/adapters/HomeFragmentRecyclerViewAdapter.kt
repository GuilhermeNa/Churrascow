package br.com.apps.churrascow.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.apps.churrascow.databinding.ItemHistoricBinding
import br.com.apps.churrascow.dto.ActionDto
import br.com.apps.churrascow.util.toActionSummary

class HomeFragmentRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeFragmentRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<ActionDto>()

    //---------------------------------------------------------------------------------------------//
    // ON CREATE VIEW HOLDER
    //---------------------------------------------------------------------------------------------//

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoricBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    //---------------------------------------------------------------------------------------------//
    // VIEW HOLDER
    //---------------------------------------------------------------------------------------------//

    class ViewHolder(binding: ItemHistoricBinding) : RecyclerView.ViewHolder(binding.root) {
        private val actionSummary = binding.itemHistoricSummary
        private val description = binding.itemHistoricDescription
        private val value = binding.itemHistoricValue

        fun vincula(actions: ActionDto) {
            actionSummary.text = actions.actionSummary.toActionSummary()?.description
            description.text = "Id do evento = ${actions.eventId}"
            actions.value?.let {
                value.text = it
            }
        }

    }

    //---------------------------------------------------------------------------------------------//
    // ON BIND VIEW HOLDER
    //---------------------------------------------------------------------------------------------//

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val events = dataSet[position]
        holder.vincula(events)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newData: List<ActionDto>) {
        this.dataSet.clear()
        this.dataSet.addAll(newData)
        notifyDataSetChanged()
    }

}