package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.service;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeService {

    private static final String ARQUIVO = "utfpr.edu.guiaconfiguracaorapidoplotterrecorte.sharedpreferences.PREFERENCIAS_TEMAS";
    private static final String TEMA = "TEMA";
    private final ContextWrapper contextWrapper;

    private int opcaoTema = AppCompatDelegate.MODE_NIGHT_NO;

    public ThemeService(ContextWrapper contextWrapper) {
        this.contextWrapper = contextWrapper;
    }

    public void lerPreferenciaTema() {
        SharedPreferences sharedPreferences = contextWrapper.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        opcaoTema = sharedPreferences.getInt(TEMA, opcaoTema);
        AppCompatDelegate.setDefaultNightMode(opcaoTema);
    }

    public void salvarPreferenciaTema(int novoTema) {
        SharedPreferences sharedPreferences = contextWrapper.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TEMA, novoTema);
        editor.commit();
        opcaoTema = novoTema;
        AppCompatDelegate.setDefaultNightMode(novoTema);
    }

    public int getOpcaoTema() {
        return opcaoTema;
    }
}
