package cl.inacap.carelesscovid.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PacientesDBOpenHelper extends SQLiteOpenHelper {

    private final String sqlCreate = "CREATE TABLE pacientes(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "rut TEXT," +
            "nombre TEXT," +
            "apellido TEXT," +
            "fecha TEXT," +
            "area_trabajo TEXT," +
            "sintomas INTEGER," +
            "tos INTEGER," +
            "temperatura FLOAT," +
            "presion INTEGER)";

    public PacientesDBOpenHelper(@Nullable Context cx,
                                 @Nullable String name,
                                 @Nullable SQLiteDatabase.CursorFactory factory,
                                 int version){
        super(cx, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.sqlCreate);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS pacientes");
        sqLiteDatabase.execSQL(sqlCreate);
    }
}
