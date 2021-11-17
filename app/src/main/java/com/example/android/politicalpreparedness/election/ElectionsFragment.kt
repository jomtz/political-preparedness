package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment: Fragment() {

    //Add ViewModel values and create ViewModel
    private val electionsViewModel: ElectionsViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ElectionsViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory)
            .get(ElectionsViewModel::class.java)

    }

    //Initiate recycler adapters
    private lateinit var upcomingElectionListAdapter: ElectionListAdapter
    private lateinit var savedElectionListAdapter: ElectionListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //Add binding values
        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_election,
            container,
            false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = electionsViewModel

        //Populate recycler adapters
        upcomingElectionListAdapter = ElectionListAdapter(ElectionListener {
            findNavController().navigate(
                ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it)
            )
        })

        binding.upcomingElectionsRecyclerView.adapter = upcomingElectionListAdapter

        savedElectionListAdapter = ElectionListAdapter(ElectionListener {
            findNavController().navigate(
                ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it)
            )
        })

        binding.savedElectionsRecyclerView.adapter = savedElectionListAdapter


        electionsViewModel.upcomingElections.observe(viewLifecycleOwner, Observer { elections ->
            elections.apply {
                upcomingElectionListAdapter.elections = elections
            }
        })

        electionsViewModel.savedElections.observe(viewLifecycleOwner, Observer { elections ->
            elections.apply {
                savedElectionListAdapter.elections = elections
            }
        })

        return binding.root

    }


}