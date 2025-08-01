package com.library.demo.utils;

import com.library.demo.Dto.FiltroLibroDto;
import com.library.demo.Entity.Book;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LibroSpecifications {
    public static Specification<Book> byFiltro(FiltroLibroDto filtroLibroDto){
        return ((root, query, cb) -> {
            List<Predicate> p = new ArrayList<>();

            if(!StringUtils.isEmpty(filtroLibroDto.getNome())){
                p.add(cb.like(cb.lower(root.get("titolo")),
                                "%"+filtroLibroDto.getNome().toLowerCase()+"%"));
            }

            if(filtroLibroDto.getPrice() != null){
                p.add(cb.equal(root.get("price"),
                                filtroLibroDto.getPrice()));
            }

            if(filtroLibroDto.getPublishingHouseId() != null){
                p.add(cb.equal(root.get("publishing_house_id"),
                                filtroLibroDto.getPublishingHouseId()));
            }

            return cb.and(p.toArray(new Predicate[0]));
        });
    }
}
