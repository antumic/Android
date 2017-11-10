package br.com.caelum.cadastro.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.com.caelum.cadastro.BuildConfig;
import br.com.caelum.cadastro.Converter.AlunoConverter;
import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.Helper.FormularioHelper;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.Model.Aluno;
import br.com.caelum.cadastro.Support.WebClient;


public class FormularioActivity extends AppCompatActivity {
    private FormularioHelper helper;
    public static final String ALUNO_SELECIONADO = "alunoSelecionado";
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIRA_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                helper.carregaImagem(this.localArquivoFoto);
            } else {
                this.localArquivoFoto = null;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            /*
            case R.id.menu_enviar_notas:
                AlunoDAO alunos = new AlunoDAO(FormularioActivity.this);
                String json = new AlunoConverter().toJSON((List<Aluno>) alunos);
                WebClient client = new WebClient();
                String resposta = client.post(json);
                Toast.makeText(this, resposta, Toast.LENGTH_LONG).show();
            */


            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAlunoDoFormulario();
                AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
                if (helper.temNome()) {

                    if (aluno.getId() == null){
                        dao.insere(aluno);
                    } else {
                        dao.altera(aluno);
                    }

                } else {
                    helper.mostraErro();
                    return false;
                }
                //Toast.makeText(this,"Aluno cadastrado com sucesso: " + aluno.getNome(), Toast.LENGTH_LONG).show();
                dao.close();
                finish();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        this.helper = new FormularioHelper(this);
        Intent intent = this.getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null){
            helper.colocaNoFormulario(aluno);
        }

        FloatingActionButton foto = helper.getFotoButton();

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localArquivoFoto = getExternalFilesDir(null) + "/"+ System.currentTimeMillis()+".jpg";;
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File arquivoFoto = new File(localArquivoFoto);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FormularioActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto));
                startActivityForResult(camera, 123);
            }
        });
    }


}
