package br.com.apps.churrascow.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.apps.churrascow.databinding.ItemEventBinding
import br.com.apps.churrascow.dto.EventDto

class HomeFragmentViewPagerAdapter(

    private val context: Context,
    private val receivedDataSet: List<EventDto>

) : RecyclerView.Adapter<HomeFragmentViewPagerAdapter.ViewHolder>() {

    private val dataSet = receivedDataSet

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.itemEventTitle
        private val description = binding.itemEventInformation
        private val participants = binding.itemEventParticipants

        fun vincula(eventDto: EventDto){
            title.text = eventDto.title
            description.text = eventDto.description
            participants.text = "text"
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventDto = dataSet[position]
        holder.vincula(eventDto)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

}