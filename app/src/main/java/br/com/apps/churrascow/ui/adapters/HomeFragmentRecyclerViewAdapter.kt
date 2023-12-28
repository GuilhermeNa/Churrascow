package br.com.apps.churrascow.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.apps.churrascow.databinding.ItemHistoricBinding

class HomeFragmentRecyclerViewAdapter(

    private val context: Context,
    private val receivedDataSet: List<Int>

) : RecyclerView.Adapter<HomeFragmentRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = receivedDataSet

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoricBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(binding: ItemHistoricBinding): RecyclerView.ViewHolder(binding.root) {
        private val historicName = binding.itemHistoricName
        private val description = binding.itemHistoricDescription
        private val value = binding.itemHistoricValue

        fun vincula(ints: Int){
            historicName.text = "Valor pago"
            description.text = "Funalo pagou ingresso"
            value.text = "R$ 50.00"
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ints = dataSet[position]
        holder.vincula(ints)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

}