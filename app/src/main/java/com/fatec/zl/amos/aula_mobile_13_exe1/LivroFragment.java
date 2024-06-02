package com.fatec.zl.amos.aula_mobile_13_exe1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.zl.amos.aula_mobile_13_exe1.controller.LivroController;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Livro;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.LivroDao;

import java.sql.SQLException;
import java.util.List;

public class LivroFragment extends Fragment {

    private View view;
    private EditText etCodigoLivro, etNomeLivro, etQuantidadeLivro, etEdicaoLivro, etISBNLivro;
    private TextView tvResLivro;
    private Button btnCadastrarLivro, btnListarLivro, btnExcluirLivro, btnConsultarLivro, btnAtualizarLivro;

    private LivroController lcont;

    public LivroFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_livro, container, false);

        // Initialize EditText fields
        etCodigoLivro = view.findViewById(R.id.etCodigoLivro);
        etNomeLivro = view.findViewById(R.id.etNomeLivro);
        etQuantidadeLivro = view.findViewById(R.id.etQuantidadeLivro);
        etEdicaoLivro = view.findViewById(R.id.etEdicaoLivro);
        etISBNLivro = view.findViewById(R.id.etISBNLivro);

        // Initialize Buttons
        btnCadastrarLivro = view.findViewById(R.id.btnCadastrarLivro);
        btnAtualizarLivro = view.findViewById(R.id.btnAtualizarLivro);
        btnExcluirLivro = view.findViewById(R.id.btnExcluirLivro);
        btnListarLivro = view.findViewById(R.id.btnListarLivro);
        btnConsultarLivro = view.findViewById(R.id.btnConsultarLivro);

        // Initialize TextView
        tvResLivro = view.findViewById(R.id.tvResLivro);
        tvResLivro.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResLivro.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Controller
        lcont = new LivroController(new LivroDao(view.getContext()));

        // Set button listeners
        btnCadastrarLivro.setOnClickListener(op -> acaoCadastrar());
        btnAtualizarLivro.setOnClickListener(op -> acaoAtualizar());
        btnListarLivro.setOnClickListener(op -> acaoListar());
        btnConsultarLivro.setOnClickListener(op -> acaoConsultar());
        btnExcluirLivro.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoListar() {
        try {
            List<Livro> livros = lcont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Livro l : livros) {
                buffer.append(l.toString()).append("\n");
            }
            tvResLivro.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Livro livro = montaLivro();
        try {
            lcont.deletar(livro);
            Toast.makeText(view.getContext(), "LIVRO EXCLUIDO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        Livro livro = montaLivro();
        try {
            livro = lcont.buscar(livro);
            if (livro.getNome() != null) {
                preencherLivro(livro);
            } else {
                Toast.makeText(view.getContext(), "LIVRO NÃO ENCONTRADO", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        Livro livro = montaLivro();
        try {
            lcont.modificar(livro);
            Toast.makeText(view.getContext(), "LIVRO ATUALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {
        Livro livro = montaLivro();
        try {
            lcont.inserir(livro);
            Toast.makeText(view.getContext(), "LIVRO CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etCodigoLivro.setText("");
        etNomeLivro.setText("");
        etQuantidadeLivro.setText("");
        etEdicaoLivro.setText("");
        etISBNLivro.setText("");
    }

    private Livro montaLivro() {
        Livro l = new Livro();

        // Verify if fields are not empty before parsing
        if (!etCodigoLivro.getText().toString().isEmpty()) {
            l.setCodigo(Integer.parseInt(etCodigoLivro.getText().toString()));
        }
        if (!etNomeLivro.getText().toString().isEmpty()) {
            l.setNome(etNomeLivro.getText().toString());
        }
        if (!etQuantidadeLivro.getText().toString().isEmpty()) {
            l.setQtdPaginas(Integer.parseInt(etQuantidadeLivro.getText().toString()));
        }
        if (!etEdicaoLivro.getText().toString().isEmpty()) {
            l.setEdicao(Integer.parseInt(etEdicaoLivro.getText().toString()));
        }
        if (!etISBNLivro.getText().toString().isEmpty()) {
            l.setISBN(etISBNLivro.getText().toString());
        }

        return l;
    }

    private void preencherLivro(Livro l) {
        etCodigoLivro.setText(String.valueOf(l.getCodigo()));
        etNomeLivro.setText(l.getNome());
        etQuantidadeLivro.setText(String.valueOf(l.getQtdPaginas()));
        etEdicaoLivro.setText(String.valueOf(l.getEdicao()));
        etISBNLivro.setText(l.getISBN());
    }
}
