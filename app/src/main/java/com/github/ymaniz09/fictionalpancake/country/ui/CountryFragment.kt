package com.github.ymaniz09.fictionalpancake.country.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ymaniz09.fictionalpancake.R
import com.github.ymaniz09.fictionalpancake.country.ui.viewmodel.CountryViewModel
import com.github.ymaniz09.fictionalpancake.utils.gone
import com.github.ymaniz09.fictionalpancake.utils.visible
import kotlinx.android.synthetic.main.fragment_country.*
import org.koin.android.viewmodel.ext.android.viewModel

class CountryFragment : Fragment() {

    companion object {
        fun newInstance() =
            CountryFragment()
    }

    private val viewModel: CountryViewModel by viewModel()

    private val countriesAdapter =
        CountryListAdapter(
            arrayListOf()
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.refresh()

        countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countriesList.visible()
                countriesAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(viewLifecycleOwner, Observer { isError ->
            list_error.visibility = if (isError.isNullOrEmpty()) View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.gone()
                    countriesList.gone()
                }
            }
        })
    }
}
