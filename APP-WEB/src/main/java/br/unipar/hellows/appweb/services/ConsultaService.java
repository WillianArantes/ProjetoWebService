package br.unipar.hellows.appweb.services;

import br.unipar.hellows.appweb.domain.Consulta;
import br.unipar.hellows.appweb.domain.Medico;
import br.unipar.hellows.appweb.domain.Paciente;
import br.unipar.hellows.appweb.dto.ConsultaDTO;
import br.unipar.hellows.appweb.repositories.ConsultaRepository;
import br.unipar.hellows.appweb.repositories.MedicoRepository;
import br.unipar.hellows.appweb.repositories.PacienteRepository;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsultaService {

    private ConsultaRepository consultaRepository = new ConsultaRepository();
    private PacienteRepository pacienteRepository = new PacienteRepository();
    private MedicoRepository medicoRepository = new MedicoRepository();

    public void agendarConsulta(ConsultaDTO consultadto) throws SQLException, NamingException {
        if (consultadto.getDataCadastro() == null || consultadto.getHoraCadastro() == null) {
            throw new IllegalArgumentException("Data e hora da consulta são obrigatórias.");
        }
        // Formata a hora de string para LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaConvertida = LocalTime.parse(consultadto.getHoraCadastro(), timeFormatter);

        // Pega o dia da semana da data
        DayOfWeek diaSemana = consultadto.getDataCadastro()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .getDayOfWeek();

        // Constrói LocalDateTime com data (Date) + hora (String)
        LocalDateTime dataHoraConvertida = consultadto.getDataCadastro()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atTime(horaConvertida);

        // Validação de horário de funcionamento (segunda a sábado, 7h às 18h)
        if (diaSemana == DayOfWeek.SUNDAY ||
                horaConvertida.isBefore(LocalTime.of(7, 0)) ||
                horaConvertida.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("Consulta fora do horário de funcionamento.");
        }

        // Validação de antecedência mínima de 30 minutos
        if (dataHoraConvertida.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("Consultas devem ser agendadas com 30 minutos de antecedência.");
        }

        // Validação paciente
        Paciente paciente = pacienteRepository.buscarPorId(consultadto.getPaciente().getId());
        if (!paciente.isAtivo()) {
            throw new IllegalArgumentException("Paciente inativo.");
        }

        // Verifica se o paciente já tem consulta no mesmo dia
        if (consultaRepository.existeConsultaPacienteNoDia(paciente.getId(), consultadto.getDataCadastro())) {
            throw new IllegalArgumentException("Paciente já possui uma consulta neste dia.");
        }

        Medico medico;
        if (consultadto.getMedico() != null && consultadto.getMedico().getId() != null) {
            medico = medicoRepository.buscarDadosPorId(consultadto.getMedico().getId());
            if (!medico.isAtivo()) {
                throw new IllegalArgumentException("Médico inativo.");
            }

            if (consultaRepository.medicoOcupado(medico.getId(), consultadto.getDataCadastro(),
                    consultadto.getHoraCadastro())) {
                throw new IllegalArgumentException("Médico já possui uma consulta neste horário.");
            }

        } else {
            // Seleciona médico disponível aleatoriamente
            List<Medico> disponiveis = medicoRepository.buscarMedicosDisponiveis(
                    consultadto.getDataCadastro(),
                    consultadto.getHoraCadastro());
            if (disponiveis.isEmpty()) {
                throw new IllegalArgumentException("Nenhum médico disponível no horário.");
            }
            medico = disponiveis.get(new Random().nextInt(disponiveis.size()));
        }

        Consulta novaConsulta = new Consulta(consultadto);
        consultaRepository.agendarConsulta(novaConsulta);
    }


    public void cancelarConsulta(int consultaId, String motivo) throws SQLException, NamingException {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo do cancelamento é obrigatório.");
        }

        List<String> motivosValidos = Arrays.asList("paciente desistiu", "médico cancelou", "outros");
        if (!motivosValidos.contains(motivo.toLowerCase())) {
            throw new IllegalArgumentException("Motivo inválido para cancelamento.");
        }

        consultaRepository.cancelarConsulta(consultaId, motivo);
    }
}
