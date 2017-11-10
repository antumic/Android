package br.com.caelum.cadastro.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.Adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.Converter.AlunoConverter;
import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.Fragment.MapaFragment;
import br.com.caelum.cadastro.Model.Aluno;
import br.com.caelum.cadastro.Permission.Permissao;
import br.com.caelum.cadastro.R;

import static android.Manifest.permission.CALL_PHONE;


public class ListaAlunosActivity extends AppCompatActivity {
    private ListView listaAlunos;
    private List<Aluno> alunos;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_enviar_notas:
                AlunoDAO dao = new AlunoDAO(this);
                List<Aluno> alunos = dao.getLista();
                dao.close();
                String json = new AlunoConverter().toJSON(alunos);
                Toast.makeText(this, json, Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_sobre:
                Toast.makeText(this,"Desenvolvedor: Michael A. Braga" + "\n" + "Versão: 1.0", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_mapa:
                Intent mapa = new Intent(ListaAlunosActivity.this, MostraAlunosActivity.class);
                startActivity(mapa);
                return true;

            case R.id.menu_sair:
                Toast.makeText(this,"Obrigado. Até breve!", Toast.LENGTH_LONG).show();
                this.finish();
                return true;

            case R.id.menu_receber_provas:
                Intent provas = new Intent (this, ProvasActivity.class);
                startActivity(provas);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Permissao.fazPermissao(this);

        listaAlunos = (ListView) findViewById(R.id.activity_lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(posicao);
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                edicao.putExtra("aluno", aluno);
                startActivity(edicao);
            }
        });

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(listaAlunos);
    }


    @Override
    public  void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);



        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Tem certeza que deseja deletar?")
                        .setPositiveButton("Sim",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                        dao.deletar(alunoSelecionado);
                                        dao.close();
                                        carregaLista();
                                    }
                                }).setNegativeButton("Não", null).show();
                return false;
            }
        });



        MenuItem email = menu.add("Enviar Email");
        Intent enviarEmail = new Intent(Intent.ACTION_SEND);
        enviarEmail.setType("message/rfc822");
        enviarEmail.putExtra(Intent.EXTRA_EMAIL, new String[] { alunoSelecionado.getSite() });
        email.setIntent(enviarEmail);


        MenuItem sms = menu.add("Enviar SMS");
        Intent enviarSMS = new Intent(Intent.ACTION_VIEW);
        enviarSMS.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));
        sms.setIntent(enviarSMS);


        MenuItem ligar = menu.add("Ligar");
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);


        MenuItem localizar = menu.add("Ver endereço");
        Intent intentVerEndereco = new Intent(Intent.ACTION_VIEW);
        intentVerEndereco.setData(Uri.parse("geo:0,0?z=14&q=" + alunoSelecionado.getEndereco()));
        localizar.setIntent(intentVerEndereco);


    }



    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }



    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);

        this.listaAlunos.setAdapter(adapter);
    }






}
