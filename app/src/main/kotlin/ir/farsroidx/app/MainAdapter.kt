package ir.farsroidx.app

import android.graphics.Color
import ir.farsroidx.app.databinding.AdapterMainBinding
import ir.farsroidx.core.recycler.CoreRecyclerViewAdapter

class MainAdapter : CoreRecyclerViewAdapter<AdapterMainBinding, MainAdapter.Model>() {

    override fun onBindViewHolder(binding: AdapterMainBinding, item: Model, position: Int) {

        binding.apply {

            if (isSelectedItem(item)) {
                cardViewRoot.setCardBackgroundColor(Color.parseColor("#455A64"))
            } else {
                cardViewRoot.setCardBackgroundColor(Color.WHITE)
            }

            cardViewRoot.setOnClickListener {
                if (isSelectedItem(item)) {
                    removeSelectedItem(item)
                } else {
                    addSelectedItem(item)
                }
            }
        }
    }

    override fun onViewRecycled(dataBinding: AdapterMainBinding) {

    }

    class Model(var data: Any)
}