package com.example.apps.optiki.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.apps.optiki.R
import com.example.apps.optiki.common.BaseFragment
import com.example.apps.optiki.ui.register.RegisterFragment
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val accountClickSubject = PublishSubject.create<View>()
    private val registerClickSubject = PublishSubject.create<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addDisposable(accountClickSubject
            .throttleFirst(800, TimeUnit.MILLISECONDS)
            .subscribe {
                AlertDialog.Builder(activity!!)
                    .setTitle(R.string.title_alert)
                    .setMessage(R.string.message_not_implemented)
                    .setPositiveButton(R.string.button_ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            })

        addDisposable(registerClickSubject
            .throttleFirst(800, TimeUnit.MILLISECONDS)
            .subscribe {
                pushFragment(RegisterFragment.newInstance())
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDisplayHomeAsUpEnabled(false)

        btnAccount.setOnClickListener {
            accountClickSubject.onNext(it)
        }

        btnRegister.setOnClickListener {
            registerClickSubject.onNext(it)
        }
    }
}
