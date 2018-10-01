package com.example.e544472.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e544472.myapplication.dao.AlunoDAO;
import com.example.e544472.myapplication.modelo.Aluno;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null){
            helper.preencheFormulario(aluno);
        }

        Button botaFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto =  getExternalFilesDir( Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                Uri photoURI = FileProvider.getUriForFile(FormularioActivity.this,  "com.example.e544472.myapplication.fileprovider" , arquivoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK);
        if (requestCode == CODIGO_CAMERA) {
            ImageView foto = (ImageView) findViewById(R.id.formulario_foto);
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmap_reduzido = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            foto.setImageBitmap(bitmap_reduzido);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                boolean resposta = helper.validaCampos(this);
                if (resposta) {
                    Aluno aluno = helper.pegaAluno();
                    AlunoDAO dao = new AlunoDAO(this);
                    if (aluno.getId() != null) {
                        dao.altera(aluno);
                    } else {
                        dao.insere(aluno);
                    }
                    dao.close();

                    Toast.makeText(FormularioActivity.this,  aluno.getNome() + " Salvo !", Toast.LENGTH_SHORT).show();
                    finish();
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
