package com.example.apps.optiki

import android.os.Bundle
import android.view.MenuItem
import com.example.apps.optiki.common.BaseActivity
import com.example.apps.optiki.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun getContainerLayoutId(): Int {
        return R.id.container
    }

    override fun setDisplayHomeAsUpEnabled(showHomeAsUp: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp)
    }
}
