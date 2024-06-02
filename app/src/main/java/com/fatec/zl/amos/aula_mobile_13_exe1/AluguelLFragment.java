package com.fatec.zl.amos.aula_mobile_13_exe1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.zl.amos.aula_mobile_13_exe1.controller.AluguelLivroController;
import com.fatec.zl.amos.aula_mobile_13_exe1.controller.AlunoController;
import com.fatec.zl.amos.aula_mobile_13_exe1.controller.LivroController;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluguel;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluno;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Livro;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.AluguelDao;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.AlunoDao;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.LivroDao;

import java.sql.SQLException;
import java.util.List;
public class AluguelLFragment extends Fragment {

    private View view;
    private EditText etDataRetirada, etDataDevolucao;
    private TextView tvResAluguelLivro;
    private Spinner spinnerLivro, spinnerAluno;
    private Button btnCadastrar, btnListar, btnExcluir, btnConsultar, btnAtualizar;
    private AluguelLivroController aluguelLivroController;
    private AlunoController alunoController;
    private LivroController livroController;

    public AluguelLFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluguel, container, false);

        // Initialize EditText fields
        etDataRetirada = view.findViewById(R.id.etDataRetiradaAluguelLivro);
        etDataDevolucao = view.findViewById(R.id.etDataDevolucaoAluguelLivro);

        // Initialize Buttons
        btnCadastrar = view.findViewById(R.id.btnCadastrarAluguelLivro);
        btnAtualizar = view.findViewById(R.id.btnAtualizarAluguelLivro);
        btnExcluir = view.findViewById(R.id.btnExcluirAluguelLivro);
        btnListar = view.findViewById(R.id.btnListarAluguelLivro);
        btnConsultar = view.findViewById(R.id.btnConsultarAluguelLivro);

        // Initialize TextView
        tvResAluguelLivro = view.findViewById(R.id.tvResAluguelLivro);
        tvResAluguelLivro.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResAluguelLivro.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Spinners
        spinnerLivro = view.findViewById(R.id.spinnerLivro);
        spinnerAluno = view.findViewById(R.id.spinnerAluno);

        // Initialize Controllers
        aluguelLivroController = new AluguelLivroController(new AluguelDao(view.getContext()));
        alunoController = new AlunoController(new AlunoDao(view.getContext())); // Ensure this controller is properly initialized
        livroController = new LivroController(new LivroDao(view.getContext())); // Ensure this controller is properly initialized

        // Set button listeners
        btnCadastrar.setOnClickListener(op -> acaoCadastrar());
        btnAtualizar.setOnClickListener(op -> acaoAtualizar());
        btnListar.setOnClickListener(op -> acaoListar());
        btnConsultar.setOnClickListener(op -> acaoConsultar());
        btnExcluir.setOnClickListener(op -> acaoExcluir());

        preencherSpinner();

        return view;
    }

    private void preencherSpinner() {
        try {
            List<Aluno> alunos = alunoController.listar();
            List<Livro> livros = livroController.listar();

            ArrayAdapter<Aluno> alunoAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, alunos);
            alunoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAluno.setAdapter(alunoAdapter);

            ArrayAdapter<Livro> livroAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, livros);
            livroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLivro.setAdapter(livroAdapter);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void acaoListar() {
        try {
            List<Aluguel> alugueis = aluguelLivroController.listar();
            StringBuilder buffer = new StringBuilder();
            for (Aluguel aluguel : alugueis) {
                buffer.append(aluguel.toString()).append("\n");
            }
            tvResAluguelLivro.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Aluguel aluguel = montaAluguelLivro();
        try {
            aluguelLivroController.deletar(aluguel);
            Toast.makeText(view.getContext(), "ALUGUEL EXCLUÍDO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        Aluguel aluguel = montaAluguelLivro();
        try {
            aluguel = aluguelLivroController.buscar(aluguel);
            if (aluguel.getExemplar() != null) {
                preencherAluguel(aluguel);
            } else {
                Toast.makeText(view.getContext(), "ALUGUEL NÃO ENCONTRADO", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        Aluguel aluguel = montaAluguelLivro();
        try {
            aluguelLivroController.modificar(aluguel);
            Toast.makeText(view.getContext(), "ALUGUEL ATUALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {

        Aluguel aluguel = montaAluguelLivro();
        aluguel.setTipoExemplar("REVISTA");
        try {
            aluguelLivroController.inserir(aluguel);
            Toast.makeText(view.getContext(), "ALUGUEL CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etDataRetirada.setText("");
        etDataDevolucao.setText("");
        spinnerLivro.setSelection(0);
        spinnerAluno.setSelection(0);
    }

    private Aluguel montaAluguelLivro() {
        Aluguel aluguel = new Aluguel();

        if (spinnerAluno.getSelectedItem() != null) {
            aluguel.setAluno((Aluno) spinnerAluno.getSelectedItem());
        }
        if (spinnerLivro.getSelectedItem() != null) {
            aluguel.setExemplar((Livro) spinnerLivro.getSelectedItem());
        }
        if (!etDataRetirada.getText().toString().isEmpty()) {
            aluguel.setDataRetirada(etDataRetirada.getText().toString());
        }
        if (!etDataDevolucao.getText().toString().isEmpty()) {
            aluguel.setDataDevolucao(etDataDevolucao.getText().toString());
        }

        return aluguel;
    }

    private void preencherAluguel(Aluguel aluguel) {
        etDataRetirada.setText(aluguel.getDataRetirada());
        etDataDevolucao.setText(aluguel.getDataDevolucao());

        ArrayAdapter<Aluno> alunoAdapter = (ArrayAdapter<Aluno>) spinnerAluno.getAdapter();
        spinnerAluno.setSelection(alunoAdapter.getPosition(aluguel.getAluno()));

        ArrayAdapter<Livro> livroAdapter = (ArrayAdapter<Livro>) spinnerLivro.getAdapter();
        spinnerLivro.setSelection(livroAdapter.getPosition((Livro) aluguel.getExemplar()));
    }
}
