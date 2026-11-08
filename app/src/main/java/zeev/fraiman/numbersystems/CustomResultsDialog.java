package zeev.fraiman.numbersystems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import zeev.fraiman.numbersystems.databinding.DialogResultsBinding;

public class CustomResultsDialog {

    public static void show(Context context, String title, String subtitle, List<ResultItem> items) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        DialogResultsBinding binding = DialogResultsBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());

        if (title != null && !title.isEmpty()) {
            binding.tvDialogTitle.setText(title);
        }
        if (subtitle != null && !subtitle.isEmpty()) {
            binding.tvDialogSubtitle.setText(subtitle);
            binding.tvDialogSubtitle.setVisibility(View.VISIBLE);
        } else {
            binding.tvDialogSubtitle.setVisibility(View.GONE);
        }

        ResultsAdapter adapter = new ResultsAdapter();
        binding.rvDialogResults.setLayoutManager(new LinearLayoutManager(context));
        binding.rvDialogResults.setAdapter(adapter);
        adapter.setItems(items);

        binding.btnClose.setOnClickListener(v -> dialog.dismiss());
        binding.btnDone.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
