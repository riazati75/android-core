package ir.farsroidx.app

import android.graphics.Color
import ir.farsroidx.app.databinding.AdapterMainBinding
import ir.farsroidx.core.recycler.AbstractRecyclerAdapter

class MainAdapter : AbstractRecyclerAdapter<AdapterMainBinding, MainAdapter.Model>() {

    class Model(var data: Any)

    override fun AdapterMainBinding.onBindViewHolder(item: Model, position: Int) {

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

    override fun AdapterMainBinding.onViewRecycled() {

    }
}