package zeev.fraiman.numbersystems;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import zeev.fraiman.numbersystems.databinding.ItemSystemResultBinding;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    private final List<ResultItem> items = new ArrayList<>();

    public void setItems(List<ResultItem> newItems) {
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSystemResultBinding binding = ItemSystemResultBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ResultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        ResultItem item = items.get(position);
        holder.binding.tvSystemName.setText(item.getSystemName());
        holder.binding.tvSystemValue.setText(item.getValue());

        holder.binding.btnCopy.setOnClickListener(v -> {
            Context context = v.getContext();
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText(item.getSystemName(), item.getValue());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        final ItemSystemResultBinding binding;

        public ResultViewHolder(@NonNull ItemSystemResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
