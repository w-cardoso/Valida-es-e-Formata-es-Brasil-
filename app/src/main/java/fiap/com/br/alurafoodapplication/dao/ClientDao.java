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
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Client.db";

    // User table name
    private static final String TABLE_CLIENT = "client";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "client_id";
    private static final String COLUMN_USER_NAME = "client_name";
    private static final String COLUMN_USER_CPF = "client_cpf";
    private static final String COLUMN_USER_PHONE = "client_phone";
    private static final String COLUMN_USER_EMAIL = "client_email";
    private static final String COLUMN_USER_PASSWORD = "client_password";

    private String CREATE_CLIENT_TABLE = " CREATE TABLE " + TABLE_CLIENT + " ("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_CPF + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + " )";

    private String DROP_CLIENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_CLIENT;

    public ClientDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(DROP_CLIENT_TABLE);
                onCreate(db);
        }
    }

    public void insertClient(ClientModel client) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getDataOfClient(client);
        db.insert(TABLE_CLIENT, null, data);
    }

    public void update(ClientModel client) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = getDataOfClient(client);
        String[] parameters = {String.valueOf(client.getId())};
        db.update(TABLE_CLIENT, data, "id = ?", parameters);
    }

    public void delete(ClientModel client) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parameters = {String.valueOf(client.getId())};
        db.delete(TABLE_CLIENT, "id = ?", parameters);
    }

    public boolean validateLogin(String cpf, String password) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {
                COLUMN_USER_ID
        };

        String selection = COLUMN_USER_CPF + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {cpf, password};
        Cursor cursor = db.query(TABLE_CLIENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;


    }

    public List<ClientModel> searchClient() {
        String sql = "SELECT * FROM Client;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<ClientModel> clients = new ArrayList<>();
        while (cursor.moveToNext()) {
            ClientModel client = new ClientModel();
            client.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            client.setCpf(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CPF)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
            client.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            client.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            clients.add(client);
        }
        cursor.close();
        return clients;
    }

    @NonNull
    private ContentValues getDataOfClient(ClientModel client) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_USER_NAME, client.getName());
        data.put(COLUMN_USER_CPF, client.getCpf());
        data.put(COLUMN_USER_PHONE, client.getPhone());
        data.put(COLUMN_USER_EMAIL, client.getEmail());
        data.put(COLUMN_USER_PASSWORD, client.getPassword());

        return data;
    }
}
