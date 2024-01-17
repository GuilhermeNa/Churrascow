package br.com.apps.churrascow.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import br.com.apps.churrascow.databinding.FragmentHomeBinding
import br.com.apps.churrascow.dto.EventWithActionsDto
import br.com.apps.churrascow.ui.adapters.HomeFragmentRecyclerViewAdapter
import br.com.apps.churrascow.ui.adapters.HomeFragmentViewPagerAdapter
import br.com.apps.churrascow.ui.fragments.baseFragment.BaseFragmentV1
import br.com.apps.churrascow.util.formatToStringBr
import br.com.apps.churrascow.util.toLocalDateTime
import me.relex.circleindicator.CircleIndicator3
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragmentV1() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private val viewModel: HomeFragmentViewModel by viewModel()
    private val recyclerAdapter by lazy { HomeFragmentRecyclerViewAdapter(requireContext()) }
    private val viewPagerAdapter by lazy { HomeFragmentViewPagerAdapter(requireContext()) }
    private lateinit var indicator: CircleIndicator3

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
        initStateManager()
        //  activity?.onBackPressedDispatcher
    }

    /**
     * Responsible for ViewPager basic implementation.
     *
     */
    private fun initViewPager() {
        viewPager = binding.homeFragmentViewPager
        viewPager.adapter = viewPagerAdapter
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
        indicator = binding.homeFragmentCircleIndicator
        indicator.setViewPager(viewPager)
        viewPagerAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
    }

    /**
     * Responsible for recycler view.
     */
    private fun initRecyclerView() {
        val recyclerView = binding.homeFragmentHistoricPanel.historicPanelRecycler
        recyclerView.adapter = recyclerAdapter
    }

    /**
     * This method is responsible for manipulate the fragment's view by observing a MutableLiveData.
     *
     */
    private fun initStateManager() {
        viewModel.eventWithActionsDataSet.observe(viewLifecycleOwner) { it ->
            it?.let { eventsWithActions ->
                if (eventsWithActions.isNotEmpty()) {
                    val listOfEvents = eventsWithActions.map { it.event }
                    viewPagerAdapter.update(listOfEvents)
                }
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val eventId = viewPagerAdapter.getEventId(position)
                viewModel.eventWithActionsDataSet.value?.let { it ->
                    it.firstOrNull { it.event.id?.toLong() == eventId }?.let {
                        updateDate(it)
                        updateRecycler(it)
                    }
                }
            }

            private fun updateRecycler(eventWithActions: EventWithActionsDto) {
                eventWithActions.actions.let { listOfActions ->
                    recyclerAdapter.update(listOfActions)
                }
            }

            private fun updateDate(eventWithActions: EventWithActionsDto) {
                eventWithActions.event.let { eventDto ->
                    val localDateTime = eventDto.date?.toLocalDateTime()
                    val dateInPtBr = localDateTime?.formatToStringBr()
                    binding.homeFragmentDate.text = dateInPtBr
                }
            }
        })

    }

    //---------------------------------------------------------------------------------------------//
    // ON DESTROY VIEW
    //---------------------------------------------------------------------------------------------//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //---------------------------------------------------------------------------------------------//
    // INTERFACES
    //---------------------------------------------------------------------------------------------//

    /**
     * This method receives the [userId] for consumption as soon as it is loaded internally.
     */
    override fun idConsumer(id: String) {
        viewModel.loadEventsAndActions(id)
    }

}