package ru.mixail_akulov.a10_lisrviewandsnipper

import android.os.Bundle
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.mixail_akulov.a10_lisrviewandsnipper.databinding.ActivitySimpleListViewBinding

class SimpleAdapterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupListViewSimple()
        setupListViewWithSimpleGeneratedData()
    }

    private fun setupListViewSimple() {
        val data = listOf(
            mapOf(
                KEY_TITLE to "First title 111",
                KEY_DESCRIPTION to "The first some description"
            ),
            mapOf(
                KEY_TITLE to "First title 222",
                KEY_DESCRIPTION to "The second some description"
            ),
            mapOf(
                KEY_TITLE to "First title 333",
                KEY_DESCRIPTION to "The third some description"
            )
        )

        // SimpleAdapter требует данные определенной структуры, это список  List<Map<key, value>
        val adapter = SimpleAdapter( // принимает 5 параметров
            this,  // в случае фрагмента пердаем context или getContext
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION), // массив используемых ключей
            intArrayOf(android.R.id.text1, android.R.id.text2)  // в text1 помещаем текст по ключу KEY_TITLE, в text2 - KEY_DESCRIPTION
        )

        binding.listview.adapter = adapter

        // обработка событий нажатия на элемент в списке
        binding.listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItemTitle = data[position][KEY_TITLE]
            val selectedItemDescription = data[position][KEY_DESCRIPTION]

            val dialog = AlertDialog.Builder(this)
                .setTitle(selectedItemTitle)
                .setMessage(getString(R.string.item_selected_message, selectedItemDescription))
                .setPositiveButton("Ok") { dialog, which -> }
                .create()
            dialog.show()
        }

    }

    private fun setupListViewWithSimpleGeneratedData() {
        val data = (1..100).map {
            mapOf(
                KEY_TITLE to "Item #$it",
                KEY_DESCRIPTION to "Description #$it"
            )
        }

        val adapter = SimpleAdapter (
            this,
            data,
            R.layout.item_custom,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(R.id.titleTextView, R.id.descriptionTextView)
            )
        binding.listview.adapter = adapter
    }



    companion object {
        @JvmStatic val KEY_TITLE = "title"
        @JvmStatic val KEY_DESCRIPTION = "description"
    }
}