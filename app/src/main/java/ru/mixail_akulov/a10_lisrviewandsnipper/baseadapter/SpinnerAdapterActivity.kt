package ru.mixail_akulov.a10_lisrviewandsnipper.baseadapter

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ru.mixail_akulov.a10_lisrviewandsnipper.R
import ru.mixail_akulov.a10_lisrviewandsnipper.databinding.ActivitySpinnerListViewBinding
import ru.mixail_akulov.a10_lisrviewandsnipper.databinding.DialogAddCharacterBinding
import java.util.*

class SpinnerAdapterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpinnerListViewBinding

    private  val data = mutableListOf(
        Character(id = 1, name = "Reptile", isCustom = false),
        Character(id = 2, name = "Subzero", isCustom = false),
        Character(id = 3, name = "Scorpion", isCustom = false),
        Character(id = 4, name = "Raiden", isCustom = false),
        Character(id = 5, name = "Smoke", isCustom = false)
    )
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapter(data) {
            deleteCharacter(it)
        }
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.characterInfoTextView.text = ""
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val character = data[position]
                binding.characterInfoTextView.text = getString(R.string.character_info, character.name, character.id)
            }
        }
    }

    private fun onAddPressed() {
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
        val random = Random()
        val character = Character(
            id = random.nextLong(),
            name = name,
            isCustom = true
        )
        data.add(character)
        // Уведомляет присоединенных наблюдателей о том, что базовые данные были изменены,
        // и любое представление, отражающее набор данных, должно обновиться
        adapter.notifyDataSetChanged()
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                data.remove(character)
                // Уведомляет присоединенных наблюдателей о том, что базовые данные были изменены,
                // и любое представление, отражающее набор данных, должно обновиться
                adapter.notifyDataSetChanged()
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_character))
            .setMessage(getString(R.string.delete_message, character.name))
            .setPositiveButton(getString(R.string.delete), listener)
            .setNegativeButton(getString(R.string.cancel), listener)
            .create()
        dialog.show()
    }
}