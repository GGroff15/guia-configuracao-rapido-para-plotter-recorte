package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.persistencia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Caneta;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Lamina;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Material;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Processo;
import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.entidades.Tapete;

@Database(entities = {Caneta.class, Lamina.class, Material.class, Processo.class, Tapete.class}, version = 2, exportSchema = false)
public abstract class PlotterDatabase extends RoomDatabase {

    public abstract CanetaDao canetaDao();
    public abstract LaminaDao laminaDao();
    public abstract MaterialDao materialDao();
    public abstract ProcessoDao processoDao();
    public abstract TapeteDao tapeteDao();

    private static PlotterDatabase instance;

    public static PlotterDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (PlotterDatabase.class) {
                if (instance == null) {
                    RoomDatabase.Builder builder = Room.databaseBuilder(context, PlotterDatabase.class, "caneta.db");

                    builder.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    carregarTapetesIniciais(context);
                                    carregarLaminasIniciais(context);
                                }
                            });
                        }
                    });

                    instance = (PlotterDatabase) builder.build();
                }
            }
        }
        return instance;
    }

    private static void carregarTapetesIniciais(final Context context){
        instance.tapeteDao().insert(new Tapete(context.getString(R.string.roxo), context.getString(R.string.standard)));
        instance.tapeteDao().insert(new Tapete(context.getString(R.string.roxo), context.getString(R.string.superforte)));
        instance.tapeteDao().insert(new Tapete(context.getString(R.string.turquesa), context.getString(R.string.superforte)));
    }

    private static void carregarLaminasIniciais(final Context context) {
        instance.laminaDao().insert(new Lamina(context.getString(R.string.turquesa), context.getString(R.string.delicado)));
        instance.laminaDao().insert(new Lamina(context.getString(R.string.roxo), context.getString(R.string.cortepesado)));
    }

}
