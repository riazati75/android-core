package ir.farsroidx.app

import ir.farsroidx.app.databinding.ActivityMainBinding
import ir.farsroidx.core.CoreActivity

class MainActivity : CoreActivity<ActivityMainBinding>() {

    override fun ActivityMainBinding.onInitialized() {

        val adapter = MainAdapter().apply {
            addItems(
                MainAdapter.Model(1),
                MainAdapter.Model(2),
                MainAdapter.Model(1),
            )
        }

        recyclerView.adapter = adapter

        btnFab.setOnClickListener {
            adapter.addItemAt(0, MainAdapter.Model(1))
        }
    }
}