package zeev.fraiman.numbersystems;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import zeev.fraiman.numbersystems.databinding.FragmentConverterBinding;

public class ConverterFragment extends Fragment {

    private FragmentConverterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConverterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnConvert.setOnClickListener(v -> performConversion());

        binding.etInputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.rgSystems.setOnCheckedChangeListener((group, checkedId) -> binding.tilInput.setError(null));
    }

    private NumeralSystem getSelectedSystem() {
        int checkedId = binding.rgSystems.getCheckedRadioButtonId();
        if (checkedId == R.id.rbBin) return NumeralSystem.BIN;
        if (checkedId == R.id.rbOct) return NumeralSystem.OCT;
        if (checkedId == R.id.rbHex) return NumeralSystem.HEX;
        if (checkedId == R.id.rbRoman) return NumeralSystem.ROMAN;
        if (checkedId == R.id.rbGematria) return NumeralSystem.GEMATRIA;
        return NumeralSystem.DEC;
    }

    private void performConversion() {
        binding.tilInput.setError(null);
        String inputText = binding.etInputNumber.getText() != null ? binding.etInputNumber.getText().toString() : "";

        try {
            NumeralSystem selectedSystem = getSelectedSystem();
            ConversionResult conversionResult = NumberConverter.convertAll(inputText, selectedSystem);

            List<ResultItem> items = new ArrayList<>();
            for (NumeralSystem system : NumeralSystem.values()) {
                String value = conversionResult.get(system);
                items.add(new ResultItem(system.getDisplayName(), value));
            }

            String subtitle = "Input: " + inputText + " (" + selectedSystem.getDisplayName() + ")";
            CustomResultsDialog.show(requireContext(), getString(R.string.results_title), subtitle, items);

        } catch (IllegalArgumentException e) {
            binding.tilInput.setError(e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
