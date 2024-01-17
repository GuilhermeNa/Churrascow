package br.com.apps.churrascow.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.apps.churrascow.databinding.ItemEventBinding
import br.com.apps.churrascow.dto.EventDto

class HomeFragmentViewPagerAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeFragmentViewPagerAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<EventDto>()

    //---------------------------------------------------------------------------------------------//
    // ON CREATE VIEW HOLDER
    //---------------------------------------------------------------------------------------------//

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    //---------------------------------------------------------------------------------------------//
    // VIEW HOLDER
    //---------------------------------------------------------------------------------------------//

    class ViewHolder(binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.itemEventTitle
        private val description = binding.itemEventInformation
        private val participants = binding.itemEventParticipants

        fun vincula(eventDto: EventDto) {
            title.text = eventDto.title
            description.text = "id do evento = ${eventDto.id}"
            participants.text = eventDto.guests
        }

    }

    //---------------------------------------------------------------------------------------------//
    // ON BIND VIEW HOLDER
    //---------------------------------------------------------------------------------------------//

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventDto: EventDto = dataSet[position]
        holder.vincula(eventDto)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    //---------------------------------------------------------------------------------------------//
    // PUBLIC METHODS
    //---------------------------------------------------------------------------------------------//

    @SuppressLint("NotifyDataSetChanged")
    fun update(newData: List<EventDto>) {
        this.dataSet.clear()
        this.dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    fun getEventId(position: Int): Long {
        if (position in 0 until dataSet.size) {
            return dataSet[position].id?.toLong()!!
        }
        return -1L
    }

}

