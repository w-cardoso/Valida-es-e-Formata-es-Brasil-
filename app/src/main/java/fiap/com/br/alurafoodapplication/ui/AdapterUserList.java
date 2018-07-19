package fiap.com.br.alurafoodapplication.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fiap.com.br.alurafoodapplication.R;
import fiap.com.br.alurafoodapplication.model.ClientModel;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.UserViewHolder> {
    private Context context;
    private List<ClientModel> listClients;


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View createView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(createView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.edtName.setText(listClients.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return listClients.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView edtName;
        public TextView edtCpf;
        public TextView edtPhone;
        public TextView edtEmail;


        public UserViewHolder(View itemView) {
            super(itemView);
            edtName = itemView.findViewById(R.id.item_user_txt_name);
            edtCpf = itemView.findViewById(R.id.item_user_txt_cpf);
            edtPhone = itemView.findViewById(R.id.item_user_txt_phone);
            edtEmail = itemView.findViewById(R.id.item_user_txt_email);

        }
    }
}
