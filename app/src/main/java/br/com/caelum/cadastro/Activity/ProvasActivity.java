package br.com.caelum.cadastro.Activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.cadastro.Fragment.DetalhesProvasFragment;
import br.com.caelum.cadastro.Fragment.ListaProvasFragment;
import br.com.caelum.cadastro.Model.Prova;
import br.com.caelum.cadastro.R;

public class ProvasActivity extends AppCompatActivity{

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.IsTablet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (isTablet()){
            transaction
                    .replace(R.id.provas_lista, new ListaProvasFragment())
                    .replace(R.id.provas_detalhes, new DetalhesProvasFragment());
        } else {
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }
        transaction.commit();
    }




    public void selecionaProva(Prova prova) {

        FragmentManager manager = getSupportFragmentManager();

        if (isTablet()) {


            DetalhesProvasFragment detalhesProva = (DetalhesProvasFragment) manager.findFragmentById(R.id.provas_detalhes);
            detalhesProva.populaCamposComDados(prova);





        } else {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova", prova);

            DetalhesProvasFragment detalhesProva = new DetalhesProvasFragment();
            detalhesProva.setArguments(argumentos);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.provas_view, detalhesProva);
            transaction.commit();
        }

    }



}
