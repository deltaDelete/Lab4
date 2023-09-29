package ru.deltadelete.lab5.ui.new_town_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import ru.deltadelete.lab5.MyViewModel
import ru.deltadelete.lab5.R
import ru.deltadelete.lab5.adapter.TownAdapter
import ru.deltadelete.lab5.databinding.FragmentNewTownBinding
import ru.deltadelete.lab5.models.Town
import java.util.Locale
import java.util.stream.Collectors
import kotlin.reflect.typeOf

class NewTownFragment() : Fragment() {
    private var binding: FragmentNewTownBinding? = null
    private var viewModel: MyViewModel? = null
    private var selectedCountry: Locale? = null
        set(selectedCountry) {
            field = selectedCountry
            if (selectedCountry != null) {
                binding!!.imageFlagPreview.setImageURI(
                        getString(
                                R.string.flagcdn_png,
                                selectedCountry.country.lowercase(Locale.getDefault())
                        )
                )
            }
        }
        get() = field

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNewTownBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel == null) {
            viewModel = ViewModelProvider(requireActivity()).get<MyViewModel>()
        }
        initButtons()
        initCountryPicker()
        initValidation()
    }

    private fun initValidation() {
        binding!!.editTextTownName.addTextChangedListener(TextFieldValidation(
                requireContext(),
                binding!!.editTextTownName,
                binding!!.inputLayoutTownName
        ))
        binding!!.dropdownCountry.addTextChangedListener(TextFieldValidation(
                requireContext(),
                binding!!.dropdownCountry,
                binding!!.inputLayoutCountryName
        ))
    }

    private fun initCountryPicker() {
        val availableLocales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        val locales = ArrayList<Locale>()
        for (locale in availableLocales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isEmpty() || countries.contains(country)) continue
            countries.add(country)
            locales.add(locale)
        }
        countries.sort()
        locales.sortWith { o1: Locale, o2: Locale ->
            o1.displayCountry.compareTo(o2.displayCountry, ignoreCase = true)
        }

        binding!!.dropdownCountry.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedCountry = locales[position]
        }

        val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                locales.stream().map { obj: Locale -> obj.displayCountry }.collect(Collectors.toList())
        )

        binding!!.dropdownCountry.setAdapter(adapter)
    }

    private fun initButtons() {
        binding!!.buttonCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding!!.buttonAdd.setOnClickListener {
            if (!isDataRight) {
                Toast.makeText(requireContext(), R.string.check_fields, Toast.LENGTH_LONG)
                        .show()
                return@setOnClickListener
            }
            viewModel?.adapter?.add(
                    Town(
                            binding!!.editTextTownName.text.toString(),
                            selectedCountry!!.displayCountry,
                            getString(
                                    R.string.flagcdn_png,
                                    selectedCountry!!.country.lowercase(Locale.getDefault())
                            )
                    )
            )
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private val isDataRight: Boolean
        get() {
            when {
                !validateTownName(
                        binding!!.editTextTownName,
                        requireContext(),
                        binding!!.inputLayoutTownName
                ) -> return false

                !validateCountry(
                        binding!!.dropdownCountry,
                        requireContext(),
                        binding!!.inputLayoutCountryName
                ) -> return false

                binding!!.inputLayoutTownName.error != null -> return false

                binding!!.inputLayoutCountryName.error != null -> return false

                else -> return true
            }
        }
}