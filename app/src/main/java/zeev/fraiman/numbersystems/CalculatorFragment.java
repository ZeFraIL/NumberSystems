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

import zeev.fraiman.numbersystems.databinding.FragmentCalculatorBinding;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnPlus.setOnClickListener(v -> insertOperator(" + "));
        binding.btnMinus.setOnClickListener(v -> insertOperator(" - "));
        binding.btnMultiply.setOnClickListener(v -> insertOperator(" * "));

        binding.btnCalculate.setOnClickListener(v -> performCalculation());

        binding.etCalcExpression.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilCalcInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.rgCalcSystems.setOnCheckedChangeListener((group, checkedId) -> binding.tilCalcInput.setError(null));
    }

    private void insertOperator(String operatorWithSpaces) {
        if (binding.etCalcExpression.getText() != null) {
            binding.etCalcExpression.append(operatorWithSpaces);
        }
    }

    private NumeralSystem getSelectedSystem() {
        int checkedId = binding.rgCalcSystems.getCheckedRadioButtonId();
        if (checkedId == R.id.rbCalcBin) return NumeralSystem.BIN;
        if (checkedId == R.id.rbCalcOct) return NumeralSystem.OCT;
        if (checkedId == R.id.rbCalcHex) return NumeralSystem.HEX;
        if (checkedId == R.id.rbCalcRoman) return NumeralSystem.ROMAN;
        if (checkedId == R.id.rbCalcGematria) return NumeralSystem.GEMATRIA;
        return NumeralSystem.DEC;
    }

    private void performCalculation() {
        binding.tilCalcInput.setError(null);
        String inputText = binding.etCalcExpression.getText() != null ? binding.etCalcExpression.getText().toString() : "";

        try {
            NumeralSystem selectedSystem = getSelectedSystem();
            CalculatorResult calcResult = ExpressionEvaluator.evaluate(inputText, selectedSystem);

            List<ResultItem> items = new ArrayList<>();
            for (NumeralSystem system : NumeralSystem.values()) {
                String expr = calcResult.getExpression(system);
                String res = calcResult.getResult(system);
                String fullDisplay = expr + " = " + res;
                items.add(new ResultItem(system.getDisplayName(), fullDisplay));
            }

            String subtitle = "Expression: " + inputText + " (" + selectedSystem.getDisplayName() + ")";
            CustomResultsDialog.show(requireContext(), getString(R.string.results_title), subtitle, items);

        } catch (IllegalArgumentException e) {
            binding.tilCalcInput.setError(e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
