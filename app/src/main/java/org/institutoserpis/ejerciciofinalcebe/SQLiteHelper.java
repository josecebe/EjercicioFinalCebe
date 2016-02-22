package org.institutoserpis.ejerciciofinalcebe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.institutoserpis.ejerciciofinalcebe.Tables.Ciudad;
import org.institutoserpis.ejerciciofinalcebe.Tables.Claseviaje;
import org.institutoserpis.ejerciciofinalcebe.Tables.Usuario;
import org.institutoserpis.ejerciciofinalcebe.Tables.Viaje;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JoseCebe
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    String createTableUsuario = "CREATE TABLE IF NOT EXISTS `usuario` (" +
            "`id` INTEGER NOT NULL PRIMARY KEY ," +
            "`usuario` TEXT NOT NULL," +
            "`password` TEXT NOT NULL" +
            ")";

    String createTableCiudad = "CREATE TABLE IF NOT EXISTS `ciudad` (" +
            "`id` INTEGER NOT NULL PRIMARY KEY ," +
            "`nombre` TEXT NOT NULL," +
            "`diminutivo` TEXT NOT NULL," +
            "`foto` TEXT," +
            "`s_foto` TEXT" +
            ")";

    String createTableClaseviaje = "CREATE TABLE IF NOT EXISTS `claseviaje` (" +
            "`id` INTEGER NOT NULL PRIMARY KEY ," +
            "`descripcion` TEXT NOT NULL" +
            ")";

    String createTableViaje = "CREATE TABLE IF NOT EXISTS `viaje` (" +
            "`id` INTEGER NOT NULL PRIMARY KEY ," +
            "`id_usuario` INTEGER NOT NULL REFERENCES usuario(id) ON DELETE CASCADE," +
            "`id_ciudad_1` INTEGER NOT NULL REFERENCES ciudad(id) ON DELETE CASCADE," +
            "`id_ciudad_2` INTEGER NOT NULL REFERENCES ciudad(id) ON DELETE CASCADE," +
            "`id_claseviaje` INTEGER NOT NULL REFERENCES claseviaje(id) ON DELETE CASCADE," +
            "`pasajeros` INTEGER NOT NULL," +
            "`vuelta` INTEGER NOT NULL," +
            "`precio` INTEGER NOT NULL" +
            ")";

    String dropTableUsuario = "DROP TABLE `usuario`";


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUsuario);
        db.execSQL(createTableCiudad);
        db.execSQL(createTableClaseviaje);
        db.execSQL(createTableViaje);

        // Insert en la tabla usuario
        ContentValues values = new ContentValues();
        values.put("usuario", "josecebe");
        values.put("password", "123");
        db.insert("usuario", null, values);


        // Inserts en la tabla ciudad
        values = new ContentValues();
        values.put("nombre", "Valencia");
        values.put("diminutivo", "VAL");
        values.put("foto", "valencia");
        values.put("s_foto", "s_valencia");
        db.insert("ciudad", null, values);

        values = new ContentValues();
        values.put("nombre", "Madrid");
        values.put("diminutivo", "MAD");
        values.put("foto", "madrid");
        values.put("s_foto", "s_madrid");
        db.insert("ciudad", null, values);

        values = new ContentValues();
        values.put("nombre", "Barcelona");
        values.put("diminutivo", "BAR");
        values.put("foto", "barcelona");
        values.put("s_foto", "s_barcelona");
        db.insert("ciudad", null, values);

        values = new ContentValues();
        values.put("nombre", "Zaragoza");
        values.put("diminutivo", "ZAR");
        values.put("foto", "zaragoza");
        values.put("s_foto", "s_zaragoza");
        db.insert("ciudad", null, values);

        values = new ContentValues();
        values.put("nombre", "Bilbao");
        values.put("diminutivo", "BIL");
        values.put("foto", "bilbao");
        values.put("s_foto", "s_bilbao");
        db.insert("ciudad", null, values);


        // Inserts en la tabla claseviaje
        values = new ContentValues();
        values.put("descripcion", "Turista");
        db.insert("claseviaje", null, values);

        values = new ContentValues();
        values.put("descripcion", "Business");
        db.insert("claseviaje", null, values);


        // Inserts en la tabla viaje
        values = new ContentValues();
        values.put("id_usuario", 1);
        values.put("id_ciudad_1", 1);
        values.put("id_ciudad_2", 2);
        values.put("id_claseviaje", 1);
        values.put("pasajeros", 2);
        values.put("vuelta", 0);
        values.put("precio", 80);
        db.insert("viaje", null, values);

        values = new ContentValues();
        values.put("id_usuario", 1);
        values.put("id_ciudad_1", 2);
        values.put("id_ciudad_2", 1);
        values.put("id_claseviaje", 2);
        values.put("pasajeros", 4);
        values.put("vuelta", 1);
        values.put("precio", 480);
        db.insert("viaje", null, values);

        values = new ContentValues();
        values.put("id_usuario", 1);
        values.put("id_ciudad_1", 3);
        values.put("id_ciudad_2", 4);
        values.put("id_claseviaje", 2);
        values.put("pasajeros", 4);
        values.put("vuelta", 1);
        values.put("precio", 480);
        db.insert("viaje", null, values);

        values = new ContentValues();
        values.put("id_usuario", 1);
        values.put("id_ciudad_1",4);
        values.put("id_ciudad_2", 3);
        values.put("id_claseviaje", 2);
        values.put("pasajeros", 4);
        values.put("vuelta", 1);
        values.put("precio", 480);
        db.insert("viaje", null, values);

        values = new ContentValues();
        values.put("id_usuario", 1);
        values.put("id_ciudad_1", 4);
        values.put("id_ciudad_2", 1);
        values.put("id_claseviaje", 2);
        values.put("pasajeros", 4);
        values.put("vuelta", 1);
        values.put("precio", 480);
        db.insert("viaje", null, values);

        values = new ContentValues();
        values.put("id_usuario", 1);
        values.put("id_ciudad_1", 4);
        values.put("id_ciudad_2", 5);
        values.put("id_claseviaje", 2);
        values.put("pasajeros", 4);
        values.put("vuelta", 1);
        values.put("precio", 480);
        db.insert("viaje", null, values);

        values = new ContentValues();
        values.put("id_usuario", 1);
        values.put("id_ciudad_1", 5);
        values.put("id_ciudad_2", 4);
        values.put("id_claseviaje", 2);
        values.put("pasajeros", 4);
        values.put("vuelta", 1);
        values.put("precio", 480);
        db.insert("viaje", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Usuario getUsuario(int id) {
        Usuario usuario = new Usuario();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"*"};
        String[] whereValues = {Integer.toString(id)};
        Cursor c = db.query("usuario", columns,  "id=?", whereValues, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                usuario.setId(id);
                usuario.setUsuario(c.getString(c.getColumnIndex("usuario")));
                usuario.setPassword(c.getString(c.getColumnIndex("password")));
            } while (c.moveToNext());
        }

        return usuario;
    }

    public Ciudad getCiudad(int id) {
        Ciudad ciudad = new Ciudad();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"*"};
        String[] whereValues = {Integer.toString(id)};
        Cursor c = db.query("ciudad", columns,  "id=?", whereValues, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                ciudad.setId(id);
                ciudad.setNombre(c.getString(c.getColumnIndex("nombre")));
                ciudad.setDiminutivo(c.getString(c.getColumnIndex("diminutivo")));
                ciudad.setFoto(c.getString(c.getColumnIndex("foto")));
                ciudad.setSFoto(c.getString(c.getColumnIndex("s_foto")));
            } while (c.moveToNext());
        }

        return ciudad;
    }

    public Claseviaje getClaseviaje(int id) {
        Claseviaje claseviaje = new Claseviaje();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"*"};
        String[] whereValues = {Integer.toString(id)};
        Cursor c = db.query("claseviaje", columns, "id=?", whereValues, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                claseviaje.setId(id);
                claseviaje.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
            } while (c.moveToNext());
        }

        return claseviaje;
    }

    public Viaje getViaje(int id) {
        Viaje viaje = new Viaje();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"*"};
        String[] whereValues = {Integer.toString(id)};
        Cursor c = db.query("viaje", columns, "id=?", whereValues, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                viaje.setId(id);
                viaje.setId_claseviaje(c.getInt(c.getColumnIndex("id_claseviaje")));
                viaje.setId_ciudad_1(c.getInt(c.getColumnIndex("id_ciudad_1")));
                viaje.setId_ciudad_2(c.getInt(c.getColumnIndex("id_ciudad_2")));
                viaje.setId_usuario(c.getInt(c.getColumnIndex("id_usuario")));
                viaje.setPasajeros(c.getInt(c.getColumnIndex("pasajeros")));
                viaje.setVuelta(c.getInt(c.getColumnIndex("vuelta")));
                viaje.setPrecio(c.getInt(c.getColumnIndex("precio")));
            } while (c.moveToNext());
        }

        return viaje;
    }

    public long setUsuario(Usuario usuario) {
        long id = 0l;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("usuario", usuario.getUsuario());
        values.put("password", usuario.getPassword());

        if (usuario.getId() == 0) {
            id = db.insert("usuario", null, values);
        } else {
            values.put("id", usuario.getId());
            String[] whereArgs = {Integer.toString(usuario.getId())};
            id = db.update("usuario", values, "id=?", whereArgs);
        }

        return id;
    }

    public long setClaseviaje(Claseviaje claseviaje) {
        long id = 0l;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("descripcion", claseviaje.getDescripcion());

        if (claseviaje.getId() == 0) {
            id = db.insert("claseviaje", null, values);
        } else {
            values.put("id", claseviaje.getId());
            String[] whereArgs = {Integer.toString(claseviaje.getId())};
            id = db.update("claseviaje", values, "id=?", whereArgs);
        }

        return id;
    }

    public long setCiudad(Ciudad ciudad) {
        long id = 0l;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", ciudad.getNombre());
        values.put("diminutivo", ciudad.getDiminutivo());
        values.put("foto", ciudad.getFoto());
        values.put("s_foto", ciudad.getSFoto());

        if (ciudad.getId() == 0) {
            id = db.insert("ciudad", null, values);
        } else {
            values.put("id", ciudad.getId());
            String[] whereArgs = {Integer.toString(ciudad.getId())};
            id = db.update("ciudad", values, "id=?", whereArgs);
        }

        return id;
    }

    public long setViaje(Viaje viaje) {
        long id = 0l;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id_ciudad_1", viaje.getId_ciudad_1());
        values.put("id_ciudad_2", viaje.getId_ciudad_2());
        values.put("id_claseviaje", viaje.getId_claseviaje());
        values.put("id_usuario", viaje.getId_usuario());
        values.put("pasajeros", viaje.getPasajeros());
        values.put("vuelta", viaje.getVuelta());
        values.put("precio", viaje.getPrecio());

        if (viaje.getId() == 0) {
            id = db.insert("viaje", null, values);
        } else {
            values.put("id", viaje.getId());
            String[] whereArgs = {Integer.toString(viaje.getId())};
            id = db.update("viaje", values, "id=?", whereArgs);
        }

        return id;
    }

    public int getId(String table, String column, String value) {
        int id = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"id"};
        String[] whereValues = {value};
        Cursor c = db.query(table, columns, column + "=?", whereValues, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            } while (c.moveToNext());
        }

        return id;
    }

    public String getOne(String table, String column, int id) {
        String value = "";

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {column};
        String[] whereValues = {Integer.toString(id)};
        Cursor c = db.query(table, columns, "id=?", whereValues, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                value = c.getString(c.getColumnIndex(column));
            } while (c.moveToNext());
        }

        return value;
    }


    public boolean removeId(String table, int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(table, "id=" + id, null);

        return true;
    }

    public int getPages(String table, double regs) {
        Double pages = 0d;

        String[] columns = {"\"1\""};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(table, columns, null, null, null, null, null, null);
        int regsTotales = c.getCount();
        pages = Math.ceil(regsTotales / regs);

        return pages.intValue();
    }

    public Map<Object, Object> getPage(String table, int regs, int page) {
        Map<Object, Object> data = new HashMap<Object, Object>();

        int calc = page - 1;
        int limit1 = calc * regs;

        String[] columns = {"*"};
        String[] limit = {Integer.toString(limit1), Integer.toString(regs)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(table, columns, null, null, null, null, null, limit1 + "," + regs);

        int col = 0;
        if (c.moveToFirst()) {

            do {
                int numColumns = c.getColumnCount();
                Map<Object, Object> data2 = new HashMap<Object, Object>();
                for (int i=0; i < numColumns; i++) {

                    String columnName = c.getColumnName(i);
                    String value = c.getString(c.getColumnIndex(columnName));

                    if (columnName.equals("id")) {
                        data2.put(columnName, value);
                    } else if (columnName.substring(0, 3).equals("id_")) {
                        data2.put(columnName, foreign(columnName, value));
                    } else {
                        data2.put(columnName, value);
                    }
                }
                data.put(col, data2);
                col++;
            } while (c.moveToNext());
        }

        return data;
    }

    public Map<Object, Object> foreign(String key, String value) {
        Map<Object, Object> data = new HashMap<Object, Object>();

        String table = key.substring(3);

        if (table.substring(table.length()-2).equals("_1") || table.substring(table.length()-2).equals("_2")) {
            table = table.substring(0, table.length()-2);
        }

        String[] columns = {"*"};
        String[] values = {value};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(table, columns, "id=?", values, null, null, null, null);

        int col = 0;
        if (c.moveToFirst()) {
            do {
                int numColumns = c.getColumnCount();
                Map<Object, Object> data2 = new HashMap<Object, Object>();
                for (int i=0; i < numColumns; i++) {
                    String columnName = c.getColumnName(i);
                    String value2 = c.getString(c.getColumnIndex(columnName));

                    if (columnName.equals("id")) {
                        data2.put(columnName, value2);
                    } else if (columnName.substring(0, 3).equals("id_")) {
                        data2.put(columnName, foreign(columnName, value2));
                    } else {
                        data2.put(columnName, value2);
                    }
                }
                data.put(col, data2);
                col++;
            } while (c.moveToNext());
        }

        return data;
    }

    public Cursor getNombreCiudades() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("select id AS _id, nombre from ciudad order by nombre asc", null);

        return c;
    }

    public Cursor getClaseViajes() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("select id AS _id, descripcion from claseviaje order by descripcion desc", null);

        return c;
    }
}