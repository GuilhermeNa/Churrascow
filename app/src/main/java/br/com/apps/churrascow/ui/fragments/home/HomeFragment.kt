package br.com.apps.churrascow.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import br.com.apps.churrascow.databinding.FragmentHomeBinding
import br.com.apps.churrascow.dto.EventDto
import br.com.apps.churrascow.ui.adapters.HomeFragmentRecyclerViewAdapter
import br.com.apps.churrascow.ui.adapters.HomeFragmentViewPagerAdapter
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragment
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragmentV1
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragmentV1() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private val viewModel: HomeFragmentViewModel by viewModel()

    //---------------------------------------------------------------------------------------------//
    // ON CREATE
    //---------------------------------------------------------------------------------------------//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //---------------------------------------------------------------------------------------------//
    // ON CREATE VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //---------------------------------------------------------------------------------------------//
    // ON VIEW CREATED
    //---------------------------------------------------------------------------------------------//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTransFormer()
        initCircleIndicator()
        initRecyclerView()
        initViewController()

        binding.homeFragmentEventTitle.setOnClickListener {
            lifecycleScope.launch {
                logout()
            }
        }
        //  activity?.onBackPressedDispatcher
    }

    /**
     * Responsible for ViewPager basic implementation.
     *
     */
    private fun initViewPager() {
        val lista = listOf(
            EventDto(
                idUser = "1",
                title = "Titulo 1",
                date = "Abril",
                urlImage = ""
            ),
            EventDto(
                idUser = "2",
                title = "Titulo 2",
                date = "Abril",
                urlImage = ""
            ),
            EventDto(
                idUser = "3",
                title = "Titulo 2",
                date = "Abril",
                urlImage = ""
            )
        )

        viewPager = binding.homeFragmentViewPager
        val homeFragmentViewPagerAdapter = HomeFragmentViewPagerAdapter(requireContext(), lista)

        viewPager.adapter = homeFragmentViewPagerAdapter
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    /**
     * Responsible for ViewPager animation when its onScreen item has changed.
     */
    private fun initTransFormer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1f - kotlin.math.abs(position)
            page.scaleY = 0.75f + (r * 0.25f)
        }
        viewPager.setPageTransformer(transformer)
    }

    /**
     * Responsible for circle indicator, used as index for viewPager.
     */
    private fun initCircleIndicator() {
        val indicator = binding.homeFragmentCircleIndicator
        indicator.setViewPager(viewPager)
    }

    /**
     * Responsible for recycler view.
     */
    private fun initRecyclerView() {
        val recyclerView = binding.homeFragmentHistoricPanel.historicPanelRecycler
        val recyclerAdapter = HomeFragmentRecyclerViewAdapter(requireContext(), listOf(1, 2, 3))

        recyclerView.adapter = recyclerAdapter

    }

    /**
     * This method is responsible for manipulate the fragment's view by observing a MutableLiveData.
     *
     */
    private fun initViewController() {
        viewModel.eventDate.observe(viewLifecycleOwner) {
            it?.let {
                binding.homeFragmentDate.text = it
            }
        }

        viewModel.eventsDataSet.observe(viewLifecycleOwner) {
            it?.let {
                //todo enviar dados para viewPager
            }
        }


    }

    //---------------------------------------------------------------------------------------------//
    // ON DESTROY VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}