
//====================================================================================
//ATENÇÃO::: PASTA ORDER EM PLANEJAMENTO |  CORRIGINDO ARQUITETURA
//====================================================================================



//package br.com.vikfastfood.api.orders.service;
//
//import br.com.vikfastfood.api.orders.dto.categoria.CategoriaAtualizarRequest;
//import br.com.vikfastfood.api.orders.dto.categoria.CategoriaDeletarRequest;
//import br.com.vikfastfood.api.orders.dto.categoria.CategoriaRequest;
//import br.com.vikfastfood.api.orders.dto.categoria.CategoriaResponse;
//import br.com.vikfastfood.api.orders.model.Categoria;
//import br.com.vikfastfood.api.orders.repository.CategoriaRepository;
//import br.com.vikfastfood.api.users.model.Estabelecimento;
//import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//@Service
//public class CategoriaService {
//    @Autowired
//    private final CategoriaRepository repository;
//    @Autowired
//    private final EstabelecimentoRepository estabelecimentoRepository;
//
//    public CategoriaService(CategoriaRepository repository, EstabelecimentoRepository estabelecimentoRepository) {
//        this.repository = repository;
//        this.estabelecimentoRepository = estabelecimentoRepository;
//    }
//
//    @Transactional
//    public CategoriaResponse criarCategoia(CategoriaRequest dto) {
//        if(repository.existsByNomeAndEstabelecimentoId(dto.nome(), dto.estabelecimentoId())){
//            throw new IllegalArgumentException("Você já tem uma categoria chamada: " + dto.nome());
//        }
//
//        Estabelecimento est = estabelecimentoRepository.findById(dto.estabelecimentoId())
//                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
//
//        Categoria categoria = new Categoria();
//
//        categoria.setNome(dto.nome());
//        categoria.setEstabelecimento(est);
//
//        Categoria salvar =  repository.save(categoria);
//
//        return new CategoriaResponse(salvar.getId(), salvar.getNome());
//    }
//
//    @Transactional
//    public CategoriaResponse atualizarCategoria(UUID id, CategoriaAtualizarRequest dto) {
//        Categoria categoriaExistente = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoria  não encontrada! "));
//
//        if(categoriaExistente.getNome().equalsIgnoreCase(dto.nome())){
//            throw new IllegalArgumentException("Categoria com o mesmo nome, Insira outro nome para categoria: " + dto.nome());
//        }
//
//        if(repository.existsByNomeAndEstabelecimentoId(dto.nome(),categoriaExistente.getEstabelecimento().getId())) {
//            throw new IllegalArgumentException("Você já possui outra categoria cadastrada com o nome: " + dto.nome());
//        }
//
//        categoriaExistente.setNome(dto.nome());
//
//        // para nao esquecer mais ::: Instancia uma nova Categoria para receber o repositorio para atualizar
//        Categoria categoriaAtualizado = repository.save(categoriaExistente);
//
//        return new CategoriaResponse(categoriaAtualizado.getId(), categoriaAtualizado.getNome());
//    }
//
//    @Transactional
//    public void deletarCategoria(CategoriaDeletarRequest request) {
//        Categoria categoriaDeletar = repository.findById(request.id()).orElseThrow(() -> new RuntimeException("Categoria nao encontrada! "));
//
//        if(!categoriaDeletar.getProdutos().isEmpty()){
//            log.warn("Tentativa de exclusão negada: Categoria '{}' (ID: {}) possui produtos ativos.",
//                    categoriaDeletar.getNome(), request.id());
//            throw new RuntimeException("A produto vinculado dentro da categoria");
//        }
//        repository.delete(categoriaDeletar);
//    }
//
//    public List<CategoriaResponse> listarCategorias(UUID estabelecimentoId) {
//        List<Categoria> categorias = repository.findAllByEstabelecimentoId(estabelecimentoId);
//        return categorias.stream()
//                .map(c -> CategoriaResponse.builder()
//                        .id(c.getId())
//                        .nome(c.getNome())
//                        .build()
//                ).toList();
//    }
//
//
//}
