package dev.aziz.librarymanagementsystem.mapper;

import dev.aziz.librarymanagementsystem.dto.ReaderRequestDto;
import dev.aziz.librarymanagementsystem.dto.ReaderResponseDto;
import dev.aziz.librarymanagementsystem.entity.Reader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReaderMapper {

    public Reader toReader(ReaderRequestDto readerRequestDto){
        Reader reader = new Reader();
        reader.setFirstName(readerRequestDto.firstName());
        reader.setLastName(readerRequestDto.lastName());
        reader.setUsername(readerRequestDto.username());
        reader.setEmail(readerRequestDto.email());
        return reader;
    }

    public ReaderResponseDto toReaderResponseDto(Reader reader){
        return new ReaderResponseDto(
                reader.getId(), reader.getFirstName(), reader.getLastName(), reader.getUsername(), reader.getEmail()
        );
    }

    public List<ReaderResponseDto> toReaderResponseDtoList(List<Reader> readers) {
        if (readers == null || readers.isEmpty()) {
            return List.of();
        }

        List<ReaderResponseDto> dtos = new ArrayList<>(readers.size());
        for (Reader reader : readers) {
            dtos.add(toReaderResponseDto(reader));
        }

        return dtos;
    }

}
