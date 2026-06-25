package dev.aziz.librarymanagementsystem.controller;

import dev.aziz.librarymanagementsystem.dto.ReaderRequestDto;
import dev.aziz.librarymanagementsystem.dto.ReaderResponseDto;
import dev.aziz.librarymanagementsystem.service.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/readers")
@RestController
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping
    public ResponseEntity<List<ReaderResponseDto>> getAllReaders() {
        return ResponseEntity.ok(readerService.getAllReaders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderResponseDto> getReaderById(@PathVariable Long id) {
        return ResponseEntity.ok(readerService.getReaderById(id));
    }

    @PostMapping
    public ResponseEntity<ReaderResponseDto> createReader(@RequestBody @Valid ReaderRequestDto readerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.createReader(readerRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReaderResponseDto> updateReader(
            @PathVariable Long id,
            @RequestBody @Valid ReaderRequestDto readerDto) {
        return ResponseEntity.ok(readerService.updateReader(id, readerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readerService.deleteReader(id);
        return ResponseEntity.noContent().build();
    }

}
