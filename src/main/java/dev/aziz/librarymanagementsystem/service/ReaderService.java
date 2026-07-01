package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.dto.ReaderRequestDto;
import dev.aziz.librarymanagementsystem.dto.ReaderResponseDto;
import dev.aziz.librarymanagementsystem.entity.Reader;
import dev.aziz.librarymanagementsystem.exception.BusinessConflictException;
import dev.aziz.librarymanagementsystem.exception.ResourceNotFoundException;
import dev.aziz.librarymanagementsystem.mapper.ReaderMapper;
import dev.aziz.librarymanagementsystem.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    public List<ReaderResponseDto> getAllReaders() {
        List<Reader> readers = readerRepository.findAll();
        return readerMapper.toReaderResponseDtoList(readers);
    }

    public ReaderResponseDto getReaderById(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", id));
        return readerMapper.toReaderResponseDto(reader);
    }

    public ReaderResponseDto createReader(ReaderRequestDto readerRequestDto) {
        if (readerRepository.existsByEmail(readerRequestDto.email())) {
            throw new BusinessConflictException("Reader with that email address already exists");
        }
        Reader reader = readerMapper.toReader(readerRequestDto);
        Reader savedReader = readerRepository.save(reader);
        return readerMapper.toReaderResponseDto(savedReader);
    }

    @Transactional
    public ReaderResponseDto updateReader(Long id, ReaderRequestDto readerRequestDto) {
        Reader foundReader = readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", id));

        if (!foundReader.getEmail().equals(readerRequestDto.email()) && readerRepository.existsByEmail(readerRequestDto.email())) {
            throw new BusinessConflictException("Reader with that email address already exists");
        }

        foundReader.setFirstName(readerRequestDto.firstName());
        foundReader.setLastName(readerRequestDto.lastName());
        foundReader.setUsername(readerRequestDto.username());
        foundReader.setEmail(readerRequestDto.email());
        foundReader.setPhoneNumber(readerRequestDto.phoneNumber());

        Reader updatedReader = readerRepository.save(foundReader);
        return readerMapper.toReaderResponseDto(updatedReader);
    }

    public void deleteReader(Long id) {
        if (!readerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reader", "id", id);
        }
        readerRepository.deleteById(id);
    }

}
