package com.fatec.zl.amos.aula_mobile_13_exe1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            carregarFragment(bundle);}




    }

    private void carregarFragment(Bundle bundle) {
        String tipo = bundle.getString("tipo");
        if (tipo.equals("Livro")) {
            fragment = new LivroFragment();

        } else if (tipo.equals("Revista")) {
            fragment = new RevistaFragment();

        }else if (tipo.equals("Aluno")){
            fragment = new AlunoFragment();
        }
        else if (tipo.equals("AluguelRevista")){
            fragment = new AluguelRFragment();


        }else if (tipo.equals("AluguelLivro")){
            fragment = new AluguelLFragment();


        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);


        if (id == R.id.item_revista) {
            bundle.putString("tipo", "Revista");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        if (id == R.id.item_aluno) {
            bundle.putString("tipo", "Aluno");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        if (id == R.id.item_livro) {
            bundle.putString("tipo", "Livro");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        if (id == R.id.item_aluguelLivro) {
            bundle.putString("tipo", "AluguelLivro");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }
        if (id == R.id.item_aluguelRevista) {
            bundle.putString("tipo", "AluguelRevista");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


}