package com.example.oham.weatherapp.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.support.v7.widget.SearchView
import android.view.MenuItem
import android.widget.Toast
import com.example.oham.weatherapp.R
import com.example.oham.weatherapp.WeatherApp
import com.example.oham.weatherapp.data.Resource
import com.example.oham.weatherapp.data.Status
import com.example.oham.weatherapp.data.entities.WeatherInfo
import com.example.oham.weatherapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        val TAG = "${SearchActivity::class.java}_TAG_"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mViewModel: SearchViewModel

    var mZipCode: String?  = null

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Set the hamburger button at the left of the action bar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Inject dependencies
        (application as WeatherApp).getAppComponent()?.inject(this)

        // Get view model
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        observeViewModel()

        refresh_spinner.setOnRefreshListener{
                mViewModel.forceSync();
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView

        // Workaround to make search view take the full width
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        mViewModel.dosearch(query)
        return false;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    fun observeViewModel() {
        mViewModel.result.observe(this, Observer { result -> observeResult(result) })
        mViewModel.query.observe(this, Observer { zipCode -> mZipCode = zipCode })
    }

    fun observeResult(resource: Resource<WeatherInfo>?) {
        resource.let {
            when (resource?.status) {
                Status.SUCCESS -> {
                    showLoading(false)
                }
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    showLoading(false)
                }
            }

            activityMainBinding.weatherInfo = resource?.data
            activityMainBinding.executePendingBindings()
        }
    }

    fun showLoading(value: Boolean) {
        refresh_spinner.isRefreshing = value
    }
}
