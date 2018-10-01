package com.example.e544472.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.e544472.myapplication.modelo.Aluno;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereço;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;

    boolean res = false;

    private Aluno aluno;


    boolean validaCampos(Context valida){

        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String endereço = campoEndereço.getText().toString();
        Double nota = ((double) campoNota.getProgress());


        if (isCampoVazio(nome) || isCampoVazio(telefone)
                || isCampoVazio(endereço) || isCampoVazio(nota.toString())){
            campoNome.requestFocus();
            res = true;
        }

        if (res) {

            AlertDialog.Builder msg = new AlertDialog.Builder(valida);
            msg.setTitle("Aviso");
            msg.setMessage("Nome, Telefone, Endereço e Nota são campos obrigatórios, preencha corretamente!");
            msg.setNeutralButton("OK", null);
            msg.show();
            return false;
        }

        return true;
    }


    private Boolean isCampoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty() );
        return resultado;

    }


    public FormularioHelper(FormularioActivity activity){
        campoNome = activity.findViewById(R.id.formulario_nome);
        campoEndereço = activity.findViewById(R.id.formulario_endereço);
        campoTelefone = activity.findViewById(R.id.formulario_telefone);
        campoSite = activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereço(campoEndereço.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereço.setText(aluno.getEndereço());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;

    }
}
