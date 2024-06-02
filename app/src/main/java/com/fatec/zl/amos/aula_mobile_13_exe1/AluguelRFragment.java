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
import com.fatec.zl.amos.aula_mobile_13_exe1.controller.AluguelRevistaController;
import com.fatec.zl.amos.aula_mobile_13_exe1.controller.AlunoController;
import com.fatec.zl.amos.aula_mobile_13_exe1.controller.RevistaController;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluguel;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Aluno;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Exemplar;
import com.fatec.zl.amos.aula_mobile_13_exe1.model.Revista;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.AluguelDao;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.AlunoDao;
import com.fatec.zl.amos.aula_mobile_13_exe1.persistence.RevistaDao;

import java.sql.SQLException;
import java.util.List;

public class AluguelRFragment extends Fragment {

    private View view;
    private EditText etDataRetirada, etDataDevolucao;
    private TextView tvResAluguelRevista;
    private Spinner spinnerRevista, spinnerAluno;
    private Button btnCadastrar, btnListar, btnExcluir, btnConsultar, btnAtualizar;
    private AluguelRevistaController arcont;
    private AlunoController acont;
    private RevistaController rcont;

    public AluguelRFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluguel_r, container, false);

        // Initialize EditText fields
        etDataRetirada = view.findViewById(R.id.dataRetiradaAluguelRevista);
        etDataDevolucao = view.findViewById(R.id.etDataDevolucaoAluguelRevista);

        // Initialize Buttons
        btnCadastrar = view.findViewById(R.id.btnCadastrarAluguelRevista);
        btnAtualizar = view.findViewById(R.id.btnAtualizarAluguelRevista);
        btnExcluir = view.findViewById(R.id.btnExcluirAluguelRevista);
        btnListar = view.findViewById(R.id.btnListarAluguelRevista);
        btnConsultar = view.findViewById(R.id.btnConsultarAluguelRevista);

        // Initialize TextView
        tvResAluguelRevista = view.findViewById(R.id.tvResAluguelRevista);
        tvResAluguelRevista.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvResAluguelRevista.setMovementMethod(new ScrollingMovementMethod());

        // Initialize Spinners
        spinnerRevista = view.findViewById(R.id.spinnerRevista);
        spinnerAluno = view.findViewById(R.id.spinnerAluno);

        // Initialize Controllers
        arcont = new AluguelRevistaController(new AluguelDao(view.getContext()));
        acont = new AlunoController(new AlunoDao(view.getContext())); // Ensure this controller is properly initialized
        rcont = new RevistaController(new RevistaDao(view.getContext())); // Ensure this controller is properly initialized

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
            List<Aluno> alunos = acont.listar();
            List<Revista> revistas = rcont.listar();

            ArrayAdapter<Aluno> alunoAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, alunos);
            alunoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAluno.setAdapter(alunoAdapter);

            ArrayAdapter<Revista> revistaAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, revistas);
            revistaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRevista.setAdapter(revistaAdapter);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Aluguel> alugueis = arcont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Aluguel ar : alugueis) {
                buffer.append(ar.toString()).append("\n");
            }
            tvResAluguelRevista.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Aluguel aluguel = montaAluguelRevista();

        try {
            arcont.deletar(aluguel);
            Toast.makeText(view.getContext(), "ALUGUEL EXCLUÍDO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoConsultar() {
        Aluguel aluguel = montaAluguelRevista();
        try {
            aluguel = arcont.buscar(aluguel);
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
        Aluguel aluguel = montaAluguelRevista();
        try {
            arcont.modificar(aluguel);
            Toast.makeText(view.getContext(), "ALUGUEL ATUALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void acaoCadastrar() {
        Aluguel aluguel = montaAluguelRevista();
        aluguel.setTipoExemplar("REVISTA");
        try {
            arcont.inserir(aluguel);
            Toast.makeText(view.getContext(), "ALUGUEL CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limparCampos();
    }

    private void limparCampos() {
        etDataRetirada.setText("");
        etDataDevolucao.setText("");
        spinnerRevista.setSelection(0);
        spinnerAluno.setSelection(0);
    }

    private Aluguel montaAluguelRevista() {
        Aluguel ar = new Aluguel();

        if (spinnerAluno.getSelectedItem() != null) {
            ar.setAluno((Aluno) spinnerAluno.getSelectedItem());
        }
        if (spinnerRevista.getSelectedItem() != null) {
            ar.setExemplar((Revista) spinnerRevista.getSelectedItem());
        }
        if (!etDataRetirada.getText().toString().isEmpty()) {
            ar.setDataRetirada(etDataRetirada.getText().toString());
        }
        if (!etDataDevolucao.getText().toString().isEmpty()) {
            ar.setDataDevolucao(etDataDevolucao.getText().toString());
        }

        return ar;
    }

    private void preencherAluguel(Aluguel ar) {
        etDataRetirada.setText(ar.getDataRetirada());
        etDataDevolucao.setText(ar.getDataDevolucao());

        ArrayAdapter<Aluno> alunoAdapter = (ArrayAdapter<Aluno>) spinnerAluno.getAdapter();
        spinnerAluno.setSelection(alunoAdapter.getPosition(ar.getAluno()));

        ArrayAdapter<Revista> revistaAdapter = (ArrayAdapter<Revista>) spinnerRevista.getAdapter();
        spinnerRevista.setSelection(revistaAdapter.getPosition((Revista) ar.getExemplar()));
    }
}
