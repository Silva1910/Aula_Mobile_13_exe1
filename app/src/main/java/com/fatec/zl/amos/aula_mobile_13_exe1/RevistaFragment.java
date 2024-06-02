package com.fatec.zl.amos.aula_mobile_13_exe1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.zl.amos.aula_mobile_13_exe1.controller.RevistaController;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Revista;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.RevistaDao;

import java.sql.SQLException;
import java.util.List;

public class RevistaFragment extends Fragment {

    private View view;
    private EditText etISSNRevista, etNomeRevista, etQuantidadeRevista, etCodigoRevista;
    private TextView tvResRevista;
    private Button btnCadastrarRevista, btnListarRevista, btnExcluirRevista, btnConsultarRevista, btnAtualizarRevista;

    private RevistaController rcont;

    public RevistaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_revista, container, false);

        // Initialize EditText fields
        etISSNRevista = view.findViewById(R.id.etISSNRevista);
        etNomeRevista = view.findViewById(R.id.etNomeRevista);
        etCodigoRevista = view.findViewById(R.id.etCodigoRevista);
        etQuantidadeRevista = view.findViewById(R.id.etQuantidadeRevista);


        // Initialize Buttons
        btnCadastrarRevista = view.findViewById(R.id.btnCadastrarRevista);
        btnAtualizarRevista = view.findViewById(R.id.btnAtualizarRevista);
        btnExcluirRevista = view.findViewById(R.id.btnExcluirRevista);
        btnListarRevista = view.findViewById(R.id.btnListarRevista);
        btnConsultarRevista = view.findViewById(R.id.btnConsultarRevista);

        // Initialize TextView
        tvResRevista = view.findViewById(R.id.tvResRevista);
        tvResRevista.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResRevista.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Controller
        rcont = new RevistaController(new RevistaDao(view.getContext()));

        // Set button listeners
        btnCadastrarRevista.setOnClickListener(op -> acaoCadastrar());
        btnAtualizarRevista.setOnClickListener(op -> acaoAtualizar());
        btnListarRevista.setOnClickListener(op -> acaoListar());
        btnConsultarRevista.setOnClickListener(op -> acaoConsultar());
        btnExcluirRevista.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoListar() {
        try {
            List<Revista> revistas = rcont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Revista r : revistas) {
                buffer.append(r.toString()).append("\n");
            }
            tvResRevista.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Revista revista = montaRevista();
        try {
            rcont.deletar(revista);
            Toast.makeText(view.getContext(), "REVISTA EXCLUIDA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        Revista revista = montaRevista();
        try {
            revista = rcont.buscar(revista);
            if (revista.getNome() != null) {
                preencherRevista(revista);
            } else {
                Toast.makeText(view.getContext(), "REVISTA NÃO ENCONTRADA", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        Revista revista = montaRevista();
        try {
            rcont.modificar(revista);
            Toast.makeText(view.getContext(), "REVISTA ATUALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {
        Revista revista = montaRevista();
        try {
            Log.d("RevistaFragment", "Dados da revista antes de inserir: " + revista.getCodigo() + ", " + revista.getNome() + ", " + revista.getQtdPaginas() + ", " + revista.getISSN());
            rcont.inserir(revista);
            Toast.makeText(view.getContext(), "REVISTA CADASTRADA COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etISSNRevista.setText("");
        etNomeRevista.setText("");
        etQuantidadeRevista.setText("");
        etCodigoRevista.setText("");
    }

    private Revista montaRevista() {
        Revista r = new Revista();

        if (!etISSNRevista.getText().toString().isEmpty()) {
            r.setISSN(etISSNRevista.getText().toString());
        }
        if (!etNomeRevista.getText().toString().isEmpty()) {
            r.setNome(etNomeRevista.getText().toString());
        }
        if (!etCodigoRevista.getText().toString().isEmpty()) {
            r.setCodigo(Integer.parseInt(etCodigoRevista.getText().toString()));
        }
        if (!etQuantidadeRevista.getText().toString().isEmpty()) {
            r.setQtdPaginas(Integer.parseInt(etQuantidadeRevista.getText().toString()));
        }

        return r;
    }


    private void preencherRevista(Revista r) {
        etISSNRevista.setText(r.getISSN());
        etNomeRevista.setText(r.getNome());
        etQuantidadeRevista.setText(String.valueOf(r.getQtdPaginas()));
        etCodigoRevista.setText(String.valueOf(r.getCodigo()));
    }
}
