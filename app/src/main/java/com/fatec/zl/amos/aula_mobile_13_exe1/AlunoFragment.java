package com.fatec.zl.amos.aula_mobile_13_exe1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.zl.amos.aula_mobile_13_exe1.controller.AlunoController;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluno;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.AlunoDao;

import java.sql.SQLException;
import java.util.List;

public class AlunoFragment extends Fragment {

    private View view;
    private EditText etRaAluno, etNomeAluno, etEmailAluno;
    private TextView tvResAluno;
    private Button btnCadastrarAluno, btnListarAluno, btnExcluirAluno, btnConsultarAluno, btnAtualizarAluno;

    private AlunoController acont;

    public AlunoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aluno, container, false);

        // Initialize EditText fields
        etRaAluno = view.findViewById(R.id.etRA);
        etNomeAluno = view.findViewById(R.id.etNomeAluno);
        etEmailAluno = view.findViewById(R.id.etEmailAluno);

        // Initialize Buttons
        btnCadastrarAluno = view.findViewById(R.id.btnCadastrarAluno);
        btnAtualizarAluno = view.findViewById(R.id.btnAtualizarAluno);
        btnExcluirAluno = view.findViewById(R.id.btnExcluirAluno);
        btnListarAluno = view.findViewById(R.id.btnListarAluno);
        btnConsultarAluno = view.findViewById(R.id.btnConsultarAluno);

        // Initialize TextView
        tvResAluno = view.findViewById(R.id.tvResAluno);
        tvResAluno.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResAluno.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Controller
        acont = new AlunoController(new AlunoDao(view.getContext()));

        // Set button listeners
        btnCadastrarAluno.setOnClickListener(op -> acaoCadastrar());
        btnAtualizarAluno.setOnClickListener(op -> acaoAtualizar());
        btnListarAluno.setOnClickListener(op -> acaoListar());
        btnConsultarAluno.setOnClickListener(op -> acaoConsultar());
        btnExcluirAluno.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoListar() {
        try {
            List<Aluno> alunos = acont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Aluno a : alunos) {
                buffer.append(a.toString()).append("\n");
            }
            tvResAluno.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Aluno aluno = montaAluno();
        try {
            acont.deletar(aluno);
            Toast.makeText(view.getContext(), "ALUNO EXCLUIDO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        Aluno aluno = montaAluno();
        try {
            aluno = acont.buscar(aluno);
            if (aluno.getNome() != null) {
                preencherAluno(aluno);
            } else {
                Toast.makeText(view.getContext(), "ALUNO NÃO ENCONTRADO", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        Aluno aluno = montaAluno();
        try {
            acont.modificar(aluno);
            Toast.makeText(view.getContext(), "ALUNO ATUALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {
        Aluno aluno = montaAluno();
        try {
            acont.inserir(aluno);
            Toast.makeText(view.getContext(), "ALUNO CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etRaAluno.setText("");
        etNomeAluno.setText("");
        etEmailAluno.setText("");
    }

    private Aluno montaAluno() {
        Aluno a = new Aluno();

        // Verify if fields are not empty before parsing
        if (!etRaAluno.getText().toString().isEmpty()) {
            a.setRA(Integer.parseInt(etRaAluno.getText().toString()));
        }
        if (!etNomeAluno.getText().toString().isEmpty()) {
            a.setNome(etNomeAluno.getText().toString());
        }
        if (!etEmailAluno.getText().toString().isEmpty()) {
            a.setEmail(etEmailAluno.getText().toString());
        }
        return a;
    }

    private void preencherAluno(Aluno a) {
        etRaAluno.setText(String.valueOf(a.getRA())); // Convert integer to string
        etNomeAluno.setText(a.getNome());
        etEmailAluno.setText(a.getEmail());
    }

}
