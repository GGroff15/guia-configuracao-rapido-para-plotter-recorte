package guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import guilhermegroff.utfpr.edu.guiaconfigrapidoplotterrecorte.R;

public class AutoriaAppActivity extends AppCompatActivity {

    public static void sobre(AppCompatActivity appCompatActivity) {
        Intent intent = new Intent(appCompatActivity, AutoriaAppActivity.class);
        appCompatActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria_app);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            cancelar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cancelar() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}