package com.example.contentprovider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.SMSViewHolder> {

    private List<SMS> smsList;

    public SMSAdapter(List<SMS> smsList) {
        this.smsList = smsList;
    }

    @NonNull
    @Override
    public SMSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_item, parent, false);
        return new SMSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SMSViewHolder holder, int position) {
        SMS sms = smsList.get(position);
        holder.addressText.setText(sms.getAddress());
        holder.bodyText.setText(sms.getBody());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    static class SMSViewHolder extends RecyclerView.ViewHolder {
        TextView addressText, bodyText;

        SMSViewHolder(View itemView) {
            super(itemView);
            addressText = itemView.findViewById(R.id.address_text);
            bodyText = itemView.findViewById(R.id.body_text);
        }
    }
}