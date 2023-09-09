package ru.practicum.main_server.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_server.compilation.CompilationRepository;
import ru.practicum.main_server.compilation.model.Compilation;
import ru.practicum.main_server.compilation.model.CompilationAdminDto;
import ru.practicum.main_server.compilation.model.CompilationDto;
import ru.practicum.main_server.compilation.model.CompilationMapper;
import ru.practicum.main_server.event.EventRepository;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public Compilation create(CompilationDto compilationDto) {
        if (compilationDto.getEvents() == null) {
            compilationDto.setEvents(new ArrayList<>());
        }
        Compilation compilation = CompilationMapper.toCompilation(compilationDto);
        List<Event> events = eventRepository.findAllByIdIn(compilationDto.getEvents());
        compilation.setEvents(events);
        return compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void delete(Long compId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
        compilationRepository.deleteById(compId);
    }

    @Transactional
    @Override
    public Compilation update(Long compId, CompilationAdminDto compilationAdminDto) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
        if (compilationAdminDto.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(compilationAdminDto.getEvents());
            compilation.setEvents(events);
        }
        if (compilationAdminDto.getTitle() != null) {
            compilation.setTitle(compilationAdminDto.getTitle());
        }
        if (compilationAdminDto.getPinned() != null) {
            compilation.setPinned(compilationAdminDto.getPinned());
        }
        return compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public Collection<Compilation> findAll(Boolean pinned, Pageable pageable) {
        if (pinned != null) {
            return compilationRepository.findAllByPinned(pinned, pageable).getContent();
        } else {
            return compilationRepository.findAll(pageable).getContent();
        }
    }

    @Transactional
    @Override
    public Compilation findById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
    }
}