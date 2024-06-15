package ru.mixail_akulov.a10_lisrviewandsnipper.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import ru.mixail_akulov.a10_lisrviewandsnipper.databinding.ItemCharacterBinding

typealias OnDeletePressedListener = (Character) -> Unit

class CharacterAdapter(
    private val character: List<Character>,
    private val onDeletePressedListener: OnDeletePressedListener
) : BaseAdapter(), View.OnClickListener {

    override fun getItem(position: Int): Character {
        return character[position]
    }

    override fun getItemId(position: Int): Long {
        return character[position].id
    }

    override fun getCount(): Int {
        return character.size
    }

    // convertView предназначен для оптимизации списка, увеличения скорости отрисовки.
    // Используется для размещения элемента списка, заменяется на новый при смещении
    // элемента за область видимости экрана.
    // вызывается, когда адаптер запрашивает создание нового элемената по индексу position
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, false)
    }

    // для того, чтобы отривоать элемент списка, который появляется в выпадающем списке
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, true)
    }

    private fun getDefaultView(position: Int, convertView: View?, parent: ViewGroup, isDropdownView: Boolean): View {
        // в тэге лежит биндинг ItemCharacterBinding, смотри метод createBinding()
        // если convertView свободных нет, экран еще полностью не заполнен, то создается новый,
        // а иначе перезаполняется уже созданный, лежащий в тэге, и не теряется память и скорость работы.
        val binding = convertView?.tag as ItemCharacterBinding? ?:
        createBinding(parent.context)

        val character = getItem(position)

        // обязательно обновить все элементы из биндинга, чтобы не наложились данные из разных Item(position)
        with(binding) {
            titleTextView.text = character.name
            deleteImageView.tag = character  // назначаем вьюшке кнопки удаления тэг самого пользователя
            // видимость кнопки удалить для кастомных персонажей, если поменять местами  (View.VISIBLE else View.GONE),
            // то мусорка будет видна в выпадающем списке, а на главном экране нет
            deleteImageView.visibility = if (isDropdownView) View.GONE else View.VISIBLE
        }
        return binding.root
    }

    override fun onClick(v: View) { // передается вьюшка, на которую произошло нажатие
        val character = v.tag as Character // берется из тега вьюшки персонажа, которого положили в getView()
        onDeletePressedListener.invoke(character) // и в слушатель передаем оповещение о событии и нового персонажа.
                                                    // подписчики на слушателя получают персонажа и задание удалить его,
                                                    // а как удалять решаться будет у подписчика
    }

    private fun createBinding(context: Context?): ItemCharacterBinding {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding // в тэг ложим биндинг ItemCharacterBinding

        return binding
    }
}