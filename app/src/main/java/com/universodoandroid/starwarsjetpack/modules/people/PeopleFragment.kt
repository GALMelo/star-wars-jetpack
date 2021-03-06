package com.universodoandroid.starwarsjetpack.modules.people

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.universodoandroid.starwarsjetpack.R
import com.universodoandroid.starwarsjetpack.constants.Constants
import com.universodoandroid.starwarsjetpack.databinding.FragmentPeopleBinding
import com.universodoandroid.starwarsjetpack.extensions.addObserver
import com.universodoandroid.starwarsjetpack.extensions.show
import com.universodoandroid.starwarsjetpack.presentation.extensions.onEvent
import com.universodoandroid.starwarsjetpack.presentation.extensions.onStateChanged
import com.universodoandroid.starwarsjetpack.presentation.people.dto.PersonDto
import com.universodoandroid.starwarsjetpack.presentation.people.models.people.PeopleListViewModel
import com.universodoandroid.starwarsjetpack.presentation.people.models.people.lifecycle.PeopleEvent
import com.universodoandroid.starwarsjetpack.ui.BindingFragment
import com.universodoandroid.starwarsjetpack.extensions.defaultNumberOfColumns
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : BindingFragment<FragmentPeopleBinding>(R.layout.fragment_people) {

    private val viewModel by viewModel<PeopleListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupEvent()
        setupState()
    }

    private fun setupObserver() {
        addObserver(viewModel)
    }

    private fun setupEvent() {
        onEvent(viewModel) { event ->
            when (event) {
                is PeopleEvent.ShowError -> showError(event.error)
                is PeopleEvent.ShowLoading -> showLoader(true)
                is PeopleEvent.HideLoading -> showLoader(false)
            }
        }
    }

    private fun setupState() {
        onStateChanged(viewModel) {
            showPeople(it.people)
        }
    }

    private fun showPeople(people: List<PersonDto>) {
        setupRecyclerView(people)
    }

    private fun setupRecyclerView(people: List<PersonDto>) {
        binding.peopleRecyclerView.run {
            layoutManager = GridLayoutManager(requireContext(), resources.defaultNumberOfColumns())
            adapter = PeopleAdapter(people, ::personDetails)
        }
    }

    private fun showError(message: String?) {
        binding.errorMessage.visibility = View.VISIBLE
        println("Error: $message")
    }

    private fun personDetails(personDto: PersonDto) {
        val args = Bundle().apply { putSerializable(Constants.PERSON_DTO, personDto) }
        navController.navigate(R.id.people_navigation_to_person_details, args)
    }

    private fun showLoader(show: Boolean) {
        binding.progressBar.show(show)
    }
}
