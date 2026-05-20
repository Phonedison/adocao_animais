package org.serratec.adocao_pets.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.adocao_pets.domain.Animal;
import org.serratec.adocao_pets.domain.InteresseAdocao;
import org.serratec.adocao_pets.domain.InteresseAdocaoPK;
import org.serratec.adocao_pets.domain.Pessoa;
import org.serratec.adocao_pets.dto.request.InteresseAdocaoDTORequest;
import org.serratec.adocao_pets.dto.response.InteresseAdocaoDTOResponse;
import org.serratec.adocao_pets.exception.RecursoNaoEncontradoException;
import org.serratec.adocao_pets.repository.AnimalRepository;
import org.serratec.adocao_pets.repository.InteresseAdocaoRepository;
import org.serratec.adocao_pets.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteresseAdocaoService {

    @Autowired
    private InteresseAdocaoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public static InteresseAdocao toInteresseAdocao(InteresseAdocaoDTORequest request,
            Pessoa pessoa,
            Animal animal) {

        InteresseAdocao interesse = new InteresseAdocao();

        interesse.setPessoa(pessoa);
        interesse.setAnimal(animal);

        interesse.setId(new InteresseAdocaoPK(pessoa.getId(), animal.getId()));
        interesse.setDataPedido(request.dataPedido());
        interesse.setStatusProcesso(request.statusProcesso());
        interesse.setObservacoes(request.observacoes());

        return interesse;
    }

    public static InteresseAdocaoDTOResponse toInteresseAdocaoResponse(InteresseAdocao interesse) {
        InteresseAdocaoDTOResponse response = new InteresseAdocaoDTOResponse();

        if (interesse.getPessoa() != null)
            response.setPessoa(PessoaService.toPessoaResponse(interesse.getPessoa()));

        if (interesse.getAnimal() != null)
            response.setAnimal(AnimalService.toAnimalResponse(interesse.getAnimal()));

        response.setDataPedido(interesse.getDataPedido());
        response.setStatusProcesso(interesse.getStatusProcesso());
        response.setObservacoes(interesse.getObservacoes());

        return response;
    }

    public List<InteresseAdocaoDTOResponse> listarTodos() {
        List<InteresseAdocao> interesses = repository.findAll();
        List<InteresseAdocaoDTOResponse> responses = new ArrayList<>();

        for (InteresseAdocao interesse : interesses) {
            responses.add(toInteresseAdocaoResponse(interesse));
        }

        return responses;
    }

    public InteresseAdocaoDTOResponse buscar(Long pessoaId, Long animalId) throws RecursoNaoEncontradoException {
        InteresseAdocaoPK pk = new InteresseAdocaoPK(pessoaId, animalId);
        Optional<InteresseAdocao> opcao = repository.findById(pk);

        if (opcao.isEmpty())
            throw new RecursoNaoEncontradoException("Interesse de adoção não encontrado para os IDs informados!");

        return toInteresseAdocaoResponse(opcao.get());
    }

    public InteresseAdocaoDTOResponse salvar(InteresseAdocaoDTORequest request) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(request.pessoaId());
        if (optionalPessoa.isEmpty())
            throw new RecursoNaoEncontradoException(
                    "Pessoa com ID " + request.pessoaId() + " não encontrada no banco.");

        Optional<Animal> optionalAnimal = animalRepository.findById(request.animalId());
        if (optionalAnimal.isEmpty())
            throw new RecursoNaoEncontradoException(
                    "Animal com ID " + request.animalId() + " não encontrado no banco.");

        InteresseAdocao interesse = toInteresseAdocao(request, optionalPessoa.get(), optionalAnimal.get());

        InteresseAdocao salvo = repository.save(interesse);
        return toInteresseAdocaoResponse(salvo);
    }

    public List<InteresseAdocaoDTOResponse> salvarList(List<InteresseAdocaoDTORequest> requestList) {
        List<Long> pessoaIds = new ArrayList<>();
        List<Long> animalIds = new ArrayList<>();

        for (InteresseAdocaoDTORequest dto : requestList) {
            if (dto.pessoaId() != null && !pessoaIds.contains(dto.pessoaId()))
                pessoaIds.add(dto.pessoaId());

            if (dto.animalId() != null && !animalIds.contains(dto.animalId()))
                animalIds.add(dto.animalId());
        }

        List<Pessoa> pessoasDoBanco = pessoaRepository.findAllById(pessoaIds);
        List<Animal> animaisDoBanco = animalRepository.findAllById(animalIds);

        List<InteresseAdocao> interesses = new ArrayList<>();

        for (InteresseAdocaoDTORequest dto : requestList) {

            Pessoa pessoa = pessoasDoBanco.stream()
                    .filter(p -> p.getId().equals(dto.pessoaId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Erro no lote: A Pessoa com ID " + dto.pessoaId() + " não existe no banco de dados!"));

            Animal animal = animaisDoBanco.stream()
                    .filter(a -> a.getId().equals(dto.animalId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Erro no lote: O Animal com ID " + dto.animalId() + " não existe no banco de dados!"));

            interesses.add(toInteresseAdocao(dto, pessoa, animal));
        }

        List<InteresseAdocao> salvos = repository.saveAll(interesses);
        List<InteresseAdocaoDTOResponse> responses = new ArrayList<>();
        for (InteresseAdocao i : salvos) {
            responses.add(toInteresseAdocaoResponse(i));
        }

        return responses;
    }

    public InteresseAdocaoDTOResponse atualizar(Long pessoaId, Long animalId, InteresseAdocaoDTORequest request)
            throws RecursoNaoEncontradoException {
        InteresseAdocaoPK pk = new InteresseAdocaoPK(pessoaId, animalId);

        if (!repository.existsById(pk))
            throw new RecursoNaoEncontradoException("Interesse de adoção não encontrado para atualização!");

        request.pessoaId();
        request.animalId();

        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(pessoaId);
        if (optionalPessoa.isEmpty())
            throw new RecursoNaoEncontradoException("Pessoa não encontrada.");

        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isEmpty())
            throw new RecursoNaoEncontradoException("Animal não encontrado.");

        InteresseAdocao interesse = toInteresseAdocao(request, optionalPessoa.get(), optionalAnimal.get());
        InteresseAdocao atualizado = repository.save(interesse);
        return toInteresseAdocaoResponse(atualizado);
    }
}