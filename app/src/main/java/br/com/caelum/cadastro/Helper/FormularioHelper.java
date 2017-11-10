package br.com.caelum.cadastro.Helper;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.caelum.cadastro.Activity.FormularioActivity;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.Model.Aluno;

public class FormularioHelper {

    private TextInputLayout campoNome;
    private Aluno aluno;
    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText email;
    private RatingBar nota;
    private ImageView foto;
    private FloatingActionButton fotoButton;


    public void carregaImagem(String localArquivoFoto) {
        Bitmap bitmap  = BitmapFactory.decodeFile(localArquivoFoto);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        foto.setImageBitmap(bitmap);
        foto.setTag(localArquivoFoto);
    }


    public FormularioHelper(FormularioActivity activity){
        this.aluno = new Aluno();
        this.nome = (EditText) activity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        this.endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        this.email = (EditText) activity.findViewById(R.id.formulario_site);
        this.nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoNome = (TextInputLayout) activity.findViewById(R.id.formulario_nome_til);
        foto = (ImageView) activity.findViewById(R.id.formulario_foto);
        fotoButton = (FloatingActionButton) activity.findViewById(R.id.formulario_foto_button);
        aluno = new Aluno();
    }


    public FloatingActionButton getFotoButton() {
        return fotoButton;
    }




    public void colocaNoFormulario(Aluno aluno) {
        nome.setText(aluno.getNome());
        nota.setProgress(aluno.getNota().intValue());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        email.setText(aluno.getSite());

        if (aluno.getCaminhoFoto() == null) {

        } else {
            carregaImagem(aluno.getCaminhoFoto());
        }


        this.aluno = aluno;
    }





    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(email.getText().toString());
        aluno.setNota((double) nota.getProgress());
        aluno.setCaminhoFoto((String) foto.getTag());
        return aluno;
    }


    public boolean temNome() {
        return !nome.getText().toString().isEmpty();
    }
    public void mostraErro() {
        campoNome.setError("Campo obrigatório");
    }


}
