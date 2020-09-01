package com.levi.algorithm;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@RestController
@SpringBootApplication
public class AlgorithmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgorithmApplication.class, args);
    }

    /**
     * http://localhost:8080/search?searchKeywords=페페로니피자,피자,라지,라지사이즈&sentence=페페로니 피자 라지 사이즈 주문
     */
    @GetMapping("/search")
    public Flux<Emit> search(@RequestParam("searchKeywords") final String searchKeywords,
                             @RequestParam("sentence") final String sentence) {

        List<String> searchKeywordList = List.of(searchKeywords.split(","));

        Trie.TrieBuilder trieBuilder = Trie.builder()
                .ignoreCase();
//                .ignoreOverlaps();

        searchKeywordList.forEach(trieBuilder::addKeyword);
        Trie trie = trieBuilder.build();

        return Flux.fromIterable(trie.parseText(sentence.replaceAll(" ", "")));
    }

    @GetMapping("/trie")
    public Mono<Trie> getTrie(@RequestParam("searchKeywords") final String searchKeywords) {
        List<String> searchKeywordList = List.of(searchKeywords.split(","));

        Trie.TrieBuilder trieBuilder = Trie.builder()
                .ignoreCase();
//                .ignoreOverlaps();

        searchKeywordList.forEach(trieBuilder::addKeyword);
        return Mono.just(trieBuilder.build());
    }

}
