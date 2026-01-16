package com.moura.mapper;

import com.moura.dto.BookDTO;
import com.moura.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookDTO dto);
    BookDTO toDTO(Book book);

    List<BookDTO> toDTOList(List<Book> entities);
    List<Book> toEntityList(List<BookDTO> dtos);
}
