package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Blade;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Mat;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;

@Database(entities = {Blade.class, Processo.class, Mat.class}, version = 1, exportSchema = false)
public abstract class PlotterDatabase extends RoomDatabase {

    public abstract LaminaDao laminaDao();
    public abstract ProcessDao processoDao();
    public abstract TapeteDao tapeteDao();
    private static PlotterDatabase instance;

    public static PlotterDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (PlotterDatabase.class) {
                if (instance == null) {
                    RoomDatabase.Builder builder = Room.databaseBuilder(context, PlotterDatabase.class, "caneta.db");
                    instance = (PlotterDatabase) builder.build();
                }
            }
        }
        return instance;
    }

}
