package br.com.apps.churrascow.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import br.com.apps.churrascow.databinding.ItemEventBinding

class ViewPagerAdapter(

    private val context: Context,
    private val lista: List<Int>,
    private val viewPager: ViewPager2

) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private val listaTeste = lista

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        private val titulo = binding.itemEventTitle
        private val informacao = binding.itemEventInformation
        private val participant = binding.itemEventParticipants

        fun vincula(inteiro: Int){
            titulo.text = inteiro.toString()
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inteiro = lista[position]
        holder.vincula(inteiro)
    }

    override fun getItemCount(): Int {
        return lista.count()
    }

}