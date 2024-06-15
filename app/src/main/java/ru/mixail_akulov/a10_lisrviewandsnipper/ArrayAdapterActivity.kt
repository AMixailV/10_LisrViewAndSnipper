package ru.mixail_akulov.a10_lisrviewandsnipper

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.mixail_akulov.a10_lisrviewandsnipper.databinding.ActivityListViewBinding
import ru.mixail_akulov.a10_lisrviewandsnipper.databinding.DialogAddCharacterBinding
import java.util.*

class ArrayAdapterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListViewBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListWithArrayAdapter()

        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupListWithArrayAdapter() {
        val data = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Reptile"),
            Character(id = UUID.randomUUID().toString(), name = "Subzero"),
            Character(id = UUID.randomUUID().toString(), name = "Scorpion"),
            Character(id = UUID.randomUUID().toString(), name = "Raiden"),
            Character(id = UUID.randomUUID().toString(), name = "Smoke")
        )

        // позволяет выводить лишь текстовые данные водну textView, но при этом позволяет
        // добавлять некоторые операции, как добавление или удаление элементов списка.
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data
        )

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)?.let {  // безопасный код, если не null, то сработает удаление
                deleteCharacter(it)
            }
        }
    }

    private fun onAddPressed() {

        // Диалог с кастомной view
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.create_character))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.add)) { d, which ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) {
                    createCharacter(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = UUID.randomUUID().toString(),
            name = name
        )
        adapter.add(character)
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                adapter.remove(character)
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_character))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(getString(R.string.delete), listener)
            .setNegativeButton(getString(R.string.cancel), listener)
            .create()
        dialog.show()
    }

    // Класс данных
    class Character(
        val id: String,
        val name: String
    ) {
        // переопределяем, чтобы адаптер показывал только имя
        override fun toString(): String {
            return name
        }
    }
}