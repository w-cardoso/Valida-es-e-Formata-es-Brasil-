package fiap.com.br.alurafoodapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fiap.com.br.alurafoodapplication.model.ClientModel;

public class ClientDao extends SQLiteOpenHelper {

    public ClientDao(Context context) {
        super(context, "FormRegister", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Client (id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "cpf TEXT, " +
                "phone TEXT, " +
                "email TEXT, " +
                "password TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "DROP TABLE IF EXISTS Client";
                db.execSQL(sql);
        }
    }

    public void insertClient(ClientModel client) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getDataOfClient(client);
        db.insert("Client", null, data);
    }

    public void update(ClientModel client) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getDataOfClient(client);
        String[] parameters = {String.valueOf(client.getId())};
        db.update("Client", data, "id = ?", parameters);
    }

    public void delete(ClientModel client) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parameters = {String.valueOf(client.getId())};
        db.delete("Client", "id = ?", parameters);
    }

    public List<ClientModel> searchClient() {
        String sql = "SELECT * FROM Client;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<ClientModel> clients = new ArrayList<>();
        while (cursor.moveToNext()) {
            ClientModel client = new ClientModel();
            client.setId(cursor.getInt(cursor.getColumnIndex("id")));
            client.setName(cursor.getString(cursor.getColumnIndex("name")));
            client.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
            client.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            client.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            client.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            clients.add(client);
        }
        cursor.close();
        return clients;
    }

    @NonNull
    private ContentValues getDataOfClient(ClientModel client) {
        ContentValues data = new ContentValues();
        data.put("name", client.getName());
        data.put("cpf", client.getCpf());
        data.put("phone", client.getPhone());
        data.put("email", client.getEmail());
        data.put("password", client.getPassword());
        return data;
    }
}
