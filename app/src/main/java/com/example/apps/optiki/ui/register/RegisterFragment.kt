package com.example.apps.optiki.ui.register

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apps.optiki.R
import com.example.apps.optiki.common.BaseFragment
import com.example.apps.optiki.lifecycle.OrganisationNamesViewModel
import com.example.apps.optiki.model.OrganisationName
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.register_fragment.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegisterFragment : BaseFragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val continueClickSubject = PublishSubject.create<View>()
    private val listAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(activity!!, R.layout.view_dropdown_item, mutableListOf())
    }

    // You want Dagger to provide an instance of MyViewModel from the graph
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppInjector()?.inject(this)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(OrganisationNamesViewModel::class.java)
        viewModel.getNames().observe(this, Observer(::onUpdateNames))

        addDisposable(continueClickSubject
            .throttleFirst(800, TimeUnit.MILLISECONDS)
            .subscribe {
                AlertDialog.Builder(activity!!)
                    .setTitle(R.string.title_alert)
                    .setMessage(R.string.message_thanks_for_registering)
                    .setPositiveButton(R.string.button_ok) { dialog, _ ->
                        dialog.dismiss()
                        popBackStack()
                    }
                    .show()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplayHomeAsUpEnabled(true)

        btnContinue.setOnClickListener {
            continueClickSubject.onNext(it)
        }

        txtSearch.threshold = 1
        txtSearch.addTextChangedListener(onTextChanged = { text, start, count, after ->
            btnContinue.isEnabled = false
            listAdapter.filter.filter(text)
        })

        listView.adapter = listAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            txtSearch.setText(listAdapter.getItem(position))
            hideSoftKeyboard()
            btnContinue.isEnabled = true
        }
    }

    private fun onUpdateNames(names: List<OrganisationName>) {
        Observable.just(names).flatMap { list ->
            Observable.fromIterable(list)
                .map { item -> item.name!! }
                .toList()
                .toObservable()
        }.subscribe { list ->
            listAdapter.clear()
            listAdapter.addAll(list)
            listView.isVisible = true
        }
    }

    private fun hideSoftKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(txtSearch.windowToken, 0)
    }
}